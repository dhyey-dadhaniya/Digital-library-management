# 🧪 API Testing Guide

## 📋 Prerequisites

1. **Start the Application**
   ```bash
   mvn spring-boot:run
   ```
   The API will be available at: `http://localhost:8080`

2. **Verify Application is Running**
   Open browser: http://localhost:8080/
   Should see: `{"message":"Digital Library Management API",...}`

---

## 🛠️ Testing Methods

### Method 1: Using Browser (GET requests only)
Simply open URLs in your browser.

### Method 2: Using curl (Command Line)
Copy and paste commands in PowerShell or Command Prompt.

### Method 3: Using Postman
Import the collection or create requests manually.

---

## 📝 Step-by-Step API Testing

### Step 1: Test Home Endpoint
**GET** `http://localhost:8080/`

**Browser:** Open in browser  
**curl:**
```bash
curl http://localhost:8080/
```

**Expected Response:**
```json
{
  "message": "Digital Library Management API",
  "endpoints": {
    "books": "/api/books",
    "members": "/api/members",
    "issues": "/api/issues"
  }
}
```

---

### Step 2: Register a Member
**POST** `http://localhost:8080/api/members/register`

**curl:**
```bash
curl -X POST http://localhost:8080/api/members/register ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"John Doe\",\"email\":\"john@example.com\",\"password\":\"pass123\",\"phone\":\"9876543210\",\"address\":\"123 Main St\",\"role\":\"USER\"}"
```

**PowerShell (better formatting):**
```powershell
$body = @{
    name = "John Doe"
    email = "john@example.com"
    password = "pass123"
    phone = "9876543210"
    address = "123 Main St"
    role = "USER"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/members/register" -Method POST -Body $body -ContentType "application/json"
```

**Postman:**
- Method: POST
- URL: `http://localhost:8080/api/members/register`
- Headers: `Content-Type: application/json`
- Body (raw JSON):
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "pass123",
  "phone": "9876543210",
  "address": "123 Main St",
  "role": "USER"
}
```

**Expected Response:** Status 201 Created
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "9876543210",
  "address": "123 Main St",
  "role": "USER"
}
```

**Note:** Save the `id` from response for later tests!

---

### Step 3: Register an Admin
**POST** `http://localhost:8080/api/members/register`

**PowerShell:**
```powershell
$body = @{
    name = "Admin User"
    email = "admin@library.com"
    password = "admin123"
    phone = "9999999999"
    address = "Library Admin Office"
    role = "ADMIN"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/members/register" -Method POST -Body $body -ContentType "application/json"
```

---

### Step 4: Login
**POST** `http://localhost:8080/api/members/login`

**PowerShell:**
```powershell
$body = @{
    email = "john@example.com"
    password = "pass123"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/members/login" -Method POST -Body $body -ContentType "application/json"
```

**Expected Response:** Status 200 OK (Member object) or 401 Unauthorized

---

### Step 5: Add a Book (Admin)
**POST** `http://localhost:8080/api/books/admin`

**PowerShell:**
```powershell
$body = @{
    title = "Clean Code"
    author = "Robert C. Martin"
    isbn = "978-0132350884"
    category = "Programming"
    publisher = "Prentice Hall"
    publishedYear = 2008
    totalCopies = 5
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/books/admin" -Method POST -Body $body -ContentType "application/json"
```

**Expected Response:** Status 201 Created
```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "category": "Programming",
  "publisher": "Prentice Hall",
  "publishedYear": 2008,
  "totalCopies": 5,
  "availableCopies": 5
}
```

**Add more books:**
```powershell
# Book 2
$body = @{
    title = "The Pragmatic Programmer"
    author = "Andrew Hunt"
    isbn = "978-0201616224"
    category = "Programming"
    publisher = "Addison-Wesley"
    publishedYear = 1999
    totalCopies = 3
} | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/api/books/admin" -Method POST -Body $body -ContentType "application/json"

# Book 3
$body = @{
    title = "1984"
    author = "George Orwell"
    isbn = "978-0451524935"
    category = "Fiction"
    publisher = "Signet Classics"
    publishedYear = 1949
    totalCopies = 10
} | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/api/books/admin" -Method POST -Body $body -ContentType "application/json"
```

---

### Step 6: Get All Books
**GET** `http://localhost:8080/api/books`

**Browser:** Open `http://localhost:8080/api/books`  
**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/books" -Method GET
```

---

### Step 7: Get Book by ID
**GET** `http://localhost:8080/api/books/1`

**Browser:** Open `http://localhost:8080/api/books/1`  
**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/books/1" -Method GET
```

---

### Step 8: Search Books by Title
**GET** `http://localhost:8080/api/books/search/title?title=Clean`

**Browser:** Open `http://localhost:8080/api/books/search/title?title=Clean`  
**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/books/search/title?title=Clean" -Method GET
```

---

### Step 9: Search Books by Author
**GET** `http://localhost:8080/api/books/search/author?author=Robert`

**Browser:** Open `http://localhost:8080/api/books/search/author?author=Robert`  
**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/books/search/author?author=Robert" -Method GET
```

---

### Step 10: Get Books by Category
**GET** `http://localhost:8080/api/books/category/Programming`

**Browser:** Open `http://localhost:8080/api/books/category/Programming`  
**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/books/category/Programming" -Method GET
```

---

### Step 11: Get Available Books Only
**GET** `http://localhost:8080/api/books/available`

**Browser:** Open `http://localhost:8080/api/books/available`  
**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/books/available" -Method GET
```

---

### Step 12: Issue a Book to a Member
**POST** `http://localhost:8080/api/issues/issue`

