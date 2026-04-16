# spring-boot-realworld-example-app

Table of Contents

- [Overview](#overview)
- [Purpose](#purpose)
- [Technical Summary](#technical-summary)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Installation and Execution Instructions](#installation-and-execution-instructions)
- [Technical Summary of Classes and Layer Relationships](#technical-summary-of-classes-and-layer-relationships)
- [License](#license)
- [Contact Information](#contact-information)

## Overview

This repository contains a Spring Boot example application that implements REST and GraphQL endpoints and includes application, core domain, and infrastructure components. The project is configured with Gradle and includes test coverage and a CI workflow.

## Purpose

The purpose of the codebase is to provide an implementation of the RealWorld example application using Spring Boot, MyBatis, and GraphQL (DGS). The project exposes REST controllers for article, user, comment, profile, tag and related operations, includes JWT-based security configuration, and contains MyBatis-based repository implementations and mappers.

## Technical Summary

Aspect | Value
--- | ---
Language | Java (sourceCompatibility = 11)
Framework | Spring Boot (Gradle plugin 'org.springframework.boot' version 2.6.3)
Build System | Gradle (build.gradle present; Gradle wrapper invoked in CI)
GraphQL | Netflix DGS (dependency com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter:4.9.21, codegen plugin configured)
ORM / Mapper | MyBatis (mybatis-spring-boot-starter:2.2.2, mapper XML locations configured)
Database | SQLite (spring.datasource.url=jdbc:sqlite:dev.db and org.xerial:sqlite-jdbc:3.36.0.3)
Security | Spring Security (spring-boot-starter-security) and JWT (jjwt 0.11.2)
API Documentation | OpenAPI annotations present (io.swagger.v3.oas.annotations used in controllers)
CI/CD | GitHub Actions workflow (.github/workflows/gradle.yml)
Testing | JUnit (useJUnitPlatform), Spring Boot test, Rest-Assured (test dependencies present)

## Technologies Used

- Java (sourceCompatibility 11)
- Spring Boot (Gradle plugin version 2.6.3)
- Spring Web
- Spring Security
- Spring Validation
- Spring HATEOAS
- MyBatis (mybatis-spring-boot-starter 2.2.2)
- Netflix DGS GraphQL (graphql-dgs-spring-boot-starter 4.9.21)
- Flyway (flyway-core)
- JJWT (io.jsonwebtoken 0.11.2)
- Joda-Time (2.10.13)
- SQLite JDBC (org.xerial:sqlite-jdbc:3.36.0.3)
- Lombok (compileOnly + annotationProcessor)
- Spotless (code formatting plugin)
- Gradle build system
- OpenAPI annotations (io.swagger.v3.oas.annotations)
- GitHub Actions (CI workflow)
- Testing libraries: JUnit, Spring Boot Test, Rest-Assured

## Project Structure

The top-level Java package structure (as detected) is shown below.

~~~
java/
└── io/
    └── spring/
        ├── JacksonCustomizations.java
        ├── MyBatisConfig.java
        ├── RealWorldApplication.java
        ├── Util.java
        ├── api/
        │   ├── ArticleApi.java
        │   ├── ArticleFavoriteApi.java
        │   ├── ArticlesApi.java
        │   ├── CommentsApi.java
        │   ├── CurrentUserApi.java
        │   ├── ProfileApi.java
        │   ├── TagsApi.java
        │   ├── UsersApi.java
        │   ├── exception/
        │   │   ├── CustomizeExceptionHandler.java
        │   │   ├── ErrorResource.java
        │   │   ├── ErrorResourceSerializer.java
        │   │   ├── FieldErrorResource.java
        │   │   ├── InvalidAuthenticationException.java
        │   │   ├── InvalidRequestException.java
        │   │   ├── NoAuthorizationException.java
        │   │   └── ResourceNotFoundException.java
        │   └── security/
        │       ├── JwtTokenFilter.java
        │       └── WebSecurityConfig.java
        ├── application/
        │   ├── ArticleQueryService.java
        │   ├── CommentQueryService.java
        │   ├── CursorPageParameter.java
        │   ├── CursorPager.java
        │   ├── DateTimeCursor.java
        │   ├── Node.java
        │   ├── Page.java
        │   ├── PageCursor.java
        │   ├── ProfileQueryService.java
        │   ├── TagsQueryService.java
        │   ├── UserQueryService.java
        │   ├── article/
        │   │   ├── ArticleCommandService.java
        │   │   ├── DuplicatedArticleConstraint.java
        │   │   ├── DuplicatedArticleValidator.java
        │   │   ├── NewArticleParam.java
        │   │   └── UpdateArticleParam.java
        │   ├── data/
        │   │   ├── ArticleData.java
        │   │   ├── ArticleDataList.java
        │   │   ├── ArticleFavoriteCount.java
        │   │   ├── CommentData.java
        │   │   ├── ProfileData.java
        │   │   ├── UserData.java
        │   │   └── UserWithToken.java
        │   └── user/
        │       ├── DuplicatedEmailConstraint.java
        │       ├── DuplicatedEmailValidator.java
        │       ├── DuplicatedUsernameConstraint.java
        │       ├── DuplicatedUsernameValidator.java
        │       ├── RegisterParam.java
        │       ├── UpdateUserCommand.java
        │       ├── UpdateUserParam.java
        │       └── UserService.java
        ├── core/
        │   ├── article/
        │   │   ├── Article.java
        │   │   ├── ArticleRepository.java
        │   │   └── Tag.java
        │   ├── comment/
        │   │   ├── Comment.java
        │   │   └── CommentRepository.java
        │   ├── favorite/
        │   │   ├── ArticleFavorite.java
        │   │   └── ArticleFavoriteRepository.java
        │   ├── service/
        │   │   ├── AuthorizationService.java
        │   │   └── JwtService.java
        │   └── user/
        │       ├── FollowRelation.java
        │       ├── User.java
        │       └── UserRepository.java
        ├── graphql/
        │   ├── ArticleDatafetcher.java
        │   ├── ArticleMutation.java
        │   ├── CommentDatafetcher.java
        │   ├── CommentMutation.java
        │   ├── MeDatafetcher.java
        │   ├── ProfileDatafetcher.java
        │   ├── RelationMutation.java
        │   ├── SecurityUtil.java
        │   ├── TagDatafetcher.java
        │   ├── UserMutation.java
        │   └── exception/
        │       ├── AuthenticationException.java
        │       └── GraphQLCustomizeExceptionHandler.java
        └── infrastructure/
            ├── mybatis/
            │   ├── DateTimeHandler.java
            │   ├── mapper/
            │   │   ├── ArticleFavoriteMapper.java
            │   │   ├── ArticleMapper.java
            │   │   ├── CommentMapper.java
            │   │   └── UserMapper.java
            │   └── readservice/
            │       ├── ArticleFavoritesReadService.java
            │       ├── ArticleReadService.java
            │       ├── CommentReadService.java
            │       ├── TagReadService.java
            │       ├── UserReadService.java
            │       └── UserRelationshipQueryService.java
            ├── repository/
            │   ├── MyBatisArticleFavoriteRepository.java
            │   ├── MyBatisArticleRepository.java
            │   ├── MyBatisCommentRepository.java
            │   └── MyBatisUserRepository.java
            └── service/
                └── DefaultJwtService.java
~~~

Detected REST endpoints (representative, not exhaustive): GET/PUT/DELETE /articles/{slug}, POST/DELETE /articles/{slug}/favorite, POST /articles, GET /articles/feed, GET /articles, comment endpoints under /articles/{slug}/comments, user endpoints /users and /users/login, /user, /profiles/{username} and follow endpoints, GET /tags. Controllers use OpenAPI annotations.

## Installation and Execution Instructions

Prerequisites and build system
- The project uses Gradle (build.gradle present). The GitHub Actions workflow executes commands using the Gradle wrapper (./gradlew), which indicates the project expects Gradle wrapper usage.

Recommended local commands (consistent with project configuration and CI):
- Run tests:
~~~
./gradlew clean test
~~~
- Build the project:
~~~
./gradlew clean build
~~~
- Run the application:
~~~
./gradlew bootRun
~~~

Configuration notes observed in repository
- The application configuration file src/main/resources/application.properties configures a SQLite datasource:
  - spring.datasource.url=jdbc:sqlite:dev.db
  - spring.datasource.driver-class-name=org.sqlite.JDBC
- The same properties file contains application-level properties for JWT (jwt.secret and jwt.sessionTime), default image URL (image.default), and MyBatis settings (mapper locations, type handlers package, and several mybatis.configuration properties).
- Tests and build rely on JUnit Platform (useJUnitPlatform), Rest-Assured and Spring Boot test support as defined in build.gradle.

Caveats
- The repository does not include Docker or container orchestration files in the detected project root; containerization steps are not described here.
- If you need to override configuration properties, use standard Spring Boot property override mechanisms (environment variables or external properties). The repository contains application.properties as the baseline configuration.

## Technical Summary of Classes and Layer Relationships

This section summarizes concrete packages, key classes, and observable responsibilities as present in the repository.

- Entry point
  - io.spring.RealWorldApplication: Spring Boot main application class annotated with @SpringBootApplication.

- API / Web layer (REST controllers)
  - Package: io.spring.api
  - Representative controllers: ArticleApi, ArticlesApi, ArticleFavoriteApi, CommentsApi, CurrentUserApi, ProfileApi, TagsApi, UsersApi
  - Characteristics:
    - Controllers are annotated with @RestController and expose REST endpoints (detected endpoints list above).
    - Controllers use OpenAPI annotations (io.swagger.v3.oas.annotations) for operation and response metadata.
    - Controllers use @AuthenticationPrincipal to access authenticated user principal where applicable.

- Security
  - Package: io.spring.api.security
  - Classes: WebSecurityConfig, JwtTokenFilter
  - Characteristics:
    - WebSecurityConfig extends WebSecurityConfigurerAdapter and configures CORS, CSRF disabled, stateless sessions, authorization rules, and registers JwtTokenFilter.
    - JwtTokenFilter is provided as a bean and is added before UsernamePasswordAuthenticationFilter.

- Application layer (use-cases, DTOs, validation)
  - Package: io.spring.application (and subpackages)
  - Representative classes: ArticleQueryService, CommentQueryService, UserQueryService, ProfileQueryService, TagsQueryService, ArticleCommandService, UserService
  - Data transfer objects: classes under io.spring.application.data (ArticleData, UserData, UserWithToken, CommentData, etc.)
  - Validation and command parameters: RegisterParam, UpdateUserParam, NewArticleParam, UpdateArticleParam, validators and constraint annotations are present.

- Core domain
  - Package: io.spring.core
  - Domain entities and repository interfaces:
    - article: Article, Tag, ArticleRepository
    - comment: Comment, CommentRepository
    - favorite: ArticleFavorite, ArticleFavoriteRepository
    - user: User, FollowRelation, UserRepository
  - Core services: AuthorizationService, JwtService (interface)

- Infrastructure / Persistence
  - Package: io.spring.infrastructure
  - MyBatis integration:
    - mybatis package contains mappers (ArticleMapper, CommentMapper, UserMapper, ArticleFavoriteMapper) and DateTimeHandler, and readservice classes for read-side queries.
    - Mapper XML files are referenced via mybatis.mapper-locations=mapper/*.xml (configured in application.properties).
  - Repository implementations:
    - repository package contains MyBatis-based implementations: MyBatisArticleRepository, MyBatisCommentRepository, MyBatisUserRepository, MyBatisArticleFavoriteRepository.
  - Service implementations:
    - DefaultJwtService implements JwtService.

- GraphQL
  - Package: io.spring.graphql
  - Classes: ArticleDatafetcher, ArticleMutation, CommentDatafetcher, CommentMutation, MeDatafetcher, ProfileDatafetcher, RelationMutation, SecurityUtil, TagDatafetcher, UserMutation
  - The project includes the DGS codegen Gradle plugin configuration (generateJava task pointing to src/main/resources/schema), indicating GraphQL schema-based code generation.

- Configuration and utilities
  - MyBatisConfig.java and JacksonCustomizations.java are present for framework and serialization configuration.
  - Util.java and other helper classes exist in the root package.

- Exception handling
  - REST exception handlers and error resource classes are present under io.spring.api.exception (CustomizeExceptionHandler, ErrorResource, etc.).
  - GraphQL-specific exception handling classes under graphql.exception.

- Tests
  - Test sources under src/test/java include controller and service tests (23 test files detected). Test support includes Spring Boot test, Mockito, and Rest-Assured. There are repository and integration-style tests referencing database fixtures and a DbTestBase.

This summary reflects concrete classes and responsibilities visible in the repository sources and configuration files. It does not speculate about runtime hosting or deployment outside of the repository artifacts.

## License

This repository includes a LICENSE file. The license provided is the MIT License.

## Contact Information

Project Maintainer: Not specified in repository metadata.