package view.paineis;

import model.ContaCorrente;
import service.ContaService;
import javax.swing.*;
import java.awt.*;

public class PainelContaSelecionada extends JPanel {
    private JLabel lblNumero;
    private JLabel lblTitular;
    private JTextField txtSaldo;
    private JButton btnAtualizar;
    private JButton btnExcluir;

    public PainelContaSelecionada() {
        setLayout(new BorderLayout(10, 10));

        setBorder(BorderFactory.createTitledBorder("Conta Selecionada"));

        JPanel dados = new JPanel();

        dados.setLayout(new BoxLayout(dados, BoxLayout.Y_AXIS));

        lblNumero = new JLabel("Número: ");
        lblTitular = new JLabel("Titular: ");

        lblNumero.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitular.setFont(new Font("Arial", Font.PLAIN, 18));

        dados.add(lblNumero);
        dados.add(Box.createVerticalStrut(10));
        dados.add(lblTitular);

        JPanel saldo = new JPanel(new BorderLayout());

        JLabel tituloSaldo = new JLabel("Saldo atual");

        txtSaldo = new JTextField();
        txtSaldo.setFont(new Font("Arial", Font.BOLD, 28));

        saldo.add(tituloSaldo, BorderLayout.NORTH);

        saldo.add(txtSaldo, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new GridLayout(1, 2, 10, 0));

        btnAtualizar = new JButton("Atualizar");

        btnExcluir = new JButton("Excluir Conta");

        botoes.add(btnAtualizar);
        botoes.add(btnExcluir);

        JPanel centro = new JPanel(new BorderLayout(10, 10));

        centro.add(dados, BorderLayout.NORTH);

        centro.add(saldo, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);

        add(botoes, BorderLayout.SOUTH);
    }

    public void mostrarConta(ContaCorrente conta) {
        if (conta == null) {
            limpar();
            return;
        }
        lblNumero.setText("Número: " + conta.getNumero());
        lblTitular.setText("Titular: " + conta.getTitular());
        txtSaldo.setText(String.format("%.2f", conta.getSaldo()));
    }

    public boolean atualizarSaldo(ContaCorrente conta, ContaService contaService) {
        if (conta == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma conta.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        try {
            double novoSaldo = Double.parseDouble(txtSaldo.getText().replace(",", "."));

            if (novoSaldo < 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "O saldo não pode ser negativo.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return false;
            }

            contaService.atualizarSaldo(conta, novoSaldo);
            JOptionPane.showMessageDialog(
                    this,
                    "Saldo atualizado com sucesso!"
            );
            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Digite um valor válido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public boolean excluirConta(ContaCorrente conta, ContaService contaService) {
        if (conta == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma conta.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        int resposta = JOptionPane.showConfirmDialog(
                        this,
                        "Deseja realmente excluir a conta "
                                + conta.getNumero()
                                + "?",
                        "Confirmar exclusão",
                        JOptionPane.YES_NO_OPTION
                );

        if (resposta != JOptionPane.YES_OPTION) {
            return false;
        }

        contaService.removerConta(conta);

        JOptionPane.showMessageDialog(this, "Conta excluída com sucesso!");
        return true;
    }

    public void limpar() {
        lblNumero.setText("Número: -");
        lblTitular.setText("Titular: -");
        txtSaldo.setText("0,00");
    }

    public JLabel getLblNumero() {
        return lblNumero;
    }

    public JLabel getLblTitular() {
        return lblTitular;
    }

    public JTextField getTxtSaldo() {
        return txtSaldo;
    }

    public JButton getBtnAtualizar() {
        return btnAtualizar;
    }

    public JButton getBtnExcluir() {
        return btnExcluir;
    }
}