**PowerShell:**
```powershell
$body = @{
    bookId = 1
    memberId = 1
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/issues/issue" -Method POST -Body $body -ContentType "application/json"
```

**Expected Response:** Status 201 Created
```json
{
  "id": 1,
  "book": {...},
  "member": {...},
  "issueDate": "2026-02-21T...",
  "dueDate": "2026-03-07T...",
  "returnDate": null,
  "fine": 0.0,
  "status": "ISSUED"
}
```

**Note:** Save the issue `id` for return test!

---

### Step 13: Get Member's Borrow History
**GET** `http://localhost:8080/api/issues/member/1`

**Browser:** Open `http://localhost:8080/api/issues/member/1`  
**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/issues/member/1" -Method GET
```

---

### Step 14: Get All Current Issues (Admin)
**GET** `http://localhost:8080/api/issues/admin/current`

**Browser:** Open `http://localhost:8080/api/issues/admin/current`  
**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/issues/admin/current" -Method GET
```

---

### Step 15: Return a Book
**PUT** `http://localhost:8080/api/issues/return/1`

**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/issues/return/1" -Method PUT
```

**Expected Response:** Status 200 OK
```json
{
  "id": 1,
  "book": {...},
  "member": {...},
  "issueDate": "2026-02-21T...",
  "dueDate": "2026-03-07T...",
  "returnDate": "2026-02-21T...",
  "fine": 0.0,
  "status": "RETURNED"
}
```

**Note:** If book is returned after due date, `fine` will be calculated automatically (₹5 per day overdue).

---

### Step 16: Update a Book (Admin)
**PUT** `http://localhost:8080/api/books/admin/1`

**PowerShell:**
```powershell
$body = @{
    title = "Clean Code: Updated Edition"
    author = "Robert C. Martin"
    isbn = "978-0132350884"
    category = "Programming"
    publisher = "Prentice Hall"
    publishedYear = 2008
    totalCopies = 10
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/books/admin/1" -Method PUT -Body $body -ContentType "application/json"
```

---

### Step 17: Get All Members (Admin)
**GET** `http://localhost:8080/api/members/admin`

**Browser:** Open `http://localhost:8080/api/members/admin`  
**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/members/admin" -Method GET
```

---

### Step 18: Get All Issue Records (Admin)
**GET** `http://localhost:8080/api/issues/admin/all`

**Browser:** Open `http://localhost:8080/api/issues/admin/all`  
**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/issues/admin/all" -Method GET
```

---

### Step 19: Delete a Book (Admin)
**DELETE** `http://localhost:8080/api/books/admin/3`

**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/books/admin/3" -Method DELETE
```

**Expected Response:** Status 204 No Content

---

### Step 20: Delete a Member (Admin)
**DELETE** `http://localhost:8080/api/members/admin/2`

**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/members/admin/2" -Method DELETE
```

**Expected Response:** Status 204 No Content

---

## 🧪 Complete Test Scenario

Here's a complete workflow to test the entire system:

```powershell
# 1. Register a member
$member = @{
    name = "Test User"
    email = "test@example.com"
    password = "test123"
    phone = "1234567890"
    address = "Test Address"
    role = "USER"
} | ConvertTo-Json
$memberResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/members/register" -Method POST -Body $member -ContentType "application/json"
$memberId = $memberResponse.id

# 2. Add a book
$book = @{
    title = "Test Book"
    author = "Test Author"
    isbn = "1234567890"
    category = "Test"
    publisher = "Test Publisher"
    publishedYear = 2024
    totalCopies = 5
} | ConvertTo-Json
$bookResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/books/admin" -Method POST -Body $book -ContentType "application/json"
$bookId = $bookResponse.id

# 3. Issue the book
$issue = @{
    bookId = $bookId
    memberId = $memberId
} | ConvertTo-Json
$issueResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/issues/issue" -Method POST -Body $issue -ContentType "application/json"
$issueId = $issueResponse.id

# 4. Check available books (should be 4 now)
Invoke-RestMethod -Uri "http://localhost:8080/api/books/available" -Method GET

# 5. Return the book
Invoke-RestMethod -Uri "http://localhost:8080/api/issues/return/$issueId" -Method PUT

# 6. Check available books again (should be 5 now)
Invoke-RestMethod -Uri "http://localhost:8080/api/books/available" -Method GET
```

---

## 📊 Expected HTTP Status Codes

| Status Code | Meaning |
|-------------|---------|
| 200 OK | Request successful |
| 201 Created | Resource created successfully |
| 204 No Content | Resource deleted successfully |
| 400 Bad Request | Invalid request data |
| 401 Unauthorized | Authentication failed |
| 404 Not Found | Resource not found |
| 409 Conflict | Resource already exists (e.g., duplicate email) |

---

## 🐛 Troubleshooting

1. **Connection Refused**
   - Make sure application is running: `mvn spring-boot:run`
   - Check if port 8080 is available

2. **404 Not Found**
   - Verify the URL is correct
   - Check if the resource exists (use GET /api/books to list all)

3. **400 Bad Request**
   - Check JSON format is correct
   - Verify all required fields are present
   - Check data types match (numbers vs strings)

4. **401 Unauthorized**
   - Verify email and password are correct
   - Check if member exists

5. **500 Internal Server Error**
   - Check application logs
   - Verify database connection
   - Check if MySQL is running

---

## 📚 Additional Resources

- **API Base URL:** `http://localhost:8080`
- **Home Endpoint:** `http://localhost:8080/` (shows all available endpoints)
- **Application Logs:** Check console output for detailed error messages
