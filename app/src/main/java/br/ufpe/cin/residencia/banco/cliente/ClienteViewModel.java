package br.ufpe.cin.residencia.banco.cliente;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.ufpe.cin.residencia.banco.BancoDB;
import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaRepository;


public class ClienteViewModel extends AndroidViewModel {

    public LiveData<List<Cliente>> clientes;
    private ClienteRepository repository;

    private MutableLiveData<Cliente> __clienteAtual = new MutableLiveData<>();
    public LiveData<Cliente> clienteAtual = __clienteAtual;


    public ClienteViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ClienteRepository(BancoDB.getDB(application).clienteDAO());
        this.clientes = repository.getClientes();

    }

    void inserir(Cliente c) {
        new Thread(() -> repository.inserir(c)).start();
    }

    void atualizar(Cliente c) {
        rodarEmBackground(
            () -> {
                Log.i("ATTUAALLLIZZAARR", "atualizar: " + c);
                repository.atualizar(c);

            }
        );

    }


    void remover(Cliente c) {
        rodarEmBackground(
            () -> {
                repository.remover(c);

            }
        );

    }

    public void buscarpeloCpf(String cpfcliente) {
        rodarEmBackground(
            () -> {
                Cliente c =this.repository.buscarPeloCPF(cpfcliente);
                __clienteAtual.postValue(c);

            }
        );
    }

    private void rodarEmBackground(Runnable r) {
        new Thread(r).start();
    }


}
