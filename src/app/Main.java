package app;

import model.ContaCorrente;
import service.ContaService;
import view.ContaGUI;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        ContaGUI gui = new ContaGUI();
        gui.setVisible(true);



        /*ContaService contaService = new ContaService();

        List<ContaCorrente> contas = contaService.lerContas("conta.txt");

        List<ContaCorrente> resultado =
                contaService.ordenarSaldoDecrescente(contas);

        for (ContaCorrente conta : resultado) {
            System.out.println(
                    conta.getNumero() + " - " + conta.getTitular() + " - " + conta.getSaldo());
        }*/

    }
}