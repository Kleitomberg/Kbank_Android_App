package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

import br.ufpe.cin.residencia.banco.conta.ContaAdapter;

//Ver anotações TODO no código
public class PesquisarActivity extends AppCompatActivity {
    BancoViewModel viewModel;
    ContaAdapter adapter;
    ImageView voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);
        EditText aPesquisar = findViewById(R.id.pesquisa);
        Button btnPesquisar = findViewById(R.id.btn_Pesquisar);
        RadioGroup tipoPesquisa = findViewById(R.id.tipoPesquisa);
        RecyclerView rvResultado = findViewById(R.id.rvResultado);
        TextView semResultado = findViewById(R.id.nenhum_result);
        adapter = new ContaAdapter(getLayoutInflater());
        rvResultado.setLayoutManager(new LinearLayoutManager(this));
        rvResultado.setAdapter(adapter);

        this.voltar = findViewById(R.id.back);
        voltar.setOnClickListener(view -> {
            Intent i = new Intent(PesquisarActivity.this, MainActivity.class);
            startActivity(i);
        });

        btnPesquisar.setOnClickListener(
                v -> {
                    String oQueFoiDigitado = aPesquisar.getText().toString();
                    //pego o ID od Radio selecionado
                    int radioSelecionado = tipoPesquisa.getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(radioSelecionado);
                    String buscarPor = radioButton.getText().toString();

                    //TODO implementar a busca de acordo com o tipo de busca escolhido pelo usuário

                    Log.i("BUSCA POR ", "BUSCA POR : "  + buscarPor);
                    if (buscarPor.equals("Nome") || buscarPor.equals("Name") || buscarPor.equals("Nombre")){
                        //realizar busca pelo nome
                        Log.i("BUSCA POR NOME", "Busca Por: "+ buscarPor + " DIGITEI: "+ oQueFoiDigitado);
                        if(!oQueFoiDigitado.isEmpty()) {
                            viewModel.buscarPeloNome(oQueFoiDigitado);
                        }

                    } else if(buscarPor.equals("CPF")){
                        //buscar pelo cpf
                        Log.i("BUSCA POR CPF", "Busca Por: "+ buscarPor + " DIGITEI: "+ oQueFoiDigitado);

                        if(!oQueFoiDigitado.isEmpty()){
                            Log.i("CHAMOU O BUSCAR CPF", "onCreate: ");

                            viewModel.buscarPeloCPF(oQueFoiDigitado);
                        }

                    }else {
                        //buscar por numero
                        Log.i("BUSCA POR NUMERO", " DIGITEI: "+ oQueFoiDigitado);
                        if(!oQueFoiDigitado.isEmpty()){
                            Log.i("CHAMOU O BUSCAR NUMERO", "onCreate: ");
                            viewModel.buscarPeloNumero(oQueFoiDigitado);
                        }
                    }

                }
        );

        //TODO atualizar o RecyclerView com resultados da busca na medida que encontrar

        viewModel.listaContasAtual.observe(this, contas -> {

            if (contas.size() == 0){
                semResultado.setVisibility(View.VISIBLE);
            }else {

                semResultado.setVisibility(View.GONE);

            }
            adapter.submitList(contas);
        });

    }
}
