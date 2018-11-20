package com.example.user.proyectoandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class PreferencesActivity extends AppCompatActivity {

    protected String nombrePreferences, usuarioPreferences, fechaPreferences, generoPreferences;

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //Buscamos el archivo xml de las settings
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferencesActivity.SettingsFragment()).commit();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent intent = getIntent();

            intent.putExtra("name", nombrePreferences);
            intent.putExtra("username", usuarioPreferences);
            intent.putExtra("date", fechaPreferences);
            intent.putExtra("genre", generoPreferences);
            setResult(RESULT_OK, intent);
            finish();
        }
        return true;
    }

}
