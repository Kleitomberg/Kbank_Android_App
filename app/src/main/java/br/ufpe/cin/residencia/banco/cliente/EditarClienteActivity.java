package br.ufpe.cin.residencia.banco.cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import br.ufpe.cin.residencia.banco.R;
import br.ufpe.cin.residencia.banco.conta.ContaRepository;
import br.ufpe.cin.residencia.banco.conta.ContaViewModel;
import br.ufpe.cin.residencia.banco.conta.ContasActivity;
import br.ufpe.cin.residencia.banco.conta.EditarContaActivity;

public class EditarClienteActivity extends AppCompatActivity {

    public static final String KEY_CPF_CLIENTE = "cpfCliente";

    ClienteViewModel viewModel;

    Cliente clienteAtual;

    ImageView voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adicionar_cliente);

        Button btnAtualizar = findViewById(R.id.btnAtualizarCliente);
        Button btnRemover = findViewById(R.id.btnRemoverCliente);
        EditText campoNome = findViewById(R.id.nome);
        EditText campoCPF = findViewById(R.id.cpf);
        RadioGroup campoSexo = findViewById(R.id.sexoRadioGroup);

        TextView title = findViewById(R.id.text_title);
        title.setText(R.string.btn_update_cliente);

        btnAtualizar.setText(R.string.btn_update_cliente);
        campoCPF.setEnabled(false);

        viewModel = new ViewModelProvider(this).get(ClienteViewModel.class);


        //pega identificador unico do cliente
        Intent i = getIntent();
        String cpfClienteAtual = i.getStringExtra(KEY_CPF_CLIENTE);

        viewModel.buscarpeloCpf(cpfClienteAtual); //chama o metodo do vm que roda em segundo plano e popula o live Data de conta

        //após o codigo a cima ser executado, teoricamente já temos um valor na variavel de conta atual que podemos observar
        //e utilziar para popular os inputs
        viewModel.clienteAtual.observe(this, cliente -> {
            campoCPF.setText(cliente.cpf);
            campoNome.setText(cliente.nome);
            this.clienteAtual = cliente;
            if (cliente.sexo.equals("Masculino") || cliente.sexo.equals("Male")) {
                campoSexo.check(R.id.masculinoRadioButton); // seleciona o RadioButton "Masculino"
            } else {
                campoSexo.check(R.id.femininoRadioButton); // seleciona o RadioButton "Feminino"
            }

        });

        //salvar Edição
        btnAtualizar.setOnClickListener(view -> {

            int radioSelecionado = campoSexo.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(radioSelecionado);

            String clienteNome = campoNome.getText().toString();
            String clienteCpf = campoCPF.getText().toString();
            String clienteSexo = radioButton.getText().toString();

            if (clienteNome.isEmpty()){
                Toast.makeText(this, "Nome é obrigatorio", Toast.LENGTH_SHORT).show();
                //Log.i("CLIENTE ERROR NOME", "DADOS: " + clienteSexo + clienteCpf + clienteNome);
                return; // interrompe a execução do método
            }

            if (clienteSexo.isEmpty()){
                Toast.makeText(this, "Sexo é obrigatorio", Toast.LENGTH_SHORT).show();
                //Log.i("CLIENTE ERROR SEXO", "DADOS: " + clienteSexo + clienteCpf + clienteNome);
                return; // interrompe a execução do método
            }

            if (clienteCpf.isEmpty() || !onlyNumeric(clienteCpf)){
                Toast.makeText(this, "Infome apenas numeros!", Toast.LENGTH_SHORT).show();
                //Log.i("CLIENTE EEROR CPF", "DADOS: " + clienteSexo + clienteCpf + clienteNome);
                return; // interrompe a execução do método
            }

            Log.i("CLIENTE UPDATE", "DADOS: " + clienteSexo + clienteCpf + clienteNome);

            Cliente c = new Cliente(clienteCpf,clienteNome,clienteSexo);

            clienteAtual.cpf =clienteCpf;
            clienteAtual.nome = clienteNome;
            clienteAtual.sexo = clienteSexo;

            viewModel.atualizar(clienteAtual);

            finish();
            Toast.makeText(this, "Cliente atualizado com sucessso!", Toast.LENGTH_SHORT).show();


        });

        //voltar para a listagem
        this.voltar = findViewById(R.id.back);
        voltar.setOnClickListener(view -> {
            Intent intent = new Intent(EditarClienteActivity.this, ClientesActivity.class);
            startActivity(intent);
        });

        //remover Cliente

        btnRemover.setOnClickListener(view -> {
            viewModel.remover(clienteAtual);
            finish();
            Toast.makeText(this, "Cliente removida com sucesso!", Toast.LENGTH_SHORT).show();

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
