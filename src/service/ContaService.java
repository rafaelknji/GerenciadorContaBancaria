package service;

import exception.SaldoInsuficienteException;
import model.Conta;
import model.ContaCorrente;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ContaService {

    private List<ContaCorrente> contas = new ArrayList<>();


    public List<ContaCorrente> lerContas(String caminho) throws IOException {
        List<String> linhas = Files.readAllLines(Paths.get(caminho));

        for(String linha : linhas){
            String[] dados = linha.split(",");
            int numero = Integer.parseInt(dados[0].trim());
            String titular = dados[1].trim();
            double saldo = Double.parseDouble(dados[2].trim());

            ContaCorrente conta = new ContaCorrente(titular, numero, saldo);
            contas.add(conta);
        }
        return contas;
    }

    public void sacarValor(ContaCorrente conta, double valor) throws SaldoInsuficienteException {
        conta.sacar(valor);
    }

    public void atualizarConta(List<ContaCorrente> contas, String caminho) throws IOException{
        List<String> linhas = new ArrayList<>();

        for(ContaCorrente conta : contas){
            String dados = conta.getTitular() + ", " + conta.getNumero() + ", " + conta.getSaldo();

            linhas.add(dados);
        }

        Files.write(Paths.get(caminho), linhas);
    }
}

