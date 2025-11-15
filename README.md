# 💰 Expense Tracker

A full-stack Spring Boot application for tracking personal expenses with a modern web interface and RESTful API.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## ✨ Features

### Core Functionality

- ✅ **Full CRUD Operations** - Create, Read, Update, and Delete expenses
- ✅ **RESTful API** - Complete REST API with proper HTTP status codes
- ✅ **Web Interface** - Beautiful Thymeleaf-based UI with responsive design
- ✅ **Search & Filter** - Search by description and filter by category
- ✅ **Date Range Queries** - Filter expenses by date range
- ✅ **Category Summaries** - View total spending by category
- ✅ **Input Validation** - Comprehensive validation with user-friendly error messages
- ✅ **Exception Handling** - Global exception handler with consistent error responses

### Technical Features

- 🚀 Java 21 (LTS)
- 🌱 Spring Boot 3.5.7
- 💾 Multi-database support (SQL Server & H2)
- 🔍 Spring Data JPA with custom queries
- 🎨 Thymeleaf template engine
- 📝 Bean Validation (Jakarta Validation)
- 🔧 Profile-based configuration
- 🎭 Layered architecture (Controller → Service → Repository)

## 🚀 Quick Start

### Prerequisites

- Java 21 or higher
- Maven 3.6+ (or use included Maven wrapper)
- SQL Server (optional - H2 included for testing)

### Clone & Run

```bash
# Clone the repository
git clone https://github.com/PuneetVerma04/expense-tracker.git
cd expense-tracker

# Run with H2 in-memory database (recommended for testing)
./mvnw spring-boot:run
```

### Access the Application

- **Web Interface**: http://localhost:8080/expenses/list
- **REST API Base URL**: http://localhost:8080/api/expenses
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: _(leave empty)_

## 📖 API Documentation

### REST Endpoints

| Method   | Endpoint                | Description                                                  |
| -------- | ----------------------- | ------------------------------------------------------------ |
| `GET`    | `/api/expenses`         | Get all expenses (supports `?category` and `?search` params) |
| `GET`    | `/api/expenses/{id}`    | Get expense by ID                                            |
| `POST`   | `/api/expenses`         | Create new expense                                           |
| `PUT`    | `/api/expenses/{id}`    | Update expense                                               |
| `DELETE` | `/api/expenses/{id}`    | Delete expense                                               |
| `GET`    | `/api/expenses/range`   | Get expenses by date range                                   |
| `GET`    | `/api/expenses/above`   | Get expenses above specified amount                          |
| `GET`    | `/api/expenses/summary` | Get expense summaries by category                            |

### Example API Requests

#### Create an Expense

```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Grocery Shopping",
    "amount": 150.50,
    "category": "Food",
    "expenseDate": "2024-11-15T10:30:00"
  }'
```

#### Get All Expenses

```bash
curl http://localhost:8080/api/expenses
```

#### Search Expenses

```bash
curl http://localhost:8080/api/expenses?search=grocery
```

#### Filter by Category

```bash
curl http://localhost:8080/api/expenses?category=Food
```

#### Get Category Summary

```bash
curl http://localhost:8080/api/expenses/summary
```

## 🗂️ Project Structure

```
expense-tracker/
├── src/main/java/com/expensetracker/expense_tracker/
│   ├── controller/
│   │   ├── ExpenseController.java      # REST API endpoints
│   │   └── WebController.java          # Web page controllers
│   ├── dto/
│   │   ├── ExpenseRequest.java         # Request DTO with validation
│   │   ├── ExpenseResponse.java        # Response DTO
│   │   └── ExpenseSummary.java         # Summary DTO for aggregation
│   ├── entity/
│   │   └── Expense.java                # JPA entity
│   ├── exception/
│   │   ├── ExpenseNotFoundException.java
│   │   ├── InvalidExpenseException.java
│   │   └── GlobalExceptionHandler.java # @ControllerAdvice for errors
│   ├── repository/
│   │   └── ExpenseRepository.java      # Spring Data JPA repository
│   └── service/
│       └── ExpenseService.java         # Business logic layer
├── src/main/resources/
│   ├── static/css/
│   │   └── style.css                   # Custom CSS styling
│   ├── templates/
│   │   ├── list.html                   # Expense list page
│   │   └── form.html                   # Add/Edit form page
│   ├── application.properties          # Main configuration
│   ├── application-h2.properties       # H2 database profile
│   ├── application-sqlserver.properties# SQL Server profile
│   └── application-test.properties     # Test profile
└── pom.xml
```

