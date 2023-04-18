package br.ufpe.cin.residencia.banco;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaComCliente;
import br.ufpe.cin.residencia.banco.conta.ContaDAO;
import br.ufpe.cin.residencia.banco.conta.ContaRepository;

//Ver anotações TODO no código
public class BancoViewModel extends AndroidViewModel {
    private ContaRepository repository;
    public static boolean status = false;
    private MutableLiveData<String> __erroMsg = new MutableLiveData<>();
    public LiveData<String> erroMsg = __erroMsg;

    public LiveData<List<Conta>> contas;

    private MutableLiveData<Conta> __contaAtual = new MutableLiveData<>();
    public LiveData<Conta> contaAtual = __contaAtual;
    private MutableLiveData<List<Conta>> __listaContasAtual = new MutableLiveData<>();
    public LiveData<List<Conta>> listaContasAtual = __listaContasAtual;



    public BancoViewModel(@NonNull Application application) {


        super(application);

        Log.i("construct", "BancoViewModel: "+ application);

        ContaDAO dao = BancoDB.getDB(application).contaDAO();
        Log.i("DAO", "BancoViewModel: "+ dao);

        this.repository = new ContaRepository(dao);
        Log.i("repository", "BancoViewModel: "+ repository);
        this.contas = repository.getContas();
    }

    public double calcularValorTotal() {
        double valorTotal = 0;
        List<Conta> listaContas = contas.getValue();
        if (listaContas != null) {
            for (Conta conta : listaContas) {
                valorTotal += conta.saldo;
            }
        }
        return valorTotal;
    }
    void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        //TODO implementar transferência entre contas (lembrar de salvar no BD os objetos Conta modificados)

        rodarEmBackground(() -> {
            Conta origem = repository.buscarPeloNumero(numeroContaOrigem);
            Conta destino = repository.buscarPeloNumero(numeroContaDestino);


            // Verifica se a conta é nula
            if (origem == null || destino == null) {
                //this.erroMsg ="Conta não encontrada!";
                __erroMsg.postValue("Contas não encontradas!");
                this.status = false;
                return; // retorna sem fazer nada
            // verifica se o valor é menor que o saldo
            }else if( valor <= 0 || valor > origem.saldo){
                //this.erroMsg ="Não há saldo suficiente para realizar a operação!";
                __erroMsg.postValue("Não há saldo suficiente para realizar a operação!");
                this.status = false;
                return; // retorna sem fazer nada
            }
            else {
                this.status = true;
                // atualizar o saldo da conta
                ///double novoSaldo = destino.saldo + valor;
                //destino.saldo = novoSaldo;
                //origem.saldo -= valor;

                origem.transferir(destino,valor);
                repository.atualizar(destino);
                repository.atualizar(origem);
            }
        });


    }

    void creditar(String numeroConta, double valor) {
        // buscar a conta atual no banco de dados
        rodarEmBackground(() -> {
            Conta contaAtual = repository.buscarPeloNumero(numeroConta);
            // Verifica se a conta é nula
            if (contaAtual == null) {
                //this.erroMsg ="Não há saldo suficiente para realizar a operação!";
                __erroMsg.postValue("Conta não encontrada!");
                this.status = false;
                return; // retorna sem fazer nada
            }else {
                this.status = true;
            // atualizar o saldo da conta
            //double novoSaldo = contaAtual.saldo + valor;
            //contaAtual.saldo = novoSaldo;
            contaAtual.creditar(valor);
            repository.atualizar(contaAtual);
            }
        });

    }

    void debitar(String numeroConta, double valor) {
        //TODO implementar debitar em conta (lembrar de salvar no BD o objeto Conta modificado)
        rodarEmBackground(() -> {
            Conta contaAtual = repository.buscarPeloNumero(numeroConta);
            // Verifica se a conta é nula
            if (contaAtual == null) {
                //this.erroMsg ="Conta não encontrada!";
                __erroMsg.postValue("Conta não encontrada!");
                this.status = false;
                return; // retorna sem fazer nada

            }
            else if (contaAtual.saldo < valor) {
                    // this.erroMsg ="Não há saldo suficiente para realizar a operação!";
                __erroMsg.postValue("Não há saldo suficiente para realizar a operação!");
                    this.status = false;
                    return; // retorna sem fazer nada
            }
            else {
                this.status = true;
                // atualizar o saldo da conta
                //double novoSaldo = contaAtual.saldo - valor;
                //contaAtual.saldo = novoSaldo;
                contaAtual.debitar(valor);
                repository.atualizar(contaAtual);
            }
        });
    }

    void buscarPeloNome(String nomeCliente) {
        rodarEmBackground(
            () -> {
                List<Conta> listaContas = this.repository.buscarPeloNome(nomeCliente);
                __listaContasAtual.postValue(listaContas);

            }
        );
    }

    void buscarPeloCPF(String cpfCliente) {
        rodarEmBackground(
            () -> {
                List<Conta> listaContas = this.repository.buscarPeloCPF(cpfCliente);
                __listaContasAtual.postValue(listaContas);

            }
        );
    }

    void buscarPeloNumero(String numeroConta) {
        rodarEmBackground(
            () -> {
                Conta c =this.repository.buscarPeloNumero(numeroConta);
                //cria uma lista vazia
                if (c != null) {
                    List<Conta> contaa = new ArrayList<>();
                    contaa.add(c);
                    __listaContasAtual.postValue(contaa);
                    __contaAtual.postValue(c);
                }else {
                    __listaContasAtual.postValue(new ArrayList<>());
                }


            }
        );
    }

    private void rodarEmBackground(Runnable r) {
        new Thread(r).start();
    }



}
