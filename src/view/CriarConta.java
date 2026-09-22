package view;

import connection.ContaDAO;
import model.ContaCorrente;
import javax.swing.*;
import java.awt.*;

public class CriarConta extends JDialog{
    private JTextField txtNumero;
    private JTextField txtTitular;
    private JTextField txtSaldo;
    private JButton btnCadastrar;
    private JButton btnCancelar;
    private ContaDAO contaDAO;

    public CriarConta(JFrame pai) {
        super(pai, "Nova Conta", true);

        contaDAO = new ContaDAO();

        criarInterface();

        setSize(400, 300);
        setLocationRelativeTo(pai);
    }

    private void criarInterface() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelCampos = new JPanel(new GridLayout(3, 2, 10, 10));

        painelCampos.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 5, 20)
        );

        txtNumero = new JTextField();
        txtTitular = new JTextField();
        txtSaldo = new JTextField();

        painelCampos.add(new JLabel("Número:"));
        painelCampos.add(txtNumero);

        painelCampos.add(new JLabel("Titular:"));
        painelCampos.add(txtTitular);

        painelCampos.add(new JLabel("Saldo inicial:"));
        painelCampos.add(txtSaldo);

        add(painelCampos, BorderLayout.CENTER);


        JPanel painelBotoes = new JPanel();

        btnCadastrar = new JButton("Cadastrar");
        btnCancelar = new JButton("Cancelar");

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnCancelar);

        add(painelBotoes, BorderLayout.SOUTH);


        btnCadastrar.addActionListener(e -> cadastrar());

        btnCancelar.addActionListener(e -> dispose());
    }

    private void cadastrar() {
        try {

            int numero = Integer.parseInt(txtNumero.getText());
            String titular = txtTitular.getText();
            double saldo = Double.parseDouble(txtSaldo.getText());

            ContaCorrente conta = new ContaCorrente(
                    titular,
                    numero,
                    saldo
            );

            contaDAO.inserir(conta);

            JOptionPane.showMessageDialog(
                    this,
                    "Conta cadastrada com sucesso!"
            );

            dispose();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Número ou saldo inválido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
