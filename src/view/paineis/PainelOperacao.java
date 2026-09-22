package view.paineis;

import exception.SaldoInsuficienteException;
import model.ContaCorrente;
import service.ContaService;

import javax.swing.*;
import java.awt.*;

public class PainelOperacao extends JPanel {
    private JTextField txtValor;
    private JButton btnDepositar;
    private JButton btnSacar;

    public PainelOperacao() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Operações"));

        JLabel lblValor = new JLabel("Valor:");

        txtValor = new JTextField();

        JPanel campo = new JPanel(new BorderLayout(10, 0));

        campo.add(lblValor, BorderLayout.WEST);
        campo.add(txtValor, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new GridLayout(1, 2, 10, 0));

        btnDepositar = new JButton("Depositar");
        btnSacar = new JButton("Sacar");

        botoes.add(btnDepositar);
        botoes.add(btnSacar);

        add(campo, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
    }

    public boolean depositar(
            ContaCorrente conta,
            ContaService contaService) {

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
            double valor = Double.parseDouble(
                    txtValor.getText().replace(",", ".")
            );

            if (valor <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Digite um valor maior que zero.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );
                return false;
            }

            contaService.depositar(conta, valor);

            JOptionPane.showMessageDialog(
                    this,
                    "Depósito realizado com sucesso!"
            );
            txtValor.setText("");
            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Digite um valor válido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public boolean sacar(
            ContaCorrente conta,
            ContaService contaService) {

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
            double valor = Double.parseDouble(
                    txtValor.getText().replace(",", ".")
            );

            if (valor <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Digite um valor maior que zero.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );
                return false;
            }
            contaService.sacar(conta, valor);
            JOptionPane.showMessageDialog(
                    this,
                    "Saque realizado com sucesso!"
            );
            txtValor.setText("");
            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Digite um valor válido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;

        } catch (SaldoInsuficienteException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public JTextField getTxtValor() {
        return txtValor;
    }

    public JButton getBtnDepositar() {
        return btnDepositar;
    }

    public JButton getBtnSacar() {
        return btnSacar;
    }
}