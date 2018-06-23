# Java Spring Boot multi-module project
The project is implemented using the following tools and technologies: Maven, Spring, JPA, Security, JUnit, Logging, REST, SOAP, Thymeleaf, Jquery. Data storage is implemented using PostgreSQL.

## Short description
The application is a part of the start-up project I was working on lately. This project is for storing and handling housing and communal services charges. It allows to create unified data from different file sources. Services allows to communicate with another applications.

## Modules
### Web
Web application. Module Web allows to get/set catalogs information, call procedures. 

### Persistence
Module Persistence allows to handle database operations.

### Job
Module Job implements scheduled and callable logic with database and files.

### Services
Web services. Module Services responds for charges calls by passed address. Two services do the same job. 

## Database configuration
"config\" project directory contains PL/pgSQL scripts.
- init_db.sql. Creates needed tables.
- func.sql. Creates needed functions.
- populate_db.sql. Populates database with test data.

## Requirements
### Before start: 
- create database (PostgreSQL) with name "e_kvit"
- create database user "admin" with password "admin"
- run all sql scripts from "config\"
- create a directory "ekvit" in the root of the C drive ("C\ekvit")
