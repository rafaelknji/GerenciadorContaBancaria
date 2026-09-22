package model;

public class HistoricoTransferencia {

    private int numeroOrigem;
    private int numeroDestino;
    private double valor;
    private double tarifa;

    public HistoricoTransferencia(
            int numeroOrigem,
            int numeroDestino,
            double valor,
            double tarifa) {

        this.numeroOrigem = numeroOrigem;
        this.numeroDestino = numeroDestino;
        this.valor = valor;
        this.tarifa = tarifa;
    }

    public int getNumeroOrigem() {
        return numeroOrigem;
    }

    public int getNumeroDestino() {
        return numeroDestino;
    }

    public double getValor() {
        return valor;
    }

    public double getTarifa() {
        return tarifa;
    }
}