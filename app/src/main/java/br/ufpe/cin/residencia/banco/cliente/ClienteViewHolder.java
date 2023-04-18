package br.ufpe.cin.residencia.banco.cliente;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;

import br.ufpe.cin.residencia.banco.R;
import br.ufpe.cin.residencia.banco.conta.EditarContaActivity;


public class ClienteViewHolder extends RecyclerView.ViewHolder {

    TextView nomeCliente, cpfCliente = null;
    ImageView icone = null;

    public static final String KEY_CPF_CLIENTE= "cpfCliente";

    public ClienteViewHolder(@NonNull View itemView) {
        super(itemView);
        this.nomeCliente = itemView.findViewById(R.id.cliente_nome);
        this.cpfCliente = itemView.findViewById(R.id.cliente_cpf);
        this.icone = itemView.findViewById(R.id.cliente_profle);
    }

    void bindTo(Cliente c) {
        this.nomeCliente.setText(c.nome);
        this.cpfCliente.setText(c.cpf);

        if(c.sexo.equals("Masculino")||c.sexo.equals("Male")){
            this.icone.setImageResource(R.drawable.ic_boy);
        }else{
            this.icone.setImageResource(R.drawable.ic_girl);
        }

        this.addListener(c.cpf);
    }

    //se clicar no card vai pra tela de edição do client passando o cpf que é a chave unica
    public void addListener(String cpf) {
        this.itemView.setOnClickListener(
            v -> {
                Context c = this.itemView.getContext();
                Intent i = new Intent(c, EditarClienteActivity.class);
                i.putExtra(KEY_CPF_CLIENTE,cpf);
                c.startActivity(i);
            }
        );
    } //FIM METODO
}
