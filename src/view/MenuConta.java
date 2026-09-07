package view;

import javax.swing.*;

public class MenuConta {
    private ContaGUI contaGUI;

    public MenuConta(ContaGUI contaGUI) {
        this.contaGUI = contaGUI;
    }


    public void configurarMenuOrdem(JButton btnOrdenar) {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem itemCrescente = new JMenuItem("Crescente(0-9)");
        itemCrescente.addActionListener(e -> contaGUI.ordenarSaldoCrescente());

        JMenuItem itemDecrescente = new JMenuItem("Decrescente(9-0)");
        itemDecrescente.addActionListener(e -> contaGUI.ordenarSaldoDecrescente());

        JMenuItem itemAlfabeticaAZ = new JMenuItem("Titular(A-Z)");
        itemAlfabeticaAZ.addActionListener(e -> contaGUI.ordemAlfabeticaAZ());

        JMenuItem itemAlfabeticaZA = new JMenuItem("Titular(Z-A)");
        itemAlfabeticaZA.addActionListener(e -> contaGUI.ordemAlfabeticaZA());

        menu.add(itemCrescente);
        menu.add(itemDecrescente);
        menu.add(itemAlfabeticaAZ);
        menu.add(itemAlfabeticaZA);

        btnOrdenar.addActionListener(e ->
                menu.show(btnOrdenar, 0, btnOrdenar.getHeight())
        );
    }


    public void configurarMenuFiltros(JButton btnFiltros) {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem itemFiltrar5k = new JMenuItem("Saldo > 5K");
        itemFiltrar5k.addActionListener(e -> contaGUI.filtrarSaldoMaior5k());

        JMenuItem itemFiltrar10k = new JMenuItem("Saldo > R$ 10K");
        itemFiltrar10k.addActionListener(e -> contaGUI.filtrarSaldoMaior10000());

        JMenuItem itemLimparFiltro = new JMenuItem("Limpar filtros");
        itemLimparFiltro.addActionListener(e -> {
            contaGUI.carregarLista();
            contaGUI.atualizarSaldoTotal();
        });

        menu.add(itemFiltrar5k);
        menu.add(itemFiltrar10k);
        menu.add(itemLimparFiltro);

        btnFiltros.addActionListener(e ->
                menu.show(btnFiltros, 0, btnFiltros.getHeight())
        );
    }


    public void configurarMenuAgrupar(JButton btnAgrupar) {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem itemAte5000 = new JMenuItem("Até R$ 5.000");
        itemAte5000.addActionListener(
                e -> contaGUI.agruparSaldo("Até R$ 5.000")
        );

        JMenuItem itemAte10000 = new JMenuItem("de R$ 5.000 a R$ 10.000");
        itemAte10000.addActionListener(
                e -> contaGUI.agruparSaldo("R$ 5.000 a R$ 10.000")
        );

        JMenuItem itemMaior10000 = new JMenuItem("Acima R$ 10.000");
        itemMaior10000.addActionListener(
                e -> contaGUI.agruparSaldo("Acima de R$ 10.000")
        );

        menu.add(itemAte5000);
        menu.add(itemAte10000);
        menu.add(itemMaior10000);

        btnAgrupar.addActionListener(e ->
                menu.show(btnAgrupar, 0, btnAgrupar.getHeight())
        );
    }
}

