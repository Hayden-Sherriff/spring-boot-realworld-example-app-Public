# Inventário de Tecnologias - Spring Boot RealWorld Example App

## 1. Linguagens de Programação

### Java 
- **Versão**: Java 11 (LTS)
- **Papel**: Linguagem principal do projeto, utilizada para desenvolver toda a aplicação backend
- **Compatibilidade**: Source Compatibility e Target Compatibility configuradas para Java 11

## 2. Frameworks e Bibliotecas

### Spring Boot
- **Versão**: 2.6.3
- **Papel**: Framework principal que fornece autoconfiguração, servidor web embarcado e estrutura da aplicação
- **Módulos utilizados**:
  - `spring-boot-starter-web`: Para construção de APIs REST e aplicações web
  - `spring-boot-starter-validation`: Para validação de dados Bean Validation
  - `spring-boot-starter-hateoas`: Para implementação de HATEOAS (Hypermedia as the Engine of Application State)
  - `spring-boot-starter-security`: Para autenticação e autorização
  - `spring-boot-starter-test`: Para testes automatizados

### Spring Security
- **Papel**: Framework de segurança para autenticação e autorização
- **Integração**: JWT tokens para autenticação stateless
- **Dependência adicional**: `spring-security-test` para testes

### MyBatis
- **Versão**: 2.2.2 (Spring Boot Starter)
- **Papel**: Framework de mapeamento objeto-relacional (ORM) que implementa o pattern Data Mapper
- **Configurações**: Cache habilitado, timeout de 3 segundos, mapeamento underscore_to_camelCase
- **Arquivos de mapeamento**: XML files na pasta `mapper/`

### GraphQL DGS Framework
- **Versão**: 4.9.21 (Netflix Domain Graph Service)
- **Papel**: Framework GraphQL para construção de APIs GraphQL
- **Code Generation**: Versão 5.0.6 para geração automática de código a partir do schema
- **Schema**: Definido em `schema/schema.graphqls`

### Flyway
- **Papel**: Ferramenta de migração de banco de dados para versionamento do schema
- **Migrações**: SQL files na pasta `db/migration/`

### Lombok
- **Papel**: Biblioteca para redução de código boilerplate através de anotações
- **Uso**: Geração automática de getters, setters, constructors, etc.

### JJWT (Java JWT)
- **Versão**: 0.11.2
- **Papel**: Biblioteca para criação e validação de tokens JWT (JSON Web Tokens)
- **Módulos**: API, Implementation e Jackson

### Joda-Time
- **Versão**: 2.10.13
- **Papel**: Biblioteca para manipulação de datas e horas

### SQLite JDBC
- **Versão**: 3.36.0.3
- **Papel**: Driver JDBC para conexão com banco de dados SQLite

### Jackson
- **Papel**: Biblioteca para serialização/desserialização JSON
- **Configuração**: `UNWRAP_ROOT_VALUE=true` para desserialização

### REST Assured
- **Versão**: 4.5.1
- **Papel**: Biblioteca para testes de APIs REST de forma fluente e expressiva
- **Módulos**: json-path, xml-path, spring-mock-mvc

## 3. Serviços e APIs Externas

### Banco de Dados
- **SQLite**: Banco de dados relacional leve, arquivo-based (`dev.db`)
- **Papel**: Persistência de dados local para desenvolvimento e testes
- **Configuração**: Driver JDBC SQLite, sem usuário/senha

### Serviços de Imagem
- **Production Ready IO**: Endpoint para imagens padrão de avatar
- **URL**: `https://static.productionready.io/images/smiley-cyrus.jpg`

## 4. Ferramentas de Infraestrutura/DevOps

### Build Tools
- **Gradle**: Sistema de automação de build e gerenciamento de dependências
- **Plugin Spring Boot**: Para empacotamento e execução da aplicação
- **Plugin Dependency Management**: Para gerenciamento centralizado de versões

### Code Quality
- **Spotless**: Ferramenta de formatação de código
- **Versão**: 6.2.1
- **Configuração**: Google Java Format para formatação automática do código Java

### CI/CD
- **GitHub Actions**: Plataforma de integração e entrega contínua
- **Workflow**: CI automatizado em ambiente Ubuntu
- **Java Version**: JDK 11 com distribuição Zulu
- **Cache**: Gradle caches para otimização de builds

### Containers
- **Docker**: Plataforma de containerização
- **Build Command**: `bootBuildImage` para criação de imagem Docker
- **Porta**: 8081 para execução em container

### Versionamento
- **Git**: Sistema de controle de versão
- **Plataforma**: GitHub

## 5. Arquitetura e Padrões

### Domain-Driven Design (DDD)
- **Estrutura em camadas**:
  - `api/`: Camada de apresentação web (REST/GraphQL)
  - `core/`: Camada de domínio com entidades e serviços de negócio
  - `application/`: Serviços de alto nível para consultas e DTOs
  - `infrastructure/`: Implementações técnicas e persistência

### CQRS (Command Query Responsibility Segregation)
- **Padrão**: Separação entre modelo de leitura e escrita
- **Implementação**: Serviços distintos para leitura e escrita

### Design Patterns
- **Data Mapper**: Implementado através do MyBatis
- **Repository**: Para abstração da camada de persistência
- **DTO**: Para transferência de dados entre camadas

## 6. API e Protocolos

### REST API
- **Framework**: Spring MVC
- **Padrão**: HATEOAS para hypermedia
- **Autenticação**: JWT Bearer tokens

### GraphQL
- **Framework**: Netflix DGS
- **Schema**: Definição de tipos, queries e mutations
- **Features**: Pagination (cursor-based), connections, edges

## 7. Testes

### Framework de Testes
- **JUnit 5**: Framework principal para testes unitários e de integração
- **Spring Boot Test**: Para testes de integração com Spring
- **REST Assured**: Para testes de APIs REST
- **MyBatis Test**: Para testes da camada de persistência

### Cobertura
- **Testes de API**: Para todos os endpoints REST
- **Testes de Serviço**: Para lógica de negócio
- **Testes de Repositório**: Para camada de persistência

## 8. Configuração

### Propriedades da Aplicação
- **Arquivo**: `application.properties`
- **Configurações**: Database, JWT, MyBatis, Logging, Jackson

### JWT Configuration
- **Secret Key**: Chave secreta para assinatura de tokens
- **Session Time**: 86400 segundos (24 horas)

### Logging
- **Nível**: DEBUG para camadas específicas do MyBatis
- **Packages**: `io.spring.infrastructure.mybatis`