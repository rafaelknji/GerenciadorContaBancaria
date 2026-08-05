package model;
import exception.SaldoInsuficienteException;

public abstract class Conta {
    private int numero;
    private String titular;
    private double saldo;

    public Conta(String titular, int numero, double saldo) {
        this.titular = titular;
        this.numero = numero;
        this.saldo = saldo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


    public abstract void sacar(double valor) throws SaldoInsuficienteException;

    public void depositar(double valor){
        if(valor > 0){
            this.saldo += valor;
        }

    }

    public void imprimirDados(){
        System.out.println("Numero da conta: " + this.numero);
        System.out.println("Titular: " + this.titular);
        System.out.println("Saldo: " + this.saldo);
    }




}
