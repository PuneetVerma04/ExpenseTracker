# 💰 Expense Tracker

A Spring Boot application for tracking personal expenses with SQL Server integration.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)

## ✨ Features

- Java 21 with Spring Boot 3.5.7
- Multi-database support (SQL Server & H2)
- Spring Data JPA with custom queries
- Profile-based configuration

## 🚀 Quick Start

### Clone & Run

```bash
git clone https://github.com/PuneetVerma04/expense-tracker.git
cd expense-tracker
./mvnw spring-boot:run -Dspring-boot.run.profiles=h2
```

Visit: http://localhost:8080/h2-console

### SQL Server Setup

1. Create database: `CREATE DATABASE ExpenseTrackerDB;`
2. Update password in `application-sqlserver.properties`
3. Run: `./mvnw spring-boot:run`

## 📦 What's Included

- **Expense Entity** - Track expenses with amount, category, date
- **Repository** - Pre-built queries for common operations
- **Auto Schema** - Database tables created automatically
- **H2 Console** - Test quickly without SQL Server

## 🧪 Testing

```bash
./mvnw test
```

## 👨‍💻 Author

**Puneet Verma** - [@PuneetVerma04](https://github.com/PuneetVerma04)
