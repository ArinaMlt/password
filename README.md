# Passwords Storage Service

This is a simple web application built with Scala Play Framework for storing and managing passwords.

## Features

- Add new passwords
- View all stored passwords
- Search for passwords

## Getting Started

### Prerequisites

- Java JDK (version 11.0.18)
- sbt (version 1.9.8)
- Scala (version 3.4.0)

### Installation


1. Navigate to the project directory:

    
    cd passwords
    

2. Run the application:

    
    sbt run
    

3. Access the application in your browser at [http://localhost:9000](http://localhost:9000).

### Usage

### Postman Request

- GET /all - Получение всех паролей
Method: GET
URL: http://localhost:9000/all


- GET /test/:id/:name - Тестовый запрос
Method: GET
URL: http://localhost:9000/


- POST /addPassword - Добавление пароля
Method: POST
URL: http://localhost:9000/addPassword
Headers: Content-Type: application/json
Body: JSON объект с данными нового пароля
{
    "name": "NewPasswordName",
    "password": "NewPassword",
    "comment": "Some comment"
}


- DELETE /validate/deleteAll -  Удаления всех паролей
Method: DELETE
URL: http://localhost:9000/validate/deleteAll


- PUT /updatePassword/:id - Обновление пароля
Method: PUT
URL: http://localhost:9000/updatePassword/1
Headers: Content-Type: application/json
Body: JSON объект с обновленными данными пароля
{
    "username": "UpdatedUsername",
    "password": "UpdatedPassword",
    "comment": "UpdatedComment"
}
