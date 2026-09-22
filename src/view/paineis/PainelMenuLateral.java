package view.paineis;

import javax.swing.*;
import java.awt.*;

public class PainelMenuLateral extends JPanel {

    private JButton btnInicio;
    private JButton btnNovaConta;
    private JButton btnListarContas;
    private JButton btnTransferencia;
    private JButton btnHistorico;
    private JButton btnConfiguracoes;

    public PainelMenuLateral() {

        setLayout(new GridLayout(8, 1, 0, 5));

        setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        btnInicio = new JButton("Início");
        btnNovaConta = new JButton("Nova Conta");
        btnListarContas = new JButton("Listar Contas");
        btnTransferencia = new JButton("Transferência");
        btnHistorico = new JButton("Histórico de Transações");

        add(btnInicio);
        add(btnNovaConta);
        add(btnListarContas);
        add(btnTransferencia);
        add(btnHistorico);
    }

    public JButton getBtnInicio() {
        return btnInicio;
    }

    public JButton getBtnNovaConta() {
        return btnNovaConta;
    }

    public JButton getBtnListarContas() {
        return btnListarContas;
    }

    public JButton getBtnTransferencia() {
        return btnTransferencia;
    }

    public JButton getBtnHistorico() {
        return btnHistorico;
    }
}