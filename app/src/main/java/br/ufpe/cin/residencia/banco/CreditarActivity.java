package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.ufpe.cin.residencia.banco.conta.AdicionarContaActivity;
import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContasActivity;

//Ver anotações TODO no código
public class CreditarActivity extends AppCompatActivity {
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
        valorOperacao.setHint(valorOperacao.getHint() + " creditado");


        tipoOperacao.setText(R.string.creditar_txt);
        btnOperacao.setText(R.string.creditar_txt);



        this.voltar = findViewById(R.id.back);

        voltar.setOnClickListener(view -> {
            Intent i = new Intent(CreditarActivity.this, MainActivity.class);
            startActivity(i);
        });


        btnOperacao.setOnClickListener(
                v -> {
                    String numOrigem = numeroContaOrigem.getText().toString();
                    //TODO lembrar de implementar validação do número da conta e do valor da operação, antes de efetuar a operação de crédito.
                    // O método abaixo está sendo chamado, mas precisa ser implementado na classe BancoViewModel para funcionar.
                    double valor = Double.valueOf(valorOperacao.getText().toString());

                    // validar número da conta e valor da operação
                    if (numOrigem.isEmpty()) {
                        Toast.makeText(this, "Número da conta não pode ser vazio.", Toast.LENGTH_SHORT).show();

                    }
                    else if (valor <= 0) {
                        Toast.makeText(this, "Valor da operação deve ser maior que zero.", Toast.LENGTH_SHORT).show();

                    }else {
                            viewModel.creditar(numOrigem, valor);
                            finish();
                            if(BancoViewModel.status){
                               // Log.i("Conta creditada", "Conta creditada");
                                Toast.makeText(this, "Valor creditado com sucesso!", Toast.LENGTH_SHORT).show();

                            }else {
                                viewModel.erroMsg.observe(this, errorMsg -> {
                                    Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                                    //Log.i("Conta não Creditada", "mensagem: " + errorMsg);
                                });

                            }
                    }
                }
        );
    }
}
