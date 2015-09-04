package com.nagarjuna_pamu.dev.uploadmanager;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.nagarjuna_pamu.dev.uploadmanager.utils.FileUtils;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testInspection() throws Throwable {
        System.out.println("inspection folder");
        assertEquals(FileUtils.getInspectorDir().getName() + "/", FileUtils.INSPECTOR_DIR);
    }


    public void testBackup() throws Throwable {
        System.out.println("backup folder");
        assertEquals(FileUtils.getBackupDir().getName() + "/", FileUtils.INSPECTOR_BACKUP_DIR);
    }
}