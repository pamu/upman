package com.nagarjuna_pamu.dev.uploadmanager.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.nagarjuna_pamu.dev.uploadmanager.R;

import java.util.UUID;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

/**
 * Created by pnagarjuna on 04/09/15.
 */
public class UploadManagerPreferences extends PreferenceActivity implements PreferenceChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void preferenceChange(PreferenceChangeEvent preferenceChangeEvent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pass", UUID.fromString(sharedPreferences.getString("user", UUID.randomUUID().toString())).toString());
    }
}
