package com.nagarjuna_pamu.dev.uploadmanager.utils;

import android.os.Environment;
import android.util.Log;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnagarjuna on 01/09/15.
 */
public class FileUtils {
    public final static String INSPECTOR_DIR = "Zoomo-Inspector/";
    public final static String INSPECTOR_BACKUP_DIR = "Zoomo-Inspector-Backup/";

    public static File getInspectorDir() {
        File inspectorDir = new File(Environment.getExternalStorageDirectory(), INSPECTOR_DIR);
        if (! inspectorDir.exists()) {
            inspectorDir.mkdirs();
        }
        return inspectorDir;
    }

    public static File getBackupDir() {
        File backupDir = new File(Environment.getExternalStorageDirectory(), INSPECTOR_BACKUP_DIR);
        if (! backupDir.exists()) {
            backupDir.mkdirs();
        }
        return backupDir;
    }

    public static File getLeadBackup(final String scrapeId) {
        File leadDir = new File(getBackupDir().getAbsolutePath() + "/" + scrapeId);
        if (! leadDir.exists()) {
            leadDir.mkdirs();
        }
        return leadDir;
    }

    public static void backup(final File file, String scrapeId) {
        File movedFile = new File(getLeadBackup(scrapeId) + "/" + file.getName());
        if (movedFile.exists()) {
            try {
                org.apache.commons.io.FileUtils.moveFile(file, movedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        List<File> images = new ArrayList<>();
        for(File file : getFilesFrom(rootDir)) {
            Log.d("files", "file " + file.getName());
            if (FilenameUtils.getExtension(file.getAbsolutePath()).equals("jpeg")) {
                images.add(file);
            }
        }
        if (images.isEmpty()) return new File[0];
        else return images.toArray(new File[images.size()]);
    }

    public static File[] getLeads() {
        List<File> fileList = new ArrayList<>();
        File inspectorDir = getInspectorDir();
        for(File file : inspectorDir.listFiles()) {
            if (file.isDirectory()) {
                fileList.add(file);
            }
        }
        return fileList.toArray(new File[fileList.size()]);
    }

    public static File[] getLeadsFromBackup() {
        List<File> fileList = new ArrayList<>();
        File backupDir = getBackupDir();
        for(File file : backupDir.listFiles()) {
            if (file.isDirectory()) {
                fileList.add(file);
            }
        }
        return fileList.toArray(new File[fileList.size()]);
    }

    public static File[] getUploadedFiles(final File leadDir) {
        List<File> backedupFiles = new ArrayList<>();
        for(File file : backedupFiles) {
            if (file.isFile()) {
                backedupFiles.add(file);
            }
        }
        return backedupFiles.toArray(new File[backedupFiles.size()]);
    }

    public static JSONObject getInspectorDetails(final File jsonFile) {
        try {
            FileInputStream fis = new FileInputStream(jsonFile);
            FileChannel fileChannel = fis.getChannel();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY,
                    0, fileChannel.size());
            String str = Charset.defaultCharset().decode(mappedByteBuffer).toString();
            JSONObject jsonObject = new JSONObject(str);
            return jsonObject;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
