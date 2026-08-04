package model;
import exception.SaldoInsuficienteException;
import org.w3c.dom.ls.LSOutput;

public class ContaCorrente extends Conta{
    public ContaCorrente(String titular, int numero, double saldo) {
        super(titular, numero, saldo);
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException{
        if(valor > getSaldo()){
            throw new SaldoInsuficienteException("Saldo insuficiente!");
        }
        setSaldo(getSaldo() - valor);
    }

}
