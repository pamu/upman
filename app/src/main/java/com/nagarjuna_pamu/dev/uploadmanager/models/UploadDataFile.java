package com.nagarjuna_pamu.dev.uploadmanager.models;

import java.io.File;

/**
 * Created by pnagarjuna on 03/09/15.
 */
public class UploadDataFile implements UploadFilesItem {
    private File file;
    private String scrapeId;
    private boolean isChecked = false;
    private boolean deletable = false;

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getScrapeId() {
        return scrapeId;
    }

    public void setScrapeId(String scrapeId) {
        this.scrapeId = scrapeId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
