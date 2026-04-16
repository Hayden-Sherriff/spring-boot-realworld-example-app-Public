**TRACEABILITY DOCUMENT — BACKEND SYSTEM (Java Spring Boot)**  
**Author:** Senior Software Architect  
**Date:** 2026-04-16  
**Version:** 1.0

---

**Logo:** (optional)

---

**Table of Contents**

1. Overview ......................................................... 2  
2. Scope and Objectives ............................................ 3  
3. REST Endpoints and Functional Requirements ...................... 4  
4. HTTP Routes Summary ............................................ 6  
5. Controller → Service → DTO Mapping ............................. 7  
6. Architecture and Layering ...................................... 9  
7. Data Validation Mechanisms .................................... 11  
8. Error Handling ................................................ 12  
9. Security, Authentication and Authorization ..................... 14  
10. Conclusions and Recommendations ............................... 16

---

**1. Overview**

This document provides an evidence-based traceability report of the backend Java Spring Boot API code provided. All statements, mappings and behavioral descriptions are strictly derived from the supplied source code. Where the code does not provide explicit evidence, the document indicates that lack of evidence.

---

**2. Scope and Objectives**

- Objective (evidence-based): Expose HTTP REST endpoints for articles, comments, profiles, tags and user management, delegating business logic to application services and persistence to repositories/read services. (Supported by controller class implementations and service wiring.)
- Scope (evidence-based): API controllers under package io.spring.api that handle create/read/update/delete operations and query endpoints for articles, comments, profiles, tags and users. (Supported by controller classes: ArticlesApi, ArticleApi, ArticleFavoriteApi, CommentsApi, ProfileApi, TagsApi, UsersApi, CurrentUserApi.)

Observation: No explicit high-level non-functional objectives (e.g., SLA, throughput) are present in the provided code.

---

**3. REST Endpoints and Functional Requirements**

Note: Each endpoint's functional requirement is recorded only when directly supported by controller, service, DTO or repository evidence.

- Articles collection (POST /articles)
  - Requirement: Create an article from NewArticleParam, require authenticated user, persist via ArticleCommandService, return created article via ArticleQueryService. (Supported: ArticlesApi.createArticle, ArticleCommandService.createArticle, ArticleQueryService.findById.)
  - Input validation: @Valid on NewArticleParam at controller and @Validated on ArticleCommandService methods. (Supported.)

- Get articles (GET /articles)
  - Requirement: Return recent articles with optional filters tag, author, favorited, support offset/limit, may accept anonymous requests. (Supported: ArticlesApi.getArticles, ArticleQueryService.findRecentArticles.)

- Get user feed (GET /articles/feed)
  - Requirement: Return feed for authenticated user with offset/limit, uses ArticleQueryService.findUserFeed. (Supported.)

- Article operations by slug (GET, PUT, DELETE on /articles/{slug})
  - GET: Retrieve article by slug, optional authenticated principal affects returned ArticleData. (Supported: ArticleApi.article, ArticleQueryService.findBySlug.)
  - PUT: Update article using UpdateArticleParam, requires authorization via AuthorizationService.canWriteArticle, persist via ArticleCommandService.updateArticle, return updated ArticleData. (Supported: ArticleApi.updateArticle, AuthorizationService, ArticleCommandService.updateArticle.)
  - DELETE: Delete article if authorized via AuthorizationService.canWriteArticle and repository removal. (Supported: ArticleApi.deleteArticle.)

- Favorite article (POST /articles/{slug}/favorite)
  - Requirement: Create ArticleFavorite relation using ArticleFavorite and ArticleFavoriteRepository.save, return updated ArticleData. Requires authenticated user. (Supported: ArticleFavoriteApi.favoriteArticle.)

- Unfavorite article (DELETE /articles/{slug}/favorite)
  - Requirement: Remove favorite relation if present via ArticleFavoriteRepository.find(...).ifPresent(remove), return updated ArticleData. (Supported: ArticleFavoriteApi.unfavoriteArticle.)