## 🎨 Web Interface

The application includes a fully functional web interface with:

- **Expense List Page** (`/expenses/list`)

  - View all expenses in a clean table
  - Search by description
  - Filter by category (Food, Transport, Entertainment, etc.)
  - Quick actions (Edit, Delete)
  - Summary section with totals

- **Add/Edit Form** (`/expenses/new` and `/expenses/edit/{id}`)
  - Intuitive form layout
  - Input validation with error messages
  - Category dropdown
  - Date-time picker
  - Responsive design

## 💾 Database Configuration

### Using H2 (Default - In-Memory)

The application is pre-configured to use H2 for quick testing. No setup required!

```properties
spring.profiles.active=h2
```

### Using SQL Server

1. **Create the database:**

   ```sql
   CREATE DATABASE ExpenseTrackerDB;
   ```

2. **Update configuration:**
   Edit `src/main/resources/application-sqlserver.properties`:

   ```properties
   spring.datasource.password=your_actual_password
   ```

3. **Change active profile:**
   Edit `src/main/resources/application.properties`:

   ```properties
   spring.profiles.active=sqlserver
   ```

4. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

## 🧪 Testing

### Run All Tests

```bash
./mvnw test
```

### Build Project

```bash
./mvnw clean install
```

The project includes:

- Unit tests for context loading
- H2 test profile for isolated testing
- Automatic test database setup

## 🏗️ Architecture

The application follows a **layered architecture**:

1. **Controller Layer** - Handles HTTP requests and responses
2. **Service Layer** - Contains business logic and validation
3. **Repository Layer** - Data access with Spring Data JPA
4. **Entity Layer** - JPA entities mapping to database tables

**Design Patterns Used:**

- DTO Pattern for data transfer
- Repository Pattern for data access
- Service Pattern for business logic
- Exception Handling with @ControllerAdvice

## 📝 Categories Available

The application supports the following expense categories:

- 🍔 Food
- 🚗 Transport
- 🎬 Entertainment
- 🛍️ Shopping
- 💡 Bills
- 🏥 Healthcare
- 📚 Education
- 📦 Other

## 🔧 Configuration Profiles

| Profile     | Database     | Use Case              |
| ----------- | ------------ | --------------------- |
| `h2`        | H2 In-Memory | Development & Testing |
| `sqlserver` | SQL Server   | Production            |
| `test`      | H2 In-Memory | Automated Tests       |

## 📚 Additional Documentation

- [IMPLEMENTATION_SUMMARY.md](IMPLEMENTATION_SUMMARY.md) - Detailed implementation guide
- [QUICKSTART.md](QUICKSTART.md) - Quick reference guide
- [SQL_SERVER_SETUP.md](SQL_SERVER_SETUP.md) - SQL Server setup instructions

## 🛠️ Tech Stack

- **Backend Framework:** Spring Boot 3.5.7
- **Language:** Java 21
- **ORM:** Hibernate 6.6.33
- **Database:** SQL Server / H2
- **Template Engine:** Thymeleaf
- **Build Tool:** Maven
- **Validation:** Jakarta Validation API
- **Connection Pool:** HikariCP

## 🚀 Future Enhancements

- [ ] User authentication with Spring Security
- [ ] Budget tracking and alerts
- [ ] Export to CSV/PDF
- [ ] Data visualization with charts
- [ ] Recurring expense support
- [ ] Multi-currency support
- [ ] Mobile app integration

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Puneet Verma**

- GitHub: [@PuneetVerma04](https://github.com/PuneetVerma04)
- Email: puneetverma04@example.com

## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- Thymeleaf for the powerful template engine
- All contributors who help improve this project

---

⭐ **Star this repository if you find it helpful!**
