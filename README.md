# Rest Assured framework


[![Build](https://github.com/pravinkumbhare/restassured-framework/actions/workflows/maven.yml/badge.svg)](https://github.com/pravinkumbhare/restassured-framework/actions/workflows/maven.yml)

📊 Reports:
- [![Extent Report](https://img.shields.io/badge/Report-Extent-blue)](https://github.com/pravinkumbhare/restassured-framework/actions/workflows/maven.yml)
- [![Allure Report](https://img.shields.io/badge/Report-Allure-orange)](https://github.com/pravinkumbhare/restassured-framework/actions/workflows/maven.yml)

## Project Overview

This project is a **REST API automation framework** built using **Rest Assured** with **Java** and **Maven**.  
The framework is designed to automate API testing for various services, validate responses, and generate detailed test reports.

**Key Features:**
- End-to-end REST API testing with GET, POST, PUT, DELETE requests.
- Test data management using JSON files.
- Schema validation for API responses.
- Supports parameterization for dynamic testing.
- Detailed reporting with **Allure** and **Extent** reports.
- Integrates with **Jenkins** for CI/CD pipeline execution.

---

## Prerequisites

Before running the tests, ensure the following are installed and configured:

1. **Java Development Kit (JDK)**
    - Version: 11 or above
    - [Download JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
    - Ensure `JAVA_HOME` environment variable is set.

2. **Maven**
    - Version: 3.6 or above
    - [Download Maven](https://maven.apache.org/download.cgi)
    - Add `MAVEN_HOME/bin` to your system `PATH`.

3. **IDE (Optional but recommended)**
    - IntelliJ IDEA, Eclipse, or VS Code for code editing.

4. **Jenkins (Optional for CI/CD)**
    - For running automated tests in a pipeline.

5. **Git**
    - For cloning the repository.

6. **Browser (Optional for HTML report viewing)**
    - Chrome, Edge, or Firefox for opening Allure/Extent reports.

---

## Project Structure

The project follows a standard Maven structure and is organized for clarity and maintainability:

    RestAssured-Framework/
    │
    ├── pom.xml # Maven project file with dependencies
    ├── README.md # Project documentation
    ├── src/
    │ ├── main/
    │ │ └── java/
    │ │ └── com/demo/utils/ # Utility classes for API requests, data handling, logging, etc.
    │ │
    │ └── test/
    │ ├── java/
    │ │ └── com/demo/tests/ # Test classes for API automation
    │ │
    │ └── resources/
    │ ├── testdata/ # JSON/CSV files for test data
    │ └── schemas/ # JSON schemas for response validation
    │
    ├── target/ # Build output, generated reports, and compiled classes
    │ ├── surefire-reports/ # Test execution results in XML
    │ ├── allure-results/ # Raw Allure results
    │ ├── allure-report/ # Generated Allure HTML report
    │ └── extent-report.html # Generated Extent report
    │
    └── Jenkinsfile # Pipeline script for CI/CD execution



**Folder Description:**

- `src/main/java/com/demo/utils/`  
  Contains helper classes such as API request builders, response validators, logging utilities, and data handling helpers.

- `src/test/java/com/demo/tests/`  
  Contains all test classes that use Rest Assured to perform API testing.

- `src/test/resources/testdata/`  
  Stores input JSON, CSV, or other data files used in test cases.

- `src/test/resources/schemas/`  
  Contains JSON schema files for response validation.

- `target/`  
  Maven output directory containing compiled classes, reports, and test results.

- `pom.xml`  
  Contains project dependencies (Rest Assured, JUnit, Allure, Extent, etc.) and build configurations.

- `Jenkinsfile`  
  Defines the Jenkins pipeline for building, testing, and reporting.


## How to Run the 

    1. Running Locally with Maven
    
    Make sure Java and Maven are installed and configured in your system PATH.
    
    Navigate to the project root directory in the terminal.
    
    Run the tests using Maven:
    
    mvn clean test
    
    Test results and reports will be generated at:
    
    JUnit/Surefire report: target/surefire-reports/
    
    Allure report: target/allure-results/ → target/allure-report/ (HTML report)
    
    Extent report: target/extent-report.html
    
    To view the Allure report locally, you can use the command (if Allure CLI is installed):
    
    allure serve target/allure-results
    
    ## 2. Running via Jenkins Pipeline
    
    Ensure the project is set up in Jenkins with proper Git repository access.
    
    The pipeline is defined in Jenkinsfile with stages:
    
    Build & Test: Runs mvn clean test
    
    Allure Report: Generates and archives the Allure report
    
    Extent Report: Publishes the Extent HTML report
    
    Publish Test Results: Publishes JUnit test results
    
    After running the pipeline:
    
    Allure report is available under Allure_20Report in Jenkins HTML report section.
    
    Extent report is available under Extent_20Report in Jenkins HTML report section.


## Builder & POJO Classes

    1. Purpose
    
    POJO (Plain Old Java Object) classes represent the request or response payloads for API calls.
    
    Builder classes create these objects with a fluent API, making test scripts cleaner and easier to read.
    
    Builder with Faker & Factory adds dynamic test data generation, reducing hard-coded values and improving test coverage.
    
    2. Location
    
    POJO classes: src/main/java/com/demo/pojo/
    
    Builder classes: src/main/java/com/demo/builder/
    
    3. Basic Example
    
    POJO Example: Post.java


    public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;

    // Getters and Setters
}


## Builder Example: PostBuilder.java

    public class PostBuilder {

    private int userId = 100;
    private String title = "Default Builder Title";
    private String body = "Default Builder Body";

    public PostBuilder withUserId(int userId){
        this.userId = userId;
        return this;
    }

    public PostBuilder withTitle(String title){
        this.title = title;
        return this;
    }

    public PostBuilder withBody(String body){
        this.body = body;
        return this;
    }

    public Post build(){
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle(title);
        post.setBody(body);
        return post;
    }


    //      👉 Benefit: Readable, flexible, avoids big constructors.
        Post postRequest = new PostBuilder()
                .withUserId(12345)
                .withTitle("Title with Buliler Pattern")
                .withBody("Body with Builder Pattern")   // we can use only required fields by using builder pattern
                .build();
}


## 4. Advanced Example (With Faker & Factory)

Builder with Faker & Factory: PostBuilderWithFakerAndFactory.java

    public class PostBuilderWithFakerAndFactory {

    private int userId = 100;
    private String title = "Default Builder Title";
    private String body = "Default Builder Body";

    public PostBuilderWithFakerAndFactory withUserId(int userId){
        this.userId = userId;
        return this;
    }

    public PostBuilderWithFakerAndFactory withTitle(String title){
        this.title = title;
        return this;
    }

    public PostBuilderWithFakerAndFactory withBody(String body){
        this.body = body;
        return this;
    }

    private static final Faker faker = new Faker();

    public PostBuilderWithFakerAndFactory withRandomData(){

        this.userId = faker.number().numberBetween(1, 5000);
        this.title = "Title: " + faker.book().title();
        this.body = faker.lorem().paragraph();
        return this;
    }

    public Post build(){
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle(title);
        post.setBody(this.body);
        return post;
    }
}


## Usage in Tests:

    Post requestData = new PostBuilderWithFakerAndFactory()
                .withRandomData()   // Faker gives you random realistic values
                .withUserId(userId) // fixed from @CsvSource
                // .withTitle(title) // intentionally skipped → faker title will be used
                .withBody(body)     // controlled from @CsvSource
                .build();

    // Builder with dynamic Faker data
    Post randomPost = PostBuilderWithFakerAndFactory.createDefaultPost();


## Benefits:

Generates realistic dynamic data for tests.
Reduces repetitive hard-coded values.
Makes tests cleaner, maintainable, and scalable.


## How to Run Tests
1. Run Tests Locally (Maven)

You can run the tests directly from your local machine using Maven.

Command:
    mvn clean test


## Notes:

This will run all tests under src/test/java/.

Test reports are generated in:

JUnit reports: target/surefire-reports/

Allure reports: allure-results/ → can be viewed via allure serve command

Extent reports: target/extent-report.html

View Allure Report Locally:
    allure serve allure-results

This opens a browser window showing the Allure report.

## 2. Run Tests via Jenkins Pipeline

The framework includes a Jenkinsfile configured to build, test, and publish reports automatically.

Steps:

Open Jenkins → New Item → Pipeline → configure repository.

Pipeline will automatically:

Run mvn clean test

Generate Allure and Extent reports

Archive reports in Jenkins

Publish JUnit test results

Reports can be accessed in the Jenkins build page:

Allure Report: Allure Report link

Extent Report: Extent Report link

JUnit: Test Result link

## Notes:

Make sure Allure plugin is installed in Jenkins.

Jenkins pipeline handles dynamic data generation via Builder & Faker classes.

## 3. Run Tests via GitHub Actions

The framework includes a Maven GitHub Actions workflow (maven.yml) for CI/CD.

Steps:

Push your code to GitHub.

GitHub Actions workflow triggers automatically:

Runs mvn clean test

Publishes test results as artifacts

Generates Allure/Extent reports (can be downloaded from Actions artifacts)

Sample Workflow Steps:

    - name: Set up JDK
    uses: actions/setup-java@v3
    with:
    java-version: 17
    distribution: temurin

    - name: Build & Test
      run: mvn clean test

    - name: Archive Reports
      uses: actions/upload-artifact@v3
      with:
      name: allure-report
      path: target/allure-report


## Benefits:

Runs tests on every push or pull request automatically.

Ensures consistent results across environments.

## Project Structure

Below is the structure of the framework and the purpose of each folder:

        restassured-demo/
    │── .allure/                  # Allure configuration files (auto-generated)
    │── .github/
    │   └── workflows/
    │       └── maven.yml         # GitHub Actions workflow for CI/CD
    │── .idea/                    # IntelliJ IDEA project settings (can be ignored)
    │── src/
    │   ├── main/
    │   │   └── java/
    │   │       └── com/demo/
    │   │           ├── builder/  # Builder pattern classes for test data setup
    │   │           ├── pojo/     # POJOs for request/response mapping
    │   │           └── utils/    # Utility classes (ApiUtils, SchemaUtils, etc.)
    │   └── test/
    │       └── java/
    │           └── com/demo/tests/  # Test classes (Schema, CRUD, etc.)
    │── allure-report/            # Generated Allure report (after test run)
    │── allure-results/           # Allure raw results (used to generate report)
    │── target/                   # Maven build output (compiled classes, Extent report, surefire-reports)
    │── pom.xml                   # Maven dependencies and plugin configuration
    │── Jenkinsfile               # Jenkins pipeline configuration
    │── README.md                 # Project documentation


## Key Components

    builder/ – Implements Builder Pattern to create test data objects (often combined with Faker for dynamic data).
    
    pojo/ – Contains Plain Old Java Objects used for request/response payloads (e.g., User, Post).
    
    utils/ – Helper classes for API requests, schema validation, logging, and test data handling.
    
    tests/ – Actual TestNG/JUnit tests using Rest Assured.
    
    allure-results/ – Stores execution results needed for Allure reports.
    
    target/extent-report.html – Auto-generated Extent Report after each run.
    
    maven.yml – GitHub Actions workflow file for CI/CD automation.
    
    Jenkinsfile – Pipeline definition for Jenkins builds and report publishing.


## Key Features

    API Testing with Rest Assured – Simplifies writing and executing REST API tests.
    
    Builder Pattern + Faker Integration – Test data is dynamically generated and reusable using PostBuilderWithFakerAndFactory and similar classes.
    
    POJO-based Payloads – Clean request/response object mapping for readability and maintainability.
    
    Schema Validation – Ensures API responses conform to expected JSON schemas.
    
    Reporting
    
    Allure Reports – Rich, interactive test execution reports.
    
    Extent Reports – Detailed HTML-based reports stored in target/extent-report.html.
    
    CI/CD Integration
    
    GitHub Actions (maven.yml) – Automates build, test, and report generation on every push/PR.
    
    Jenkins Pipeline (Jenkinsfile) – Supports build execution, Allure & Extent report publishing.
    
    Maven-based Project – Handles dependency management and test execution.
    
    Scalable Design – Utilities (ApiUtils, SchemaUtils, LoggerUtil) and modular structure allow easy extension for new test cases.