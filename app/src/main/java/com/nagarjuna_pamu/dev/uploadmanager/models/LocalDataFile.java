package com.nagarjuna_pamu.dev.uploadmanager.models;

import java.io.File;

/**
 * Created by pnagarjuna on 03/09/15.
 */
public class LocalDataFile implements LocalFilesItem {
    private File file;
    private String scrapeId;

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
