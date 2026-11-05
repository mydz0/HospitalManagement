# Hospital Management - Nurse REST API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-cloud-blue)
[![Java CI with Maven](https://github.com/mydz0/HospitalManagement/actions/workflows/ci.yml/badge.svg)](https://github.com/mydz0/HospitalManagement/actions/workflows/ci.yml)
![Release](https://img.shields.io/github/v/release/mydz0/HospitalManagement)

> REST API to manage the nurses of a hospital, built with **Spring Boot**, **Spring Data JPA (Hibernate)** and **MySQL**.

---

## Table of contents

1. [Description](#description)
2. [Features](#features)
3. [Tech stack](#tech-stack)
4. [Project structure](#project-structure)
5. [Installation](#installation)
6. [Usage](#usage)
7. [Testing](#testing)
8. [Continuous integration](#continuous-integration)
9. [Releases](#releases)
10. [Team](#team)

---

## Description

Hospital Management is the backend of a hospital management system. Its purpose is to manage the information of the hospital's nurses and make it available to other applications (for example a web or mobile frontend) through a **REST API**.

### The problem it solves

In many hospitals the staff information is kept in spreadsheets or separate files. This makes it hard to keep the data up to date, there is no secure way for nurses to log in, and other applications cannot access the information easily. Hospital Management solves this by keeping all the nurses in **one central database** and offering a standard REST API that any application can use to read and manage them.

### What it does

With this API a client application can:

- Show the list of all the nurses working in the hospital.
- Let a nurse **log in** with their user and password.
- Find a nurse by **name** or by **id**.
- **Create**, **update** and **delete** nurses.

The data is stored in a MySQL database hosted in the cloud on [filess.io](https://filess.io/), so every member of the team works with the same data.

## Features

- [x] List all nurses
- [x] Nurse login (user + password)
- [x] Search nurses by name
- [x] Find a nurse by id
- [x] Create, update and delete nurses (CRUD)
- [x] Cloud MySQL database with Hibernate
- [x] Unit tests with JUnit 5 and Mockito
- [x] Continuous integration with GitHub Actions

## Tech stack

| Technology | Version | Purpose |
| --- | --- | --- |
| Java | 17 | Programming language |
| Spring Boot (Web MVC) | 4.1.1 | REST API |
| Spring Data JPA / Hibernate | - | Database access (ORM) |
| MySQL | - | Database (hosted on filess.io) |
| Maven (wrapper included) | - | Build and dependency management |
| JUnit 5 + Mockito | - | Unit tests |
| GitHub Actions | - | Continuous integration |

## Project structure

```
HospitalManagement
├── .github/workflows/ci.yml                      # CI pipeline
├── src
│   ├── main
│   │   ├── java/com/HospitalManagent/HospitalManagement
│   │   │   ├── HospitalManagementApplication.java  # Entry point
│   │   │   ├── controller/NurseController.java     # REST endpoints
│   │   │   ├── model/Nurse.java                    # JPA entity (table "nurse")
│   │   │   └── repository/NurseRepository.java     # Database queries
│   │   └── resources
│   │       ├── application.properties              # Database configuration
│   │       └── nurses.json                         # Initial data (500 nurses)
│   └── test
│       └── java/.../controller/NurseControllerTest.java  # Unit tests
├── mvnw / mvnw.cmd                               # Maven wrapper
└── pom.xml                                       # Dependencies
```

## Installation

### Prerequisites

Before you start, make sure you have:

- **Java JDK 17** or later ([download](https://adoptium.net/))
- **Git** ([download](https://git-scm.com/))
- The password of the project's database, or your own MySQL server
- An IDE such as **Eclipse** or **IntelliJ IDEA** (optional)

> [!NOTE]
> You do not need to install Maven. The project includes the Maven Wrapper (`mvnw`).

### Step 1. Clone the repository

```bash
git clone https://github.com/mydz0/HospitalManagement.git
cd HospitalManagement
```

### Step 2. Install the dependencies

All the dependencies are defined in `pom.xml` (Spring Boot, Spring Data JPA, MySQL driver, JUnit, Mockito...). Download them and build the project with the Maven Wrapper:

```bash
# Linux / macOS
./mvnw clean install
```

```powershell
# Windows
mvnw.cmd clean install
```

If you use Eclipse, import the project with *File > Import > Existing Maven Projects* and Eclipse downloads the dependencies automatically.

### Step 3. Configure the environment

The database connection is configured in `src/main/resources/application.properties`. For security reasons the password is **not** stored in the repository. It is read from the `DB_PASSWORD` environment variable:

```properties
spring.datasource.password=${DB_PASSWORD}
```

Set the variable in the terminal where you will run the application:

```bash
# Linux / macOS
export DB_PASSWORD=your_password
```

```powershell
# Windows (PowerShell)
$env:DB_PASSWORD="your_password"
```

If you use Eclipse, add it in *Run > Run Configurations > Environment*.

> [!IMPORTANT]
> The filess.io free plan only allows **5 connections** at the same time. For this reason each application uses a maximum of 2 (`spring.datasource.hikari.maximum-pool-size=2`). If the application fails with `Unable to determine Dialect`, close other running instances and try again.

To use a **local MySQL** database instead, change these properties to your own values:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospitalmanagement
spring.datasource.username=root
spring.datasource.password=
```

Hibernate creates the `nurse` table automatically the first time the application starts (`spring.jpa.hibernate.ddl-auto=update`).

## Usage

### Run the application

```bash
# Linux / macOS
./mvnw spring-boot:run
```

```powershell
# Windows
mvnw.cmd spring-boot:run
```

You can also run `HospitalManagementApplication.java` from your IDE (*Run As > Spring Boot App* in Eclipse).

When the application starts, the API is available at:

```
http://localhost:8080
```

### Key features

All the endpoints start with `/nurse`. Requests and responses use **JSON**.

### Nurse object

```json
{
  "user": "1",
  "password": "1234",
  "name": "Maria"
}
```

| Field | Type | Description |
| --- | --- | --- |
| `user` | String | Nurse id (primary key). Used to log in. |
| `password` | String | Nurse password. |
| `name` | String | Nurse name. |

All three fields are required to create or update a nurse.

### Endpoints

| Method | Endpoint | Description | Responses |
| --- | --- | --- | --- |
| `GET` | `/nurse/index` | Get all nurses | `200 OK` |
| `POST` | `/nurse/login` | Log in with user and password | `200 OK`, `401 Unauthorized` |
| `GET` | `/nurse/name/{name}` | Find nurses by name | `200 OK`, `404 Not Found` |
| `GET` | `/nurse/{id}` | Find a nurse by id | `200 OK`, `404 Not Found` |
| `POST` | `/nurse/create` | Create a new nurse | `201 Created`, `400 Bad Request` |
| `PUT` | `/nurse/{id}` | Update a nurse | `200 OK`, `400 Bad Request`, `404 Not Found` |
| `DELETE` | `/nurse/delete/{id}` | Delete a nurse | `200 OK`, `404 Not Found` |

### API usage examples

#### Get all nurses

```bash
curl http://localhost:8080/nurse/index
```

Response `200 OK`:

```json
[
  { "user": "1", "password": "1234", "name": "Maria" },
  { "user": "2", "password": "abcd", "name": "Laura" }
]
```

#### Log in

```bash
curl -X POST http://localhost:8080/nurse/login \
  -H "Content-Type: application/json" \
  -d '{"user": "1", "password": "1234"}'
```

- `200 OK` with the nurse if the user and password are correct.
- `401 Unauthorized` if they are wrong.

#### Find nurses by name

```bash
curl http://localhost:8080/nurse/name/Maria
```

Returns **all** the nurses with that name, or `404 Not Found` if there are none.

#### Find a nurse by id

```bash
curl http://localhost:8080/nurse/1
```

#### Create a nurse

```bash
curl -X POST http://localhost:8080/nurse/create \
  -H "Content-Type: application/json" \
  -d '{"user": "501", "password": "abcd", "name": "Elena"}'
```

- `201 Created` with the new nurse.
- `400 Bad Request` if a field is missing or the `user` already exists.

#### Update a nurse

```bash
curl -X PUT http://localhost:8080/nurse/501 \
  -H "Content-Type: application/json" \
  -d '{"user": "501", "password": "newpass", "name": "Elena"}'
```

> [!WARNING]
> The `user` in the body must be the same as the `{id}` in the URL. Otherwise the API returns `400 Bad Request`.

#### Delete a nurse

```bash
curl -X DELETE http://localhost:8080/nurse/delete/501
```

Returns `200 OK` with the deleted nurse, or `404 Not Found` if it does not exist.

> [!TIP]
> You can also test all the endpoints with [Postman](https://www.postman.com/).

## Testing

`NurseController` is covered by unit tests written with **JUnit 5** and **Mockito**. The repository is mocked, so the tests **do not need a database**.

```bash
# Linux / macOS
./mvnw test
```

```powershell
# Windows
mvnw.cmd test
```

## Continuous integration

The GitHub Actions workflow [`.github/workflows/ci.yml`](.github/workflows/ci.yml) builds the project and runs the tests on every **push** and **pull request** to `main`. You can see the results in the [Actions](https://github.com/mydz0/HospitalManagement/actions) tab.

## Releases

The latest release is **v0.4.0**. All releases are available on the [Releases](https://github.com/mydz0/HospitalManagement/releases) page. Changes are listed from the most recent to the oldest.

### Since v0.4.0 (in `main`, not released yet)

- Fixed a typo in the login endpoint: it was mapped as `/nurse/logifn` and now it is `/nurse/login` again (#53).
- The CI workflow was tested on purpose with a failing build and then fixed, to confirm that GitHub Actions detects errors (#51).
- The `contextLoads` test was removed because it needs a real database connection, which is not available in CI (#38).
- README.md with the purpose, installation and usage of the project (#40).
- Next planned release: **v1.0.0** (#42).

### v0.4.0 (latest) - October 27, 2026

Full nurse management (CRUD), tests and automation.

- **Create** a nurse: `POST /nurse/create` (#32).
- **Find by id**: `GET /nurse/{id}` (#33).
- **Update**: `PUT /nurse/{id}` (#34).
- **Delete**: `DELETE /nurse/delete/{id}` (#35).
- **Unit tests** for every `NurseController` method (#36).
- **Continuous integration** with GitHub Actions (#37, #38, #39).
- Database connection pool limited to 2 because of the filess.io limit.

It also includes the move from a JSON file to a real database:

- Hibernate and Spring Data JPA installed and configured (#14).
- `Nurse` became a JPA entity and Hibernate generates the `nurse` table (#13, #15).
- `NurseRepository` created. `getAll()`, `login()` and `findByName()` now read from the database instead of `nurses.json` (#16, #17, #18).
- Local database moved to the cloud on filess.io, with the password in the `DB_PASSWORD` environment variable (#20).
- A failed login now returns `401 Unauthorized` instead of `404 Not Found` (#22).

### v0.3.0 - October 13, 2026

First version of the REST API. The data was read from `nurses.json` (500 nurses) with Jackson, without a database.

- `GET /nurse/index`: list all nurses (#1).
- `POST /nurse/login`: nurse login with user and password in the request body (#2, #9).
- `GET /nurse/name/{name}`: search nurses by name (#3).
- Endpoints checked with Postman (#4).

## Team

| Member | GitHub |
| --- | --- |
| Stephany | [@mydz0](https://github.com/mydz0) |
| Yessamin | [@yessamin-blip](https://github.com/yessamin-blip) |
| Jordi | [@Jrrrd3x7](https://github.com/Jrrrd3x7) |
| Tamara | [@TamaraBermudez](https://github.com/TamaraBermudez) |
