package com.nagarjuna_pamu.dev.uploadmanager.service;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.nagarjuna_pamu.dev.uploadmanager.R;
import com.nagarjuna_pamu.dev.uploadmanager.ui.MainActivity;
import com.nagarjuna_pamu.dev.uploadmanager.utils.FileUtils;
import com.nagarjuna_pamu.dev.uploadmanager.utils.S3Uploader;

import java.io.File;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UploaderService extends Service {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_UPLOAD = "com.nagarjuna_pamu.dev.uploadmanager.service.action.UPLOAD";

    // TODO: Rename parameters
    private static final String FILE_PATH = "com.nagarjuna_pamu.dev.uploadmanager.service.extra.FILE_PATH";
    private static final String SCRAPE_ID = "com.nagarjuna_pamu.dev.uploadmanager.service.extra.SCRAPE_ID";

    private ServiceCallBack serviceCallBack;
    int counter = 0;

    private volatile Looper mServiceLooper;
    private volatile ServiceHandler mServiceHandler;
    private String name;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            onHandleIntent((Intent)msg.obj);
        }
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionUpload(Context context, String filePath, String scrapeId) {
        Intent intent = new Intent(context, UploaderService.class);
        intent.setAction(ACTION_UPLOAD);
        intent.putExtra(FILE_PATH, filePath);
        intent.putExtra(SCRAPE_ID, scrapeId);
        context.startService(intent);
    }

    public UploaderService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("Service[" + name + "]");
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    public class LocalBinder extends Binder {
        public UploaderService getServiceInstance() {
            return UploaderService.this;
        }
    }

    public void register(MainActivity mainActivity) {
        serviceCallBack = mainActivity;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        mServiceHandler.sendMessage(msg);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mServiceLooper.quit();
    }

    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPLOAD.equals(action)) {
                final String filePath = intent.getStringExtra(FILE_PATH);
                final String scrapeId = intent.getStringExtra(SCRAPE_ID);
                handleActionUpload(filePath, scrapeId);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpload(String filePath, final String scrapeId) {
        // TODO: Handle action Foo
        TransferManager transferManager = S3Uploader.getTransferManager(getApplicationContext());
        final File file = new File(filePath);
        if (! file.isFile()) {
            Log.e("error", "File for upload is a directory");
            return;
        }

        Log.d("upload", "in handle action upload file: " + filePath + " scrapeId " +scrapeId);
        try {

            //final NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(NOTIFICATION_SERVICE);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

            final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setContentTitle("Upload " + scrapeId);
            builder.setContentText("Uploading " + file.getName());
            builder.setSmallIcon(R.drawable.ic_upload);
            builder.setContentIntent(pendingIntent);

            final int r = counter + 1;

            S3Uploader.uploadToS3(transferManager, file, new S3Uploader.FileProgressListener() {
                long total = 0;
                @Override
                public void onProgressChanged(long bytesTransferred) {
                    Log.d("upload", "bytes transfered " + bytesTransferred + "file length " + file.length());
                    total += bytesTransferred;
                    int percentage = (int) (((double)total/(double)file.length()) * 100);
                    Log.d("upload", "progress " + percentage);
                    builder.setProgress(100, percentage, false);
                    startForeground(r, builder.build());
                    //notificationManager.notify(r, builder.build());
                }

                @Override
                public void onCompleteUpload() {
                    FileUtils.backup(file, scrapeId);
                    Log.d("upload", "complete");
                    builder.setContentTitle("Upload Finished");
                    builder.setContentText("uploaded " + file.getName());
                    builder.setProgress(0, 0, false);
                    startForeground(r, builder.build());
                    //notificationManager.notify(r, builder.build());
                    serviceCallBack.onComplete();
                }

                @Override
                public void onFailedUpload() {
                    Log.d("upload", "failed");
                    builder.setContentTitle("Upload Failed");
                    builder.setContentText("uploaded failed for file " + file.getName());
                    builder.setProgress(0, 0, false);
                    startForeground(r, builder.build());
                    //notificationManager.notify(r, builder.build());
                }

                @Override
                public void onStartUpload() {
                    Log.d("upload", "upload started");
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public interface ServiceCallBack {
        public void onComplete();
    }

}
