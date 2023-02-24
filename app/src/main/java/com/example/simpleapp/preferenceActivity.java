package com.example.simpleapp;

import static android.app.PendingIntent.getActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.simpleapp.userHandler.DBHandler;

public class preferenceActivity extends PreferenceActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        DBHandler DB = new DBHandler(preferenceActivity.this);

        Preference button = findPreference(getString(R.string.updateButton));
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                String userName = preferences.getString("nameInUse_pref", "");
                String alias = preferences.getString("alias_pref", "user");
                int age = Integer.parseInt(preferences.getString("age_pref", "0"));
                String loc = preferences.getString("location_pref", "nodata");

                DB.updateUserByName(userName, alias, age, loc);

                Toast toast = Toast.makeText(getBaseContext(), "Changes have been applied successfully", Toast.LENGTH_SHORT);
                toast.show();

                return true;
            }
        });
    }

}
/*
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("nameInUse_pref", "a");
                editor.apply();

                String s = preferences.getString("nameInUse_pref", "pp");
                System.out.println(s);
 */