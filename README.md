# NLW Connect 2025 - Sistema de Eventos

## Sobre o Projeto

O **NLW Connect 2025** é um sistema de gerenciamento de eventos desenvolvido durante o Next Level Week (NLW) da Rocketseat. O sistema permite criar eventos, gerenciar inscrições de usuários e implementar um sistema de indicações com ranking.

## Funcionalidades

### Gestão de Eventos
- Criar novos eventos
- Listar todos os eventos
- Buscar evento por nome amigável (pretty name)
- Geração automática de pretty name baseado no título

### Sistema de Inscrições
- Inscrever usuários em eventos
- Sistema de indicações (usuários podem indicar outros)
- Prevenção de inscrições duplicadas
- Geração de links personalizados para cada inscrição

### Sistema de Ranking
- Ranking geral do evento (top 3)
- Ranking personalizado por usuário
- Contagem de indicações por usuário

## Tecnologias Utilizadas

### Backend
- **Java 23** - Linguagem de programação
- **Spring Boot 3.4.2** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Web** - API REST
- **MySQL 8.4** - Banco de dados
- **Maven** - Gerenciamento de dependências

### Infraestrutura
- **Docker Compose** - Containerização do banco de dados
- **Hibernate** - ORM para mapeamento objeto-relacional

## Estrutura do Banco de Dados

### Tabelas Principais

#### `tbl_event`
- `event_id` (PK) - ID único do evento
- `title` - Título do evento
- `pretty_name` - Nome amigável para URLs
- `location` - Local do evento
- `price` - Preço do evento
- `start_date` / `end_date` - Datas de início e fim
- `start_time` / `end_time` - Horários de início e fim

#### `tbl_user`
- `user_id` (PK) - ID único do usuário
- `user_name` - Nome do usuário
- `user_email` - Email único do usuário

#### `tbl_subscription`
- `subscription_number` (PK) - Número da inscrição
- `event_id` (FK) - Referência ao evento
- `subscribed_user_id` (FK) - Usuário inscrito
- `indication_user_id` (FK) - Usuário que indicou (opcional)

## Configuração e Execução

### Pré-requisitos
- Java 23
- Maven
- Docker e Docker Compose

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd NLW-Connect-2025
```

### 2. Inicie o banco de dados
```bash
docker-compose up -d
```

### 3. Configure o banco de dados
Crie o banco `db_events` no MySQL:
```sql
CREATE DATABASE db_events;
```

### 4. Execute a aplicação
```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## Endpoints da API

### Eventos
- `POST /events` - Criar novo evento
- `GET /events` - Listar todos os eventos
- `GET /events/{prettyName}` - Buscar evento por nome amigável

### Inscrições
- `POST /subscription/{prettyName}` - Inscrever usuário no evento
- `POST /subscription/{prettyName}/{userId}` - Inscrever com indicação
- `GET /subscription/{prettyName}/ranking` - Ranking geral do evento
- `GET /subscription/{prettyName}/ranking/{userId}` - Ranking do usuário

## Exemplos de Uso

### Criar um Evento
```json
POST /events
{
  "title": "Workshop de Spring Boot",
  "location": "São Paulo, SP",
  "price": 150.00,
  "startDate": "2025-02-15",
  "endDate": "2025-02-15",
  "startTime": "09:00",
  "endTime": "17:00"
}
```

### Inscrever Usuário
```json
POST /subscription/workshop-de-spring-boot
{
  "name": "João Silva",
  "email": "joao@email.com"
}
```

## Arquitetura

O projeto segue os padrões de arquitetura em camadas:

- **Controller** - Camada de apresentação (REST endpoints)
- **Service** - Camada de negócio (regras de negócio)
- **Repository** - Camada de acesso a dados
- **Model** - Entidades do domínio
- **DTO** - Objetos de transferência de dados
- **Exception** - Tratamento de exceções customizadas

## Estrutura do Projeto

```
src/main/java/br/com/nlw/events/
├── controller/          # Controllers REST
├── dto/                # Data Transfer Objects
├── exception/          # Exceções customizadas
├── model/              # Entidades JPA
├── repo/               # Repositories
└── service/            # Services de negócio
```

## Próximos Passos

- [ ] Implementar autenticação e autorização
- [ ] Adicionar validações de dados
- [ ] Implementar testes unitários e de integração
- [ ] Adicionar documentação da API com Swagger
- [ ] Implementar cache para melhorar performance
- [ ] Adicionar logs estruturados

## Licença

Este projeto foi desenvolvido durante o NLW Connect 2025 da Rocketseat.
