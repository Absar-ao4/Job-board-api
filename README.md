# Job Board API

A production-ready REST API backend for a Job Board platform built with Java and Spring Boot. The project supports JWT-based authentication, recruiter/applicant role workflows, job posting, job listing, and job application management.

The backend is deployed live on Railway with a hosted MySQL database.

---

## Live API

**Base URL**

```txt
https://job-board-api-production-94ef.up.railway.app
```

---

## Features

- User registration and login with JWT authentication
- BCrypt password hashing for secure credential storage
- Role-based workflow for `RECRUITER` and `APPLICANT`
- Recruiters can create and manage job posts
- Applicants can apply for available jobs
- Application status management: `APPLIED`, `SHORTLISTED`, `REJECTED`
- Protected routes using Spring Security and JWT filter chain
- Stateless session management
- MySQL persistence with Spring Data JPA and Hibernate
- Deployed on Railway with environment-based database configuration
- API tested using Postman

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.6 |
| Security | Spring Security, JWT, BCrypt |
| ORM | Spring Data JPA, Hibernate |
| Database | MySQL |
| Build Tool | Maven |
| Deployment | Railway |
| API Testing | Postman |

---

## API Endpoints

### Auth

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| POST | `/auth/register` | Register a new user and return JWT token | No |
| POST | `/auth/login` | Login user and return JWT token | No |

### Users

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| GET | `/users` | Get all users | Yes |
| GET | `/users/{id}` | Get user by ID | Yes |
| POST | `/users` | Create a user | Yes |
| DELETE | `/users/{id}` | Delete user by ID | Yes |

### Jobs

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| GET | `/jobs` | Get all jobs | Yes |
| GET | `/jobs/{id}` | Get job by ID | Yes |
| POST | `/jobs?userId={id}` | Create a job post — `RECRUITER` only | Yes |
| DELETE | `/jobs/{id}` | Delete job by ID | Yes |

### Applications

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| POST | `/applications?userId={id}&jobId={id}` | Apply for a job — `APPLICANT` only | Yes |
| GET | `/applications/job/{jobId}` | Get all applications for a job | Yes |
| PUT | `/applications/{id}/status?status={status}` | Update application status | Yes |

---

## Authentication Flow

### Register

```http
POST /auth/register
```

Request body:

```json
{
  "name": "Absar Ali",
  "email": "absar@test.com",
  "password": "123456",
  "role": "RECRUITER"
}
```

Response:

```json
{
  "token": "your_jwt_token_here"
}
```

### Login

```http
POST /auth/login
```

Request body:

```json
{
  "email": "absar@test.com",
  "password": "123456"
}
```

Response:

```json
{
  "token": "your_jwt_token_here"
}
```

### Access Protected Routes

For protected endpoints, include the JWT token in the request header:

```txt
Authorization: Bearer your_jwt_token_here
```

---

## Sample Requests

### Get All Users

```http
GET /users
Authorization: Bearer your_jwt_token_here
```

### Create Job

```http
POST /jobs?userId=1
Authorization: Bearer your_jwt_token_here
Content-Type: application/json
```

Request body:

```json
{
  "title": "Backend Developer Intern",
  "description": "Spring Boot internship role",
  "location": "Remote",
  "salary": 15000
}
```

### Apply for Job

```http
POST /applications?userId=2&jobId=1
Authorization: Bearer your_jwt_token_here
```

### Update Application Status

```http
PUT /applications/1/status?status=SHORTLISTED
Authorization: Bearer your_jwt_token_here
```

---

## Business Rules

- Only users with role `RECRUITER` can post jobs.
- Only users with role `APPLICANT` can apply for jobs.
- Application status can only be one of:
  - `APPLIED`
  - `SHORTLISTED`
  - `REJECTED`
- Passwords are hashed using BCrypt before being stored.
- All endpoints except `/auth/**` require a valid JWT token.

---

## Getting Started Locally

### Prerequisites

- Java 21 or compatible Java version
- MySQL
- Maven
- Postman or any API testing tool

---

### 1. Clone the Repository

```bash
git clone https://github.com/Absar-ao4/Job-board-api.git
cd Job-board-api
```

---

### 2. Create MySQL Database

```sql
CREATE DATABASE jobboard;
```

---

### 3. Configure `application.properties`

Create or update:

```txt
src/main/resources/application.properties
```

Example local configuration:

```properties
spring.application.name=jobboard

server.port=${PORT:8081}

spring.datasource.url=jdbc:mysql://localhost:3306/jobboard
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false

jwt.secret=your_jwt_secret_key
jwt.expiration=86400000
```

---

### 4. Run the Application

```bash
mvn spring-boot:run
```

Local API will run on:

```txt
http://localhost:8081
```

---

## Railway Deployment

The project is deployed on Railway with a MySQL database service.

### Railway Environment Variables

The Spring Boot service uses Railway environment variables for database connection and JWT configuration.

```env
SPRING_DATASOURCE_URL=jdbc:mysql://${{MySQL.MYSQLHOST}}:${{MySQL.MYSQLPORT}}/${{MySQL.MYSQLDATABASE}}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=true
JWT_SECRET=your_jwt_secret_key
```

Railway automatically provides the runtime port through the `PORT` variable. The application uses:

```properties
server.port=${PORT:8081}
```

This allows the app to run on `8081` locally and Railway’s assigned port in production.

---

## Project Structure

```txt
com.absar.jobboard
├── controller
│   ├── AuthController.java
│   ├── UserController.java
│   ├── JobController.java
│   └── ApplicationController.java
├── model
│   ├── User.java
│   ├── Job.java
│   └── Application.java
├── repository
│   ├── UserRepository.java
│   ├── JobRepository.java
│   └── ApplicationRepository.java
└── security
    ├── JwtUtil.java
    ├── JwtFilter.java
    └── SecurityConfig.java
```

---

## Deployment Debugging Notes

During deployment, several production-level issues were resolved:

- Configured Railway root directory correctly for Maven build.
- Connected Spring Boot service with Railway MySQL in the same project.
- Used Railway environment variables for secure database configuration.
- Fixed MySQL authentication by using Railway-generated MySQL credentials.
- Configured public Railway domain to route to the correct internal application port.
- Verified deployment using Postman with JWT-protected API routes.

---

## Future Improvements

- Hide password field from user API responses using DTOs or `@JsonIgnore`
- Add validation for request bodies
- Add global exception handling
- Add pagination for job listings and users
- Add role-based authorization at Spring Security level
- Add Swagger/OpenAPI documentation
- Add Docker support
- Add unit and integration tests

---

## Author

**Absar Ali**

- Email: [absaralioff@gmail.com](mailto:absaralioff@gmail.com)
- LinkedIn: [Absar Ali](https://www.linkedin.com/in/watching-absar-ali/)
