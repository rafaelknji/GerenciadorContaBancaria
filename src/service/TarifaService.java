package service;

import model.TarifaStrategy;

public class TarifaService {

    public double calcularTarifa(double saldo, TarifaStrategy strategy) {
        return strategy.calcularTarifa(saldo);
    }
}