- Comments for article (POST, GET, DELETE on /articles/{slug}/comments)
  - POST: Create comment with NewCommentParam.body validated @NotBlank, requires authenticated user, persist via CommentRepository.save, return 201 with CommentData from CommentQueryService.findById. (Supported: CommentsApi.createComment, NewCommentParam.)
  - GET: Return comments list for article using CommentQueryService.findByArticleId after article existence check. (Supported: CommentsApi.getComments.)
  - DELETE: Delete a comment by id if AuthorizationService.canWriteComment allows, otherwise throw NoAuthorizationException; return 204. Article and comment existence checks performed. (Supported: CommentsApi.deleteComment, AuthorizationService.canWriteComment.)

- Profiles (GET /profiles/{username}, POST /profiles/{username}/follow, DELETE /profiles/{username}/follow)
  - GET: Return ProfileData via ProfileQueryService.findByUsername; optional current user influences "following" flag. (Supported.)
  - POST (follow): Save FollowRelation via UserRepository.saveRelation and return updated profile. (Supported: ProfileApi.follow.)
  - DELETE (unfollow): Remove relation via UserRepository.removeRelation after lookup; return updated profile. (Supported: ProfileApi.unfollow.)

- Tags (GET /tags)
  - Requirement: Return all tags via TagsQueryService.allTags(). (Supported: TagsApi.getTags.)

- Users (POST /users, POST /users/login)
  - POST /users: Create user using UserService.createUser from RegisterParam @Valid, generate JWT via JwtService.toToken, return 201 with UserWithToken. (Supported: UsersApi.createUser, UserService.createUser, DefaultJwtService.)
  - POST /users/login: Authenticate by finding user by email via UserRepository.findByEmail and verifying passwordEncoder.matches; on success return UserWithToken with jwtService.toToken; on failure throw InvalidAuthenticationException. (Supported: UsersApi.userLogin.)

- Current user endpoints (/user GET, PUT)
  - GET /user: Return current authenticated user's UserWithToken assembled with Authorization header token substring. Uses UserQueryService.findById. (Supported: CurrentUserApi.currentUser.)
  - PUT /user: Update current user using UserService.updateUser with UpdateUserCommand validated by custom constraint UpdateUserConstraint; return updated UserWithToken assembled with provided token. (Supported: CurrentUserApi.updateProfile, UserService.updateUser, UpdateUserConstraint/UpdateUserValidator.)

If any endpoint refers to DTOs (ArticleData, CommentData, ProfileData, UserData, UserWithToken, NewArticleParam, UpdateArticleParam, RegisterParam, UpdateUserParam) they appear in code only as types used by services or controllers. DTO definitions were not provided in the supplied code. Evidence of their usage is shown where indicated.

---

**4. HTTP Routes Summary**

<table style="border-collapse:collapse;width:100%;table-layout:auto"><thead><tr><th style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Route</th><th style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">Method</th><th style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Controller</th><th style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Brief description</th></tr></thead><tbody><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/articles</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">POST</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticlesApi.createArticle</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Create article; requires authentication</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/articles</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">GET</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticlesApi.getArticles</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">List recent articles with optional filters</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/articles/feed</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">GET</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticlesApi.getFeed</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Get authenticated user's feed</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/articles/{slug}</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">GET</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticleApi.article</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Get article by slug</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/articles/{slug}</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">PUT</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticleApi.updateArticle</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Update article; authorization required</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/articles/{slug}</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">DELETE</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticleApi.deleteArticle</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Delete article; authorization required</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/articles/{slug}/favorite</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">POST</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticleFavoriteApi.favoriteArticle</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Favorite article; requires auth</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/articles/{slug}/favorite</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">DELETE</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticleFavoriteApi.unfavoriteArticle</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Unfavorite article; requires auth</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/articles/{slug}/comments</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">POST</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">CommentsApi.createComment</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Create comment; requires auth</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/articles/{slug}/comments</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">GET</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">CommentsApi.getComments</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Get comments for article</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/articles/{slug}/comments/{id}</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">DELETE</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">CommentsApi.deleteComment</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Delete comment; authorization required</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/profiles/{username}</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">GET</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ProfileApi.getProfile</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Get profile by username</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/profiles/{username}/follow</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">POST</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ProfileApi.follow</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Follow user; requires auth</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/profiles/{username}/follow</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">DELETE</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ProfileApi.unfollow</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Unfollow user; requires auth</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/tags</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">GET</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">TagsApi.getTags</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Get all tags</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/users</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">POST</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">UsersApi.createUser</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Register user; returns token</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/users/login</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">POST</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">UsersApi.userLogin</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Authenticate user; returns token</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/user</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">GET</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">CurrentUserApi.currentUser</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Get current user with token</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">/user</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:right;white-space:pre-wrap;word-break:break-word">PUT</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">CurrentUserApi.updateProfile</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Update current user; validated</td></tr></tbody></table>
---

