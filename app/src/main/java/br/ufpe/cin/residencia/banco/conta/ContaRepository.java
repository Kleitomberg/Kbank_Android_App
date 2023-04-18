package br.ufpe.cin.residencia.banco.conta;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.ufpe.cin.residencia.banco.cliente.ClienteComContas;

//Ver anotações TODO no código
public class ContaRepository {
    private ContaDAO dao;
    private LiveData<List<Conta>> contas;
    private LiveData<List<ContaComCliente>> contasComCLiente;


    public ContaRepository(ContaDAO dao) {
        this.dao = dao;
        this.contas = dao.contas();
        this.contasComCLiente = dao.getContasComClientes();
    }

    public LiveData<List<Conta>> getContas() {
        return contas;
    }

    public LiveData<List<ContaComCliente>> getContasComClientes() {
        return contasComCLiente;
    }

    @WorkerThread
    public void inserir(Conta c) {
        dao.adicionar(c);
    }

    @WorkerThread
    public void atualizar(Conta c) {
        //TODO implementar atualizar
        dao.atualizar(c);
    }

    @WorkerThread
    public void remover(Conta c) {
        //TODO implementar remover
        dao.remover(c);
    }

    @WorkerThread
    public List<Conta> buscarPeloNome(String nomeCliente) {
        return dao.buscarPeloNome(nomeCliente);
    }

    @WorkerThread
    public List<Conta> buscarPeloCPF(String cpfCliente) {
        return dao.buscarPeloCPF(cpfCliente);
    }

    @WorkerThread
    public Conta buscarPeloNumero(String numeroConta) {
        Conta conta = dao.buscarPeloNumero(numeroConta);
        return conta;
    }


}
