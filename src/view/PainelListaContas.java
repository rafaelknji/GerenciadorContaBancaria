package view;

import javax.swing.*;

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
        add(scrollPane);
    }

    public JList<String> getListaContas() {
        return listaContas;
    }

    public DefaultListModel<String> getModeloLista() {
        return modeloLista;
    }
}

