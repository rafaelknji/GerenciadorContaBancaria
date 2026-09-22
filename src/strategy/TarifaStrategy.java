package strategy;

public enum TarifaStrategy {
    FIXA {
        @Override
        public double calcularTarifa(double valor) {
            return 10.0;
        }
    },

    PERCENTUAL {
        @Override
        public double calcularTarifa(double valor) {
            return valor * 0.01;
        }
    },

    ISENTA {
        @Override
        public double calcularTarifa(double valor) {
            return 0;
        }
    };

    public abstract double calcularTarifa(double valor);
}
