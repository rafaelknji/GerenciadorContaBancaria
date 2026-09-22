package service;

import connection.ContaDAO;
import exception.SaldoInsuficienteException;
import model.ContaCorrente;
import model.HistoricoTransferencia;
import strategy.TarifaStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContaService {

    private List<ContaCorrente> contas;
    private List<HistoricoTransferencia> historico;

    private ContaDAO contaDAO;

    public ContaService() {

        contas = new ArrayList<>();
        historico = new ArrayList<>();

        contaDAO = new ContaDAO();

        carregarContas();
    }

    public List<ContaCorrente> getContas() {

        return contaDAO.listar();
    }

    public void carregarContas() {

        contas.clear();

        contas.addAll(
                contaDAO.listar()
        );
    }

    public void inserir(
            ContaCorrente conta) {

        contaDAO.inserir(conta);

        contas.add(conta);
    }

    public void atualizarSaldo(
            ContaCorrente conta,
            double novoSaldo) {

        conta.setSaldo(novoSaldo);

        contaDAO.atualizarSaldo(
                conta.getNumero(),
                novoSaldo
        );
    }

    public void depositar(
            ContaCorrente conta,
            double valor) {

        conta.depositar(valor);

        contaDAO.atualizarSaldo(
                conta.getNumero(),
                conta.getSaldo()
        );
    }

    public void sacar(
            ContaCorrente conta,
            double valor)
            throws SaldoInsuficienteException {

        conta.sacar(valor);

        contaDAO.atualizarSaldo(
                conta.getNumero(),
                conta.getSaldo()
        );
    }

    public void removerConta(
            ContaCorrente conta) {

        contaDAO.remover(
                conta.getNumero()
        );

        contas.remove(conta);
    }

    public boolean transferir(
            int numeroOrigem,
            int numeroDestino,
            double valor,
            TarifaStrategy tarifaS) {

        double tarifa =
                tarifaS.calcularTarifa(valor);

        boolean sucesso =
                contaDAO.transferir(
                        numeroOrigem,
                        numeroDestino,
                        valor,
                        tarifa
                );

        if (sucesso) {

            historico.add(
                    new HistoricoTransferencia(
                            numeroOrigem,
                            numeroDestino,
                            valor,
                            tarifa
                    )
            );
        }

        return sucesso;
    }

    public List<HistoricoTransferencia> getHistorico() {
        return historico;
    }

    public double calcularSaldoTotal(
            List<ContaCorrente> contas) {

        return contas.stream()
                .map(ContaCorrente::getSaldo)
                .reduce(
                        0.0,
                        Double::sum
                );
    }

    public List<ContaCorrente> filtrarSaldoMaior5k(
            List<ContaCorrente> contas) {

        return contas.stream()
                .filter(
                        c -> c.getSaldo() > 5000
                )
                .toList();
    }

    public List<ContaCorrente> filtrarContaPar(
            List<ContaCorrente> contas) {

        return contas.stream()
                .filter(
                        c -> c.getNumero() % 2 == 0
                )
                .toList();
    }

    public List<ContaCorrente> ordenarSaldoDecrescente(
            List<ContaCorrente> contas) {

        return contas.stream()
                .sorted(
                        Comparator.comparingDouble(
                                ContaCorrente::getSaldo
                        ).reversed()
                )
                .toList();
    }

    public List<ContaCorrente> ordenarSaldoCrescente(
            List<ContaCorrente> contas) {

        return contas.stream()
                .sorted(
                        Comparator.comparingDouble(
                                ContaCorrente::getSaldo
                        )
                )
                .toList();
    }

    public List<ContaCorrente> ordemAlfabeticaAZ(
            List<ContaCorrente> contas) {

        return contas.stream()
                .sorted(
                        Comparator.comparing(
                                ContaCorrente::getTitular
                        )
                )
                .toList();
    }

    public List<ContaCorrente> ordemAlfabeticaZA(
            List<ContaCorrente> contas) {

        return contas.stream()
                .sorted(
                        Comparator.comparing(
                                ContaCorrente::getTitular
                        ).reversed()
                )
                .toList();
    }

    public Map<String, List<ContaCorrente>> agruparSaldo(
            List<ContaCorrente> contas) {

        return contas.stream()
                .collect(
                        Collectors.groupingBy(
                                conta -> {

                                    double saldo =
                                            conta.getSaldo();

                                    if (saldo <= 5000) {
                                        return "Até R$ 5.000";

                                    } else if (saldo <= 10000) {
                                        return "R$ 5.000 a R$ 10.000";

                                    } else {
                                        return "Acima de R$ 10.000";
                                    }
                                }
                        )
                );
    }

    public List<ContaCorrente> lerContas(
            String caminho)
            throws IOException {

        List<ContaCorrente> contas =
                new ArrayList<>();

        List<String> linhas =
                Files.readAllLines(
                        Paths.get(caminho)
                );

        for (String linha : linhas) {

            String[] dados =
                    linha.split(",");

            int numero =
                    Integer.parseInt(
                            dados[0].trim()
                    );

            String titular =
                    dados[1].trim();

            double saldo =
                    Double.parseDouble(
                            dados[2].trim()
                    );

            ContaCorrente conta =
                    new ContaCorrente(
                            titular,
                            numero,
                            saldo
                    );

            contas.add(conta);
        }

        return contas;
    }

    public void atualizarConta(
            List<ContaCorrente> contas,
            String caminho)
            throws IOException {

        List<String> linhas =
                new ArrayList<>();

        for (ContaCorrente conta : contas) {

            String dados =
                    conta.getTitular()
                            + ", "
                            + conta.getNumero()
                            + ", "
                            + conta.getSaldo();

            linhas.add(dados);
        }

        Files.write(
                Paths.get(caminho),
                linhas
        );
    }
}