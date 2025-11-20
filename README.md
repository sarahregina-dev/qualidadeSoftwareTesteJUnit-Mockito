# Parquímetro 
Sistema de parquímetro em Java desenvolvido para praticar testes unitários com JUnit e Mockito.

Este repositório contém uma implementação simples em Java de um parquímetro para estacionamento
que atende aos requisitos do enunciado: entrada de placa, seleção de tempo (30 min / 1 h / 2 h),
pagamento em moedas (R$0,50; R$1; R$2), verificação e disponibilização de troco, gerenciamento de
moedas e geração de ticket com placa, hora início e hora fim exibidos em tela.

# Para rodar
Verifique o Java/JDK:
java -version
javac -version

Compile todas as fontes para uma pasta out:
mkdir -Force out
$files = Get-ChildItem -Path .\src\main\java -Recurse -Filter *.java | ForEach-Object FullName
javac -d out $files

Execute a classe principal:
java -cp out br.com.estacionamento.parquimetro.Parquimetro****



