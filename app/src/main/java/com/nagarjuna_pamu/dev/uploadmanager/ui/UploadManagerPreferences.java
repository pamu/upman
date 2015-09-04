package com.nagarjuna_pamu.dev.uploadmanager.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.nagarjuna_pamu.dev.uploadmanager.R;

/**
 * Created by pnagarjuna on 04/09/15.
 */
public class UploadManagerPreferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
