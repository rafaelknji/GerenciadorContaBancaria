package view;

import model.HistoricoTransferencia;
import service.ContaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Historico extends JDialog {

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private ContaService contaService;

    public Historico(
            JFrame pai,
            ContaService contaService) {

        super(
                pai,
                "Histórico de Transferências",
                true
        );

        this.contaService = contaService;

        criarInterface();
        carregarHistorico();

        setSize(
                700,
                400
        );

        setLocationRelativeTo(pai);
    }

    private void criarInterface() {

        setLayout(
                new BorderLayout(
                        10,
                        10
                )
        );

        JPanel painel =
                new JPanel(
                        new BorderLayout()
                );

        painel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        modeloTabela =
                new DefaultTableModel(
                        new Object[]{
                                "Origem",
                                "Destino",
                                "Valor",
                                "Tarifa"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        tabela =
                new JTable(
                        modeloTabela
                );

        tabela.setRowHeight(30);

        painel.add(
                new JScrollPane(tabela),
                BorderLayout.CENTER
        );

        add(
                painel,
                BorderLayout.CENTER
        );
    }

    private void carregarHistorico() {

        modeloTabela.setRowCount(0);

        for (
                HistoricoTransferencia transferencia
                : contaService.getHistorico()
        ) {

            modeloTabela.addRow(
                    new Object[]{
                            transferencia
                                    .getNumeroOrigem(),

                            transferencia
                                    .getNumeroDestino(),

                            String.format(
                                    "R$ %.2f",
                                    transferencia.getValor()
                            ),

                            String.format(
                                    "R$ %.2f",
                                    transferencia.getTarifa()
                            )
                    }
            );
        }
    }
}