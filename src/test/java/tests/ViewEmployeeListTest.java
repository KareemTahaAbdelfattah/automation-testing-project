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
import pages.DashboardPage;
import pages.LoginPage;
import pages.ViewEmployeeListPage;
import utils.DataDriven;

import java.time.Duration;

public class ViewEmployeeListTest extends Base {
    private WebDriverWait wait;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private ViewEmployeeListPage viewEmployeeListPage;

    @BeforeMethod
    public void beforeMethod()
    {
        loginPage = new LoginPage(driver.get());
        dashboardPage = new DashboardPage(driver.get());
        viewEmployeeListPage = new ViewEmployeeListPage(driver.get());
        wait = new WebDriverWait(driver.get(), Duration.ofSeconds(5));
    }

    @Test(dataProvider = "ExistEmployees", dataProviderClass = DataDriven.class)
    public void searchForAnEmployee(String employeeName)
    {
        loginPage.loginUsingCredentials(DataDriven.jsonReader("validLoginData").get("username"),
                DataDriven.jsonReader("validLoginData").get("password"));

        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));
        dashboardPage.navigateToPIMLinkText();

        //Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='Employee Information']")));
        viewEmployeeListPage.searchEmployeeByName(employeeName);

        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//div[contains(text(), '" + employeeName + "')]")));
        viewEmployeeListPage.assertExistanceEmployeeIsDisplayedInSearchResults(employeeName);
    }

    @Test(dataProvider = "NonExistEmployees", dataProviderClass = DataDriven.class)
    public void searchForNonExistEmployee(String employeeName)
    {
        loginPage.loginUsingCredentials(DataDriven.jsonReader("validLoginData").get("username"),
                DataDriven.jsonReader("validLoginData").get("password"));

        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));
        dashboardPage.navigateToPIMLinkText();

        //Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='Employee Information']")));
        viewEmployeeListPage.searchEmployeeByName(employeeName);

        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//p[@class='oxd-text oxd-text--p oxd-text--toast-message oxd-toast-content-text']")));
        viewEmployeeListPage.assertNonExistanceEmployeeAlertMessageIsDisplayed(employeeName);
    }

    @Test
    public void openAddEmployeePage()
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
        Assert.assertTrue(driver.get().findElement(By.xpath("//input[@placeholder='First Name']")).isDisplayed(),
                "First Name field is not displayed.");
        Assert.assertTrue(driver.get().findElement(By.xpath("//input[@placeholder='Last Name']")).isDisplayed(),
                "Last Name field is not displayed.");
    }
}