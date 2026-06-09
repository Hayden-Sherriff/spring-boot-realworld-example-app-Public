# Spring Boot RealWorld Example App

## Visão Geral

Este é um projeto Spring Boot que implementa a especificação RealWorld, demonstrando uma aplicação full-stack completa com CRUD, autenticação, paginação e outros padrões avançados. O projeto segue princípios de Domain-Driven Design (DDD) e implementa simultaneamente APIs REST e GraphQL.

## Stack de Tecnologias

### Core
- **Spring Boot 2.6.3** - Framework principal
- **Java 11** - Versão do Java
- **MyBatis** - ORM/Data Mapper pattern
- **SQLite** - Banco de dados (development)
- **JWT (JSON Web Tokens)** - Autenticação

### GraphQL
- **Netflix DGS Framework** - Servidor GraphQL
- **GraphQL Schema** - Definido em `schema.graphqls`

### Dependencies
- **Lombok** - Redução de código boilerplate
- **Joda Time** - Manipulação de datas
- **Flyway** - Migrations de banco
- **Spring Security** - Segurança

### Testing
- **JUnit 5** - Testes unitários
- **Rest Assured** - Testes de API REST
- **Spring Boot Test** - Testes de integração

### Code Quality
- **Spotless** - Formatação de código (Google Java Format)

## Arquitetura

O projeto segue uma arquitetura em camadas com DDD:

```
src/main/java/io/spring/
├── api/                    # Web layer (Spring MVC)
│   ├── ArticleApi.java
│   ├── UsersApi.java
│   └── security/
├── core/                   # Business model
│   ├── article/
│   ├── user/
│   └── service/
├── application/           # High-level services (CQRS read model)
│   ├── ArticleQueryService.java
│   └── UserQueryService.java
├── infrastructure/        # Implementações técnicas
│   ├── mybatis/
│   ├── repository/
│   └── service/
└── graphql/              # GraphQL resolvers
    ├── ArticleDatafetcher.java
    └── UserMutation.java
```

### Padrões Implementados

- **DDD (Domain-Driven Design)** - Separação clara entre domínio e infraestrutura
- **CQRS** - Separação entre modelos de leitura e escrita
- **Data Mapper** - Padrão de persistência com MyBatis
- **Repository Pattern** - Abstração de acesso a dados
- **JWT Authentication** - Tokens para autenticação stateless

## Banco de Dados

### Schema (SQLite)
- **users** - Usuários da aplicação
- **articles** - Artigos/conteúdo
- **article_favorites** - Favoritos de artigos
- **follows** - Relacionamento de follow entre usuários
- **tags** - Tags para artigos
- **article_tags** - RelacionamentoMany-to-Many artigos-tags
- **comments** - Comentários de artigos

### Migrations
- Localização: `src/main/resources/db/migration/`
- Ferramenta: Flyway

## Configuração

### Application Properties
```properties
# Database
spring.datasource.url=jdbc:sqlite:dev.db
spring.datasource.driver-class-name=org.sqlite.JDBC

# JWT
jwt.secret=<secret-key>
jwt.sessionTime=86400

# MyBatis
mybatis.configuration.cache-enabled=true
mybatis.configuration.map-underscore-to-camel-case=true
```

### Ambiente Local
- Database: `dev.db` (SQLite local)
- Porta padrão: `8080`
- Endpoint base: `http://localhost:8080`

## Comandos Úteis

### Desenvolvimento
```bash
# Executar aplicação
./gradlew bootRun

# Executar testes
./gradlew test

# Formatar código
./gradlew spotlessJavaApply

# Limpar build e database
./gradlew clean
```

### Docker
```bash
# Build imagem
./gradlew bootBuildImage --imageName spring-boot-realworld-example-app

# Executar container
docker run -p 8081:8080 spring-boot-realworld-example-app
```

## APIs Disponíveis

### REST Endpoints
- `GET /tags` - Listar todas as tags
- `GET /articles` - Listar artigos (com paginação)
- `POST /articles` - Criar artigo
- `PUT /articles/{slug}` - Atualizar artigo
- `DELETE /articles/{slug}` - Remover artigo
- `POST /articles/{slug}/favorite` - Favoritar artigo
- `DELETE /articles/{slug}/favorite` - Desfavoritar
- `GET /articles/{slug}/comments` - Listar comentários
- `POST /articles/{slug}/comments` - Criar comentário
- `DELETE /articles/{slug}/comments/{id}` - Remover comentário
- `POST /users` - Registrar usuário
- `POST /users/login` - Login
- `GET /user` - Obter usuário atual
- `PUT /user` - Atualizar usuário
- `GET /profiles/{username}` - Obter perfil
- `POST /profiles/{username}/follow` - Seguir usuário
- `DELETE /profiles/{username}/follow` - Deixar de seguir

### GraphQL
- Schema disponível em: `src/main/resources/schema/schema.graphqls`
- Endpoint: `http://localhost:8080/graphql`

## Estrutura de Testes

### Test Coverage
- **API Tests** - Testes de integração dos endpoints REST
- **Repository Tests** - Testes de persistência
- **Application Tests** - Testes dos services de query

### Executar Testes
```bash
# Todos os testes
./gradlew test

# Teste específico
./gradlew test --tests ArticleApiTest
```

## Modelos de Dados

### Core Entities
- **User** - Entidade de usuário
- **Article** - Entidade de artigo
- **Comment** - Entidade de comentário
- **Tag** - Entidade de tag
- **FollowRelation** - Relacionamento de follow

### DTOs
- `ProfileData` - Perfil de usuário
- `ArticleData` - Dados do artigo
- `CommentData` - Dados do comentário

## Ambiente de Desenvolvimento

### Pré-requisitos
- Java 11 ou superior
- Gradle 6.x
- Git

### Setup
```bash
# Clonar repositório
git clone <repository-url>

# Entrar no diretório
cd spring-boot-realworld-example-app

# Executar aplicação
./gradlew bootRun
```

### IDE Config
- Importar como projeto Gradle
- Configurar Java 11
- Habilitar Lombok annotation processing

## Links Úteis

- **RealWorld Spec**: https://github.com/gothinkster/realworld
- **Spring Boot Docs**: https://spring.io/projects/spring-boot
- **MyBatis Docs**: https://mybatis.org/mybatis-3/
- **Netflix DGS**: https://github.com/Netflix/dgs-framework
- **JWT**: https://jwt.io/

## Notas Importantes

1. **JWT Secret**: Configure uma chave secreta segura em produção
2. **Database**: Em produção, substitua SQLite por PostgreSQL/MySQL
3. **Security**: Validação de inputs e sanitização necessária em produção
4. **Performance**: Considerar caching para queries frequentes
5. **Logging**: Configurar níveis de logging apropriados para produção

## Contribuição

1. Seguir padrões de código existentes
2. Executar `./gradlew spotlessJavaApply` antes de commits
3. Escrever testes para novas funcionalidades
4. Manter cobertura de testes

## CLI / MCP Configuration

Para agentes AI (CODA CLI / MCP):

- Configurar `GITHUB_PERSONAL_ACCESS_TOKEN` no `.env`
- Token com permissão 'repo' necessário
- MCP server configurado em `.mcp.json`