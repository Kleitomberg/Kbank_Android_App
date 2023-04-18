package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//Ver anotações TODO no código
public class TransferirActivity extends AppCompatActivity {

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

        valorOperacao.setHint(valorOperacao.getHint() + " transferido");

        tipoOperacao.setText(R.string.realizar_opera_o);
        btnOperacao.setText(R.string.transferencia_txt);


        this.voltar = findViewById(R.id.back);
        voltar.setOnClickListener(view -> {
            Intent i = new Intent(TransferirActivity.this, MainActivity.class);
            startActivity(i);
        });

        btnOperacao.setOnClickListener(
                v -> {
                    String numOrigem = numeroContaOrigem.getText().toString();
                    String numDestino = numeroContaDestino.getText().toString();
                    //TODO lembrar de implementar validação dos números das contas e do valor da operação, antes de efetuar a operação de transferência.
                    // O método abaixo está sendo chamado, mas precisa ser implementado na classe BancoViewModel para funcionar.
                    double valor = Double.valueOf(valorOperacao.getText().toString());
                    viewModel.transferir(numOrigem, numDestino, valor);
                    finish();
                    if(BancoViewModel.status){
                        //Log.i("Transferencia", "Transferencia realizada com sucesso!");
                        Toast.makeText(this, "Transferência realizada com sucesso!", Toast.LENGTH_SHORT).show();

                    }else {
                        viewModel.erroMsg.observe(this, errorMsg -> {
                            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                            //Log.i("Conta não Creditada", "mensagem: " + errorMsg);
                        });
                    }
                }
        );

    }
}
