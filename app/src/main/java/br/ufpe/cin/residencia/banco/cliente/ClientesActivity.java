package br.ufpe.cin.residencia.banco.cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import br.ufpe.cin.residencia.banco.MainActivity;
import br.ufpe.cin.residencia.banco.R;
import br.ufpe.cin.residencia.banco.conta.ContaAdapter;
import br.ufpe.cin.residencia.banco.conta.ContaViewModel;
import br.ufpe.cin.residencia.banco.conta.ContasActivity;

public class ClientesActivity extends AppCompatActivity {

    ClienteViewModel viewModel;
    ClienteAdapter adapter;

    Button adicionar;
    ImageView voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        //botão de voltar
        this.voltar = findViewById(R.id.back);

        voltar.setOnClickListener(view -> {
            Intent i = new Intent(ClientesActivity.this, MainActivity.class);
            startActivity(i);
        });

        //botão adicionar
        this.adicionar = findViewById(R.id.btn_Adiciona);

        adicionar.setOnClickListener(view -> {
            Intent i = new Intent(ClientesActivity.this, AdicionarClienteActivity.class);
            startActivity(i);
        });


        //incializando o viewmodel de clientes

        viewModel = new ViewModelProvider(this).get(ClienteViewModel.class);

        //configurando recylcer view para a exibição da lista
        RecyclerView recyclerView = findViewById(R.id.rvClientes);

        adapter = new ClienteAdapter(getLayoutInflater());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        //observando mudançças na lista de clientes e e com o auxilio do DiffUtil ele compara o que tem de diferente e atualiza só o que mudou na lista
        viewModel.clientes.observe(this, clientes -> {
            adapter.submitList(clientes);
        });


    }
}
