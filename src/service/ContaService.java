package service;

import exception.SaldoInsuficienteException;
import model.Conta;
import model.ContaCorrente;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ContaService {

    public List<ContaCorrente> lerContas(String caminho) throws IOException {

        List<ContaCorrente> contas = new ArrayList<>();

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

    public void atualizarConta(List<ContaCorrente> contas, String caminho) throws IOException{
        List<String> linhas = new ArrayList<>();

        for(ContaCorrente conta : contas){
            String dados = conta.getTitular() + ", " + conta.getNumero() + ", " + conta.getSaldo();

            linhas.add(dados);
        }

        Files.write(Paths.get(caminho), linhas);
    }

    public List<ContaCorrente> filtrarSaldoMaior10000(List<ContaCorrente> contas) {
        return contas.stream().filter(conta -> conta.getSaldo() > 10000).toList();
    }

    public double calcularSaldoTotal(List<ContaCorrente> contas) {
        return contas.stream().map(ContaCorrente::getSaldo).reduce(0.0, Double::sum);
    }

    public Map<String, List<ContaCorrente>> agruparSaldo(List<ContaCorrente> contas) {
        return contas.stream().collect(Collectors.groupingBy(conta -> {
            double saldo = conta.getSaldo();

            if(saldo <=5000) {
                return "Até R$ 5.000";
            } else if (saldo <= 10000) {
                return "R$ 5.000 a R$ 10.000";
            } else {
                return "Acima de R$ 10.000";
            }
        }));
    }

    public List<ContaCorrente> filtrarSaldoMaior5k (List<ContaCorrente> contas) {
        Predicate<ContaCorrente> saldoMaior5k = c -> c.getSaldo() > 5000;
        List<ContaCorrente> resultado = contas.stream()
                .filter(saldoMaior5k)
                .collect((Collectors.toList()));
        return resultado;
    }

    public List<ContaCorrente> filtrarContaPar (List<ContaCorrente> contas) {
        Predicate<ContaCorrente> contasPar = c -> c.getNumero() % 2==0;
        List<ContaCorrente> resultado = contas.stream()
                .filter(contasPar)
                .collect(Collectors.toList());
        return resultado;
    }


    // Ordenacao

    public List<ContaCorrente> ordenarSaldoDecrescente (List<ContaCorrente> contas) {
        Comparator<ContaCorrente> porSaldoDecrescente = (c1, c2) ->
                Double.compare(c2.getSaldo(), c1.getSaldo());
         contas.sort(porSaldoDecrescente);

         return contas;
    }

    public List<ContaCorrente> ordenarSaldoCrescente (List<ContaCorrente> contas) {
        Comparator<ContaCorrente> porSaldoCrescente = (c1, c2) ->
                Double.compare(c1.getSaldo(), c2.getSaldo());
        contas.sort(porSaldoCrescente);

        return contas;
    }

    public List<ContaCorrente> ordemAlfabeticaAZ (List<ContaCorrente> contas) {
        Comparator<ContaCorrente> porTitular = (c1, c2) ->
                c1.getTitular().compareTo(c2.getTitular());
        contas.sort(porTitular);

        return contas;
    }

    public List<ContaCorrente> ordemAlfabeticaZA (List<ContaCorrente> contas) {
        Comparator<ContaCorrente> porTitular = (c1, c2) ->
                c2.getTitular().compareTo(c1.getTitular());
        contas.sort(porTitular);

        return contas;
    }







}

