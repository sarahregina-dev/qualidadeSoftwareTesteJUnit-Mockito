package br.com.estacionamento.parquimetro;

/**
 * Valores de moedas representados em centavos para evitar problemas com ponto flutuante.
 */
public enum ValorMoeda {
    CENT50(50),
    ONE(100),
    TWO(200);

    private final int centavos;

    ValorMoeda(int centavos) {
        this.centavos = centavos;
    }

    public int getCentavos() {
        return centavos;
    }
}
