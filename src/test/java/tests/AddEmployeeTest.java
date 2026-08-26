/** ==============================================**
 ** @Author: Kareem Taha Abd El-Fattah Mohammed
 ** @Category: Route Testing Diploma
 ** @brief: OrangeHRM Graduation Project
/** ==============================================**/
package tests;

import base.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.DashboardPage;
import pages.LoginPage;
import pages.ViewEmployeeListPage;
import utils.DataDriven;

import java.time.Duration;

public class AddEmployeeTest extends Base {
    private WebDriverWait wait;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private ViewEmployeeListPage viewEmployeeListPage;
    private AddEmployeePage addEmployeePage;

    @BeforeMethod
    public void beforeMethod()
    {
        loginPage = new LoginPage(driver.get());
        dashboardPage = new DashboardPage(driver.get());
        viewEmployeeListPage = new ViewEmployeeListPage(driver.get());
        addEmployeePage = new AddEmployeePage(driver.get());
        wait = new WebDriverWait(driver.get(), Duration.ofSeconds(5));
    }

    @Test
    public void addNewEmployeeWithEmptyField()
    {
        loginPage.loginUsingCredentials(DataDriven.jsonReader("validLoginData").get("username"),
                DataDriven.jsonReader("validLoginData").get("password"));

        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));
        dashboardPage.navigateToPIMLinkText();

        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='Employee Information']")));
        viewEmployeeListPage.clickAddEmployeeButton();

        // Explicit wait
        wait.until(ExpectedConditions.urlContains("/pim/addEmployee"));
        Assert.assertTrue(driver.get().getCurrentUrl().contains("/pim/addEmployee"), "Failed to navigate to Add Employee page.");

        // Assert the "First Name" and "Last Name" fields are displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='First Name']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Last Name']")));

        addEmployeePage.AddEmployee("", "");
        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='oxd-input-group']//div[1]//span[1]")));
        addEmployeePage.assertValidationErrorWhenEmptyFields();
    }

    @Test
    public void endToEndAddNewEmployee()
    {
        String EmployeeName = "Koey Mark";
        String[] nameParts = EmployeeName.trim().split("\\s+");
        String firstName = nameParts[0];
        String lastName = nameParts[1];

        loginPage.loginUsingCredentials(DataDriven.jsonReader("validLoginData").get("username"),
                DataDriven.jsonReader("validLoginData").get("password"));

        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));
        dashboardPage.navigateToPIMLinkText();

        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='Employee Information']")));
        viewEmployeeListPage.clickAddEmployeeButton();

        // Explicit wait
        wait.until(ExpectedConditions.urlContains("/pim/addEmployee"));
        Assert.assertTrue(driver.get().getCurrentUrl().contains("/pim/addEmployee"), "Failed to navigate to Add Employee page.");

        // Assert the "First Name" and "Last Name" fields are displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='First Name']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Last Name']")));

        addEmployeePage.AddEmployee(firstName, lastName);
        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Personal Details']")));
        addEmployeePage.assertPersonalDetailsPageOpenAfterNewEpmployeeAdded();

        addEmployeePage.navigateToEmployeeListPage();
        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='Employee Information']")));
        viewEmployeeListPage.searchEmployeeByName(EmployeeName);

        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '" + firstName + "')]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '" + lastName + "')]")));
        viewEmployeeListPage.assertExistanceEmployeeIsDisplayedInSearchResults(EmployeeName);
    }
}