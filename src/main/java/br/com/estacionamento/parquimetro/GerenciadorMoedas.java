package br.com.estacionamento.parquimetro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Gerencia o estoque de moedas do equipamento e fornece troco quando possível.
 */
public class GerenciadorMoedas {
    private final EnumMap<ValorMoeda, Integer> estoque = new EnumMap<>(ValorMoeda.class);

    public GerenciadorMoedas() {
        // inicializa com zero
        for (ValorMoeda v : ValorMoeda.values()) {
            estoque.put(v, 0);
        }
    }

    public synchronized void adicionarMoeda(ValorMoeda moeda) {
        estoque.put(moeda, estoque.getOrDefault(moeda, 0) + 1);
    }

    public synchronized void adicionarMoedas(ValorMoeda moeda, int quantidade) {
        estoque.put(moeda, estoque.getOrDefault(moeda, 0) + quantidade);
    }

    public synchronized int obterQuantidade(ValorMoeda moeda) {
        return estoque.getOrDefault(moeda, 0);
    }

    public synchronized Map<ValorMoeda, Integer> getEstoque() {
        return new EnumMap<>(estoque);
    }

    /**
     * Tenta calcular o troco exato para a quantia em centavos. Se possível, decrementa o estoque
     * e retorna um mapa com as moedas que serão entregues como troco. Se não for possível, retorna null.
     */
    public synchronized Map<ValorMoeda, Integer> fornecerTroco(int trocoCentavos) {
        if (trocoCentavos <= 0) {
            return Collections.emptyMap();
        }

        // algoritmo guloso: começar pelas moedas maiores
        List<ValorMoeda> ordenadas = new ArrayList<>();
        Collections.addAll(ordenadas, ValorMoeda.values());
        // ordenar decrescente por centavos
        ordenadas.sort((a, b) -> Integer.compare(b.getCentavos(), a.getCentavos()));

        EnumMap<ValorMoeda, Integer> usado = new EnumMap<>(ValorMoeda.class);
        int restante = trocoCentavos;

        for (ValorMoeda moeda : ordenadas) {
            int valor = moeda.getCentavos();
            int disponivel = estoque.getOrDefault(moeda, 0);
            int maxNecessario = restante / valor;
            int usar = Math.min(maxNecessario, disponivel);
            if (usar > 0) {
                usado.put(moeda, usar);
                restante -= usar * valor;
            }
        }

        if (restante != 0) {
            // não é possível fornecer troco exato com o estoque atual
            return null;
        }

        // decrementar estoque
        for (Map.Entry<ValorMoeda, Integer> e : usado.entrySet()) {
            ValorMoeda m = e.getKey();
            int q = e.getValue();
            estoque.put(m, estoque.getOrDefault(m, 0) - q);
        }

        return usado;
    }
}
