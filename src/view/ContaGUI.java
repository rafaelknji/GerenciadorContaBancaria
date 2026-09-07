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
    private MenuConta menu;


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


        // Painel Saldo total
        painelSaldoTotal = new PainelSaldoTotal();

        // Operações
        painelOperacoes = new PainelOperacoes();

        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(new GridLayout(2, 1));
        painelInferior.add(painelSaldoTotal);
        painelInferior.add(painelOperacoes);
        add(painelInferior, BorderLayout.SOUTH);

        // Menus
        menu = new MenuConta(this);
        menu.configurarMenuOrdem(painelDados.getBtnOrdenar());
        menu.configurarMenuFiltros(painelOperacoes.getBtnFiltros());
        menu.configurarMenuAgrupar(painelOperacoes.getBtnAgrupar());

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
        painelLista.getModeloLista().clear();

        for (ContaCorrente conta : contas) {
            painelLista.getModeloLista().addElement(
                    String.format("%-49s %-52s R$ %.2f", conta.getNumero(), conta.getTitular(), conta.getSaldo())
            );
        }
    }

    public void carregarLista() {
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

    public void filtrarSaldoMaior10000() {
        List<ContaCorrente> contasFiltradas =
                contaService.filtrarSaldoMaior10000(contas);

        exibirContas(contasFiltradas);

        double saldoTotal = contaService.calcularSaldoTotal(contasFiltradas);

        painelSaldoTotal.getLblResultadoSaldoTotal().setText(String.format("R$ %.2f", saldoTotal));
    }

    public void agruparSaldo(String categoria) {
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

    public void atualizarSaldoTotal() {
        double saldoTotal = contaService.calcularSaldoTotal(contas);

        painelSaldoTotal.getLblResultadoSaldoTotal().setText(
                String.format("R$ %.2f", saldoTotal)
        );
    }

    public void atualizarTela() {
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

    public void filtrarSaldoMaior5k() {
        List<ContaCorrente> contasFiltradas = contaService.filtrarSaldoMaior5k(contas);
        exibirContas(contasFiltradas);
    }

    public void filtrarContaPar() {
        List<ContaCorrente> contasFiltradas = contaService.filtrarContaPar(contas);
        exibirContas(contasFiltradas);
    }

    public void ordenarSaldoDecrescente() {
        List<ContaCorrente> contasFiltradas = contaService.ordenarSaldoDecrescente(contas);
        exibirContas(contasFiltradas);
    }

    public void ordenarSaldoCrescente() {
        List<ContaCorrente> contasFiltradas = contaService.ordenarSaldoCrescente(contas);
        exibirContas(contasFiltradas);
    }

    public void ordemAlfabeticaAZ() {
        List<ContaCorrente> contasFiltradas = contaService.ordemAlfabeticaAZ(contas);
        exibirContas(contasFiltradas);
    }

    public void ordemAlfabeticaZA() {
        List<ContaCorrente> contasFiltradas = contaService.ordemAlfabeticaZA(contas);
        exibirContas(contasFiltradas);
    }
}




