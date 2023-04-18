package br.ufpe.cin.residencia.banco.conta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.ufpe.cin.residencia.banco.R;
import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.ClienteViewModel;

//Ver anotações TODO no código
public class EditarContaActivity extends AppCompatActivity {

    public static final String KEY_NUMERO_CONTA = "numeroDaConta";
    ContaViewModel viewModel;

    ClienteViewModel clienteViewModel;
    Conta currentConta;
    ContaComCliente atualContCliente;
    ImageView voltar;

    Cliente atualCliente;

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
        TextView title = findViewById(R.id.text_title);

        //title.setText("Atualizar conta");
        title.setText(R.string.txt_autalizar_conta);
        campoNumero.setEnabled(false);

        Intent i = getIntent();
        String numeroConta = i.getStringExtra(KEY_NUMERO_CONTA);

        this.voltar = findViewById(R.id.back);

        voltar.setOnClickListener(view -> {
            finish();
        });

        //campoNumero.setText(numeroConta);
        //TODO usar o número da conta passado via Intent para recuperar informações da conta

        viewModel.buscarPeloNumero(numeroConta); //chama o metodo do vm que roda em segundo plano e popula o live Data de conta

        //após o codigo a cima ser executado, teoricamente já temos um valor na variavel de conta atual que podemos observar
        //e utilziar para popular os inputs
        viewModel.contaAtual.observe(this, conta -> {
            campoCPF.setText(conta.cpfCliente);
            //campoNome.setText(conta.nomeCliente);
            String saldo = String.valueOf(conta.saldo);
            campoSaldo.setText(saldo);
            campoNumero.setText(conta.numero);
            this.currentConta = conta;

        });

        //btnAtualizar.setText("Salvar Alterações");
        btnAtualizar.setText(R.string.btn_salvar);

        btnAtualizar.setOnClickListener(
                v -> {
                    //String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();

                    //TODO: Incluir validações aqui, antes de criar um objeto Conta. Se todas as validações passarem, aí sim monta um objeto Conta.
                    //validação do nome

                   // if (nomeCliente.trim().length() < 5 || nomeCliente.isEmpty()) {
                       // Toast.makeText(this, "Nome deve ter pelo menos 5 caracteres", Toast.LENGTH_SHORT).show();
                       // return; // interrompe a execução do método
                   // }

                    //validação do cpf
                    if(cpfCliente.isEmpty() || cpfCliente.length() < 11 || !onlyNumeric(cpfCliente)){
                        Toast.makeText(this, "Infome Cpf valido", Toast.LENGTH_SHORT).show();
                        return; // interrompe a execução do método
                    }
                    //validação do saldo, se der erro na conversão da string para double o valor n é numerico
                    try {
                        Double.parseDouble(saldoConta);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Saldo deve ser um número válido", Toast.LENGTH_SHORT).show();
                        return; // interrompe a execução do método
                    }

                    this.clienteViewModel.buscarpeloCpf(cpfCliente);

                    //TODO: chamar o método que vai atualizar a conta no Banco de Dados
                    this.clienteViewModel.clienteAtual.observe(this, cliente -> {
                        this.atualCliente = cliente;
                        if (this.atualCliente != null) {
                            Conta c = new Conta(numeroConta, Double.valueOf(saldoConta), this.atualCliente.nome, this.atualCliente.cpf);
                            viewModel.atualizar(c);
                            finish();
                            Toast.makeText(this, "Conta atualizada com sucessso!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(this, "Não existe um Cliente com esse CPF", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
        );

        btnRemover.setOnClickListener(v -> {
            //TODO implementar remoção da conta
            viewModel.remover(currentConta);
            finish();
            Toast.makeText(this, "Conta removida com sucesso!", Toast.LENGTH_SHORT).show();
        });
    }

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
