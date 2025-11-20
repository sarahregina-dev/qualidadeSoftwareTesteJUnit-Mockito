package br.com.estacionamento.parquimetro;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
    private final String placa;
    private final LocalDateTime inicio;
    private final LocalDateTime fim;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Ticket(String placa, LocalDateTime inicio, LocalDateTime fim) {
        this.placa = placa;
        this.inicio = inicio;
        this.fim = fim;
    }

    public String getPlaca() {
        return placa;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- TICKET DE ESTACIONAMENTO ---\n");
        sb.append("Placa: ").append(placa).append('\n');
        sb.append("Hora início: ").append(inicio.format(FORMAT)).append('\n');
        sb.append("Hora fim: ").append(fim.format(FORMAT)).append('\n');
        sb.append("-------------------------------\n");
        return sb.toString();
    }
}
