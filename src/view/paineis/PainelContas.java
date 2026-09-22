package view.paineis;

import model.ContaCorrente;
import service.ContaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PainelContas extends JPanel {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JMenu menuFiltro;
    private List<ContaCorrente> contasExibidas;
    private ContaService contaService;

    public PainelContas(ContaService contaService) {
        this.contaService = contaService;
        this.contasExibidas = new ArrayList<>();

        criarInterface();
        configurarFiltro();
    }

    private void criarInterface() {
        setLayout(new BorderLayout(10, 10));

        setBorder(BorderFactory.createTitledBorder("Contas Cadastradas"));

        // Cabeçalho
        JPanel cabecalho = new JPanel(new BorderLayout());

        menuFiltro = new JMenu("Filtro");

        JMenuItem itemTodas = new JMenuItem("Todas as contas");
        JMenuItem itemSaldo5000 = new JMenuItem("Saldo maior que 5000");
        JMenuItem itemContaPar = new JMenuItem("Conta par");
        JMenuItem itemSaldoDecrescente = new JMenuItem("Saldo decrescente");
        JMenuItem itemAlfabetica = new JMenuItem("Ordem alfabética A-Z");

        menuFiltro.add(itemTodas);
        menuFiltro.addSeparator();
        menuFiltro.add(itemSaldo5000);
        menuFiltro.add(itemContaPar);
        menuFiltro.add(itemSaldoDecrescente);
        menuFiltro.add(itemAlfabetica);

        cabecalho.add(menuFiltro, BorderLayout.EAST);

        add(cabecalho, BorderLayout.NORTH);

        // Tabela
        modeloTabela = new DefaultTableModel(
                new Object[]{"Número", "Titular", "Saldo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabela.setRowHeight(35);

        JScrollPane scrollPane = new JScrollPane(tabela);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void configurarFiltro() {
        for (Component componente : menuFiltro.getMenuComponents()) {

            if (componente instanceof JMenuItem item) {

                item.addActionListener(e -> aplicarFiltro(item.getText()));
            }
        }
    }

    public void carregarDados() {
        contasExibidas = contaService.getContas();
        atualizarTabela();
    }

    private void aplicarFiltro(String filtro) {

        List<ContaCorrente> contas = contaService.getContas();

        switch (filtro) {

            case "Saldo maior que 5000":
                contasExibidas = contaService.filtrarSaldoMaior5k(contas);
                break;

            case "Conta par":
                contasExibidas = contaService.filtrarContaPar(contas);
                break;

            case "Saldo decrescente":
                contasExibidas = contaService.ordenarSaldoDecrescente(contas);
                break;

            case "Ordem alfabética A-Z":
                contasExibidas = contaService.ordemAlfabeticaAZ(contas);
                break;

            default:
                contasExibidas = contas;
                break;
        }
        atualizarTabela();
    }

    private void atualizarTabela() {

        modeloTabela.setRowCount(0);

        for (ContaCorrente conta : contasExibidas) {

            modeloTabela.addRow(new Object[]{
                            conta.getNumero(),
                            conta.getTitular(),
                            String.format("R$ %.2f", conta.getSaldo())
                    }
            );
        }
    }

    public ContaCorrente getContaSelecionada() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            return null;
        }
        return contasExibidas.get(linha);
    }

    public JTable getTabela() {
        return tabela;
    }

    public DefaultTableModel getModeloTabela() {
        return modeloTabela;
    }

    public List<ContaCorrente> getContasExibidas() {
        return contasExibidas;
    }
}