**5. Controller → Service → DTO Mapping (Summary Table)**

<table style="border-collapse:collapse;width:100%;table-layout:auto"><thead><tr><th style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Controller</th><th style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">Called Service(s)</th><th style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">DTOs / Domain types observed</th></tr></thead><tbody><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticlesApi</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticleCommandService, ArticleQueryService</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">NewArticleParam, Article, ArticleData</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticleApi</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticleQueryService, ArticleCommandService, ArticleRepository</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">UpdateArticleParam, Article, ArticleData</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticleFavoriteApi</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticleFavoriteRepository, ArticleRepository, ArticleQueryService</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticleFavorite, Article, ArticleData</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">CommentsApi</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ArticleRepository, CommentRepository, CommentQueryService</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">NewCommentParam, Comment, CommentData</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ProfileApi</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ProfileQueryService, UserRepository</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">ProfileData, FollowRelation</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">TagsApi</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">TagsQueryService</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">List&lt;String&gt; (tags)</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">UsersApi</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">UserService, UserQueryService, JwtService, UserRepository</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">RegisterParam, LoginParam, UserData, UserWithToken</td></tr><tr><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">CurrentUserApi</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">UserQueryService, UserService</td><td style="border:1px solid #444;padding:6px;vertical-align:top;text-align:left;white-space:pre-wrap;word-break:break-word">UpdateUserParam, UpdateUserCommand, UserData, UserWithToken</td></tr></tbody></table>
Comment: DTO class definitions were referenced but not supplied in the code excerpt. Mapping above reflects types used by controllers and services only.

---

**6. Architecture and Layering (Evidence-based)**

- Layering observed (from code packages and classes):
  - API Layer: io.spring.api — Spring REST controllers (ArticlesApi, ArticleApi, CommentsApi, ProfileApi, TagsApi, UsersApi, CurrentUserApi, ArticleFavoriteApi). Purpose: HTTP request handling, request binding, response building.
  - Application / Service Layer: io.spring.application and subpackages — business logic and query composition (ArticleCommandService, ArticleQueryService, CommentQueryService, ProfileQueryService, TagsQueryService, UserService, UserQueryService). Purpose: orchestrate operations, enrich DTOs, enforce validation annotations (@Validated).
  - Core / Domain Layer: io.spring.core.* — domain entities and repositories (Article, Comment, User, repositories, AuthorizationService, JwtService interface). Purpose: domain rules and repository contracts.
  - Infrastructure Layer: io.spring.infrastructure.* — MyBatis read-service mappers and JWT implementation (ArticleReadService, CommentReadService, UserReadService, TagReadService, UserRelationshipQueryService, ArticleFavoritesReadService, DefaultJwtService). Purpose: persistence or third-party integration.
