package view;

import javax.swing.*;

public class PainelOperacoes extends JPanel {

    private JTextField txtValor;

    private JButton btnSacar;
    private JButton btnDepositar;
    private JButton btnFiltros;
    private JButton btnAgrupar;

    public PainelOperacoes() {

        txtValor = new JTextField(10);
        btnSacar = new JButton("Sacar");
        btnDepositar = new JButton("Depositar");
        btnFiltros = new JButton("Filtros");
        btnAgrupar = new JButton("Agrupar");

        add(new JLabel("Valor:"));
        add(txtValor);
        add(btnSacar);
        add(btnDepositar);
        add(btnFiltros);
        add(btnAgrupar);
    }

    public JTextField getTxtValor() {
        return txtValor;
    }

    public JButton getBtnSacar() {
        return btnSacar;
    }

    public JButton getBtnDepositar() {
        return btnDepositar;
    }

    public JButton getBtnFiltros() {
        return btnFiltros;
    }

    public JButton getBtnAgrupar() {
        return btnAgrupar;
    }
}

