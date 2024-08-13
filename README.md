# Library Management System API Documentation

## Table of Contents
1. [Introduction](#introduction)
2. [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running the Application](#running-the-application)
3. [API Endpoints](#api-endpoints)
    - [Book Management](#book-management)
    - [Patron Management](#patron-management)
    - [Borrowing Management](#borrowing-management)
4. [Error Handling](#error-handling)
5. [Testing](#testing)

---

## Introduction

The Library Management System API allows librarians to manage books, patrons, and borrowing records. It is built using Spring Boot and provides a RESTful interface for interacting with the library's resources.

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed on your machine:
- **Java 17** or later
- **Maven 3.8** or later
- **Postgresql** or another relational database

### Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/mahmoudxyz/library-management-system.git
   cd library-management-system
   ```

2. **Set Up the Database** :
   If you are using MySQL, Postgres or another database, update the `application.properties` file with your database credentials.

3. **Build the Application**:
   ```bash
   mvn clean install
   ```

### Running the Application

To run the application, use the following command:
```bash
mvn spring-boot:run
```

By default, the application will start on `http://localhost:8080`.

## API Endpoints

### Book Management

- **GET /api/books**: Retrieve a list of all books.
- **GET /api/books/{id}**: Retrieve details of a specific book by ID.
- **POST /api/books**: Add a new book to the library.
    - **Request Body**:
      ```json
      {
          "title": "Effective Java",
          "author": "Joshua Bloch",
          "publicationYear": 2017,
          "isbn": "9780134685991"
      }
      ```
- **PUT /api/books/{id}**: Update an existing book's information.
- **DELETE /api/books/{id}**: Remove a book from the library.

### Patron Management

- **GET /api/patrons**: Retrieve a list of all patrons.
- **GET /api/patrons/{id}**: Retrieve details of a specific patron by ID.
- **POST /api/patrons**: Add a new patron to the system.
    - **Request Body**:
      ```json
      {
          "name": "John Doe",
          "email": "john.doe@example.com",
          "address": "Paris"
      }
      ```
- **PUT /api/patrons/{id}**: Update an existing patron's information.
- **DELETE /api/patrons/{id}**: Remove a patron from the system.

### Borrowing Management

- **POST /api/borrow/{bookId}/patron/{patronId}**: Allow a patron to borrow a book.
- **PUT /api/return/{bookId}/patron/{patronId}**: Record the return of a borrowed book by a patron.

### Example Requests

- **Borrow a Book**:
  ```bash
  curl -X POST "http://localhost:8080/api/borrow/1/patron/1"
  ```
- **Return a Book**:
  ```bash
  curl -X PUT "http://localhost:8080/api/return/1/patron/1"
  ```

## Error Handling

The API uses standard HTTP status codes to indicate the success or failure of requests. Common status codes include:

- `200 OK`: The request was successful.
- `201 Created`: A new resource was successfully created.
- `400 Bad Request`: The request could not be understood due to malformed syntax.
- `404 Not Found`: The requested resource could not be found.
- `409 Conflict`: Indicates a conflict, such as trying to borrow a book that is already borrowed.
- `500 Internal Server Error`: The server encountered an unexpected condition.

Example of an error response when a book is already borrowed:
```json
{
  "error": "Book is already borrowed"
}
```

## Authentication 

Not implemented, you don't have to use any

## Testing

You can run the tests using the following command:
```bash
mvn test
```

The tests cover various scenarios, including successful operations and handling of edge cases such as missing resources or invalid input.


