package br.com.estacionamento.parquimetro;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TempoPermanenciaTest {

    @Test
    void calculaPrecoCorretoPorIntervalo() {
        // 30 minutos -> R$0,50 -> 50 centavos
        assertEquals(50, TempoPermanencia.MIN30.getPrecoCentavos(), "Preço para 30 minutos deve ser 50 centavos");

        // 60 minutos -> R$1,00 -> 100 centavos
        assertEquals(100, TempoPermanencia.HORA1.getPrecoCentavos(), "Preço para 60 minutos deve ser 100 centavos");

        // 120 minutos -> R$2,00 -> 200 centavos
        assertEquals(200, TempoPermanencia.HORA2.getPrecoCentavos(), "Preço para 120 minutos deve ser 200 centavos");
    }
}

zipStoreBase=PROJECT
zipStorePath=.mvn/wrapper/dists
