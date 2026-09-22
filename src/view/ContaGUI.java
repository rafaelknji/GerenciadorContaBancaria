package view;

import model.ContaCorrente;
import service.ContaService;
import view.paineis.PainelContaSelecionada;
import view.paineis.PainelContas;
import view.paineis.PainelMenuLateral;
import view.paineis.PainelOperacao;
import view.paineis.PainelResumo;
import javax.swing.*;
import java.awt.*;

public class ContaGUI extends JFrame {

    private ContaService contaService;
    private PainelMenuLateral painelMenu;
    private PainelContas painelContas;
    private PainelContaSelecionada painelContaSelecionada;
    private PainelOperacao painelOperacao;
    private PainelResumo painelResumo;

    public ContaGUI() {
        contaService = new ContaService();

        configurarJanela();
        criarInterface();
        carregarDados();
        configurarEventos();
    }

    private void configurarJanela() {
        setTitle("Gerenciador de Contas Bancárias");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void criarInterface() {
        setLayout(new BorderLayout());

        painelMenu = new PainelMenuLateral();
        painelMenu.setPreferredSize(new Dimension(220, 0));

        painelContas = new PainelContas(contaService);

        painelContaSelecionada = new PainelContaSelecionada();
        painelContaSelecionada.setPreferredSize(new Dimension(350, 0));

        painelOperacao = new PainelOperacao();

        painelResumo = new PainelResumo();
        painelResumo.setPreferredSize(new Dimension(0, 150));

        add(painelMenu, BorderLayout.WEST);

        JPanel centro = new JPanel(new BorderLayout(10, 10));

        centro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        centro.add(painelContas, BorderLayout.CENTER);

        centro.add(painelResumo, BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);

        JPanel painelDireito = new JPanel(new BorderLayout(10, 10));

        painelDireito.add(painelContaSelecionada, BorderLayout.CENTER);

        painelDireito.add(painelOperacao, BorderLayout.SOUTH);
        add(painelDireito, BorderLayout.EAST);
    }

    private void carregarDados() {
        painelContas.carregarDados();
        painelResumo.atualizar(painelContas.getContasExibidas(), contaService);
    }

    private void configurarEventos() {
        painelContas.getTabela().getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        ContaCorrente conta = painelContas.getContaSelecionada();
                        painelContaSelecionada.mostrarConta(conta);
                    }
                });

        painelContaSelecionada.getBtnAtualizar()
                .addActionListener(e -> {

                    ContaCorrente conta = painelContas.getContaSelecionada();

                    boolean sucesso = painelContaSelecionada.atualizarSaldo(conta, contaService);

                    if (sucesso) {
                        carregarDados();
                        painelContaSelecionada.mostrarConta(conta);
                    }
                });

        painelContaSelecionada.getBtnExcluir()
                .addActionListener(e -> {

                    ContaCorrente conta = painelContas.getContaSelecionada();

                    boolean sucesso = painelContaSelecionada.excluirConta(conta, contaService);

                    if (sucesso) {
                        carregarDados();
                        painelContaSelecionada.limpar();
                    }
                });

        painelOperacao.getBtnDepositar().addActionListener(e -> {
                    ContaCorrente conta = painelContas.getContaSelecionada();

                    boolean sucesso = painelOperacao.depositar(conta, contaService);

                    if (sucesso) {
                        carregarDados();
                        painelContaSelecionada.mostrarConta(conta);
                    }
                });

        painelOperacao.getBtnSacar().addActionListener(e -> {
                    ContaCorrente conta = painelContas.getContaSelecionada();

                    boolean sucesso = painelOperacao.sacar(conta, contaService);

                    if (sucesso) {
                        carregarDados();
                        painelContaSelecionada.mostrarConta(conta);
                    }
                });

        painelMenu.getBtnNovaConta().addActionListener(e -> {

                    CriarConta janela = new CriarConta(this);
                    janela.setVisible(true);
                    carregarDados();
                });

        painelMenu.getBtnTransferencia().addActionListener(e -> {

                    Transferencia janela = new Transferencia(this);
                    janela.setVisible(true);
                    carregarDados();
                });
    }
}