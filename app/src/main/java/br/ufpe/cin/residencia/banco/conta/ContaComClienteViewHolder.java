package br.ufpe.cin.residencia.banco.conta;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class ContaComClienteViewHolder extends RecyclerView.ViewHolder {
    TextView nomeCliente = null;
    TextView infoConta, infonumero = null;
    ImageView icone = null;

    public static final String KEY_NUMERO_CONTA = "numeroDaConta";

    public ContaComClienteViewHolder(@NonNull View linha) {
        super(linha);
        this.nomeCliente = linha.findViewById(R.id.nomeCliente);
        this.infoConta = linha.findViewById(R.id.infoConta);
        this.icone = linha.findViewById(R.id.icone);
        this.infonumero = linha.findViewById(R.id.infonumero);
    }

    void bindTo(ContaComCliente c) {
        Log.i("c", "bindTo: "+ c);
        this.nomeCliente.setText(c.nomeCliente);
        this.infoConta.setText(NumberFormat.getCurrencyInstance().format(c.saldo));
        this.infonumero.setText(c.numero);

        if (c.saldo <= 0) {
            this.icone.setImageResource(R.drawable.ic_negative);
        } else {
            this.icone.setImageResource(R.drawable.ic_positive);
        }

        this.addListener(c.numero);
    }

    public void addListener(String numeroConta) {
        this.itemView.setOnClickListener(
                v -> {
                    Context c = this.itemView.getContext();
                    Intent i = new Intent(c, EditarContaActivity.class);
                    i.putExtra(KEY_NUMERO_CONTA,numeroConta);
                    //TODO Está especificando a Activity mas não está passando o número da conta pelo Intent
                    c.startActivity(i);
                }
        );
    } //FIM METODO
}
