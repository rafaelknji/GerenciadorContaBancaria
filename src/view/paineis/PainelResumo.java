package view.paineis;

import model.ContaCorrente;
import service.ContaService;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelResumo extends JPanel {
    private JLabel lblTotalContas;
    private JLabel lblSaldoTotal;

    public PainelResumo() {

        setLayout(new GridLayout(1, 2, 20, 0));

        setBorder(BorderFactory.createTitledBorder("Resumo"));

        JPanel painelContas = new JPanel();

        painelContas.setLayout(new BoxLayout(painelContas, BoxLayout.Y_AXIS));

        painelContas.add(new JLabel("Total de contas"));

        lblTotalContas = new JLabel("0");

        lblTotalContas.setFont(new Font("Arial", Font.BOLD, 26));

        painelContas.add(lblTotalContas);

        JPanel painelSaldo = new JPanel();

        painelSaldo.setLayout(new BoxLayout(painelSaldo, BoxLayout.Y_AXIS));

        painelSaldo.add(new JLabel("Saldo total"));

        lblSaldoTotal = new JLabel("R$ 0,00");

        lblSaldoTotal.setFont(new Font("Arial", Font.BOLD, 26));

        painelSaldo.add(lblSaldoTotal);

        add(painelContas);
        add(painelSaldo);
    }

    public void atualizar(List<ContaCorrente> contas, ContaService contaService) {
        double saldoTotal = contaService.calcularSaldoTotal(contas);

        lblTotalContas.setText(String.valueOf(contas.size()));

        lblSaldoTotal.setText(String.format("R$ %.2f", saldoTotal));
    }

    public JLabel getLblTotalContas() {
        return lblTotalContas;
    }

    public JLabel getLblSaldoTotal() {
        return lblSaldoTotal;
    }
}