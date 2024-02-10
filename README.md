# MandaCaru Broker API

## Descrição

A Mandacaru Broker API é uma aplicação Spring Boot que fornece operações CRUD (Create, Read, Update, Delete) para
gerenciar informações sobre ações (stocks).

## Recursos

### Listar Todas as Ações

Retorna uma lista de todas as ações disponíveis.

**Endpoint:**

```http
GET /stocks
```

**Resposta:**

```json
[
  {
    "id": 1,
    "symbol": "BBAS3",
    "companyName": "Banco do Brasil SA",
    "price": 56.97
  },
  {
    "id": 2,
    "symbol": "PETR4",
    "companyName": "Petrobras S.A.",
    "price": 32.84
  },
  {
    "id": 3,
    "symbol": "VALE3",
    "companyName": "Vale S.A.",
    "price": 89.75
  }
]
```

### Obter uma Ação por ID

Retorna os detalhes de uma ação específica com base no ID.

**Endpoint:**

```http
GET /stocks/{id}
```

**Resposta:**

```json
{
  "id": 1,
  "symbol": "BBAS3",
  "companyName": "Banco do Brasil SA",
  "price": 56.97
}
```

### Criar uma Nova Ação

Cria uma nova ação com base nos dados fornecidos.

**Endpoint:**

```http
POST /stocks
```

**Corpo da Solicitação (Request Body):**

```json
{
  "symbol": "MGLU3",
  "companyName": "Magazine Luiza S.A.",
  "price": 6.95
}
```

**Resposta:**

```json
{
  "id": 4,
  "symbol": "MGLU3",
  "companyName": "Magazine Luiza S.A.",
  "price": 6.95
}
```

### Atualizar uma Ação por ID

Atualiza os detalhes de uma ação específica com base no ID.

**Endpoint:**

```http
PUT /stocks/{id}
```

**Corpo da Solicitação (Request Body):**

```json
{
  "symbol": "PETR4",
  "companyName": "Petrobras S.A.",
  "price": 33.10
}
```

**Resposta:**

```json
{
  "id": 2,
  "symbol": "PETR4",
  "companyName": "Petrobras S.A.",
  "price": 33.10
}
```

### Excluir uma Ação por ID

Exclui uma ação específica com base no ID.

**Endpoint:**

```http
DELETE /stocks/{id}
```

**Resposta:**

```json
{
  "success": true,
  "message": "Stock ID {id} deletado com sucesso."
}
```

## Uso

1. Clone o repositório: `git clone https://github.com/seu-usuario/MandaCaruBrokerAPI.git`
2. Importe o projeto em sua IDE preferida.
3. Configure o banco de dados e as propriedades de aplicação conforme necessário.
4. Execute o aplicativo Spring Boot.
5. Acesse a API em `http://localhost:8080`.

## Requisitos

- Java 11 ou superior
- Maven
- Banco de dados

## Tecnologias Utilizadas

- Spring Boot
- Spring Data JPA
- Maven
- PostgreSQL

## Contribuições

Contribuições são bem-vindas!
