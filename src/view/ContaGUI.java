package view;
import model.ContaCorrente;

import view.paineis.PainelContas;
import view.paineis.PainelContaSelecionada;
import view.paineis.PainelMenuLateral;
import view.paineis.PainelResumo;

import connection.ContaDAO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ContaGUI extends JFrame {
    private PainelContas painelContas;
    private PainelContaSelecionada painelContaSelecionada;
    private PainelMenuLateral painelMenuLateral;
    private PainelResumo painelResumo;

    private List<ContaCorrente> contasExibidas;
    private ContaDAO contaDAO;

    public ContaGUI() {
        contaDAO = new ContaDAO();
        contasExibidas = new ArrayList<>();

        criarInterface();
        carregarLista();

        setTitle("Gerenciador de Contas Bancárias");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void criarInterface() {
        setLayout(new BorderLayout());

        // Menu Lateral
        painelMenuLateral = new PainelMenuLateral();
        painelMenuLateral.setPreferredSize(new Dimension(220, 0));
        add(painelMenuLateral, BorderLayout.WEST);

        // Tabela de contas
        painelContas = new PainelContas();

        // conta selecionada
        painelContaSelecionada = new PainelContaSelecionada();
        painelContaSelecionada.setPreferredSize(new Dimension(350, 0));

        // Resumo
        painelResumo = new PainelResumo();
        painelResumo.setPreferredSize(new Dimension(0, 150));

        // Painel central
        JPanel painelCentro = new JPanel(new BorderLayout(10, 10));

        painelCentro.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        painelCentro.add(painelContas, BorderLayout.CENTER);
        painelCentro.add(painelResumo, BorderLayout.SOUTH);

        add(painelCentro, BorderLayout.CENTER);
        add(painelContaSelecionada, BorderLayout.EAST);

        // Seleção da tabela
        painelContas.getTabela().getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        mostrarContaSelecionada();
                    }
                });


        // Btn excluir
        painelContaSelecionada.getBtnExcluir()
                .addActionListener(e -> excluirConta());

        // Btn atualizar
        painelContaSelecionada.getBtnAtualizar()
                .addActionListener(e -> atualizarSaldo());

        painelMenuLateral.getBtnNovaConta()
                .addActionListener(e -> {
                    CriarConta janela = new CriarConta(this);
                    janela.setVisible(true);
                    carregarLista();
                });
    }


    public void carregarLista() {
        contasExibidas = contaDAO.listar();

        painelContas.getModeloTabela().setRowCount(0);

        for (ContaCorrente conta : contasExibidas) {

            painelContas.getModeloTabela().addRow(new Object[]{
                    conta.getNumero(),
                    conta.getTitular(),
                    String.format("R$ %.2f", conta.getSaldo()),
                    "-"
            });
        }
        atualizarResumo();
    }

    private void atualizarSaldo() {
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
            double novoSaldo = Double.parseDouble(
                    painelContaSelecionada.getTxtSaldo().getText().replace(",", ".")
            );
            contaDAO.atualizarSaldo(conta.getNumero(), novoSaldo);

            JOptionPane.showMessageDialog(
                    this,
                    "Saldo atualizado com sucesso!"
            );

            carregarLista();
            limparContaSelecionada();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite um valor válido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private ContaCorrente getContaSelecionada() {
        int linha = painelContas.getTabela().getSelectedRow();;

        if (linha == -1) {
            return null;
        }
        return contasExibidas.get(linha);
    }

    private void mostrarContaSelecionada() {
        ContaCorrente conta = getContaSelecionada();

        if (conta == null) {
            return;
        }

        painelContaSelecionada.getLblNumero().setText("Número: " + conta.getNumero());
        painelContaSelecionada.getLblTitular().setText("Titular: " + conta.getTitular());
        painelContaSelecionada.getTxtSaldo().setText(String.format("%.2f", conta.getSaldo()));
    }

    private void excluirConta() {
        ContaCorrente conta = getContaSelecionada();

        if(conta == null) {
            JOptionPane.showMessageDialog(
                    this, "Selecione uma conta",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir a conta " + conta.getNumero() + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {

            contaDAO.remover(conta.getNumero());

            JOptionPane.showMessageDialog(
                    this,
                    "Conta excluída com sucesso!"
            );

            carregarLista();
            limparContaSelecionada();
        }
    }


    public void atualizarResumo() {
        double saldoTotal = 0;

        for (ContaCorrente conta : contasExibidas) {
            saldoTotal += conta.getSaldo();
        }

        painelResumo.getLblTotalContas()
                .setText(String.valueOf(contasExibidas.size()));

        painelResumo.getLblSaldoTotal()
                .setText(String.format("R$ %.2f", saldoTotal));
    }


    public void atualizarTela() {
        carregarLista();
        int linha = painelContas.getTabela().getSelectedRow();
        if (linha != -1) {
            mostrarContaSelecionada();
        }
    }


    private void limparContaSelecionada() {
        painelContaSelecionada.getLblNumero().setText("Número: -");
        painelContaSelecionada.getLblTitular().setText("Titular: -");
        painelContaSelecionada.getTxtSaldo().setText("R$ 0,00");
    }
}






