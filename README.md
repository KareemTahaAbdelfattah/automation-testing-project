# OrangeHRM UI Automation Framework

A Selenium WebDriver automation project for testing the [OrangeHRM Open Source Demo](https://opensource-demo.orangehrmlive.com/) using **Java, TestNG, Maven, and the Page Object Model (POM)**.

The project automates important HR application workflows, including authentication, employee management, user management, navigation, and UI validation.

## Technologies Used

- Java
- Selenium WebDriver
- TestNG
- Maven
- Page Object Model (POM)
- Jackson or Gson for JSON test-data handling
- Allure Report
- Git and GitHub
- Chrome WebDriver

## Application Under Test

- **Application:** OrangeHRM Open Source Demo
- **URL:** https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
- **Username:** `Admin`
- **Password:** `admin123`

> These credentials are provided by the public OrangeHRM demo application and should not be used for production systems.

## Project Objectives

The main objectives of this project are to:

- Automate OrangeHRM UI workflows using Selenium WebDriver.
- Apply the Page Object Model design pattern.
- Create reusable and maintainable test components.
- Implement positive, negative, and validation test scenarios.
- Use explicit waits instead of `Thread.sleep()`.
- Execute tests in parallel using independent WebDriver instances.
- Read test data from JSON files using TestNG DataProviders.
- Generate detailed Allure test reports.

## Automated Test Scenarios

The project covers the following scenarios:

1. Login with valid credentials.
2. Login with invalid credentials.
3. Login with empty username and password fields.
4. Search for an existing employee.
5. Search for a non-existing employee.
6. Open and verify the Add Employee page.
7. Validate required fields when adding an employee.
8. Add a new employee and verify the employee record.
9. Verify the Admin > User Management > Add User page.
10. Verify the OrangeHRM footer and branding link.
11. Verify the sidebar menu items.

## Framework Features

### Page Object Model

Each application page has a separate page class containing:

- Web element locators.
- Page-specific actions.
- Reusable methods.
- Page navigation methods.

This keeps test classes clean and improves maintainability when the application UI changes.

### Explicit Waits

The framework uses Selenium explicit waits to synchronize test execution with the application state. It avoids fixed delays such as `Thread.sleep()`.

Examples of conditions used include:

- Element visibility.
- Element clickability.
- URL changes.
- Presence of elements.
- Text visibility.

### Data-Driven Testing

Test data is stored in JSON files under:

```text
src/test/resources/testdata/
```

The data is read using a TestNG `DataProvider`, allowing the same test method to run with multiple data sets without hardcoding values inside the test methods.

Example data may include:

- Valid usernames and passwords.
- Invalid login credentials.
- Employee names.
- New employee details.
- Non-existing employee names.

### Configuration Management

Environment-specific settings are stored in:

```text
src/test/resources/config.properties
```

Example:

```properties
base.url=https://opensource-demo.orangehrmlive.com/web/index.php/
browser=chrome
explicit.wait=10
```

The configuration is loaded using Java's built-in `Properties` class.

### Parallel Execution

Parallel execution is configured in `testng.xml`:

```xml
<suite name="OrangeHRM Test Suite" parallel="tests" thread-count="2">
```

The framework uses `ThreadLocal<WebDriver>` so every parallel test thread receives its own independent browser instance.

### Allure Reporting

The project integrates Allure with TestNG to provide:

- Test execution results.
- Test steps.
- Test status.
- Failure details.
- Execution duration.
- Screenshots or attachments, when configured.

## Project Structure

```text
OrangeHRM-Automation/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── pages/
│   │   │   │   ├── AddEmployeePage.java
│   │   │   │   ├── CommonPage.java
│   │   │   │   ├── DashboardPage.java
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── SaveSystemUser.java
│   │   │   │   ├── ViewEmployeeListPage.java
│   │   │   │   └── ViewSystemUsersPage.java
│   │   │   │
│   │   │   └── utils/
│   │   │       ├── DataDriven.java
│   │   │       └── WindowManager.java
│   │   │
│   │   └── resources/
│   │       └── screenshots/
│   │
│   └── test/
│       ├── java/
│       │   ├── base/
│       │   │   └── Base.java
│       │   │
│       │   ├── tests/
│       │   │   ├── AddEmployeeTest.java
│       │   │   ├── DashboardTest.java
│       │   │   ├── LoginTest.java
│       │   │   ├── SaveSystemUserPageList.java
│       │   │   └── ViewEmployeeListTest.java
│       │   │
│       │   └── utility/
│       │       └── ConfigReader.java
│       │
│       └── resources/
│           ├── testData/
│           │   └── testData.json
│           └── config.properties
│
├── testNG.xml
├── pom.xml
├── .gitignore
├── README.md
└── allure-report/
```

> Adjust the structure above to match the actual package and file names in your repository.

## Prerequisites

Before running the project, install the following:

- Java JDK 8 or later.
- Apache Maven.
- Google Chrome.
- Allure Commandline.
- Git.

Verify the installations:

```bash
java -version
mvn -version
allure --version
```

## Configuration

Update the following file if necessary:

```text
src/test/resources/config.properties
```

Example:

```properties
base.url=https://opensource-demo.orangehrmlive.com/web/index.php/
browser=chrome
explicit.wait=10
```

The default browser is Chrome. If your framework supports other browsers, update the browser value according to your implementation.

## Running the Tests

### Run all tests

```bash
mvn clean test
```

### Run tests using TestNG configuration

```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Run a specific test class

```bash
mvn -Dtest=LoginTests test
```

### Generate the Allure Report

After test execution, generate the static report using:

```bash
allure generate allure-results --clean -o allure-report
```

Alternatively, if configured in Maven:

```bash
mvn test allure:report
```

### Open the Allure Report

```bash
allure open allure-report
```

The generated report is stored in:

```text
allure-report/
```

The `allure-results` directory contains temporary execution data and should not be committed to GitHub.

## Git Ignore

The following generated files should generally be excluded from version control:

```gitignore
target/
allure-results/
*.log
.idea/
*.iml
```

The generated `allure-report` directory may be committed if you want visitors to view the static report directly from the repository.

## Test Execution Flow

The general test execution flow is:

1. Load browser and environment settings from `config.properties`.
2. Create a thread-safe WebDriver instance.
3. Open the OrangeHRM application.
4. Execute the selected test scenario.
5. Apply explicit waits and assertions.
6. Capture test results through Allure.
7. Close the browser after test execution.
8. Generate the Allure HTML report.

## Example Test Flow

```text
Open OrangeHRM
        ↓
Login with valid credentials
        ↓
Navigate to PIM
        ↓
Open Employee List
        ↓
Search for employee
        ↓
Validate search result
        ↓
Generate test result
```

## Test Design Approach

The tests include:

- Positive testing.
- Negative testing.
- Boundary and validation testing.
- End-to-end testing.
- UI element verification.
- Navigation verification.
- Data-driven testing.

Assertions are used to verify:

- Page URLs.
- Page headers.
- Error messages.
- Validation messages.
- Search results.
- Form fields.
- Sidebar menu items.
- Footer links.

## Reporting

Allure reports provide a clear view of:

- Passed tests.
- Failed tests.
- Skipped tests.
- Test steps.
- Failure messages.
- Execution time.
- Test history, if configured.

A sample report can be viewed from the committed `allure-report` directory or generated locally after running the test suite.

## Best Practices Applied

- Page Object Model for maintainable test design.
- Reusable methods for common application actions.
- Explicit waits instead of hardcoded delays.
- Meaningful assertions for every test case.
- Externalized configuration values.
- Externalized test data.
- Thread-safe WebDriver management.
- Clear test naming conventions.
- Separation between test logic and page interaction logic.
- Maven-based project execution.
- Allure reporting for test visibility.

## Future Improvements

Possible future improvements include:

- Adding cross-browser testing for Chrome and Firefox.
- Adding automated screenshots for failed tests.
- Adding GitHub Actions CI/CD execution.
- Adding API testing for OrangeHRM endpoints.
- Adding database validation.
- Adding retry logic for unstable tests.
- Adding parallel execution across multiple browsers.
- Adding test tagging for smoke and regression suites.
- Publishing Allure reports through GitHub Pages.

## Author

**Kareem Taha Abd El-Fattah**

Aspiring Software Testing and QA Engineer with experience in:

- Manual testing.
- API testing.
- Selenium automation.
- Java and TestNG.
- SQL.
- Jira and Zephyr.
- Git and GitHub.

## License

This project is created for educational and portfolio purposes using the publicly available OrangeHRM demo application.
