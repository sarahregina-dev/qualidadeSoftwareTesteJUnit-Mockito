package br.com.estacionamento.parquimetro;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlacaMercosulTest {

    @Test
    void placasValidasDevemPassarNaValidacao() {
        assertTrue(Parquimetro.validarPlacaMercosul("ABC1D23"));
        assertTrue(Parquimetro.validarPlacaMercosul("XYZ9E88"));
        assertTrue(Parquimetro.validarPlacaMercosul("FGH4C67"));
    }

    @Test
    void placasInvalidasDevemFalharNaValidacao() {
        assertFalse(Parquimetro.validarPlacaMercosul("ABC-1234"));
        assertFalse(Parquimetro.validarPlacaMercosul("abc1d23"));
        assertFalse(Parquimetro.validarPlacaMercosul("ABC1D2"));
        assertFalse(Parquimetro.validarPlacaMercosul("AB12C34"));
        assertFalse(Parquimetro.validarPlacaMercosul("A1C1D23"));
    }
}
