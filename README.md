# Parquímetro — projeto de exemplo

Este repositório contém uma implementação simples em Java de um parquímetro para estacionamento
que atende aos requisitos do enunciado: entrada de placa, seleção de tempo (30 min / 1 h / 2 h),
pagamento em moedas (R$0,50; R$1; R$2), verificação e disponibilização de troco, gerenciamento de
moedas e geração de ticket com placa, hora início e hora fim exibidos em tela.

Como está organizado
- Código-fonte: `src/main/java/br/com/estacionamento/parquimetro`
- Testes: `src/test/java/br/com/estacionamento/parquimetro` (JUnit 5)

Como compilar e executar
1. Compilar com Maven:

```pwsh
mvn -q -DskipTests compile
```

2. Executar testes:

```pwsh
mvn -q test
```

3. Executar a aplicação (console):

Após compilar, você pode executar a classe `Parquimetro` diretamente:

```pwsh
java -cp target/classes br.com.estacionamento.parquimetro.Parquimetro
```

Observações
- O programa de console inicializa o gerenciador de moedas com algumas moedas para permitir a simulação de troco.
- Se ocorrer falha ao fornecer troco, a transação é rejeitada e as moedas são devolvidas (simulado).

Melhorias possíveis
- Adicionar interface gráfica/web.
- Persistir o estoque de moedas em arquivo ou banco.
- Mensagens de erro mais elaboradas e logs.

---

# qualidadeSoftwareTesteJUnit-Mockito
Sistema de parquímetro em Java desenvolvido para praticar testes unitários com JUnit e Mockito.
