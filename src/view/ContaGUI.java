package view;

import exception.SaldoInsuficienteException;
import model.ContaCorrente;
import service.ContaService;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ContaGUI extends JFrame {

    private List<ContaCorrente> contas;
    private ContaService contaService;
    private PainelListaContas painelLista;
    private PainelDados painelDados;
    private PainelSaldoTotal painelSaldoTotal;
    private PainelOperacoes painelOperacoes;


    public ContaGUI() {
        contaService = new ContaService();

        try {
            contas = contaService.lerContas("conta.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar contas: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        criarInterface();
        carregarLista();
        atualizarSaldoTotal();

        setTitle("Gerenciador de Contas Bancárias");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void criarInterface() {
        setLayout(new BorderLayout());

        // Lista das contas
        painelLista = new PainelListaContas();
        add(painelLista, BorderLayout.CENTER);

        // Painel Informações da conta selecionada
        painelDados = new PainelDados();
        add(painelDados, BorderLayout.NORTH);

        JPopupMenu menuOrdem = new JPopupMenu();

        JMenuItem itemCrescente = new JMenuItem("Crescente(0-9)");
        itemCrescente.addActionListener(e -> ordenarSaldoCrescente());

        JMenuItem itemDecrescente = new JMenuItem("Decrescente(9-0");
        itemDecrescente.addActionListener(e -> ordenarSaldoDecrescente());

        JMenuItem itemAlfabeticaAZ = new JMenuItem("Titular(A-Z)");
        itemAlfabeticaAZ.addActionListener(e -> ordemAlfabeticaAZ());

        JMenuItem itemAlfabeticaZA = new JMenuItem("Titular(Z-A)");
        itemAlfabeticaZA.addActionListener(e -> ordemAlfabeticaZA());

        menuOrdem.add(itemCrescente);
        menuOrdem.add(itemDecrescente);
        menuOrdem.add(itemAlfabeticaAZ);
        menuOrdem.add(itemAlfabeticaZA);
       painelDados.getBtnOrdenar().addActionListener(e ->
               menuOrdem.show(painelDados.getBtnOrdenar(), 0, painelDados.getBtnOrdenar().getHeight()));

        // Painel Saldo total
        painelSaldoTotal = new PainelSaldoTotal();

        // Operações
        painelOperacoes = new PainelOperacoes();

        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(new GridLayout(2, 1));
        painelInferior.add(painelSaldoTotal);
        painelInferior.add(painelOperacoes);
        add(painelInferior, BorderLayout.SOUTH);

        //menu de filtros gerais
        JPopupMenu menuFiltros = new JPopupMenu();

        JMenuItem itemFiltrar5k = new JMenuItem("Saldo > 5K");
        itemFiltrar5k.addActionListener(e -> filtrarSaldoMaior5k());

        JMenuItem itemFiltrar10k = new JMenuItem("Saldo > R$ 10K");
        itemFiltrar10k.addActionListener(e -> filtrarSaldoMaior10000());

        JMenuItem itemLimparFiltro = new JMenuItem("Limpar filtros");
        itemLimparFiltro.addActionListener(e -> {
            carregarLista();
            atualizarSaldoTotal();
        });

        menuFiltros.add(itemFiltrar5k);
        menuFiltros.add(itemFiltrar10k);
        menuFiltros.add(itemLimparFiltro);

        painelOperacoes.getBtnFiltros().addActionListener(e ->
                menuFiltros.show(painelOperacoes.getBtnFiltros(), 0, painelOperacoes.getBtnFiltros().getHeight()));

        // Agrupar por saldo
        JPopupMenu AgruparSaldo = new JPopupMenu();

        JMenuItem itemAte5000 = new JMenuItem("Até R$ 5.000");
        itemAte5000.addActionListener(e -> agruparSaldo("Até R$ 5.000"));

        JMenuItem itemAte10000 = new JMenuItem("de R$ 5.000 a R$ 10.000");
        itemAte10000.addActionListener(e -> agruparSaldo("R$ 5.000 a R$ 10.000"));

        JMenuItem itemMaior10000 = new JMenuItem("Acima R$ 10.000");
        itemMaior10000.addActionListener(e -> agruparSaldo("Acima de R$ 10.000"));

        AgruparSaldo.add(itemAte5000);
        AgruparSaldo.add(itemAte10000);
        AgruparSaldo.add(itemMaior10000);

        painelOperacoes.getBtnAgrupar().addActionListener(e ->
                AgruparSaldo.show(painelOperacoes.getBtnAgrupar(), 0, painelOperacoes.getBtnAgrupar().getHeight()));

        // Quando selecionar uma conta
        painelLista.getListaContas().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                mostrarContaSelecionada();
            }
        });

        painelOperacoes.getBtnSacar().addActionListener(e -> sacar());
        painelOperacoes.getBtnDepositar().addActionListener(e -> depositar());
    }

    private void exibirContas(List<ContaCorrente> contas) {
        exibirContas(contas);
    }

    private void carregarLista() {
        exibirContas(contas);
    }

    private ContaCorrente getContaSelecionada() {
        int indice = painelLista.getListaContas().getSelectedIndex();;

        if (indice == -1) {
            return null;
        }
        return contas.get(indice);
    }

    private void mostrarContaSelecionada() {
        ContaCorrente conta = getContaSelecionada();

        if (conta == null) {
            return;
        }

        painelDados.getLblNumero().setText("Número: " + conta.getNumero());
        painelDados.getLblTitular().setText("Titular: " + conta.getTitular());
        painelDados.getLblSaldo().setText("Saldo: R$ " + conta.getSaldo());
    }

    private void sacar() {
        ContaCorrente conta = getContaSelecionada();

        if (conta == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma conta.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            double valor = Double.parseDouble(painelOperacoes.getTxtValor().getText());
            conta.sacar(valor);
            JOptionPane.showMessageDialog(this, "Saque realizado com sucesso!");

            atualizarTela();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Digite um valor válido.", "Erro", JOptionPane.ERROR_MESSAGE
            );

        } catch (SaldoInsuficienteException e) {
            JOptionPane.showMessageDialog(
                    this, e.getMessage(), "Saque não realizado!", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void depositar() {
        ContaCorrente conta = getContaSelecionada();
        if (conta == null) {
            JOptionPane.showMessageDialog(
                    this, "Selecione uma conta.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double valor = Double.parseDouble(painelOperacoes.getTxtValor().getText());
            if (valor <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "O valor do depósito deve ser positivo.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            conta.depositar(valor);
            JOptionPane.showMessageDialog(this, "Depósito realizado com sucesso!");
            atualizarTela();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Digite um valor válido.", "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void filtrarSaldoMaior10000() {
        List<ContaCorrente> contasFiltradas =
                contaService.filtrarSaldoMaior10000(contas);

        exibirContas(contasFiltradas);

        double saldoTotal = contaService.calcularSaldoTotal(contasFiltradas);

        painelSaldoTotal.getLblResultadoSaldoTotal().setText(String.format("R$ %.2f", saldoTotal));
    }

    private void agruparSaldo(String categoria) {
        Map<String, List<ContaCorrente>> grupos = contaService.agruparSaldo(contas);

        List<ContaCorrente> contasAgrupadas = grupos.get(categoria);

        exibirContas(contasAgrupadas);

        if (contasAgrupadas == null || contasAgrupadas.isEmpty()) {
            painelSaldoTotal.getLblResultadoSaldoTotal().setText("R$ 0,00");
            return;
        }

        double saldoTotal = contaService.calcularSaldoTotal(contasAgrupadas);

        painelSaldoTotal.getLblResultadoSaldoTotal().setText(String.format("R$ %.2f", saldoTotal));
    }

    private void atualizarSaldoTotal() {
        double saldoTotal = contaService.calcularSaldoTotal(contas);

        painelSaldoTotal.getLblResultadoSaldoTotal().setText(
                String.format("R$ %.2f", saldoTotal)
        );
    }

    private void atualizarTela() {
        carregarLista();
        mostrarContaSelecionada();
        atualizarSaldoTotal();

        try {
            contaService.atualizarConta(contas, "contas_atualizadas.txt");
        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this, "Erro ao salvar contas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void filtrarSaldoMaior5k() {
        List<ContaCorrente> contasFiltradas = contaService.filtrarSaldoMaior5k(contas);
        exibirContas(contasFiltradas);
    }

    private void filtrarContaPar() {
        List<ContaCorrente> contasFiltradas = contaService.filtrarContaPar(contas);
        exibirContas(contasFiltradas);
    }

    private void ordenarSaldoDecrescente() {
        List<ContaCorrente> contasFiltradas = contaService.ordenarSaldoDecrescente(contas);
        exibirContas(contasFiltradas);
    }

    private void ordenarSaldoCrescente() {
        List<ContaCorrente> contasFiltradas = contaService.ordenarSaldoCrescente(contas);
        exibirContas(contasFiltradas);
    }

    private void ordemAlfabeticaAZ() {
        List<ContaCorrente> contasFiltradas = contaService.ordemAlfabeticaAZ(contas);
        exibirContas(contasFiltradas);
    }

    private void ordemAlfabeticaZA() {
        List<ContaCorrente> contasFiltradas = contaService.ordemAlfabeticaZA(contas);
        exibirContas(contasFiltradas);
    }
}




