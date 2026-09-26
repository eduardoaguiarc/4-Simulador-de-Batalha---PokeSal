# Relatório detalhado dos testes

## Identificação e resultado

Execução de **26/09/2026 às 00:31:40 (America/Bahia, UTC−03:00)**, sobre o
projeto Simulador de Batalha Pokésal. Commit de referência:
`532b38ab1cd2d2dfbb3e1e8054e22bfe49a5b180`. Os hashes dos arquivos analisados
estão no [manifesto da execução](evidencias/execucao.json), pois havia alterações
locais de configuração e documentação ainda não commitadas.

**O JUnit executou oito testes e aprovou os oito, sem falhas, erros ou testes
ignorados. Entretanto, somente sete possuem verificações: CT-07 está vazio.**
A aprovação da suíte não demonstra atendimento integral dos requisitos.

| Indicador | Resultado |
| --- | ---: |
| Classes de teste | 5 |
| Testes descobertos / executados / aprovados pelo JUnit | 8 / 8 / 8 |
| Falhas / erros / ignorados / abortados | 0 / 0 / 0 / 0 |
| Métodos de teste com asserções | 7 |
| Métodos de teste vazios | 1 |
| Tempo informado pelo console JUnit | 330 ms |
| Tempo da suíte Jupiter no XML | 0,189 s |
| Cobertura de linhas JaCoCo | 196 / 490 = **40,0%** |
| Cobertura de ramos JaCoCo | 70 / 240 = **29,2%** |

Os tempos do console e da suíte têm escopos distintos e não incluem download
das ferramentas nem compilação. Não são um ensaio de desempenho.

## Objetivo, escopo e método

O objetivo foi executar a suíte existente, registrar entradas, resultados
esperados e limitações de cada teste, e relacionar as verificações aos
requisitos descritos no [README](../../README.md) e às regras do código.
A [matriz de rastreabilidade](MATRIZ-RASTREABILIDADE.md) fornece esse vínculo.

Foram compilados os 14 arquivos Java de produção em `src` e as cinco classes
em `test`. Os testes fazem chamadas diretas a objetos de domínio; alguns
integram Batalha, Treinador, Mochila e itens. São usados cenários nominais,
limites e exceções esperadas. Não houve execução de uma partida completa
pelo console, teste manual de aceitação, teste de carga ou teste de segurança.
Nenhum teste ou regra de negócio foi alterado para produzir este relatório.

Uma aprovação significa que o método terminou sem erro e suas asserções,
quando presentes, foram satisfeitas. Código apenas percorrido sem asserção
sobre seu efeito não é considerado requisito validado.

## Ambiente e reprodução

| Componente | Configuração |
| --- | --- |
| Sistema | Windows 11, amd64 |
| JDK | Azul OpenJDK 25.0.4.1 |
| Compilação | `javac --release 21 -encoding UTF-8 -g` |
| Testes | JUnit Jupiter 5.10.2 / Platform Console Standalone 1.10.2 |
| Cobertura | JaCoCo 0.8.14, agente durante os testes e relatório XML/HTML |
| Escopo da cobertura | Todos os arquivos de produção em `src`, sem exclusões |

