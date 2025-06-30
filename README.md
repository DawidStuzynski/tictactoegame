# Tic Tac Toe Game

## Overview
This project implements a simple Tic Tac Toe game using Spring Boot and Kotlin. The application allows players to create games, make moves, and retrieve the current state of the game. It uses a PostgreSQL database for persistence and Testcontainers for integration testing.

## Architecture
The application follows a simple Controller-Service-Repository architecture:
- **Controller**: Handles HTTP requests and maps them to service methods.
- **Service**: Contains business logic for managing games.
- **Repository**: Provides access to the database using Spring Data JPA.

This architecture was chosen because the task description mentioned a time constraint of approximately 4 hours. It provides a clean separation of concerns while being quick to implement.

## Improvements and Future Work
The project was built quickly using a simple Controller-Service-Repository structure to meet the requirements efficiently. Some areas for improvement include:

- **Optimistic Locking**: Used to handle rare concurrent updates without the overhead of pessimistic locking or the limitations of `synchronized`.
- **Domain Models**: While the domain models are not tightly coupled to the Spring framework, they could be further refined to make them more reusable and independent. Utility methods like board serialization and parsing, currently in a utility class, could be moved to the domain layer to better align with domain-specific logic.
- **Validation**: Instead of throwing exceptions for validation, it might be better to return validation results for more flexibility.
- **Testing**: Basic tests are in place, but additional tests for edge cases would improve reliability.

Future work could focus on refining the domain models, improving validation mechanisms, and expanding test coverage.

## How to Run
### Prerequisites
- Docker
- Java 21
- PostgreSQL

### Steps
1. Start the PostgreSQL database using Docker Compose:
   ```bash
   docker-compose up -d
   ```
2. Build and run the application:
   ```bash
   ./gradlew bootRun
   ```
3. Access the API at `http://localhost:8080/api/games`.

### Running Tests
Run the tests using Gradle:
```bash
./gradlew test
```

## Technologies Used
- **Spring Boot**: Application framework.
- **Kotlin**: Programming language.
- **PostgreSQL**: Database.
- **Testcontainers**: Integration testing.
- **Flyway**: Database migrations.
- **RestAssured**: API testing.

## Conclusion
This project demonstrates a simple implementation of a Tic Tac Toe game with a focus on clean architecture. While the current implementation meets the requirements, there are opportunities for improvement to make the application more robust and maintainable.
