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
import pages.*;
import utils.DataDriven;

import java.time.Duration;

public class SaveSystemUserPageList extends Base {
    private WebDriverWait wait;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private ViewSystemUsersPage viewSystemUsersPage;
    private SaveSystemUser saveSystemUser;

    @BeforeMethod
    public void beforeMethod()
    {
        loginPage = new LoginPage(driver.get());
        dashboardPage = new DashboardPage(driver.get());
        viewSystemUsersPage = new ViewSystemUsersPage(driver.get());
        saveSystemUser = new SaveSystemUser(driver.get());
        wait = new WebDriverWait(driver.get(), Duration.ofSeconds(5));
    }

    @Test
    public void addNewEmployeeWithEmptyField()
    {
        loginPage.loginUsingCredentials(DataDriven.jsonReader("validLoginData").get("username"),
                DataDriven.jsonReader("validLoginData").get("password"));

        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));
        dashboardPage.navigateToAdminLinkText();

        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='User Management']")));
        viewSystemUsersPage.navigateToUserManagement();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Users")));
        viewSystemUsersPage.navigateToUsersInManagementList();

        // Explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='System Users']")));
        viewSystemUsersPage.clickAddUserButton();

        saveSystemUser.assertaddUserFormFieldsIsDisplayed();
    }
}