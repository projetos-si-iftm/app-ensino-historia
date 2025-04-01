📚 HistoLinguo - Aprenda História de Forma Divertida!

📌 Sobre o Projeto

O HistoLinguo é um aplicativo educacional gamificado, desenvolvido para alunos do ensino médio testarem seus conhecimentos em História através de quizzes interativos.

Os alunos podem escolher diferentes temas, separados por dificuldade, responder perguntas e competir no ranking. Além disso, um painel web exclusivo para professores permite a gestão de turmas, temas e questões.
🚀 Tecnologias Utilizadas

🖥️ Frontend
🔹 Mobile: React Native (JavaScript)
🔹 Web (Admin): React.js (JavaScript)

⚙️ Backend
🔹 Linguagem: Java + Spring Boot
🔹 Banco de Dados: MongoDB
🔹 Autenticação: Firebase Authentication (Google)
🔹 Arquitetura: Microserviços

🔗 Infraestrutura
🔹 Service Discovery: Eureka
🔹 API Gateway: Spring Cloud Gateway
🔹 Circuit Breaker: Resilience4j


📂 Estrutura do Projeto

📱 Aplicativo Mobile
✅ Tela de Login (Google Authentication)
✅ Tela Inicial (Escolher tema, ranking, perfil)
✅ Tela de Temas (Listagem por dificuldade)
✅ Tela de Quiz (Questões interativas)
✅ Tela de Ranking (Classificação dos alunos)

💻 Aplicação Web (Professores/Admin)
✅ Login do Professor
✅ Cadastro e Gerenciamento de Turmas
✅ Gerenciamento de Temas e Questões
✅ Visualização do Ranking dos Alunos

🛠️ Backend - Microserviços
✅ Temas: CRUD de temas do quiz
✅ Questões: CRUD de perguntas
✅ Respostas: Processamento de respostas e pontuação
✅ Ranking: Classificação dos alunos
✅ Autenticação: Gerenciamento de professores
✅ API Gateway: Encaminhamento de requisições
✅ Service Discovery: Registro dinâmico dos serviços


🎮 Como Rodar o Projeto?

🔧 Configuração do Backend

# Clone o repositório
Em breve ... 

📱 Configuração do Mobile (React Native)
# Instale as dependências
cd histolinguo/mobile
npm install

# Execute o projeto no emulador
npm run android

💻 Configuração do Web (React.js)
# Instale as dependências
cd histolinguo/web
npm install

# Execute a aplicação
npm start
