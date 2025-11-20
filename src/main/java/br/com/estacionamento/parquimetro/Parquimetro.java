package br.com.estacionamento.parquimetro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe principal que realiza a lógica do parquímetro: entrada de placa, seleção de tempo, pagamento,
 * verificação de troco e geração de ticket.
 */
public class Parquimetro {
    private final GerenciadorMoedas gerenciador;

    public Parquimetro(GerenciadorMoedas gerenciador) {
        this.gerenciador = gerenciador;
    }

    public static class ResultadoPagamento {
        private final Ticket ticket;
        private final Map<ValorMoeda, Integer> troco;

        public ResultadoPagamento(Ticket ticket, Map<ValorMoeda, Integer> troco) {
            this.ticket = ticket;
            this.troco = troco;
        }

        public Ticket getTicket() {
            return ticket;
        }

        public Map<ValorMoeda, Integer> getTroco() {
            return troco;
        }
    }

    /**
     * Tenta realizar o pagamento. Se bem-sucedido e houver troco possível, retorna um ResultadoPagamento.
     * Se não for possível fornecer troco quando necessário, retorna null.
     */
    public ResultadoPagamento realizarPagamento(String placa, TempoPermanencia tempo, List<ValorMoeda> inseridas) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("Placa inválida");
        }

        int valorNecessario = tempo.getPrecoCentavos();
        int somaInserida = inseridas.stream().mapToInt(ValorMoeda::getCentavos).sum();

        if (somaInserida < valorNecessario) {
            // pagamento insuficiente
            return null;
        }

        int troco = somaInserida - valorNecessario;

        // primeiro, adicionar as moedas inseridas ao gerenciador (o parquímetro passa a ter essas moedas)
        for (ValorMoeda m : inseridas) {
            gerenciador.adicionarMoeda(m);
        }

        // tentar fornecer troco (se houver)
        Map<ValorMoeda, Integer> trocoFornecido = null;
        if (troco > 0) {
            trocoFornecido = gerenciador.fornecerTroco(troco);
            if (trocoFornecido == null) {
                // não há como fornecer troco exato -> desfazer: retirar moedas inseridas
                for (ValorMoeda m : inseridas) {
                    gerenciador.adicionarMoedas(m, -1);
                }
                return null;
            }
        }

        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusMinutes(tempo.getMinutos());
        Ticket ticket = new Ticket(placa, inicio, fim);

        return new ResultadoPagamento(ticket, trocoFornecido);
    }

    // Pequena interface de console para demonstração
    public static void main(String[] args) {
    GerenciadorMoedas gm = new GerenciadorMoedas();
    // inicializa conforme requisito: 3 moedas de 0,50; 1 moeda de 1 real; 0 moedas de 2 reais
    gm.adicionarMoedas(ValorMoeda.CENT50, 3);
    gm.adicionarMoedas(ValorMoeda.ONE, 1);
    // ValorMoeda.TWO já começa com zero

        Parquimetro p = new Parquimetro(gm);
        Scanner sc = new Scanner(System.in);

        System.out.println("Bem-vindo ao parquímetro\n");
        System.out.print("Digite a placa do veículo: ");
        String placa = sc.nextLine().trim();
        // Validação simples: placa no formato ABC-1234 ou ABC1D23 (padrão Mercosul)
        if (!placa.matches("^[A-Z]{3}-?\d{1}[A-Z0-9]{1}\d{2}$") && !placa.matches("^[A-Z]{3}-?\d{4}$")) {
            System.out.println("Placa inválida. Encerrando.");
            sc.close();
            return;
        }

        System.out.println("Informe o tempo de permanência desejado (em minutos):");
        String tempoStr = sc.nextLine().trim();
        int tempoMin;
        try {
            tempoMin = Integer.parseInt(tempoStr);
        } catch (NumberFormatException e) {
            System.out.println("Tempo inválido. Encerrando.");
            sc.close();
            return;
        }
        // Arredondamento para cima conforme especificação
        TempoPermanencia tempo;
        if (tempoMin <= 30) {
            tempo = TempoPermanencia.MIN30;
        } else if (tempoMin <= 60) {
            tempo = TempoPermanencia.HORA1;
        } else if (tempoMin <= 120) {
            tempo = TempoPermanencia.HORA2;
        } else {
            tempo = TempoPermanencia.HORA2;
        }

        System.out.printf("Valor a pagar: R$ %.2f (aceita R$0,50; R$1; R$2)\n", tempo.getPrecoCentavos() / 100.0);

        List<ValorMoeda> inseridas = new ArrayList<>();
        int soma = 0;
        while (soma < tempo.getPrecoCentavos()) {
            System.out.print("Insira moeda (0.5, 1, 2) ou 'ok' para finalizar: ");
            String tok = sc.nextLine().trim();
            if (tok.equalsIgnoreCase("ok")) {
                break;
            }
            try {
                double v = Double.parseDouble(tok.replace(',', '.'));
                ValorMoeda moeda = switch ((int) Math.round(v * 100)) {
                    case 50 -> ValorMoeda.CENT50;
                    case 100 -> ValorMoeda.ONE;
                    case 200 -> ValorMoeda.TWO;
                    default -> null;
                };
                if (moeda == null) {
                    System.out.println("Moeda inválida");
                } else {
                    inseridas.add(moeda);
                    soma += moeda.getCentavos();
                    System.out.printf("Total inserido: R$ %.2f\n", soma / 100.0);
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida");
            }
        }

        ResultadoPagamento res = p.realizarPagamento(placa, tempo, inseridas);
        if (res == null) {
            System.out.println("Pagamento não realizado (valor insuficiente ou troco indisponível). Moedas devolvidas.");
        } else {
            System.out.println(res.getTicket());
            if (res.getTroco() != null && !res.getTroco().isEmpty()) {
                System.out.println("Troco entregue:");
                for (Map.Entry<ValorMoeda, Integer> e : res.getTroco().entrySet()) {
                    System.out.printf("  %d x R$ %.2f\n", e.getValue(), e.getKey().getCentavos() / 100.0);
                }
            } else {
                System.out.println("Sem troco.");
            }
        }

        sc.close();
    }
}
