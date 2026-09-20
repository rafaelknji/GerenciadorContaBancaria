package app;

import view.ContaGUI;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        ContaGUI gui = new ContaGUI();
        gui.setVisible(true);


        /*ContaService contaService = new ContaService();
        List<ContaCorrente> contas = contaService.lerContas("conta.txt");
        List<ContaCorrente> resultado = contaService.ordenarSaldoDecrescente(contas);
        for (ContaCorrente conta : resultado) {
            System.out.println(
                    conta.getNumero() + " - " + conta.getTitular() + " - " + conta.getSaldo());
        }*/


        /*contaDAO dao = new contaDAO();

        Conta conta = new ContaCorrente("Kenji", 123, 1000.0);
        dao.inserir(conta);
        System.out.println("Conta inserida!");

        //Testar listagem
        System.out.println("\nContas cadastradas:");
        List<Conta> contas = dao.listar();
        for (Conta c : contas) {
            System.out.println(c);
        }

        //Testar depósito
        Conta contaBusca = dao.buscarPorNumero(123);
        if (contaBusca != null) {
            double novoSaldo = contaBusca.getSaldo() + 500;
            dao.atualizarSaldo(123, novoSaldo);
            System.out.println("\nDepósito de R$ 500 realizado!");
            System.out.println("Novo saldo: R$ " + novoSaldo);
        }

        //Testar saque
        contaBusca = dao.buscarPorNumero(123);
        if (contaBusca != null) {
            double novoSaldo = contaBusca.getSaldo() - 200;
            dao.atualizarSaldo(123, novoSaldo);
            System.out.println("\nSaque de R$ 200 realizado!");
            System.out.println("Novo saldo: R$ " + novoSaldo);
        }
        */


    }


}