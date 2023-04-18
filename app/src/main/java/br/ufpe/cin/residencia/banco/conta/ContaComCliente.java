package br.ufpe.cin.residencia.banco.conta;

import androidx.annotation.NonNull;

public class ContaComCliente {
    public String numero;
    public double saldo;
    public String nomeCliente;
    public String cpfCliente;

    public ContaComCliente(String numero, double saldo, String nomeCliente, String cpfCliente) {
        this.numero = numero;
        this.saldo = saldo;
        this.nomeCliente = nomeCliente;
        this.cpfCliente = cpfCliente;
    }

    @NonNull
    @Override
    public String toString() {
        return "ContaComCliente{" +
            "numero='" + numero + '\'' +
            ", saldo=" + saldo +
            ", nomeCliente='" + nomeCliente + '\'' +
            ", cpfCliente='" + cpfCliente + '\'' +
            '}';
    }
}
