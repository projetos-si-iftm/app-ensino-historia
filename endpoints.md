# Lista de Endpoints

## Questões API

**Base Path:** `/questoes`

- `GET /questoes`  
  **Descrição:** Retorna todas as questões cadastradas no sistema.

- `GET /questoes/{id}`  
  **Descrição:** Retorna os detalhes de uma questão específica pelo ID.

- `POST /questoes`  
  **Descrição:** Cria uma nova questão no banco de dados.

- `GET /questoes/tema/{temaId}`  
  **Descrição:** Retorna questões associadas a um tema específico.

- `GET /questoes/dificuldade/{nivel}`  
  **Descrição:** Retorna questões filtradas por nível de dificuldade.

- `DELETE /questoes/{id}`  
  **Descrição:** Remove uma questão específica pelo ID.

---

## Alternativas API

**Base Path:** `/api/alternativas`

- `GET /api/alternativas`  
  **Descrição:** Retorna todas as alternativas disponíveis.

- `GET /api/alternativas/{id}`  
  **Descrição:** Retorna os detalhes de uma alternativa específica pelo ID.

- `POST /api/alternativas`  
  **Descrição:** Cria uma nova alternativa para uma questão.

- `PUT /api/alternativas/{id}`  
  **Descrição:** Atualiza os dados de uma alternativa específica.

- `DELETE /api/alternativas/{id}`  
  **Descrição:** Remove uma alternativa específica pelo ID.

---

## Temas API

**Base Path:** `/temas`

- `GET /temas`  
  **Descrição:** Retorna todos os temas cadastrados no sistema.

- `GET /temas/{id}`  
  **Descrição:** Retorna os detalhes de um tema específico pelo ID.

- `POST /temas`  
  **Descrição:** Cria um novo tema no sistema.

- `DELETE /temas/{id}`  
  **Descrição:** Remove um tema específico pelo ID.

---

## Respostas API

**Base Path:** `/api/respostas`

- `POST /api/respostas`  
  **Descrição:** Registra uma nova resposta de um aluno.

- `GET /api/respostas`  
  **Descrição:** Retorna todas as respostas registradas no sistema.

- `GET /api/respostas/{id}`  
  **Descrição:** Retorna os detalhes de uma resposta específica pelo ID.

- `GET /api/respostas/aluno/{alunoId}`  
  **Descrição:** Retorna todas as respostas enviadas por um aluno específico.

- `GET /api/respostas/estatisticas/{questaoId}`  
  **Descrição:** Retorna estatísticas de respostas para uma questão específica.

- `DELETE /api/respostas/{id}`  
  **Descrição:** Desativa ou remove uma resposta específica pelo ID.

---

## Auth API

**Base Path:** `/auth`

- `POST /auth/login`  
  **Descrição:** Realiza login de um usuário e retorna um token de autenticação.

- `POST /auth/register`  
  **Descrição:** Registra um novo usuário no sistema.

- `POST /auth/validateToken`  
  **Descrição:** Valida um token de autenticação para verificar sua validade.

- `POST /auth/google`  
  **Descrição:** Realiza login de um usuário utilizando autenticação via Google.

---

## Classroom API

**Base Path:** `/classroom`

- `POST /classroom`  
  **Descrição:** Cria uma nova turma no sistema.

- `GET /classroom`  
  **Descrição:** Retorna todas as turmas cadastradas.

- `GET /classroom/{id}`  
  **Descrição:** Retorna os detalhes de uma turma específica pelo ID.

- `PUT /classroom/{id}`  
  **Descrição:** Atualiza os dados de uma turma específica.

- `DELETE /classroom/{id}`  
  **Descrição:** Remove uma turma específica pelo ID.

---

## Gateway API

**Rotas Configuradas:**

- `/frontend-react/frontend/**` → `http://localhost:8080/`  
  **Descrição:** Redireciona para o frontend da aplicação.

- `/questoes-api/**` → `http://localhost:8081/`  
  **Descrição:** Redireciona para os serviços relacionados às questões.

- `/respostas-api/**` → `http://localhost:8082/`  
  **Descrição:** Redireciona para os serviços relacionados às respostas.

- `/autenticacao/**` → `http://localhost:8083/`  
  **Descrição:** Redireciona para os serviços de autenticação.
