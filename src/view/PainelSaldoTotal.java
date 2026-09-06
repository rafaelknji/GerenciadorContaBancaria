package view;

import javax.swing.*;
import java.awt.*;

public class PainelSaldoTotal extends JPanel {
    private JLabel lblSaldoTotal;
    private JLabel lblResultadoSaldoTotal;

    public PainelSaldoTotal() {

        setLayout(new BorderLayout());

        lblSaldoTotal = new JLabel("Saldo Total:");
        lblResultadoSaldoTotal = new JLabel("00,00");

        add(lblSaldoTotal, BorderLayout.WEST);
        add(lblResultadoSaldoTotal, BorderLayout.EAST);

        setBorder(
                BorderFactory.createEmptyBorder(0, 5, 0, 10)
        );
    }

    public JLabel getLblResultadoSaldoTotal() {
        return lblResultadoSaldoTotal;
    }
}

