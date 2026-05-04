# Job Board API

A production-grade REST API backend for a job board platform built with Java and Spring Boot. Supports role-based access, JWT authentication, and full job application lifecycle management.

---

## Features

- JWT authentication with BCrypt password hashing
- Role-based access control — recruiters post jobs, applicants apply
- Full CRUD for users, jobs, and applications
- Application status management — APPLIED, SHORTLISTED, REJECTED
- Spring Security filter chain with stateless session management
- MySQL persistence with JPA/Hibernate ORM

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.0.6 |
| Security | Spring Security + JWT (jjwt 0.11.5) |
| ORM | Spring Data JPA |
| Database | MySQL 8.0 |
| Build Tool | Maven |

---

## API Endpoints

### Auth
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| POST | /auth/register | Register new user, returns JWT | No |
| POST | /auth/login | Login, returns JWT | No |

### Users
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| GET | /users | Get all users | Yes |
| GET | /users/{id} | Get user by ID | Yes |
| POST | /users | Create user | Yes |
| DELETE | /users/{id} | Delete user | Yes |

### Jobs
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| GET | /jobs | Get all jobs | Yes |
| GET | /jobs/{id} | Get job by ID | Yes |
| POST | /jobs?userId={id} | Post a job — RECRUITER only | Yes |
| DELETE | /jobs/{id} | Delete job | Yes |

### Applications
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| POST | /applications?userId={id}&jobId={id} | Apply for job — APPLICANT only | Yes |
| GET | /applications/job/{jobId} | Get all applications for a job | Yes |
| PUT | /applications/{id}/status?status={status} | Update application status | Yes |

---

## Getting Started

**Prerequisites**

- Java 17+
- MySQL 8.0
- Maven

**1. Clone the repository**

    git clone https://github.com/Absar-ao4/Job-board-api.git
    cd Job-board-api

**2. Create MySQL database**

    CREATE DATABASE jobboard;

**3. Configure application.properties**

    spring.datasource.url=jdbc:mysql://localhost:3306/jobboard
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    jwt.secret=your_secret_key
    jwt.expiration=86400000

**4. Run the application**

    mvn spring-boot:run

API runs on http://localhost:8081

---

## Authentication

Register and receive a token by sending a POST request to /auth/register with name, email, password, and role (RECRUITER or APPLICANT) in the request body.

Then include the token in all subsequent requests using the Authorization header:

    Authorization: Bearer your_token_here

---

## Project Structure

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

---

## Business Rules

- Only RECRUITER role can post jobs
- Only APPLICANT role can apply for jobs
- Application status values: APPLIED, SHORTLISTED, REJECTED
- Passwords are BCrypt hashed before storage
- All endpoints except /auth/** require a valid JWT token

---

## Author

**Absar Ali** — [GitHub](https://github.com/Absar-ao4) · [LinkedIn](https://www.linkedin.com/in/watching-absar-ali/) · [Portfolio](https://watchingabsar.vercel.app/)
