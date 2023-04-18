package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//Ver anotações TODO no código
public class DebitarActivity extends AppCompatActivity {
    BancoViewModel viewModel;
    ImageView voltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacoes);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        TextView tipoOperacao = findViewById(R.id.tipoOperacao);
        EditText numeroContaOrigem = findViewById(R.id.numeroContaOrigem);
        TextView labelContaDestino = findViewById(R.id.labelContaDestino);
        EditText numeroContaDestino = findViewById(R.id.numeroContaDestino);
        EditText valorOperacao = findViewById(R.id.valor);
        Button btnOperacao = findViewById(R.id.btnOperacao);

        labelContaDestino.setVisibility(View.GONE);
        numeroContaDestino.setVisibility(View.GONE);

        valorOperacao.setHint(valorOperacao.getHint() + " debitado");

        tipoOperacao.setText(R.string.debitar_txt);
        btnOperacao.setText(R.string.debitar_txt);


        this.voltar = findViewById(R.id.back);
        voltar.setOnClickListener(view -> {
            Intent i = new Intent(DebitarActivity.this, MainActivity.class);
            startActivity(i);
        });

        btnOperacao.setOnClickListener(
                v -> {
                    String numOrigem = numeroContaOrigem.getText().toString();
                    //TODO lembrar de implementar validação do número da conta e do valor da operação, antes de efetuar a operação de débito.
                    // O método abaixo está sendo chamado, mas precisa ser implementado na classe BancoViewModel para funcionar.
                    double valor = Double.valueOf(valorOperacao.getText().toString());
                    viewModel.debitar(numOrigem, valor);

                    if(BancoViewModel.status){
                        //Log.i("Conta debitada", "Debito realizado com sucesso!");
                        Toast.makeText(getApplicationContext(), "Valor debitado com sucesso!", Toast.LENGTH_SHORT).show();

                    }else {
                        viewModel.erroMsg.observe(this, errorMsg -> {
                            Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
                            //Log.i("Conta não Creditada", "mensagem: " + errorMsg);
                        });
                    }
                    finish();
                }
        );
    }
}
