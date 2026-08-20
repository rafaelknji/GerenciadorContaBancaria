package view;

import exception.SaldoInsuficienteException;
import model.ContaCorrente;
import service.ContaService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ContaGUI extends JFrame {

    private List<ContaCorrente> contas;
    private ContaService contaService;

    private JList<String> listaContas;
    private DefaultListModel<String> modeloLista;

    private JLabel lblNumero;
    private JLabel lblTitular;
    private JLabel lblSaldo;

    private JTextField txtValor;

    private JButton btnSacar;
    private JButton btnDepositar;

    public ContaGUI() {

        contaService = new ContaService();

        try {
            contas = contaService.lerContas("conta.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar contas: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        criarInterface();
        carregarLista();

        setTitle("Gerenciador de Contas Bancárias");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }


    private void criarInterface() {

        setLayout(new BorderLayout());

        // Lista das contas
        modeloLista = new DefaultListModel<>();
        listaContas = new JList<>(modeloLista);

        listaContas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(listaContas);

        add(scrollPane, BorderLayout.CENTER);

        // Informações da conta selecionada
        JPanel painelDados = new JPanel();
        painelDados.setLayout(new GridLayout(3, 1));

        lblNumero = new JLabel("Número: ");
        lblTitular = new JLabel("Titular: ");
        lblSaldo = new JLabel("Saldo: ");

        painelDados.add(lblNumero);
        painelDados.add(lblTitular);
        painelDados.add(lblSaldo);

        add(painelDados, BorderLayout.NORTH);

        // Operações
        JPanel painelOperacoes = new JPanel();

        txtValor = new JTextField(10);

        btnSacar = new JButton("Sacar");
        btnDepositar = new JButton("Depositar");

        painelOperacoes.add(new JLabel("Valor:"));
        painelOperacoes.add(txtValor);
        painelOperacoes.add(btnSacar);
        painelOperacoes.add(btnDepositar);

        add(painelOperacoes, BorderLayout.SOUTH);

        // Quando selecionar uma conta
        listaContas.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                mostrarContaSelecionada();
            }

        });

        // Botão sacar
        btnSacar.addActionListener(e -> sacar());

        // Botão depositar
        btnDepositar.addActionListener(e -> depositar());
    }

    private void carregarLista() {

        modeloLista.clear();

        for (ContaCorrente conta : contas) {

            modeloLista.addElement(
                    conta.getNumero()
                            + " - "
                            + conta.getTitular()
                            + " - R$ "
                            + conta.getSaldo()
            );
        }
    }

    private ContaCorrente getContaSelecionada() {

        int indice = listaContas.getSelectedIndex();

        if (indice == -1) {
            return null;
        }

        return contas.get(indice);
    }

    private void mostrarContaSelecionada() {

        ContaCorrente conta = getContaSelecionada();

        if (conta == null) {
            return;
        }

        lblNumero.setText("Número: " + conta.getNumero());
        lblTitular.setText("Titular: " + conta.getTitular());
        lblSaldo.setText("Saldo: R$ " + conta.getSaldo());
    }

    private void sacar() {

        ContaCorrente conta = getContaSelecionada();

        if (conta == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma conta.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {

            double valor = Double.parseDouble(txtValor.getText());

            conta.sacar(valor);

            JOptionPane.showMessageDialog(
                    this, "Saque realizado com sucesso!"
            );

            atualizarTela();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this, "Digite um valor válido.", "Erro", JOptionPane.ERROR_MESSAGE
            );

        } catch (SaldoInsuficienteException e) {

            JOptionPane.showMessageDialog(
                    this, e.getMessage(), "Saque não realizado!", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void depositar() {

        ContaCorrente conta = getContaSelecionada();

        if (conta == null) {
            JOptionPane.showMessageDialog(
                    this, "Selecione uma conta.", "Aviso", JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {

            double valor = Double.parseDouble(txtValor.getText());

            if (valor <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "O valor do depósito deve ser positivo.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            conta.depositar(valor);

            JOptionPane.showMessageDialog(this, "Depósito realizado com sucesso!");

            atualizarTela();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this, "Digite um valor válido.", "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void atualizarTela() {

        carregarLista();
        mostrarContaSelecionada();

        try {
            contaService.atualizarConta(contas, "contas_atualizadas.txt");
        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this, "Erro ao salvar contas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