- Pattern: MVC-like separation where Controllers handle HTTP concerns, Services implement application logic, Repositories/ReadServices access data. Evidence: controller classes delegate to services/readservices/repositories; services call read services and mappers.
- Diagram (textual, evidence-based):
  - Client -> Controllers (io.spring.api) -> Application Services (io.spring.application) -> ReadServices / Repositories (io.spring.infrastructure / io.spring.core) -> Database
- Comment: No explicit configuration classes or dependency injection configuration were provided in the snippet beyond @AllArgsConstructor/@Autowired usage; standard Spring DI is implied by annotations in code.

---

**7. Data Validation Mechanisms (Evidence-based)**

- Bean Validation annotations observed at controller method parameters: @Valid on request body parameters (e.g., NewArticleParam, UpdateArticleParam, RegisterParam, LoginParam, UpdateUserParam, NewCommentParam). (Supported by multiple controllers.)
- Constraint annotations on DTO-like parameter classes:
  - LoginParam.email annotated with @NotBlank and @Email.
  - LoginParam.password annotated with @NotBlank.
  - NewCommentParam.body annotated with @NotBlank.
- Service-level validation:
  - ArticleCommandService and UserService are annotated with @Validated, enabling validation of method parameters like @Valid NewArticleParam and @Valid UpdateUserCommand. (Supported.)
- Custom validation:
  - UpdateUserConstraint annotation and UpdateUserValidator implement a custom constraint that validates UpdateUserCommand to ensure email and username uniqueness relative to target user using UserRepository lookups. (Supported.)
- Constraint violation handling:
  - CustomizeExceptionHandler handles ConstraintViolationException and MethodArgumentNotValidException and maps them to HTTP 422 response with an ErrorResource containing field error details. (Supported.)

If DTO classes (NewArticleParam, UpdateArticleParam, RegisterParam, UpdateUserParam) contain additional constraints, definitions were not present in provided code; only referenced validations above are documented.

---

**8. Error Handling (Evidence-based)**

- Global exception handling:
  - Class CustomizeExceptionHandler extends ResponseEntityExceptionHandler and is annotated @RestControllerAdvice. It provides handlers for:
    - InvalidRequestException -> returns ErrorResource with field details at HTTP 422.
    - InvalidAuthenticationException -> returns {"message": e.getMessage()} with HTTP 422.
    - MethodArgumentNotValidException -> overrides handleMethodArgumentNotValid to return ErrorResource with HTTP 422.
    - ConstraintViolationException -> returns ErrorResource with HTTP 422.
  - Implementation converts validation errors into structured FieldErrorResource and wraps into ErrorResource. (Supported.)
- Controller-level exceptions:
  - Controllers throw ResourceNotFoundException when lookups fail (e.g., repository.findBySlug(...).orElseThrow(ResourceNotFoundException::new)). Evidence: multiple controllers.
  - Controllers throw NoAuthorizationException when AuthorizationService denies access. Evidence: ArticleApi.updateArticle/deleteArticle, CommentsApi.deleteComment.
  - UsersApi.userLogin throws InvalidAuthenticationException on authentication failure.
- HTTP status evidence:
  - Several methods explicitly set status codes:
    - CommentsApi.createComment returns ResponseEntity.status(201) when creating comments.
    - UsersApi.createUser returns ResponseEntity.status(201) when creating users.
    - Delete operations return noContent() leading to 204 responses in code paths.
  - CustomizeExceptionHandler maps validation and authentication errors to HTTP 422 as shown by ResponseStatus and returned statuses.
- Examples of error response shapes (as evidenced by handlers):
  - Validation (422): ErrorResource object containing list of FieldErrorResource entries (field, message, code). (Exact class definitions not provided in snippet; structure derived from mapping code.)
  - InvalidAuthenticationException (422): {"message": "<exception message>"}
- Observation: The code refers to ResourceNotFoundException and NoAuthorizationException but the handlers for these exceptions are not shown in the provided snippet; therefore mapping of those exceptions to HTTP codes is not evidenced here. (No evidence of global handler for 404/403 in provided CustomizeExceptionHandler.)

---

