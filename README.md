# 📚 Library Management System

This project is a distributed **microservice-based Library Management System**, designed to manage multiple libraries within the same organization or network. It supports book reservations, transfers between libraries, user management, and a modern web interface.

---

## 🧩 Architecture

The system is built using **Spring Boot** microservices with the following key components:

- **Frontend**: React.js SPA (Single Page Application)
- **API Gateway**: Spring Cloud Gateway for routing and JWT validation
- **Service Discovery**: Eureka server for dynamic service registration and discovery
- **RabbitMQ**: Used for asynchronous messaging (e.g., book reservation and transfer logic)
- **Databases**: Each service uses its own isolated PostgreSQL database
- **Docker**: Docker and Docker Compose are used to containerize and orchestrate the system

---

## 🧱 Microservices

- `book-service`: Manages books, reservations, and loans
- `library-service`: Manages libraries, users, roles
- `transfer-service`: Handles inter-library book transfers

Each service is self-contained and communicates via REST or RabbitMQ where applicable.

---

## 🔐 Authentication & Authorization

- JWT-based authentication and role-based access control (RBAC)
- API Gateway handles token validation and routes requests accordingly

---

## 🎬 Demo Video
Watch the video demonstration here:
[Library Management System – Video Demo](https://drive.google.com/drive/folders/1MeQ1Y3ZQhm4QMjr2fiB3cAudbA4djnOW)

---

## 👥 Authors
- Dalila Kršlak
- Tajra Selimović
- Nejra Adilović
