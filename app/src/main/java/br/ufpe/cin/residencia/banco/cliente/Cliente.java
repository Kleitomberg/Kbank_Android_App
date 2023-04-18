package br.ufpe.cin.residencia.banco.cliente;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;

@Entity(tableName = "clientes")
public class Cliente {
    @PrimaryKey
    @NonNull
    public String cpf;
    @NonNull
    public String nome;
    public  String sexo;

    /*
    @Relation(parentColumn = "cpf", entityColumn = "cpfCliente")
    public List<Conta> minhasContas;
*/
    public Cliente(@NonNull String cpf, @NonNull String nome, @NonNull String sexo) {
        this.cpf = cpf;
        this.nome = nome;
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "nome='" + nome + '\'' +
            '}';
    }
}
