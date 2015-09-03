package com.nagarjuna_pamu.dev.uploadmanager.models;

/**
 * Created by pnagarjuna on 03/09/15.
 */
public class UploadDataFile implements UploadFilesItem {
    private String path;
    private String scrapeId;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getScrapeId() {
        return scrapeId;
    }

    public void setScrapeId(String scrapeId) {
        this.scrapeId = scrapeId;
    }
}
