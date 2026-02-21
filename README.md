# 📚 Digital Library Management System
**Spring Boot + MySQL**

---

## ⚙️ Setup

### 1. Configure MySQL
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/digital_library?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_password
```

### 2. Run the application
```bash
mvn spring-boot:run
```
The database tables will be created automatically on first run.

---

## 🗄️ Database Tables

| Table         | Description                          |
|---------------|--------------------------------------|
| `books`       | Book catalog with copy tracking      |
| `members`     | Users and Admins                     |
| `book_issues` | Issue/return records with fine calc  |

---

## 🔗 REST API Endpoints

### 📖 Books

| Method | URL                              | Role  | Description              |
|--------|----------------------------------|-------|--------------------------|
| GET    | /api/books                       | User  | Get all books            |
| GET    | /api/books/{id}                  | User  | Get book by ID           |
| GET    | /api/books/available             | User  | Get available books only |
| GET    | /api/books/search/title?title=X  | User  | Search by title          |
| GET    | /api/books/search/author?author=X| User  | Search by author         |
| GET    | /api/books/category/{category}   | User  | Browse by category       |
| POST   | /api/books/admin                 | Admin | Add a new book           |
| PUT    | /api/books/admin/{id}            | Admin | Update book              |
| DELETE | /api/books/admin/{id}            | Admin | Delete book              |

### 👤 Members

| Method | URL                    | Role  | Description         |
|--------|------------------------|-------|---------------------|
| POST   | /api/members/register  | All   | Register new member |
| POST   | /api/members/login     | All   | Login               |
| GET    | /api/members/admin     | Admin | Get all members     |
| GET    | /api/members/admin/{id}| Admin | Get member by ID    |
| PUT    | /api/members/admin/{id}| Admin | Update member       |
| DELETE | /api/members/admin/{id}| Admin | Delete member       |

### 📋 Issue / Return

| Method | URL                         | Role  | Description                  |
|--------|-----------------------------|-------|------------------------------|
| POST   | /api/issues/issue           | Admin | Issue a book to a member     |
| PUT    | /api/issues/return/{id}     | Admin | Return a book (calc fine)    |
| GET    | /api/issues/member/{id}     | User  | View member's borrow history |
| GET    | /api/issues/{id}            | User  | Get issue details            |
| GET    | /api/issues/admin/all       | Admin | All issue records            |
| GET    | /api/issues/admin/current   | Admin | Currently issued books       |

---

## 📝 Sample Requests

### Register a Member
```json
POST /api/members/register
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "pass123",
  "phone": "9876543210",
  "address": "123 Main Street",
  "role": "USER"
}
```

### Add a Book (Admin)
```json
POST /api/books/admin
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "category": "Programming",
  "publisher": "Prentice Hall",
  "publishedYear": 2008,
  "totalCopies": 5
}
```

### Issue a Book
```json
POST /api/issues/issue
{
  "bookId": 1,
  "memberId": 2
}
```

### Return a Book
```
PUT /api/issues/return/1
```
Returns the issue record with fine calculated automatically if overdue.

### Login
```json
POST /api/members/login
{
  "email": "john@example.com",
  "password": "pass123"
}
```

---

## 💰 Fine Calculation

- **Loan period**: 14 days
- **Fine rate**: ₹5.00 per day overdue (configurable in `application.properties`)
- Fine is calculated automatically on book return

---

## 📁 Project Structure

```
src/main/java/com/library/
│
├── DigitalLibraryApplication.java   ← Entry point
│
├── entity/
│   ├── Book.java                    ← Book table
│   ├── Member.java                  ← Member table (ADMIN/USER roles)
│   └── BookIssue.java               ← Issue/return records
│
├── repository/
│   ├── BookRepository.java
│   ├── MemberRepository.java
│   └── BookIssueRepository.java
│
├── service/
│   ├── BookService.java
│   ├── MemberService.java
│   └── BookIssueService.java
│
└── controller/
    ├── BookController.java
    ├── MemberController.java
    ├── BookIssueController.java
    └── GlobalExceptionHandler.java
```
