# Desafio-Invext-Api-UBOTS
API REST para problema de distribuição da Ubots.

## Sobre a API
API desenvolvida para resolver problemas de distribuição de solicitações entre times para a Invext. A API é construída com Java e Spring Boot e também implementa testes unitários.

Para facilitar os testes, a solução foi desenvolvida considerando os três times propostos: Cartões, Empréstimos e Outros Assuntos. Dentro destes times também foram cadastrados 2 atendentes por time afim de facilitar os testes.

Segue a relação de times e membros:
- Cartões:
  - André
  - Bianca

- Empréstimos:
  - Carla
  - Diego

- Outros Assuntos:
  - Eduardo
  - Fabiana

## Endpoints
Essa API provê os seguintes endpoints para as seguintes funcionalidades:

### POST Request
- Adição de nova solicitação: `POST/request`

### GET Request
- Listagem de solicitações por times, membros dos times e filas de espera: `GET/request`

### DELETE Request
- Exclusão/movimentação de solicitação da fila de atendimento por time e nome do atendente: `DELETE/request/{teamName}/{attendantName}`

## Detalhes
Exemplos de requisições para os endpoints listados anteriormente:


`POST/request`

**Body:**
```json
{
    "type": "Problemas com cartão"
}
```
Realiza a adição de uma nova solicitação. 

Os tipos podem ser: "Problemas com cartão", "Contratação de empréstimo" e demais assuntos.
- Solicitações do tipo "Problemas com cartão" são direcionadas ao time de "Cartões".
- Solicitações do tipo "Contratação de empréstimo" são direcionadas ao time de "Empréstimos".
- Demais solicitações são enviadas ao time de "Outros Assuntos".

`GET/request`

Retorna a listagem de solicitações atualmente sendo atendidas por times e a lista de espera de cada time.

**Exemplo de retorno:**
```json
[
    "Time Cartões: André - 2 solicitações / Bianca - 1 solicitação / Solicitações pendentes - 0",
    "Time Empréstimos: Carla - 0 solicitações / Diego - 0 solicitações / Solicitações pendentes - 0",
    "Time Outros Assuntos: Eduardo - 0 solicitações / Fabiana - 0 solicitações / Solicitações pendentes - 0"
]
```

`DELETE/request/{teamName}/{attendantName}`

Realiza a exclusão/movimentação de uma solicitação para um atendente de um time.

Serve para mostrar o funcionamento da distribuição das solicitações em fila de espera por time.

É necessário que seja passado como parâmetro na URL o nome do time e o nome de um atendente deste time.

**Exemplo de requisição:**
```
http://localhost:8081/request/Cartões/André
```
```
http://localhost:8081/request/Empréstimos/Carla
```
```
http://localhost:8081/request/Outros Assuntos/Fabiana
```

## Tecnologias
Projeto desenvolvido com as seguintes tecnologias:
- Java 17
- Spring Boot 3.3.3
- Maven
- JUnit

## Package
A API foi desenvolvida para ser executada a partir de um jar. Para que o jar seja gerado, o seguinte comando pode ser executado na pasta do projeto:
```bash
mvn clean package
```

## Execução
Para executar a API, o jar pode ser executado normalmente ou com o seguinte comando na pasta do projeto:
```bash
mvn spring-boot:run
```
Por padrão a aplicação fica disponível em `http://localhost:8081`.

## Testes
Os testes estão incluídos na aplicação para garantir o funcionamento correto do projeto. Para executar os testes use o seguinte comando:
```bash
mvn test
```

## Postman
Foi adicionado o arquivo `Desafio Ubots.postman_collection.json` com a coleção de requisições disponibilizadas pela API.
