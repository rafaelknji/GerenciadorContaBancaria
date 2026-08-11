package model;
import exception.SaldoInsuficienteException;

public class ContaCorrente extends Conta{
    public ContaCorrente(String titular, int numero, double saldo) {
        super(titular, numero, saldo);
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException{
        if(valor <= 0){
            throw new SaldoInsuficienteException("O valor de saque deve ser positivo!");
        }
        if(valor > getSaldo()){
            throw new SaldoInsuficienteException("Saldo insuficiente!");
        }
        setSaldo(getSaldo() - valor);
    }
}
