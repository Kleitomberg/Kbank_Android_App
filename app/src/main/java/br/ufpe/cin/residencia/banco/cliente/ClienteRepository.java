package br.ufpe.cin.residencia.banco.cliente;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;


public class ClienteRepository {

    private ClienteDAO dao;
    private LiveData<List<Cliente>> clientes;

    //incializando as dependencia pelo construtor

    public ClienteRepository(ClienteDAO dao) {
        this.dao = dao;
        this.clientes = dao.clientes();
    }

    //retorna a lista de clientes
    public LiveData<List<Cliente>> getClientes() {
        return clientes;
    }

    //adiciona cliente
    @WorkerThread
    public void inserir(Cliente c) {
        dao.inserir(c);
    }

    //atualiza cliente
    @WorkerThread
    public void atualizar(Cliente c) {
        dao.atualizar(c);
    }

    //remove cliente
    @WorkerThread
    public void remover(Cliente c) {
        dao.remover(c);
    }

    @WorkerThread
    public Cliente buscarPeloCPF(String cpfCliente) {
        return dao.buscarPeloCPF(cpfCliente);
    }


}
