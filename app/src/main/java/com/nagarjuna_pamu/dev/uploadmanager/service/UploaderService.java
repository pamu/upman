package com.nagarjuna_pamu.dev.uploadmanager.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.nagarjuna_pamu.dev.uploadmanager.R;
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
public class UploaderService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_UPLOAD = "com.nagarjuna_pamu.dev.uploadmanager.service.action.UPLOAD";

    // TODO: Rename parameters
    private static final String FILE_PATH = "com.nagarjuna_pamu.dev.uploadmanager.service.extra.FILE_PATH";
    private static final String SCRAPE_ID = "com.nagarjuna_pamu.dev.uploadmanager.service.extra.SCRAPE_ID";

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
        super("UploaderService");
    }

    @Override
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

        try {
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setContentTitle(scrapeId + " Upload");
            builder.setContentText("Uploading " + file.getName());
            builder.setSmallIcon(R.mipmap.ic_launcher);

            S3Uploader.uploadToS3(transferManager, file, new S3Uploader.FileProgressListener() {
                @Override
                public void onProgressChanged(long bytesTransferred) {
                    int percentage = (int) (bytesTransferred/file.length()) * 100;
                    builder.setProgress(100, percentage, false);
                    startForeground(1, builder.build());
                }

                @Override
                public void onCompleteUpload() {
                    FileUtils.backup(file, scrapeId);
                }

                @Override
                public void onFailedUpload() {

                }

                @Override
                public void onStartUpload() {

                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
