# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
./gradlew build          # Build the project
./gradlew bootRun        # Run the application
./gradlew test           # Run all tests
./gradlew clean build    # Clean and rebuild
```

Run a single test class:
```bash
./gradlew test --tests "be.kdg.dishsg.SomeTestClass"
```

## Infrastructure

Start all required services (PostgreSQL on 5445, RabbitMQ on 5672/15672, Keycloak on 8180):
```bash
docker compose up -d
```

## Architecture

Modular monolith using **Spring Modulith** with **hexagonal architecture** (ports & adapters).

**Modules** (`src/main/java/be/kdg/dishsg/`):
- `security` — Authentication/session management, Keycloak/JWT integration
- `restaurant` — Restaurant CRUD and owner associations
- `catalog` — Dish publishing/draft lifecycle
- `dish` — Dish stock management and pending changes (max 10 live dishes/restaurant)
- `common` — RabbitMQ configuration shared across modules

**Layer pattern per module:**
```
web/dto/         HTTP controllers & request/response DTOs
app/             Use-case services (implement in-ports)
ports/           Interface definitions (in-ports = use cases, out-ports = repositories)
adapter/         Repository implementations (out-port adapters)
infra/           JPA entities, Spring Data repositories
domain/          Pure domain models and business rules
```

**Request flow:** `Controller → UseCase (app service) → Domain logic → Repository port → JPA adapter → PostgreSQL`

## Key Tech

- Java 21, Spring Boot 3.5.6, Spring Modulith 1.4.1
- Spring Security 6 + OAuth2 JWT resource server + Keycloak 26.3 (realm: `kdg-realm`, issuer: `http://localhost:8180/realms/kdg-realm`)
- Spring Data JPA + PostgreSQL (Hibernate DDL auto — **no Flyway/Liquibase**, schema is created from entities on every start with `ddl-auto=create`)
- RabbitMQ via Spring AMQP with Spring Modulith event externalization

## Authentication

Two parallel auth mechanisms coexist:
1. **Keycloak JWT Bearer tokens** — standard OAuth2 resource server path
2. **Custom session tokens** — `OwnerSessionAuthenticationFilter` extracts a session token from the store for local dev/testing; `POST /unsecured/auth/signup` and `POST /unsecured/auth/signin` issue these

Routes under `/unsecured/**` bypass authentication; all others require it.

## Database

- Production: PostgreSQL at `localhost:5445`, db `postgres`, credentials `user/password`
- Tests: H2 in-memory with `MODE=PostgreSQL`, schema recreated per test run (`ddl-auto=create-drop`)
- Schema is defined entirely via JPA annotations — adding/changing entity fields changes the schema on next boot
