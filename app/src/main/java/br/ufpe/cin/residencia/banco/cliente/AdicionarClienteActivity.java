package br.ufpe.cin.residencia.banco.cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import br.ufpe.cin.residencia.banco.R;
import br.ufpe.cin.residencia.banco.conta.AdicionarContaActivity;
import br.ufpe.cin.residencia.banco.conta.ContaViewModel;
import br.ufpe.cin.residencia.banco.conta.ContasActivity;

public class AdicionarClienteActivity extends AppCompatActivity {

    ClienteViewModel viewModel;

    ImageView voltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cliente);

        viewModel = new ViewModelProvider(this).get(ClienteViewModel.class);


        EditText campoNome = findViewById(R.id.nome);
        EditText campoCPF = findViewById(R.id.cpf);
        Button btnAtualizar = findViewById(R.id.btnAtualizarCliente);
        Button btnRemover = findViewById(R.id.btnRemoverCliente);

        RadioGroup sexoCliente = findViewById(R.id.sexoRadioGroup);

        //removendo as labels no form de adicionr

        TextView labelNome = findViewById(R.id.label_nome_cliente);
        TextView labelCpf = findViewById(R.id.label_cpf_cliente);

        labelNome.setVisibility(View.GONE);
        labelCpf.setVisibility(View.GONE);

        btnAtualizar.setText(R.string.btn_add_cliente);


        btnRemover.setVisibility(View.GONE);

        this.voltar = findViewById(R.id.back);

        voltar.setOnClickListener(view -> {
            Intent i = new Intent(AdicionarClienteActivity.this, ClientesActivity.class);
            startActivity(i);
        });

        //ação após clicar em adicionar cliente

        btnAtualizar.setOnClickListener(view -> {
            // logica de adicionar usando o viewModel

            String clienteNome = campoNome.getText().toString();
            String clienteCpf = campoCPF.getText().toString();

            int radioSelecionado = sexoCliente.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(radioSelecionado);

            String clienteSexo = radioButton.getText().toString();

            if (clienteNome.isEmpty()){
                Toast.makeText(this, "Nome é obrigatorio", Toast.LENGTH_SHORT).show();
                return; // interrompe a execução do método
            }

            if (clienteSexo.isEmpty()){
                Toast.makeText(this, "Sexo é obrigatorio", Toast.LENGTH_SHORT).show();
                return; // interrompe a execução do método
            }

            if (clienteCpf.isEmpty() || !onlyNumeric(clienteCpf)){
                Toast.makeText(this, "Infome apenas numeros!", Toast.LENGTH_SHORT).show();
                return; // interrompe a execução do método
            }

            Log.i("CLIENTE", "DADOS: " + clienteSexo + clienteCpf + clienteNome);

            Cliente c = new Cliente(clienteCpf,clienteNome,clienteSexo);

            viewModel.inserir(c);

            finish();
            Toast.makeText(this, "Cliente Adicionado com sucessso!", Toast.LENGTH_SHORT).show();


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
