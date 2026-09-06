package view;

import javax.swing.*;
import java.awt.*;

public class PainelListaContas extends JPanel {

    private JList<String> listaContas;
    private DefaultListModel<String> modeloLista;

    public PainelListaContas() {
        modeloLista = new DefaultListModel<>();
        listaContas = new JList<>(modeloLista);

        listaContas.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        JScrollPane scrollPane = new JScrollPane(listaContas);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    public JList<String> getListaContas() {
        return listaContas;
    }

    public DefaultListModel<String> getModeloLista() {
        return modeloLista;
    }
}

