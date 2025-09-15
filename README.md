# ![RealWorld Example App using Kotlin and Spring](example-logo.png)

[![Actions](https://github.com/gothinkster/spring-boot-realworld-example-app/workflows/Java%20CI/badge.svg)](https://github.com/gothinkster/spring-boot-realworld-example-app/actions)

> ### Spring boot + MyBatis codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld-example-apps) spec and API.

This codebase was created to demonstrate a fully fledged full-stack application built with Spring boot + Mybatis including CRUD operations, authentication, routing, pagination, and more.

For more information on how to this works with other frontends/backends, head over to the [RealWorld](https://github.com/gothinkster/realworld) repo.

# *NEW* GraphQL Support  

Following some DDD principles. REST or GraphQL is just a kind of adapter. And the domain layer will be consistent all the time. So this repository implement GraphQL and REST at the same time.

The GraphQL schema is https://github.com/gothinkster/spring-boot-realworld-example-app/blob/master/src/main/resources/schema/schema.graphqls and the visualization looks like below.

![](graphql-schema.png)

And this implementation is using [dgs-framework](https://github.com/Netflix/dgs-framework) which is a quite new java graphql server framework.
# How it works

The application uses Spring Boot (Web, Mybatis).

* Use the idea of Domain Driven Design to separate the business term and infrastructure term.
* Use MyBatis to implement the [Data Mapper](https://martinfowler.com/eaaCatalog/dataMapper.html) pattern for persistence.
* Use [CQRS](https://martinfowler.com/bliki/CQRS.html) pattern to separate the read model and write model.

## Architecture

The following diagram illustrates the overall architecture of the application:

```mermaid
graph TB
    subgraph "Client Layer"
        WEB[Web Frontend]
        MOBILE[Mobile App]
        API_CLIENT[API Client]
    end

    subgraph "API Layer"
        REST[REST API Controllers]
        GRAPHQL[GraphQL DataFetchers]
        SECURITY[Spring Security + JWT]
    end

    subgraph "Application Layer (CQRS)"
        QUERY[Query Services]
        COMMAND[Command Services]
        DTO[Data Transfer Objects]
    end

    subgraph "Core Domain Layer"
        ENTITIES[Domain Entities]
        SERVICES[Domain Services]
        REPOSITORIES[Repository Interfaces]
    end

    subgraph "Infrastructure Layer"
        REPO_IMPL[Repository Implementations]
        MYBATIS[MyBatis Mappers]
        DB_CONFIG[Database Configuration]
    end

    subgraph "Database"
        SQLITE[(SQLite Database)]
    end

    subgraph "External Services"
        JWT_SERVICE[JWT Token Service]
        VALIDATION[Bean Validation]
    end

    %% Client connections
    WEB --> REST
    WEB --> GRAPHQL
    MOBILE --> REST
    MOBILE --> GRAPHQL
    API_CLIENT --> REST

    %% API Layer connections
    REST --> SECURITY
    GRAPHQL --> SECURITY
    SECURITY --> QUERY
    SECURITY --> COMMAND

    %% Application Layer connections
    QUERY --> ENTITIES
    COMMAND --> SERVICES
    QUERY --> DTO
    COMMAND --> DTO

    %% Core Domain connections
    SERVICES --> ENTITIES
    SERVICES --> REPOSITORIES
    ENTITIES --> REPOSITORIES

    %% Infrastructure connections
    REPOSITORIES --> REPO_IMPL
    REPO_IMPL --> MYBATIS
    MYBATIS --> DB_CONFIG
    DB_CONFIG --> SQLITE

    %% External service connections
    SECURITY --> JWT_SERVICE
    REST --> VALIDATION
    GRAPHQL --> VALIDATION

    %% Styling
    classDef clientLayer fill:#e1f5fe
    classDef apiLayer fill:#f3e5f5
    classDef applicationLayer fill:#e8f5e8
    classDef domainLayer fill:#fff3e0
    classDef infrastructureLayer fill:#fce4ec
    classDef database fill:#f1f8e9
    classDef external fill:#f5f5f5

    class WEB,MOBILE,API_CLIENT clientLayer
    class REST,GRAPHQL,SECURITY apiLayer
    class QUERY,COMMAND,DTO applicationLayer
    class ENTITIES,SERVICES,REPOSITORIES domainLayer
    class REPO_IMPL,MYBATIS,DB_CONFIG infrastructureLayer
    class SQLITE database
    class JWT_SERVICE,VALIDATION external
```

## Code Organization

The code is organized as this:

1. `api` is the web layer implemented by Spring MVC
2. `core` is the business model including entities and services
3. `application` is the high-level services for querying the data transfer objects
4. `infrastructure`  contains all the implementation classes as the technique details

# Security

Integration with Spring Security and add other filter for jwt token process.

The secret key is stored in `application.properties`.

# Database

It uses a ~~H2 in-memory database~~ sqlite database (for easy local test without losing test data after every restart), can be changed easily in the `application.properties` for any other database.

# Getting started

You'll need Java 11 installed.

    ./gradlew bootRun

To test that it works, open a browser tab at http://localhost:8080/tags .  
Alternatively, you can run

    curl http://localhost:8080/tags

# Try it out with [Docker](https://www.docker.com/)

You'll need Docker installed.
	
    ./gradlew bootBuildImage --imageName spring-boot-realworld-example-app
    docker run -p 8081:8080 spring-boot-realworld-example-app

# Try it out with a RealWorld frontend

The entry point address of the backend API is at http://localhost:8080, **not** http://localhost:8080/api as some of the frontend documentation suggests.

# Run test

The repository contains a lot of test cases to cover both api test and repository test.

    ./gradlew test

# Code format

Use spotless for code format.

    ./gradlew spotlessJavaApply

# Help

Please fork and PR to improve the project.
