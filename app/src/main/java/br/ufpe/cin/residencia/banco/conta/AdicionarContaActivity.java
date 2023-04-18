package br.ufpe.cin.residencia.banco.conta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.residencia.banco.MainActivity;
import br.ufpe.cin.residencia.banco.R;
import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.ClienteRepository;
import br.ufpe.cin.residencia.banco.cliente.ClienteViewModel;

//Ver anotações TODO no código
public class AdicionarContaActivity extends AppCompatActivity {

    ContaViewModel viewModel;

    Cliente atualCliente;
    List<Cliente> clientes;
    ClienteViewModel clienteViewModel;
    ImageView voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_conta);

        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);
        clienteViewModel = new ViewModelProvider(this).get(ClienteViewModel.class);

        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        Button btnRemover = findViewById(R.id.btnRemover);
        //EditText campoNome = findViewById(R.id.nome);
        EditText campoNumero = findViewById(R.id.numero);
        EditText campoCPF = findViewById(R.id.cpf);
        EditText campoSaldo = findViewById(R.id.saldo);



        //TextView labelNome = findViewById(R.id.label_nome);
        TextView labelNumero = findViewById(R.id.label_numero);
        TextView labelCpf = findViewById(R.id.label_cpf);
        TextView labelSaldo = findViewById(R.id.label_saldo);

        labelNumero.setVisibility(View.GONE);
       // labelNome.setVisibility(View.GONE);
        labelCpf.setVisibility(View.GONE);
        labelSaldo.setVisibility(View.GONE);

        ///btnAtualizar.setText("Adicionar");
        btnAtualizar.setText(R.string.adicionar_conta_txt_btn);
        btnRemover.setVisibility(View.GONE);



        this.voltar = findViewById(R.id.back);

        voltar.setOnClickListener(view -> {
            Intent i = new Intent(AdicionarContaActivity.this, ContasActivity.class);
            startActivity(i);
        });

        btnAtualizar.setOnClickListener(
                v -> {
                    //String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String numeroConta = campoNumero.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();



                    //TODO: Incluir validações aqui, antes de criar um objeto Conta (por exemplo, verificar que digitou um nome com pelo menos 5 caracteres, que o campo de saldo tem de fato um número, assim por diante). Se todas as validações passarem, aí sim cria a Conta conforme linha abaixo.

                    //validação do nome

                    //validação do cpf
                   if(cpfCliente.isEmpty() || cpfCliente.length() < 11 || !onlyNumeric(cpfCliente)){
                        Toast.makeText(this, "Infome Cpf valido", Toast.LENGTH_SHORT).show();
                       return; // interrompe a execução do método
                    }

                    //validação numero da conta

                    if (numeroConta.isEmpty() || !onlyNumeric(numeroConta)){
                        Toast.makeText(this, "Não pode ser vazio e  apenas numeros!", Toast.LENGTH_SHORT).show();
                        return; // interrompe a execução do método
                    }

                    //validação do saldo, se der erro na conversão da string para double o valor n é numerico
                    try {
                        Double.parseDouble(saldoConta);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Saldo deve ser um número válido", Toast.LENGTH_SHORT).show();
                        return; // interrompe a execução do método
                    }

                    //dando tudo certo ele cria a conta e salva

                    // verifica se existe um cliente com o cpf informado

                    this.clienteViewModel.buscarpeloCpf(cpfCliente);

                    this.clienteViewModel.clienteAtual.observe(this, cliente -> {
                        this.atualCliente = cliente;
                        if (this.atualCliente != null) {
                            Conta c = new Conta(numeroConta, Double.valueOf(saldoConta), this.atualCliente.nome, this.atualCliente.cpf);
                            viewModel.inserir(c);
                            finish();
                            Toast.makeText(this, "Conta Adicionada com sucessso!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(this, "Não existe um Cliente com esse CPF", Toast.LENGTH_SHORT).show();
                        }
                    });





                }
        );

    }
    //verifica se o valor passado contem apenas numeros
    private boolean onlyNumeric(String value) {
        // Verifica se a string é nula ou vazia
        if (value == null || value.isEmpty()) {
            return false;
        }

        // Verifica se cada caractere da string é um dígito numérico
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }

        // Se chegou até aqui, significa que todos os caracteres são dígitos numéricos
        return true;
    }
}
