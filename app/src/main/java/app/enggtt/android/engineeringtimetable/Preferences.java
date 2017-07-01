package app.enggtt.android.engineeringtimetable;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.sql.SQLException;
import java.util.Calendar;


/**
 * Created by Ibrahimkb on 28-12-2015.
 */
public class Preferences extends PreferenceActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final ListPreference lp = (ListPreference) findPreference("widget_columns");
        final ListPreference clp = (ListPreference) findPreference("widget_theme");
        String value = sp.getString("widget_columns","Four");
        String cvalue = sp.getString("widget_theme","Blue");
        lp.setSummary(value);
        clp.setSummary(cvalue);

        lp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                Toast.makeText(Preferences.this, "Remove and Replace the widget on the homescreen to apply the changes", Toast.LENGTH_LONG).show();

                lp.setSummary(newValue.toString());

                return true;
            }
        });
        clp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                Toast.makeText(Preferences.this, "Remove and Replace the widget on the homescreen to apply the changes", Toast.LENGTH_LONG).show();

                clp.setSummary(newValue.toString());

                return true;
            }
        });


    }

}


