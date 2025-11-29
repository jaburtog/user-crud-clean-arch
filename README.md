# User CRUD Clean Architecture

A RESTful user management system implementing Clean Architecture principles with Jakarta EE 10, Liberty server, and PostgreSQL database.

## Architecture

This project follows Clean Architecture principles with clear separation of concerns:

- **Domain Layer** (`domain`): Core business entities (User)
- **Repository Layer** (`repository`): Data persistence interfaces and JPA implementations
- **Service Layer** (`service`): Business logic and use cases
- **API Layer** (`api`): RESTful endpoints using JAX-RS

## Technology Stack

- **Java**: 17
- **Jakarta EE**: 10.0.0
- **Application Server**: Open Liberty
- **Database**: PostgreSQL 16
- **ORM**: Hibernate 6.2.7
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose

## Features

- Create, Read, Update, and Delete (CRUD) operations for users
- RESTful API with JSON responses
- JPA/Hibernate for database persistence
- PostgreSQL database integration
- Docker containerization for easy deployment
- Clean Architecture principles
- Dependency Injection with CDI

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- Docker and Docker Compose (for containerized deployment)
- PostgreSQL 16 (if running locally without Docker)

## Project Structure

```
user-crud-clean-arch/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── usercrud/
│   │   │           ├── domain/         # Domain entities
│   │   │           │   └── User.java
│   │   │           ├── repository/     # Data access layer
│   │   │           │   ├── UserRepository.java
│   │   │           │   └── UserRepositoryImpl.java
│   │   │           ├── service/        # Business logic
│   │   │           │   └── UserService.java
│   │   │           └── api/            # REST API
│   │   │               ├── UserApplication.java
│   │   │               └── UserResource.java
│   │   ├── liberty/
│   │   │   └── config/
│   │   │       └── server.xml          # Liberty server configuration
│   │   └── resources/
│   │       └── META-INF/
│   │           └── persistence.xml     # JPA configuration
│   └── test/
│       └── java/                       # Unit tests
├── Dockerfile                          # Docker configuration
├── docker-compose.yml                  # Multi-container setup
├── pom.xml                            # Maven configuration
├── LICENSE                            # MIT License
└── README.md                          # This file
```

## Building the Project

### Using Maven

```bash
mvn clean package
```

This will create a WAR file in the `target` directory.

## Running the Application

### Option 1: Using Docker Compose (Recommended)

1. Download the PostgreSQL JDBC driver:
   ```bash
   curl -o postgresql-42.6.0.jar https://jdbc.postgresql.org/download/postgresql-42.6.0.jar
   ```

2. Build the project:
   ```bash
   mvn clean package
   ```

3. Start the application and database:
   ```bash
   docker-compose up --build
   ```

The application will be available at: `http://localhost:9080/user-crud`

### Option 2: Using Liberty Maven Plugin

1. Configure PostgreSQL database connection in `src/main/liberty/config/server.xml`

2. Run with Liberty:
   ```bash
   mvn liberty:dev
   ```

## API Endpoints

Base URL: `http://localhost:9080/user-crud/api`

### Create User
```bash
POST /users
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john.doe@example.com"
}
```

### Get All Users
```bash
GET /users
```

### Get User by ID
```bash
GET /users/{id}
```

### Update User
```bash
PUT /users/{id}
Content-Type: application/json

{
  "username": "johndoe_updated",
  "email": "john.updated@example.com"
}
```

### Delete User
```bash
DELETE /users/{id}
```

## Example Usage

### Create a new user:
```bash
curl -X POST http://localhost:9080/user-crud/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","email":"alice@example.com"}'
```

### Get all users:
```bash
curl http://localhost:9080/user-crud/api/users
```

### Get user by ID:
```bash
curl http://localhost:9080/user-crud/api/users/1
```

### Update a user:
```bash
curl -X PUT http://localhost:9080/user-crud/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"username":"alice_updated","email":"alice.updated@example.com"}'
```

### Delete a user:
```bash
curl -X DELETE http://localhost:9080/user-crud/api/users/1
```

## Database Configuration

The application uses PostgreSQL with the following default configuration (when using Docker Compose):

- **Host**: postgres
- **Port**: 5432
- **Database**: userdb
- **User**: userapp
- **Password**: userpass123

These can be modified in the `docker-compose.yml` file.

## Testing

Run the unit tests:

```bash
mvn test
```

## Clean Architecture Principles

This project demonstrates Clean Architecture through:

1. **Independence of Frameworks**: Business logic doesn't depend on Liberty or JAX-RS
2. **Testability**: Business rules can be tested without UI, database, or external services
3. **Independence of UI**: The REST API can be replaced without changing business logic
4. **Independence of Database**: JPA abstracts the database, allowing easy switching
5. **Separation of Concerns**: Each layer has a single, well-defined responsibility

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Author

User CRUD Clean Architecture Team