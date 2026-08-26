# Automation-Testing-Project
# OrangeHRM Test Automation Framework

This project is an automated UI testing framework for the OrangeHRM demo application. It is developed using Java, Selenium WebDriver, TestNG, Maven, and the Page Object Model design pattern.

The framework automates important OrangeHRM workflows, including authentication, dashboard navigation, employee management, system-user management, sidebar validation, footer validation, and data-driven employee creation.

OrangeHRM is a Human Resource Management System that provides modules such as administration, employee information management, leave management, recruitment, time management, performance management, and directory services. [web:215][web:323][web:329]

## Project Objectives

The main objectives of this project are to:

- Automate functional test scenarios for the OrangeHRM web application.
- Validate critical user journeys and application navigation.
- Reduce duplicated Selenium code using the Page Object Model.
- Use data-driven testing for scenarios with multiple test inputs.
- Externalize environment settings and test data.
- Generate readable execution reports using Allure.
- Capture screenshots when tests fail.
- Apply reusable waits and common test utilities.

## Application Under Test

- Application: OrangeHRM Demo
- URL: https://opensource-demo.orangehrmlive.com
- Application type: Human Resource Management System
- Test type: Web UI functional automation

The OrangeHRM demo application is used to validate workflows related to administration, employee information, leave, recruitment, time, performance, and directory management. [web:318][web:323][web:329]

## Technology Stack

- Java
- Selenium WebDriver
- TestNG
- Maven
- Page Object Model
- Apache POI or JSON-based test data
- Allure Report
- Git and GitHub
- IntelliJ IDEA
- Chrome WebDriver

Selenium Page Object Model separates test logic from page interaction logic, which improves maintainability and reduces locator duplication. [web:154][web:322]

## Main Test Areas

### Login

- Login with valid credentials.
- Validate the Dashboard after successful login.
- Validate invalid and missing credentials.
- Validate logout functionality.

### Dashboard

- Verify Dashboard navigation.
- Verify the sidebar menu.
- Validate available navigation modules.
- Verify footer content.

### Admin

- Navigate to the Admin module.
- Open the System Users page.
- Open the Add User page.
- Validate User Role, Employee Name, Username, Password, and Status fields.
- Add a new system user.
- Search for an existing user.

### PIM

- Add a new employee.
- Use TestNG DataProvider for first name and last name.
- Verify employee details.
- Search for an employee.
- Validate the employee search result.

### Leave

- Open the Leave module.
- Apply for leave.
- Validate leave-related fields and messages.
- Verify leave records.

### Recruitment and Other Modules

- Validate navigation to Recruitment, Time, My Info, Performance, and Directory.
- Verify that accessible sidebar menu items are displayed for the logged-in user.

## Framework Structure

```text
orangehrm_graduation_project
│
├── .mvn
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── pages
│   │   │   ├── utility
│   │   │   └── utils
│   │   └── resources
│   │
│   └── test
│       ├── java
│       │   ├── base
│       │   ├── tests
│       │   └── testData
│       │
│       └── resources
│           ├── config.properties
│           ├── allure.properties
│           └── testNG.xml
│
├── pom.xml
└── README.md
```

## Design Pattern

This project uses the Page Object Model. Each important page or reusable application area is represented by a Java class containing its locators and actions.

Examples:

```text
LoginPage.java
DashboardPage.java
ViewEmployeeListPage.java
AddEmployeePage.java
ViewSystemUserPage.java
AddUserPage.java
CommonPage.java
```

Tests call page methods instead of directly interacting with Selenium locators:

```java
loginPage.loginUsingCredentials(username, password);

dashboardPage.navigateToPIMLinkText();

addEmployeePage.AddEmployee(firstName, lastName);

viewEmployeeListPage.searchEmployeeByName(fullName);
```

This approach keeps tests readable and ensures that locator changes can be handled inside the relevant page object. [web:154][web:155]

## Data-Driven Testing

TestNG DataProvider is used to execute the same test with multiple employee names:

```java
@DataProvider(name = "employeeNamesData")
public static Object[][] employeeNamesData() {
    return new Object[][]{
            {"Koey", "Mark"},
            {"Kareem", "Taha"},
            {"Ahmed", "Ali"}
    };
}
```

The test receives the values as method parameters:

```java
@Test(
        dataProvider = "employeeNamesData",
        dataProviderClass = DataDriven.class
)
public void addEmployeeTest(
        String firstName,
        String lastName
) {
    addEmployeePage.AddEmployee(firstName, lastName);
}
```

TestNG executes the test once for each row returned by the DataProvider. [web:34][web:67]

## Configuration

Environment settings are stored in:

```text
src/test/resources/config.properties
```

Example:

```properties
base.url=https://opensource-demo.orangehrmlive.com/web/index.php/
browser=chrome
explicit.wait=10
```

The configuration is read using Java’s `Properties` class instead of hardcoding values in the test code.

## Reporting

Allure is integrated with TestNG to generate interactive test reports. The raw Allure results are stored in:

```text
target/allure-results
```

To generate and open the report:

```powershell
allure generate .\target\allure-results --clean -o .\target\allure-report
allure open .\target\allure-report
```

Alternatively:

```powershell
allure serve .\target\allure-results
```

Allure reports require result files to be generated by the test framework before the report is opened. [web:80][web:237][web:253]

## Test Execution

Run all tests from the project root:

```powershell
mvn clean test
```

If the Maven command is unavailable, install Maven and add its `bin` directory to the Windows PATH.

After execution, verify the Allure result files:

```powershell
Get-ChildItem .\target\allure-results
```

Then open the report:

```powershell
allure serve .\target\allure-results
```

## TestNG Suite

Tests can be executed using:

```text
src/test/resources/testNG.xml
```

Example command:

```powershell
mvn test
```

or:

```powershell
mvn -DsuiteXmlFile=src/test/resources/testNG.xml test
```

## Failure Handling

When a test fails, the framework:

- Captures a screenshot.
- Saves the screenshot under the screenshots directory.
- Closes the WebDriver instance.
- Generates Allure test results when the Allure TestNG listener is configured.
- Makes failures easier to investigate.

Screenshots can be attached directly to Allure using an Allure attachment method so they appear in the report rather than only being saved as local image files.

## Future Improvements

- Add cross-browser execution.
- Add parallel test execution.
- Add API-based test data setup.
- Add database validation using MySQL.
- Add GitHub Actions CI/CD integration.
- Add retry handling for temporary failures.
- Add environment selection for QA and staging.
- Add HTML test data files or Excel-based DataProviders.
- Improve Allure screenshot and video attachments.
- Add logging with Log4j or SLF4J.

## Author

```text
Kareem Taha Abd El-Fattah Mohammed
Route Testing Diploma
OrangeHRM Graduation Project
```
