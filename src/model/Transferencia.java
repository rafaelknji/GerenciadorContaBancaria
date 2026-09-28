package model;
import java.time.LocalDateTime;

public class Transferencia {
    private int id;
    private int origem;
    private int destino;
    private double valor;
    private double tarifa;
    private LocalDateTime dataHora;

    public Transferencia(int id, int origem, int destino, double valor, double tarifa, LocalDateTime dataHora) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
        this.tarifa = tarifa;
        this.dataHora = dataHora;
    }

    public int getId() {
        return id;
    }

    public int getOrigem() {
        return origem;
    }

    public int getDestino() {
        return destino;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public double getTarifa(){
        return tarifa;
    }
}


