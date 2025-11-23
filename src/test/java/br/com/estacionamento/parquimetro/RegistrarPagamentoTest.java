package br.com.estacionamento.parquimetro;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrarPagamentoTest {

    @Test
    void testRegistrarPagamento() {
        double valorTotal = 2.00;
        double valorPago = 2.00;
        double troco = Parquimetro.registrarPagamento(valorTotal, valorPago);
        assertEquals(0.00, troco, 0.001, "Troco é 0.00 quando pagamento é exato");

        valorPago = 1.50;
        troco = Parquimetro.registrarPagamento(valorTotal, valorPago);
        assertEquals(0.00, troco, 0.001, "Troco é 0.00 quando pagamento insuficiente ou seja transação rejeitada");
    }
}
