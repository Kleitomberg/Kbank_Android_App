package br.ufpe.cin.residencia.banco.cliente;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;

@Dao
public interface ClienteDAO {

    // adicionar cliente
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserir(Cliente c);

    //atualizar um cliente
    @Update
    void atualizar(Cliente c);

    //excluir um cliente
    @Delete
    void remover(Cliente c);

    //buscar cliente todos os clientes ordenado pelo nome
    @Query("SELECT * FROM clientes ORDER BY nome ASC")
    LiveData<List<Cliente>> clientes();

    @Query("SELECT * FROM clientes WHERE cpf = :cpfCliente LIMIT 1")
    Cliente buscarPeloCPF(String cpfCliente);



    @Transaction
    @Query("SELECT * FROM clientes WHERE cpf = :cpf")
    public ClienteComContas getClienteComContas(String cpf);


}
