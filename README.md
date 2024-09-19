# Reservation System

Reservation System is a RESTful application for remote event books. The system allows users to book seats for events, manage their accounts, and provides administrators with tools to manage events and users.

## Features

### For Users:
- Registration and authentication.
- View available events and places.
- Book seats for events.
- Manage their bookings.
- Manage their accounts: users can update their login and password information.

### For Administrators:
- Add, modify, and delete events and places.
- Manage user data, including account details.
- Ability to block users.
- Full control over books and event information.

## Technologies

- Java 17
- Spring Boot 3.2.4
  - Spring MVC — for creating RESTful APIs.
  - Spring Data JPA — for working with databases.
  - Spring Validation — for validating user inputs.
  - Spring AOP — for handling cross-cutting concerns such as logging 
  - Spring Security — for user authentication and authorization.
- Swagger 3 — for API documentation and testing.
- PostgreSQL — database.
- Maven — for dependency management and project build.

## Requirements

To run the project, you need:

- Java 17
- Maven 3.x
- Database:
  - By default, PostgreSQL is used, but it can be configured for MySQL.

## Installation and Run

1. Clone the repository:
   `bash
   git clone https://github.com/SeregaMarchenko/ReservationSystem.git
   cd ReservationSystem

2. Build the project and install dependencies:

mvn clean install


3. Run the application:

mvn spring-boot:run


4. Open your browser and go to http://localhost:8080/swagger-ui/index.html to view the API documentation.



Application Configuration

Database and other configuration settings can be found in the src/main/resources/application.properties file. By default, the PostgreSQL database is used. To configure for MySQL, modify the following properties:

### Example for PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/ReservationSystem

spring.datasource.username=username

spring.datasource.password=password

### Example for Security

## Roles and Permissions

Users: have access to view events, make bookings, and manage their accounts (change login, password).

Administrators: have full access to manage events and users. They can:

Add and modify events.

Block and unblock users.

Edit user and booking data.

## Authors

Siarhei Marchanka
