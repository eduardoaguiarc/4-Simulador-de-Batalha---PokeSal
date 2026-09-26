# Relatório SonarQube — análise pendente de autenticação

Em 26/09/2026, foi executado o SonarScanner CLI 8.1.0.6389 contra o
SonarQube local 26.9.0.129388, disponível em http://localhost:9000.
O servidor retornou **HTTP 401 Unauthorized**. Esta tentativa não produziu
uma nova análise: as métricas abaixo ainda não foram obtidas do SonarQube.

Projeto: `Simulador-de-Batalha-PokeSal`.
Commit de referência: `532b38ab1cd2d2dfbb3e1e8054e22bfe49a5b180`.
Escopo preparado: 14 arquivos Java de produção em `src` e cinco classes de testes
em `test`, sem exclusões de cobertura.

| Métrica obrigatória | Resultado do SonarQube |
| --- | --- |
| Code Smells | Indisponível — autenticação necessária |
| Bugs | Indisponível — autenticação necessária |
| Vulnerabilities | Indisponível — autenticação necessária |
| Coverage % | Indisponível — XML JaCoCo gerado, mas ainda não importado |

Não se deve interpretar métricas indisponíveis como zero. O Quality Gate
também não foi consultado por falta de autenticação.

## Testes e cobertura executados localmente

Compilação com JDK 25.0.4.1, `--release 21`, informações de depuração e UTF-8.
Testes executados com JUnit Platform 1.10.2; cobertura medida com JaCoCo 0.8.14.

| Verificação | Resultado |
| --- | ---: |
| Testes executados / aprovados | 8 / 8 |
| Falhas / erros / ignorados | 0 / 0 / 0 |
| Linhas cobertas / total | 196 / 490 |
| Cobertura de linhas JaCoCo | 40,0% |
| Ramos cobertos / total | 70 / 240 |
| Cobertura de ramos JaCoCo | 29,2% |

Esses percentuais são evidências locais do JaCoCo, não o resultado de Coverage
do SonarQube. A métrica do SonarQube considera linhas e condições e deve ser
obtida após o processamento da análise.

## Evidências incluídas

- [Log da tentativa do SonarScanner](evidencias/sonar-scanner.txt)
- [Resultado dos testes](evidencias/testes.txt)
- [XML de cobertura JaCoCo](evidencias/jacoco.xml)

## Como concluir e reproduzir

Requisitos: JDK 21 ou superior, `sonar-scanner` no PATH, SonarQube local ativo
e um token com permissão de executar análise e consultar o projeto.
Disponibilize o token na variável de ambiente `SONAR_TOKEN`, sem incluí-lo
em arquivos versionados. Na raiz do projeto, execute:

```powershell
powershell -ExecutionPolicy Bypass -File scripts/analisar-sonar.ps1
```

O script baixa as dependências do Maven Central, recompila, executa os testes,
gera o XML, envia a análise e aguarda o processamento. Depois, exporta as
métricas, o resultado do Quality Gate e a identificação da análise para
`docs/sonarqube/evidencias/`, substituindo este relatório pelos resultados reais.
O HTML detalhado da cobertura fica em `out/quality/coverage/index.html`.

Para reproduzir somente os testes e a cobertura, sem autenticação:

```powershell
powershell -ExecutionPolicy Bypass -File scripts/analisar-sonar.ps1 -SomenteTestes
```

[Documentação da geração de relatórios JaCoCo](https://www.jacoco.org/jacoco/trunk/doc/cli.html).
