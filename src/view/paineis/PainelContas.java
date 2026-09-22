package view.paineis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PainelContas extends JPanel{
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public PainelContas() {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Contas Cadastradas"));

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

    public JTable getTabela() {
        return tabela;
    }

    public DefaultTableModel getModeloTabela() {
        return modeloTabela;
    }
}

