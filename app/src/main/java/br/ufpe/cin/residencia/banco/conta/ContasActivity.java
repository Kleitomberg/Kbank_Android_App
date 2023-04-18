package br.ufpe.cin.residencia.banco.conta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import br.ufpe.cin.residencia.banco.MainActivity;
import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class ContasActivity extends AppCompatActivity {
    ContaComClienteAdapter adapter;
    ContaViewModel viewModel;
    ImageView voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas);

        //botão de voltar
        this.voltar = findViewById(R.id.back);

        voltar.setOnClickListener(view -> {
            Intent i = new Intent(ContasActivity.this, MainActivity.class);
            startActivity(i);
        });

        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.rvContas);

        adapter = new ContaComClienteAdapter(getLayoutInflater());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Button adicionarConta = findViewById(R.id.btn_Adiciona);
        adicionarConta.setOnClickListener(
                v -> startActivity(new Intent(this, AdicionarContaActivity.class))
        );

        //observando a lista de contas do viewModel e atulizando a lista que vai para o adapter sempre que ocorrer alguma mudança
        //usar o submitList é mais eficiente, pois utiliza DiffUtil  que calcular as diferenças entre a lista antiga e a nova, e atualiza a lista de forma mais eficiente.
        viewModel.contasComCliente.observe(this, contas -> {
            adapter.submitList(contas);
        });
    }

}
