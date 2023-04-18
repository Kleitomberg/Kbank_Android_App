package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import br.ufpe.cin.residencia.banco.cliente.ClientesActivity;
import br.ufpe.cin.residencia.banco.conta.ContasActivity;

//Ver anotações TODO no código
public class MainActivity extends AppCompatActivity {
    BancoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        // Verifica se o idioma foi alterado nas configurações
        // Monitora se ouve mudança no idioma e chama o metodo que altera  o idiomaa
        // Listener para a mudança de idioma nas preferências
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals("language")) {
                        updateLanguageConfiguration();

                    }
                }
            });
        setContentView(R.layout.activity_main);

        //String theme = prefs.getString("theme", "light");

        //if (theme.equals("dark")) {
            //setTheme(R.style.Theme_MyApp_Dark);
        //} else {
           /// setTheme(R.style.Theme_MyApp_Light);
        //}

        ImageView eye = findViewById(R.id.show_valor);
        CardView contas = findViewById(R.id.btnContas);
        CardView clientes = findViewById(R.id.btnClientes);
        CardView transferir = findViewById(R.id.btnTransferir);
        CardView debitar = findViewById(R.id.btnDebitar);
        CardView creditar = findViewById(R.id.btnCreditar);
        CardView ajustes = findViewById(R.id.btnSettings);
        ImageView pesquisar = findViewById(R.id.btnPesquisar);
        TextView totalBanco = findViewById(R.id.totalDinheiroBanco);

        //Remover a linha abaixo se for implementar a parte de Clientes
        //clientes.setEnabled(false);



        eye.setOnClickListener(view -> {
            //String atual = getResources().getDrawable(R.drawable.ic_open_eyes).getConstantState()); //descontinuado no 29

            String tag = (String) view.getTag();
            if (tag.equals("open")) {
                eye.setImageResource(R.drawable.ic_closed_eyes);
                view.setTag("closed");
                totalBanco.setVisibility(View.INVISIBLE);
            } else {
                //view.setImageResource();
                eye.setImageResource(R.drawable.ic_open_eyes);
                view.setTag("open");
                totalBanco.setVisibility(View.VISIBLE);
            }

        });

        contas.setOnClickListener(
                v -> startActivity(new Intent(this, ContasActivity.class))
        );
        clientes.setOnClickListener(
                v -> startActivity(new Intent(this, ClientesActivity.class))
        );
        transferir.setOnClickListener(
                v -> startActivity(new Intent(this, TransferirActivity.class))
        );
        creditar.setOnClickListener(
                v -> startActivity(new Intent(this, CreditarActivity.class))
        );
        debitar.setOnClickListener(
                v -> startActivity(new Intent(this, DebitarActivity.class))
        );
        pesquisar.setOnClickListener(
                v -> startActivity(new Intent(this, PesquisarActivity.class))
        );

        ajustes.setOnClickListener(
            v -> startActivity(new Intent(this, AjustesActivity.class))
        );


        // monitora alterações nas contas e sempre que ouver chama a função que calcula o valor total pega esse valor e exibi na main
        viewModel.contas.observe(this, contas1 -> {
            double somaSaldo = viewModel.calcularValorTotal();
            totalBanco.setText("R$ "+ somaSaldo+"0");
            //Log.i("VALOR TOTAL", "VALOR TOTAL: "+ somaSaldo);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Verifica se o idioma foi alterado nas configurações
        // Monitora se ouve mudança no idioma e chama o metodo que altera  o idiomaa
        // Listener para a mudança de idioma nas preferências
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals("language")) {
                        updateLanguageConfiguration();

                    }
                }
            });
    }

    //metodo que altera o idioma e recria a tela para refletir as mudanças
    private void updateLanguageConfiguration() {

        // Força a atualização da configuração de idioma
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
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

    //TODO Neste arquivo ainda falta a atualização automática do valor total de dinheiro armazenado no banco
}