Na raiz do projeto, com JDK 21 ou superior disponível:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File scripts/analisar-sonar.ps1 -SomenteTestes
```

O [script](../../scripts/analisar-sonar.ps1) baixa as ferramentas do Maven
Central na primeira execução, limpa suas pastas de classes e relatórios,
compila e executa os testes. A modalidade `-SomenteTestes` não depende do
SonarQube nem de token. Os resultados novos ficam em:

- `out/quality/tests.log`: resumo da execução;
- `out/quality/junit/TEST-junit-jupiter.xml`: resultados por método;
- `out/quality/jacoco.xml`: contadores de cobertura;
- `out/quality/coverage/index.html`: navegação pela cobertura do código.

Este documento e suas evidências são um registro da execução identificada
acima; executar o script novamente não atualiza automaticamente esta análise
textual nem a matriz. Novas medições exigem revisar os documentos e renovar
as evidências em conjunto.

## Catálogo dos casos executados

Os identificadores CT foram atribuídos neste relatório. Os nomes completos
no XML distinguem os dois métodos chamados `testMaximoDeUsoGolpes`.

| ID | Classe e método | Tempo no XML | Resultado |
| --- | --- | ---: | --- |
| CT-01 | `ArenaTest.testEfeitoTerrenoEstacionamentoUCSal` | 0,010 s | Aprovado |
| CT-02 | `BatalhaTest.testOrdemDeAtaquePorVelocidade` | 0,001 s | Aprovado |
| CT-03 | `BatalhaTest.testUsoLimiteDeItensExcedido` | 0,026 s | Aprovado |
| CT-04 | `BatalhaTest.testCalculoDanoBoundaryValues` | 0,007 s | Aprovado |
| CT-05 | `GolpeTest.testMaximoDeUsoGolpes` | 0,053 s | Aprovado |
| CT-06 | `PokesalTest.testMaximoDeGolpes` | 0,002 s | Aprovado |
| CT-07 | `PokesalTest.testMaximoDeUsoGolpes` | 0,001 s | Aprovado pelo executor; vazio |
| CT-08 | `TipoElementalTest.testVantagemElemental` | 0,000 s | Aprovado |

### CT-01 — Efeitos dos terrenos

**Fonte:** [ArenaTest.java](../../test/br/edu/ucsal/pokesal/ArenaTest.java).
**Requisitos:** RF-05, RF-06 e RF-07.

**Preparação e passos:** criar as arenas Asfalto Quente, Poça de Chuva e
Canteiro Central; consultar os multiplicadores dos três tipos nas duas
primeiras; criar um Pokésal de Planta com HP máximo 100, aplicar dano 20 e
aplicar uma vez o efeito do canteiro.

**Esperado e observado pelas asserções:** Asfalto retorna 1,15 para Fogo e
1,0 para Água/Planta; Poça retorna 1,10 para Água e 1,0 para Fogo/Planta.
O HP é 80 depois do dano e 85 após a cura. Todas as verificações passaram.

**Limites:** não verifica arredondamento da cura, HP perto do máximo, alvo
derrotado, outros tipos no canteiro nem integração dos bônus ao ataque.

### CT-02 — Ordem por velocidade

**Fonte:** [BatalhaTest.java](../../test/br/edu/ucsal/pokesal/BatalhaTest.java).
**Requisito:** RF-08.

**Preparação e passos:** dois treinadores distintos, com Pokésal de velocidades
100 e 10, sem status; o mais rápido ocupa a posição de treinador 1.
Consultar `determinarPrimeiroAtacante()`.

**Esperado e observado:** retorna o treinador de velocidade 100; asserção
aprovada. **Limites:** não verifica treinador 2 mais rápido, empate, paralisia
nem a execução efetiva das ações nessa ordem.

### CT-03 — Limite de itens

**Fonte:** [BatalhaTest.java](../../test/br/edu/ucsal/pokesal/BatalhaTest.java).
**Requisito:** RF-18.

**Preparação e passos:** Pokésal com HP 100 e sem status; mochila com um antídoto
e dois itens de cura de 20 HP. O mesmo objeto Treinador é passado nas duas
posições da batalha. Usar o antídoto e a primeira cura; tentar a segunda cura.

**Esperado e observado:** os dois primeiros usos terminam sem exceção; o terceiro
lança `IllegalStateException`, confirmada por `assertThrows`.

**Limites:** não verifica contadores separados de dois treinadores reais,
remoção dos itens ou consumo da ação. Como o alvo está saudável e com HP
completo, este teste não comprova recuperação de HP nem remoção de um status
existente. As mensagens de uso no log, por si só, não comprovam esses efeitos.

### CT-04 — Limites do dano e do HP

**Fonte:** [BatalhaTest.java](../../test/br/edu/ucsal/pokesal/BatalhaTest.java).
**Requisitos:** RI-01 e RI-02.

**Preparação e passos:** acessar `Batalha.calcularDano` por reflexão, com
`setAccessible(true)`, usando terreno Canteiro e tipos Fogo contra Fogo
(multiplicadores neutros). Executar os três cenários:

| Entrada | Esperado | Resultado das asserções |
| --- | --- | --- |
| ATK 10 + poder 5 contra DEF 100 | Dano 1 | Confirmado |
| ATK 0 + poder 0 contra DEF 0 | Dano 1 | Confirmado |
| Alvo com HP 1 recebe dano 9999 | HP final 0 | Confirmado |

**Limites:** não cobre dano positivo comum, vantagem/desvantagem combinada com
terreno, queimadura ou arredondamento. A reflexão acopla o teste ao nome e à
assinatura de um método privado. HP igual a zero é verificado, mas o
encerramento da batalha e o anúncio de vencedor não são exercitados.

### CT-05 — Esgotamento dos usos de um golpe

**Fonte:** [GolpeTest.java](../../test/br/edu/ucsal/pokesal/GolpeTest.java).
**Requisito:** RE-03.

**Preparação e passos:** criar golpe com máximo de três usos; chamar
`consumirUso()` três vezes e tentar uma quarta chamada.

**Esperado e observado:** as três primeiras chamadas terminam sem exceção e a
quarta lança `IllegalStateException`; asserção aprovada.
**Limites:** não verifica o contador após cada uso, reset, seleção de golpe
esgotado no console ou consumo de uso quando o ataque erra.

### CT-06 — Limite de quatro golpes

**Fonte:** [PokesalTest.java](../../test/br/edu/ucsal/pokesal/PokesalTest.java).
**Requisito:** RE-01.

**Preparação e passos:** criar um Pokésal com lista vazia; adicionar quatro
vezes a mesma instância de Golpe e tentar a quinta inclusão.

**Esperado e observado:** quatro inclusões sem exceção; a quinta lança
`IllegalStateException`, confirmada pela asserção.
**Limites:** não valida quatro golpes distintos, tamanho final da lista,
lista inicial acima do limite nem os golpes dos seis iniciais criados em Main.

### CT-07 — Método vazio

**Fonte:** [PokesalTest.java](../../test/br/edu/ucsal/pokesal/PokesalTest.java),
método `testMaximoDeUsoGolpes`.

Não há preparação, ações ou asserções. O resultado esperado de negócio não
está definido. O JUnit o marcou como aprovado porque o método terminou sem
lançar exceção. **Este caso não fornece evidência de nenhum requisito** e não
recebe vínculo funcional na matriz. A verificação real de usos está em CT-05.

### CT-08 — Tabela de vantagens elementais

**Fonte:** [TipoElementalTest.java](../../test/br/edu/ucsal/pokesal/TipoElementalTest.java).
**Requisito:** RF-04.

**Preparação e passos:** consultar as nove combinações dos três tipos em
`calcularMultiplicadorContra`.

| Tipo do golpe | Contra Fogo | Contra Água | Contra Planta |
| --- | ---: | ---: | ---: |
| Fogo | 1,0 | 0,5 | 2,0 |
| Água | 2,0 | 1,0 | 0,5 |
| Planta | 0,5 | 2,0 | 1,0 |

**Esperado e observado:** as nove asserções confirmaram a tabela.
**Limites:** não verifica argumentos nulos nem o dano final de um ataque que
combine essa tabela com outros modificadores.

## Cobertura estrutural

Valores extraídos do [XML JaCoCo desta execução](evidencias/jacoco.xml).
Frações indicam elementos cobertos / total; “—” significa que não há ramos
contabilizados para a classe.

| Classe | Linhas | Cobertura de linhas | Ramos |
| --- | ---: | ---: | ---: |
| Main | 0 / 84 | 0,0% | 0 / 20 |
| Arena | 17 / 21 | 81,0% | 12 / 17 |
| Batalha | 42 / 183 | 23,0% | 7 / 92 |
| Status | 5 / 5 | 100,0% | — |
| TipoElemental | 18 / 19 | 94,7% | 13 / 14 |
| TipoTerreno | 4 / 4 | 100,0% | — |
| Antidoto | 6 / 7 | 85,7% | 2 / 4 |
| Item | 5 / 5 | 100,0% | — |
| ItemCura | 7 / 8 | 87,5% | 2 / 4 |
| EfeitoStatus | 8 / 25 | 32,0% | 2 / 19 |
| Golpe | 26 / 44 | 59,1% | 14 / 30 |
| Pokesal | 41 / 65 | 63,1% | 16 / 36 |
| Mochila | 8 / 11 | 72,7% | 2 / 4 |
| Treinador | 9 / 9 | 100,0% | — |
| **Total** | **196 / 490** | **40,0%** | **70 / 240** |

Cobertura de linhas indica execução, não qualidade das asserções. Por exemplo,
Antidoto tem linhas percorridas, mas CT-03 não verifica a remoção de um status
preexistente. Main não foi exercitado e a maior parte de Batalha permanece
sem execução. Estes valores são do JaCoCo e não substituem a métrica Coverage
do SonarQube, cuja autenticação continua pendente no
[relatório específico](../sonarqube/RELATORIO.md).

## Lacunas e próximos testes propostos

Os itens abaixo são propostas, não testes implementados ou executados.
Nenhuma falha de produto foi demonstrada pela execução atual; foram identificadas
limitações da suíte.

| Prioridade | Lacuna | Verificação proposta |
| --- | --- | --- |
| Alta | CT-07 vazio | Implementar um cenário distinto, como reset de usos, ou remover a duplicação vazia. |
| Alta | Fluxo da batalha (RF-10, RF-11, RF-19) | Simular entradas de console; provar encerramento imediato, ausência de ação após derrota e consumo de turno pelo item. |
| Alta | Precisão e usos (RE-02, RE-03) | Usar precisão 0 e 1 para cenários determinísticos; verificar HP e decremento mesmo no erro. |
| Alta | Status (RF-12 a RF-14, RF-17) | Aplicar cada status e conferir atributos/HP; remover um status efetivamente presente com antídoto. |
| Alta | Itens (RF-16, RF-18, RF-19) | Curar alvos feridos com 20 e 40 HP, conferir teto e inventário; verificar limites independentes de dois treinadores. |
| Média | Terreno e dano (RF-07, RI-01) | Verificar cura perto do teto, derrotados, arredondamento e composição dos multiplicadores. |
| Média | Inicialização e entradas (RF-01 a RF-03, RF-15, RI-03, RI-04) | Percorrer os seis iniciais, mochila, seleção de arena e entradas inválidas; conferir rejeições dos construtores. |
| Média | Velocidade e golpes (RF-08, RF-09, RE-01) | Inverter o participante mais rápido, aplicar paralisia, verificar empate com aleatoriedade controlada e listas iniciais de golpes. |

## Evidências e critério de leitura

- [Resumo do console JUnit](evidencias/testes.txt): total, duração e resultado.
- [Resultados por método](evidencias/junit-jupiter.xml): extrato do XML original
  com os atributos da suíte e dos casos; propriedades de ambiente e saídas
  auxiliares foram omitidas. O XML completo permanece em `out/quality/junit`.
- [XML JaCoCo](evidencias/jacoco.xml): cobertura por classe, método e linha.
- [Manifesto da execução](evidencias/execucao.json): ambiente, contagens e SHA-256
  dos fontes, testes, configuração e script utilizados.
- [Matriz de rastreabilidade](MATRIZ-RASTREABILIDADE.md): requisitos, código,
  casos de teste e lacunas.

Os sete casos com asserções passaram nos cenários documentados. As lacunas
listadas impedem concluir que todo o comportamento do simulador foi validado.