**9. Security, Authentication and Authorization (Evidence-based)**

- Authentication principal injection:
  - Controllers use @AuthenticationPrincipal User user to obtain the current authenticated user principal in multiple endpoints (ArticlesApi, ArticleApi, ArticleFavoriteApi, CommentsApi, ProfileApi, CurrentUserApi). This indicates Spring Security integration for principal propagation. (Supported.)
- JWT integration:
  - JwtService interface exists with methods toToken(User) and getSubFromToken(String). DefaultJwtService implements JwtService using io.jsonwebtoken (JJWT) to sign tokens with HS512 and parse tokens. It uses application properties jwt.secret and jwt.sessionTime. (Supported.)
  - UsersApi uses JwtService.toToken to generate tokens on registration and login. CurrentUserApi extracts bearer token from Authorization header and includes token substring in returned UserWithToken. (Supported.)
- Password hashing:
  - PasswordEncoder is injected into UsersApi and used to verify password matches during login (passwordEncoder.matches) and UserService uses PasswordEncoder.encode when creating new users. (Supported.)
- Authorization rules:
  - AuthorizationService contains static methods:
    - canWriteArticle(User, Article): true when user.id equals article.userId.
    - canWriteComment(User, Article, Comment): true when user.id equals article.userId OR user.id equals comment.userId.
  - Controllers call AuthorizationService to enforce write/delete permissions (ArticleApi and CommentsApi). (Supported.)
- Roles: No evidence of role-based authorities, role strings, or role checks in the supplied code.
- Spring Security configuration: No explicit WebSecurityConfigurerAdapter or SecurityFilterChain configuration is present in the supplied code. Presence of @AuthenticationPrincipal and JwtService suggest security is configured elsewhere, but that configuration is not in the provided snippet. (No evidence.)
- Token extraction: CurrentUserApi and CurrentUserApi.updateProfile expect the Authorization header and extract token via token.split(" ")[1]. This implies bearer token format "Bearer <token>". No evidence of validation logic here; token validation likely occurs in security filter not provided. (Observation.)

---

**10. Conclusions and Recommendations**

Conclusions (evidence-based):
- The codebase exhibits a clear layered architecture: controllers delegate to application services which rely on repositories/read services and domain objects. (Supported.)
- Validation is enforced at controller and service levels using JSR-303 annotations and a custom validator for user update uniqueness. Validation failures are consistently mapped to HTTP 422 by a global exception handler. (Supported.)
- Authentication is performed using JWTs; tokens are created via JwtService and DefaultJwtService implements signing/parsing. Password hashing via PasswordEncoder is present. Controllers expect an authenticated principal via @AuthenticationPrincipal. (Supported.)
- Authorization checks for modifying/deleting articles and comments are centrally implemented via AuthorizationService and invoked from controllers prior to state-changing operations. (Supported.)
- Error handling for validation and invalid authentication is explicitly implemented. Handling for ResourceNotFoundException and NoAuthorizationException is used in controllers but no explicit mapping to HTTP 404/403 was included in the provided global handler code. (Supported and noted missing evidence.)

Recommendations (evidence-based or reasonable observations flagged):
- Add explicit exception handlers for ResourceNotFoundException and NoAuthorizationException in CustomizeExceptionHandler to ensure consistent HTTP 404 and 403 responses. (Reasonable inference due to absence of handlers in provided snippet.)
- Ensure the security filter and token parsing/validation logic are present and aligned with DefaultJwtService.getSubFromToken to populate AuthenticationPrincipal; this configuration was not provided. (Observation: needed, not evidenced.)
- Provide DTO class definitions (ArticleData, CommentData, UserWithToken, etc.) in repository for full traceability and to confirm JSON response schemas. (Evidence: DTOs are referenced but definitions not included.)
- Include test cases (unit/integration) that assert behavior of authorization and validation flows. (Recommendation; not evidenced.)

---

Document prepared strictly from supplied source code. Missing evidence has been noted where applicable.


