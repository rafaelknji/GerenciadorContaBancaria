package view.paineis;

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

        lblNumero = new JLabel("Titular: -");
        lblTitular = new JLabel("Numero: -");

        dados.add(lblNumero);
        dados.add(Box.createVerticalStrut(10));
        dados.add(lblTitular);


        JPanel saldo = new JPanel(new BorderLayout());


        JLabel tituloSaldo = new JLabel("Saldo atual");
        txtSaldo = new JTextField("0,00");

        txtSaldo.setFont(new Font("Arial", Font.BOLD, 28));

        saldo.add(tituloSaldo, BorderLayout.NORTH);
        saldo.add(txtSaldo, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new GridLayout(1, 2, 10, 0));

        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");

        botoes.add(btnAtualizar);
        botoes.add(btnExcluir);

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.add(dados, BorderLayout.NORTH);
        centro.add(saldo, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

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

