# Respostas API

A **Respostas API** gerencia as respostas dos alunos para as questões, permitindo a criação, consulta, listagem e desativação de respostas.  
Esta documentação apresenta instruções claras de uso, os formatos esperados para requisições e respostas, além de recomendações importantes para a configuração do ambiente.

---

## Atenção

- **Estatísticas:**  
  A funcionalidade de estatísticas está desabilitada até que a integração com o serviço de questões e alternativas seja realizada.

- **Credenciais do MongoDB Atlas:**  
  Para executar o projeto, é necessário incluir suas credenciais de acesso ao MongoDB Atlas no arquivo `application.properties`.  
  Por exemplo:
  ```properties
  # MongoDB
  spring.data.mongodb.uri=mongodb+srv://<USUARIO>:<SENHA>@<CLUSTER>.mongodb.net/ensinohistoria?retryWrites=true&w=majority
  ```
  Substitua `<USUARIO>`, `<SENHA>` e `<CLUSTER>` pelos valores correspondentes.

---

## Sumário

- [Pré-requisitos](#pré-requisitos)
- [Configuração do Projeto](#configuração-do-projeto)
- [Execução](#execução)
- [Endpoints](#endpoints)
  - [Criar Resposta](#criar-resposta)
  - [Listar Todas as Respostas](#listar-todas-as-respostas)
  - [Buscar Resposta por ID](#buscar-resposta-por-id)
  - [Desativar Resposta](#desativar-resposta)
  - [Buscar Resposta por Aluno](#buscar-resposta-por-aluno)
- [Considerações Finais](#considerações-finais)

---

## Pré-requisitos

- **Java 17** (ou superior)
- **Maven** instalado
- Acesso a uma instância do **MongoDB Atlas** (ou outro MongoDB devidamente configurado)

---

## Configuração do Projeto

1. **Clonagem do Repositório:**  
   Clone o projeto para sua máquina:
   ```bash
   git clone <URL_DO_REPOSITÓRIO>
   ```
2. **Configuração do MongoDB Atlas:**  
   No arquivo `src/main/resources/application.properties`, insira suas credenciais de acesso. Exemplo:
   ```properties
   # MongoDB
   spring.data.mongodb.uri=mongodb+srv://<USUARIO>:<SENHA>@<CLUSTER>.mongodb.net/ensinohistoria?retryWrites=true&w=majority
   ```
   Certifique-se de substituir `<USUARIO>`, `<SENHA>` e `<CLUSTER>` pelos valores corretos.

---

## Execução

Para compilar e executar o projeto, siga os passos abaixo:

1. **Compilação:**
   ```bash
   mvn clean install
   ```
2. **Execução:**
   ```bash
   mvn spring-boot:run
   ```
   A aplicação será iniciada na porta **8080**.

---

## Endpoints

### Criar Resposta

- **URL:**  
  `POST http://localhost:8080/api/respostas`
- **Descrição:**  
  Registra uma nova resposta para uma questão.

- **Formato de Requisição (JSON):**

  ```json
  {
    "alunoId": "550e8400-e29b-41d4-a716-446655440000",
    "questaoId": "550e8400-e29b-41d4-a716-446655440001",
    "alternativaId": "550e8400-e29b-41d4-a716-446655440002",
    "dataInicio": "2024-02-20T10:00:00",
    "dataFim": "2024-02-20T10:05:00"
  }
  ```

- **Formato de Resposta (JSON):**
  ```json
  {
    "id": "67a91c2b694cd8252ca63113",
    "alunoId": "550e8400-e29b-41d4-a716-446655440000",
    "questaoId": "550e8400-e29b-41d4-a716-446655440001",
    "alternativaId": "550e8400-e29b-41d4-a716-446655440002",
    "dataInicio": "2024-02-20T10:00:00",
    "dataFim": "2024-02-20T10:05:00",
    "dataCriacao": "2025-02-09T18:20:43.9304243",
    "dataAtualizacao": "2025-02-09T18:20:43.9304243",
    "ativo": true
  }
  ```

---

### Listar Todas as Respostas

- **URL:**  
  `GET http://localhost:8080/api/respostas`

- **Descrição:**  
  Retorna a lista de todas as respostas registradas.

- **Formato de Resposta (JSON):**
  ```json
  [
    {
      "id": "67a91ba2694cd8252ca63112",
      "alunoId": "550e8400-e29b-41d4-a716-446655440000",
      "questaoId": "550e8400-e29b-41d4-a716-446655440001",
      "alternativaId": "550e8400-e29b-41d4-a716-446655440002",
      "dataInicio": "2024-02-20T10:00:00",
      "dataFim": "2024-02-20T10:05:00",
      "dataCriacao": "2025-02-09T18:18:26.251",
      "dataAtualizacao": "2025-02-09T18:18:26.251",
      "ativo": true
    },
    {
      "id": "67a91c2b694cd8252ca63113",
      "alunoId": "550e8400-e29b-41d4-a716-446655440000",
      "questaoId": "550e8400-e29b-41d4-a716-446655440001",
      "alternativaId": "550e8400-e29b-41d4-a716-446655440002",
      "dataInicio": "2024-02-20T10:00:00",
      "dataFim": "2024-02-20T10:05:00",
      "dataCriacao": "2025-02-09T18:20:43.93",
      "dataAtualizacao": "2025-02-09T18:20:43.93",
      "ativo": true
    }
  ]
  ```

---

### Buscar Resposta por ID

- **URL:**  
  `GET http://localhost:8080/api/respostas/67a91c2b694cd8252ca63113`

- **Descrição:**  
  Retorna os detalhes de uma resposta específica, identificada pelo seu ID.

- **Formato de Resposta (JSON):**
  ```json
  {
    "id": "67a91c2b694cd8252ca63113",
    "alunoId": "550e8400-e29b-41d4-a716-446655440000",
    "questaoId": "550e8400-e29b-41d4-a716-446655440001",
    "alternativaId": "550e8400-e29b-41d4-a716-446655440002",
    "dataInicio": "2024-02-20T10:00:00",
    "dataFim": "2024-02-20T10:05:00",
    "dataCriacao": "2025-02-09T18:20:43.93",
    "dataAtualizacao": "2025-02-09T18:20:43.93",
    "ativo": true
  }
  ```

---

### Desativar Resposta

- **URL:**  
  `DELETE http://localhost:8080/api/respostas/67a90dde27de0e5920e0e24a`

- **Descrição:**  
  Desativa (inativa) uma resposta, alterando seu status para `ativo: false`.

- **Resposta Esperada:**  
  Código **204 No Content**

---

### Buscar Resposta por Aluno

- **URL:**  
  `GET http://localhost:8080/api/respostas/aluno/550e8400-e29b-41d4-a716-446655440000`

- **Descrição:**  
  Retorna todas as respostas registradas por um aluno específico, identificado pelo seu ID.

- **Formato de Resposta (JSON):**
  ```json
  [
    {
      "id": "67a91ba2694cd8252ca63112",
      "alunoId": "550e8400-e29b-41d4-a716-446655440000",
      "questaoId": "550e8400-e29b-41d4-a716-446655440001",
      "alternativaId": "550e8400-e29b-41d4-a716-446655440002",
      "dataInicio": "2024-02-20T10:00:00",
      "dataFim": "2024-02-20T10:05:00",
      "dataCriacao": "2025-02-09T18:20:43.93",
      "dataAtualizacao": "2025-02-09T18:20:43.93",
      "ativo": true
    }
  ]
  ```

---

## Considerações Finais

- **Atenção:**  
  Algumas funcionalidades podem ser desabilitadas conforme a evolução do sistema. Certifique-se de acompanhar a documentação atualizada.

---

# Fim da Documentação
