package com.nagarjuna_pamu.dev.uploadmanager.utils;

import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnagarjuna on 01/09/15.
 */
public class FileUtils {
    public final static String INSPECTOR_DIR = "Zoomo-Inspector/";

    public static File getInspectorDir() {
        File inspectorDir = new File(Environment.getExternalStorageDirectory(), INSPECTOR_DIR);
        return inspectorDir;
    }

    public static File getFileFrom(final File rootDir, final String fileName) {
        File file = new File(rootDir.getAbsolutePath() + "/" + fileName);
        return file;
    }

    public static File getJsonFile(final String scrapeId) {
        return getFileFrom(getInspectorDir(), scrapeId.trim() + ".json");
    }

    public static File getImagesDir(final String scrapeId) {
        return getFileFrom(getInspectorDir(), "/" + scrapeId.trim());
    }

    public static File[] getFilesFrom(final File rootDir) {
        if (! rootDir.exists()) return new File[0];
        File[] files = rootDir.listFiles();
        List<File> fileList = new ArrayList<File>();
        for(File file: files) {
            if (file.isFile()) {
                fileList.add(file);
            }
        }
        if (fileList.isEmpty()) return new File[0];
        else return fileList.toArray(new File[fileList.size()]);
    }

    public static File[] getJpegsFrom(final File rootDir) {
        List<File> images = new ArrayList<File>();
        for(File file : getFilesFrom(rootDir)) {
            if (file.getName().split(".")[1].trim().equals("jpeg")) {
                images.add(file);
            }
        }
        if (images.isEmpty()) return new File[0];
        else return images.toArray(new File[images.size()]);
    }

    public static String getDataPacked(final String scrapeId) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("jsonFile", getJsonFile(scrapeId).getAbsolutePath());
            JSONArray jarr = new JSONArray();
            for(File file : getJpegsFrom(getImagesDir(scrapeId))) {
                jarr.put(file.getAbsolutePath());
            }
            jo.put("images", jarr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo.toString();
    }
}
