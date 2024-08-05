# URL Shortener Application

## Overview

URL Shortener application built using Spring Boot. 
IT allows users to shorten URLs, redirect them to the original URL, and manage URL data. 
It includes features like URL creation, redirection, update, and deletion, with some other additional functionalities for handling expired URLs and logging activities.

## Features

- URL Shortening
- URL Redirection
- URL Deletion
- Expired URL Cleanup
- URL Update
- Error Handling
- Logging and Performance Monitoring

## Technologies Used

- Java
- Spring Boot
- Hibernate (JPA)
- Lombok
- SLF4J (Logging)
- PostgreSQL

## Prerequisites

- Java 17
- Maven
- PostgreSQL

## Getting Started

### Database Setup

1. Install PostgreSQL and create a database named `url_shortener`.

    ```sql
    CREATE DATABASE url_shortener;
    ```

2. Update the `application.properties` file with your PostgreSQL credentials.

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/url_shortener
    spring.datasource.username=your_postgres_username
    spring.datasource.password=your_postgres_password
    ```

### Application Setup

1. Clone the repository

    ```bash
    git clone https://github.com/Mouayad91/Url-Shortener.git
    ```

2. Navigate to the project directory

    ```bash
    cd url-shortener
    ```

3. Build the project using Maven

    ```bash
    mvn clean install
    ```

4. Run the application

    ```bash
    mvn clean spring-boot:run
    ```

### Endpoints

The application has the following endpoints:

#### URL Management

- `POST /` - Create a new shortened URL
- `GET /{shortUrl}` - Redirect to the original URL
- `DELETE /short/{shortUrl}` - Delete a URL by its short URL
- `GET /id/{id}` - Get URL details by ID
- `PUT /{id}` - Update a URL by ID
- `DELETE /expired` - Delete all expired URLs
- `GET /` - Get all URLs
- `DELETE /{id}` - Delete a URL by ID

### Using Postman

1. **Create a Short URL:**
    - URL: `http://localhost:8080/`
    - Method: `POST`
    - Body (JSON):
      ```json
      {
          "originalUrl": "http://google.com",
          "ttl": "7"
      }
      ```

2. **Redirect to Original URL:**
    - URL: `http://localhost:8080/{shortUrl}`
    - Method: `GET`
    - Replace `{shortUrl}` with the actual short URL

3. **Delete a URL by Short URL:**
    - URL: `http://localhost:8080/short/{shortUrl}`
    - Method: `DELETE`
    - Replace `{shortUrl}` with the actual short URL

4. **Get URL Details by ID:**
    - URL: `http://localhost:8080/id/{id}`
    - Method: `GET`
    - Replace `{id}` with the actual ID

5. **Update a URL by ID:**
    - URL: `http://localhost:8080/{id}`
    - Method: `PUT`
    - Replace `{id}` with the actual ID
    - Body (JSON):
      ```json
      {
          "originalUrl": "http://facebook.com",
          "ttl": "10"
      }
      ```

6. **Delete All Expired URLs:**
    - URL: `http://localhost:8080/expired`
    - Method: `DELETE`

7. **Get All URLs:**
    - URL: `http://localhost:8080/`
    - Method: `GET`

8. **Delete a URL by ID:**
    - URL: `http://localhost:8080/{id}`
    - Method: `DELETE`
    - Replace `{id}` with the actual ID

## Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Hibernate (JPA)](https://hibernate.org/)
- [SLF4J (Logging)](http://www.slf4j.org/)
- [Lombok](https://projectlombok.org/)
- [PostgreSQL](https://www.postgresql.org/)

## Contributing

Pull requests are welcome  ^_^.

## ENJOY
