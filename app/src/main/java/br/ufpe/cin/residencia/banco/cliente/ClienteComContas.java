package br.ufpe.cin.residencia.banco.cliente;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;

//classe que representa a relação entre Cliente e Contas , ela representa um Cliente com suas várias contas associadas.
public class ClienteComContas {

    @Embedded
    public Cliente cliente;

    @Relation(parentColumn = "cpf", entityColumn = "cpfCliente")
    public List<Conta> contas;
}
