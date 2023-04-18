package br.ufpe.cin.residencia.banco.conta;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import br.ufpe.cin.residencia.banco.R;

//ESTA CLASSE NAO PRECISA SER MODIFICADA!
public class ContaComClienteAdapter extends ListAdapter<ContaComCliente, ContaComClienteViewHolder> {
    LayoutInflater inflater;

    public ContaComClienteAdapter(LayoutInflater layoutInflater) {
        super(DIFF_CALLBACK);
        this.inflater = layoutInflater;
    }

    @NonNull
    @Override
    public ContaComClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContaComClienteViewHolder(inflater.inflate(R.layout.linha_conta, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContaComClienteViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    private static final DiffUtil.ItemCallback<ContaComCliente> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ContaComCliente>() {
                @Override
                public boolean areItemsTheSame(@NonNull ContaComCliente oldItem, @NonNull ContaComCliente newItem) {
                    return oldItem.numero.equals(newItem.numero);
                }

                @Override
                public boolean areContentsTheSame(@NonNull ContaComCliente oldItem, @NonNull ContaComCliente newItem) {
                    boolean nomeClienteEqual = false;
                    boolean cpfClienteEqual = false;
                    boolean numeroEqual = false;
                    boolean saldoEqual = false;

                    if (oldItem.nomeCliente != null && newItem.nomeCliente != null) {
                        nomeClienteEqual = oldItem.nomeCliente.equals(newItem.nomeCliente);
                    }

                    if (oldItem.cpfCliente != null && newItem.cpfCliente != null) {
                        cpfClienteEqual = oldItem.cpfCliente.equals(newItem.cpfCliente);
                    }

                    if (oldItem.numero != null && newItem.numero != null) {
                        numeroEqual = oldItem.numero.equals(newItem.numero);
                    }

                    saldoEqual = oldItem.saldo == newItem.saldo;

                    return nomeClienteEqual && cpfClienteEqual && numeroEqual && saldoEqual;
                }
            };
}
