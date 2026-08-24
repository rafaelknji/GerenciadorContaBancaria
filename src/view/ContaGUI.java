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
    private JLabel lblSaldoTotal;
    private JLabel lblResultadoSaldoTotal;

    private JTextField txtValor;

    private JButton btnSacar;
    private JButton btnDepositar;
    private JButton btnFiltros;
    private JButton btnAgrupar;


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
        atualizarSaldoTotal();

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
        painelDados.setLayout(new GridLayout(1, 3, 30, 10));

        lblNumero = new JLabel("Número: ");
        lblTitular = new JLabel("Titular: ");
        lblSaldo = new JLabel("Saldo: ");

        painelDados.add(lblNumero);
        painelDados.add(lblTitular);
        painelDados.add(lblSaldo);

        add(painelDados, BorderLayout.NORTH);

        //Painel Total Saldo
        JPanel painelSaldoTotal = new JPanel();
        painelSaldoTotal.setLayout(new BorderLayout());

        lblSaldoTotal = new JLabel("Saldo Total:");
        lblResultadoSaldoTotal = new JLabel("00,00");

        painelSaldoTotal.add(lblSaldoTotal, BorderLayout.WEST);
        painelSaldoTotal.add(lblResultadoSaldoTotal, BorderLayout.EAST);

        painelSaldoTotal.setBorder(
                BorderFactory.createEmptyBorder(0, 5, 0, 10)
        );

        // Operações
        JPanel painelOperacoes = new JPanel();

        txtValor = new JTextField(10);

        btnSacar = new JButton("Sacar");
        btnDepositar = new JButton("Depositar");
        btnFiltros = new JButton("Filtros");
        btnAgrupar = new JButton("Agrupar");

        painelOperacoes.add(new JLabel("Valor:"));
        painelOperacoes.add(txtValor);
        painelOperacoes.add(btnSacar);
        painelOperacoes.add(btnDepositar);
        painelOperacoes.add(btnFiltros);
        painelOperacoes.add(btnAgrupar);

        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(new GridLayout(2, 1));
        painelInferior.add(painelSaldoTotal);
        painelInferior.add(painelOperacoes);
        add(painelInferior, BorderLayout.SOUTH);


        //menu de filtros gerais
        JPopupMenu menuFiltros = new JPopupMenu();

        JMenuItem itemFiltrar = new JMenuItem("Saldo > R$ 10K");
        itemFiltrar.addActionListener(e -> filtrarSaldoMaior10000());

        JMenuItem itemLimparFiltro = new JMenuItem("Limpar filtros");
        itemLimparFiltro.addActionListener(e -> {
            carregarLista();
            atualizarSaldoTotal();
        });

        menuFiltros.add(itemFiltrar);
        menuFiltros.add(itemLimparFiltro);

        btnFiltros.addActionListener(e -> menuFiltros.show(btnFiltros, 0, btnFiltros.getHeight()));




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
                    String.format(
                            "%-49s %-52s R$ %.2f",
                            conta.getNumero(), conta.getTitular(), conta.getSaldo()
                    )
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
            JOptionPane.showMessageDialog(this, "Saque realizado com sucesso!");

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
                    this, "Selecione uma conta.", "Aviso", JOptionPane.WARNING_MESSAGE);
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

    private void filtrarSaldoMaior10000() {
        List<ContaCorrente> contasFiltradas =
                contaService.filtrarSaldoMaior10000(contas);

        modeloLista.clear();

        for (ContaCorrente conta : contasFiltradas) {
            modeloLista.addElement(String.format(
                    "%-49s %-52s R$ %.2f",
                    conta.getNumero(), conta.getTitular(), conta.getSaldo()
            ));
        }

        double saldoTotal = contaService.calcularSaldoTotal(contasFiltradas);

        lblResultadoSaldoTotal.setText(String.format("R$ %.2f", saldoTotal));
    }

    private void atualizarSaldoTotal() {
        double saldoTotal = contaService.calcularSaldoTotal(contas);

        lblResultadoSaldoTotal.setText(
                String.format("R$ %.2f", saldoTotal)
        );
    }

    private void atualizarTela() {

        carregarLista();
        mostrarContaSelecionada();
        atualizarSaldoTotal();

        try {
            contaService.atualizarConta(contas, "contas_atualizadas.txt");
        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this, "Erro ao salvar contas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

