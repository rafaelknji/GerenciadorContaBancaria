package view;

import javax.swing.*;
import java.awt.*;

public class PainelDados extends JPanel {

    private JLabel lblNumero;
    private JLabel lblTitular;
    private JLabel lblSaldo;
    private JButton btnOrdenar;

    public PainelDados() {
        setLayout(new GridLayout(1, 4, 30, 10));

        lblNumero = new JLabel("Numero: ");
        lblTitular = new JLabel("Titular: ");
        lblSaldo = new JLabel("Saldo: ");

        add(lblNumero);
        add(lblTitular);
        add(lblSaldo);
    }

    public JLabel getLblNumero() {
        return lblNumero;
    }

    public JLabel getLblTitular() {
        return lblTitular;
    }

    public JLabel getLblSaldo() {
        return lblSaldo;
    }

    public JButton getBtnOrdenar() {
        return btnOrdenar;
    }
}

