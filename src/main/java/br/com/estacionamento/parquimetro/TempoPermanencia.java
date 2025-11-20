package br.com.estacionamento.parquimetro;

/**
 * Tempo de permanência e preço correspondente (em centavos).
 */
public enum TempoPermanencia {
    MIN30(30, 50),
    HORA1(60, 100),
    HORA2(120, 200);

    private final int minutos;
    private final int precoCentavos;

    TempoPermanencia(int minutos, int precoCentavos) {
        this.minutos = minutos;
        this.precoCentavos = precoCentavos;
    }

    public int getMinutos() {
        return minutos;
    }

    public int getPrecoCentavos() {
        return precoCentavos;
    }
}
