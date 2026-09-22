package service;

import strategy.TarifaStrategy;

public class TarifaService {

    public double calcularTarifa(double saldo, TarifaStrategy strategy) {
        return strategy.calcularTarifa(saldo);
    }
}
