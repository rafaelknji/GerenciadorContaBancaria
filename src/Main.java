import exception.SaldoInsuficienteException;
import model.ContaCorrente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        //lendo o arquivo
        try {
            BufferedReader br = new BufferedReader(new FileReader("conta.txt"));

            String linha = br.readLine();
            br.close();

            // SEPARAR OS DADOS
            String[] dados = linha.split(",");

            int numero = Integer.parseInt(dados[0]);
            String titular = dados[1];
            double saldo = Double.parseDouble(dados[2]);

            ContaCorrente conta = new ContaCorrente(titular, numero, saldo);


            System.out.println("Digite o valor que deseja sacar: ");
            double valor = sc.nextDouble();

        try{
            conta.sacar(valor);
        }
        catch (SaldoInsuficienteException e){
            System.out.println(e.getMessage());
        }

        //escrevendo novo arquivo
            BufferedWriter bw = new BufferedWriter(new FileWriter("conta_atualizada.txt"));

            bw.write(conta.getNumero() + "," + conta.getTitular() + "," + conta.getSaldo());
            bw.close();

            System.out.println("Arquivo atualizado criado!");

        }

        catch(IOException e) {
            System.out.println("Erro no arquivo: " + e.getMessage());
        }


        }
}
