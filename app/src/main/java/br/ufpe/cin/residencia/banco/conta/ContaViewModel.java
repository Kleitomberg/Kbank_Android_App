package br.ufpe.cin.residencia.banco.conta;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.ufpe.cin.residencia.banco.BancoDB;

//Ver métodos anotados com TODO
public class ContaViewModel extends AndroidViewModel {

    private ContaRepository repository;

    public LiveData<List<Conta>> contas;
    public LiveData<List<ContaComCliente>> contasComCliente;
    private MutableLiveData<Conta> _contaAtual = new MutableLiveData<>();
    public LiveData<Conta> contaAtual = _contaAtual;

    private MutableLiveData<ContaComCliente> _contaComClienteAtual = new MutableLiveData<>();
    public LiveData<ContaComCliente> ContaComClienteAtual = _contaComClienteAtual;

    public ContaViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        this.contas = repository.getContas();
        this.contasComCliente = repository.getContasComClientes();
    }

    void inserir(Conta c) {
        new Thread(() -> repository.inserir(c)).start();
    }

    void atualizar(Conta c) {
        //TODO implementar
        rodarEmBackground(
            () -> {
                repository.atualizar(c);

            }
        );

    }


    void remover(Conta c) {
        //TODO implementar

        rodarEmBackground(
            () -> {
                repository.remover(c);

            }
        );

    }

    void buscarPeloNumero(String numeroConta) {
        rodarEmBackground(
            () -> {
                Conta c =this.repository.buscarPeloNumero(numeroConta);
                _contaAtual.postValue(c);

            }
        );
    }

    private void rodarEmBackground(Runnable r) {
        new Thread(r).start();
    }


}
