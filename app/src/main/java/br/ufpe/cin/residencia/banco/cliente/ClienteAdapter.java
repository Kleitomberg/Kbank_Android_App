package br.ufpe.cin.residencia.banco.cliente;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import br.ufpe.cin.residencia.banco.R;
import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaViewHolder;

public class ClienteAdapter extends ListAdapter<Cliente, ClienteViewHolder> {

    LayoutInflater inflater;

    protected ClienteAdapter(LayoutInflater layoutInflater) {
        super(DIFF_CALLBACK);
        this.inflater = layoutInflater;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClienteViewHolder(inflater.inflate(R.layout.linha_cliente, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        holder.bindTo(getItem(position));

    }

    private static final DiffUtil.ItemCallback<Cliente> DIFF_CALLBACK =
        new DiffUtil.ItemCallback<Cliente>() {
            @Override
            public boolean areItemsTheSame(@NonNull Cliente oldItem, @NonNull Cliente newItem) {
                return oldItem.cpf.equals(newItem.cpf);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Cliente oldItem, @NonNull Cliente newItem) {
                return oldItem.nome.equals(newItem.nome) &&
                    oldItem.cpf.equals(newItem.cpf) &&
                    oldItem.sexo.equals(newItem.sexo);
            }
        };
}
