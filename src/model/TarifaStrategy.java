package model;

public enum TarifaStrategy {
    FIXA {
        @Override
        public double calcularTarifa(double saldo) {
            return 10.0;
        }
    },

    PERCENTUAL {
        @Override
        public double calcularTarifa(double saldo) {
            return saldo * 0.01;
        }
    },

    ISENTA {
        @Override
        public double calcularTarifa(double saldo) {
            return 0;
        }
    };

    public abstract double calcularTarifa(double saldo);
}
