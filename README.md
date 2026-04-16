# RealWorld Example App using Java and Spring Boot

[![Actions](https://github.com/gothinkster/spring-boot-realworld-example-app/workflows/Java%20CI/badge.svg)](https://github.com/gothinkster/spring-boot-realworld-example-app/actions)
![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=spring-boot&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-ORM-red)
![License](https://img.shields.io/badge/license-MIT-blue)

> ### Spring Boot + MyBatis codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld-example-apps) spec and API.

This codebase was created to demonstrate a fully fledged full-stack application built with Spring Boot + MyBatis including CRUD operations, authentication, routing, pagination, and more.

For more information on how this works with other frontends/backends, head over to the [RealWorld](https://github.com/gothinkster/realworld) repo.

# *NEW* GraphQL Support

Following some DDD principles. REST or GraphQL is just a kind of adapter. And the domain layer will be consistent all the time. So this repository implements GraphQL and REST at the same time.

The GraphQL schema is <https://github.com/gothinkster/spring-boot-realworld-example-app/blob/master/src/main/resources/schema/schema.graphqls> and the visualization looks like below.

[![](https://github.com/gothinkster/spring-boot-realworld-example-app/raw/master/graphql-schema.png)](https://github.com/gothinkster/spring-boot-realworld-example-app/blob/master/graphql-schema.png)

This implementation uses [dgs-framework](https://github.com/Netflix/dgs-framework), a Netflix open-source Java GraphQL server framework.

# How it works

The application uses Spring Boot (Web, MyBatis).

- Uses Domain Driven Design to separate the business term and infrastructure term.
- Uses MyBatis to implement the [Data Mapper](https://martinfowler.com/eaaCatalog/dataMapper.html) pattern for persistence.
- Uses [CQRS](https://martinfowler.com/bliki/CQRS.html) pattern to separate the read model and write model.

Code is organized as follows:

1. `api` — web layer implemented by Spring MVC
2. `core` — business model including entities and services
3. `application` — high-level services for querying data transfer objects
4. `infrastructure` — all implementation classes as technical details

# Security

Integrates with Spring Security and adds a custom filter for JWT token processing.

The secret key is stored in `application.properties`.

# Database

Uses a ~~H2 in-memory database~~ SQLite database (for easy local testing without losing data after every restart). Can be changed to any other database via `application.properties`.

# Getting started

You'll need **Java 17 or higher** installed (Java 21 LTS recommended).

```bash
./gradlew bootRun
```

To verify it works, open a browser tab at <http://localhost:8080/tags> or run:

```bash
curl http://localhost:8080/tags
```

# Try it out with [Docker](https://www.docker.com/)

You'll need Docker installed.

```bash
./gradlew bootBuildImage --imageName spring-boot-realworld-example-app
docker run -p 8081:8080 spring-boot-realworld-example-app
```

# Try it out with a RealWorld frontend

The backend API entry point is at <http://localhost:8080>, **not** <http://localhost:8080/api> as some frontend documentation suggests.

# Run tests

The repository contains test cases covering both API tests and repository tests.

```bash
./gradlew test
```

# Code format

Uses [Spotless](https://github.com/diffplug/spotless) for code formatting.

```bash
./gradlew spotlessJavaApply
```

# Contributing

Please fork the repository and open a pull request to improve the project. Contributions of all kinds are welcome — bug fixes, documentation improvements, and new features.
