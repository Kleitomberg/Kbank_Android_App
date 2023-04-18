package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.Locale;

import br.ufpe.cin.residencia.banco.conta.AdicionarContaActivity;
import br.ufpe.cin.residencia.banco.conta.ContasActivity;

public class AjustesActivity extends AppCompatActivity {
    ImageView voltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        this.voltar = findViewById(R.id.back);

        voltar.setOnClickListener(view -> {
            Intent i = new Intent(AjustesActivity.this, MainActivity.class);
            startActivity(i);
        });

        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.ajustes_container, new AjustesFragment())
            .commit();



        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals("language")) {
                        Log.i("MUDOU", "onSharedPreferenceChanged: ");
                        updateLanguageConfiguration();
                    }
                }
                private void updateLanguageConfiguration() {
                    // Atualiza a configuração de idioma de acordo com as preferências
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AjustesActivity.this);
                    String language = preferences.getString("language", "");
                    if (!language.isEmpty()) {
                        Locale locale = new Locale(language);
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.setLocale(locale);
                        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                        recreate();
                    }
                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Força a atualização da configuração de idioma
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String language = preferences.getString("language", "");
        if (!language.isEmpty()) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }
    }

    public static class AjustesFragment extends PreferenceFragmentCompat  {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.ajustes_preference, rootKey);
        }

    }

}
