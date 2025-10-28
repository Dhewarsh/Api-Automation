# API Testing Framework

This is a Java-based API testing framework using REST Assured and TestNG for testing the reqres.in API endpoints.

## Project Structure
```
src
├── main/java/com/api/automation
│   └── utils
│       └── ConfigProperties.java
└── test/java/com/api/automation
    └── tests
        └── UserApiTest.java
```

## Setup Requirements
- Java 11 or higher
- Maven

## Running Tests
To run the tests, use the following command:
```bash
mvn clean test
```

## Configuration
The base URL and endpoints are configured in `ConfigProperties.java`.
