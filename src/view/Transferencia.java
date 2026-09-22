package view;

import service.ContaService;
import javax.swing.*;
import java.awt.*;

public class Transferencia extends JDialog {
    private JTextField txtNumeroOrigem;
    private JTextField txtNumeroDestino;
    private JTextField txtValor;
    private JButton btnTransferir;
    private JButton btnCancelar;
    private ContaService contaService;

    public Transferencia(JFrame pai) {
        super(pai, "Transferência", true);

        contaService = new ContaService();

        criarInterface();

        setSize(400, 300);
        setLocationRelativeTo(pai);
    }

    private void criarInterface() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelCampos = new JPanel(new GridLayout(3, 2, 10, 10));

        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        txtNumeroOrigem = new JTextField();
        txtNumeroDestino = new JTextField();
        txtValor = new JTextField();

        painelCampos.add(new JLabel("Conta origem:"));
        painelCampos.add(txtNumeroOrigem);

        painelCampos.add(new JLabel("Conta destino:"));
        painelCampos.add(txtNumeroDestino);

        painelCampos.add(new JLabel("Valor:"));
        painelCampos.add(txtValor);

        add(painelCampos, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();

        btnTransferir = new JButton("Transferir");
        btnCancelar = new JButton("Cancelar");

        painelBotoes.add(btnTransferir);
        painelBotoes.add(btnCancelar);

        add(painelBotoes, BorderLayout.SOUTH);

        btnTransferir.addActionListener(e -> transferir());

        btnCancelar.addActionListener(e -> dispose());
    }

    private void transferir() {

        try {
            int numeroOrigem = Integer.parseInt(txtNumeroOrigem.getText());

            int numeroDestino = Integer.parseInt(txtNumeroDestino.getText());

            double valor = Double.parseDouble(txtValor.getText().replace(",", "."));

            if (valor <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Digite um valor maior que zero.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (numeroOrigem == numeroDestino) {
                JOptionPane.showMessageDialog(
                        this,
                        "A conta de origem e destino devem ser diferentes.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            contaService.transferir(numeroOrigem, numeroDestino, valor);

            JOptionPane.showMessageDialog(this, "Transferência realizada com sucesso!");

            dispose();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Número da conta ou valor inválido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
