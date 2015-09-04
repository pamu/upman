package com.nagarjuna_pamu.dev.uploadmanager.utils;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

/**
 * Created by pnagarjuna on 03/09/15.
 */
public class S3Uploader {

    public static interface FileProgressListener {
        public void onProgressChanged(long bytesTransferred);
        public void onCompleteUpload();
        public void onFailedUpload();
        public void onStartUpload();
    }

    public static void uploadToS3(TransferManager transferManager, final File file) throws InterruptedException {
        PutObjectRequest putObjectRequest = new PutObjectRequest("car-classifieds", file.getName(), file)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        final Upload upload = transferManager.upload(putObjectRequest);
        upload.addProgressListener(new ProgressListener() {
            @Override
            public void progressChanged(ProgressEvent progressEvent) {
                if (progressEvent.getEventCode() == ProgressEvent.COMPLETED_EVENT_CODE) {

                }
            }
        });
    }

    public static void uploadToS3(TransferManager transferManager, final File file, final FileProgressListener fileProgressListener) throws InterruptedException {
        PutObjectRequest putObjectRequest = new PutObjectRequest("car-classifieds", file.getName(), file)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        final Upload upload = transferManager.upload(putObjectRequest);
        upload.addProgressListener(new ProgressListener() {
            @Override
            public void progressChanged(ProgressEvent progressEvent) {
                fileProgressListener.onProgressChanged(progressEvent.getBytesTransferred());
                if (progressEvent.getEventCode() == ProgressEvent.COMPLETED_EVENT_CODE) {
                    fileProgressListener.onCompleteUpload();
                }
                if (progressEvent.getEventCode() == com.amazonaws.event.ProgressEvent.STARTED_EVENT_CODE) {
                    fileProgressListener.onStartUpload();
                }
                if (progressEvent.getEventCode() == com.amazonaws.event.ProgressEvent.FAILED_EVENT_CODE) {
                    fileProgressListener.onFailedUpload();
                }
            }
        });
    }

    public static TransferManager getTransferManager(Context context) {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-east-1:c2432cb5-55e1-42ec-a12f-4be2bc8abd9a",
                Regions.US_EAST_1
        );
        TransferManager transferManager = new TransferManager(credentialsProvider);
        return transferManager;
    }
}
