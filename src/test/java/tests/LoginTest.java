package tests;

import base.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CommonPage;
import pages.LoginPage;
import utils.DataDriven;

import java.time.Duration;

public class LoginTest extends Base {
    private WebDriverWait wait;
    private LoginPage loginPage;
    private CommonPage commonPage;

    @BeforeMethod
    public void beforeMethod()
    {
        wait = new WebDriverWait(driver.get(), Duration.ofSeconds(5));
        loginPage = new LoginPage(driver.get());
        commonPage = new CommonPage(driver.get(), wait);
    }

    @Test(dataProvider = "validLoginData", dataProviderClass = DataDriven.class)
    public void loginTestUsingValidCredentials(String username, String password)
    {
        loginPage.loginUsingCredentials(username, password);

        // Explicit Wait
        wait.until(ExpectedConditions.urlContains("/dashboard/index"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));

        loginPage.assertUrlContainDashboardAfterSuccessfulLogin()
                .assertDashboardHeaderIsDisplayedAfterSuccessfulLogin();
    }

    @Test(dataProvider = "invalidLoginData", dataProviderClass = DataDriven.class)
    public void loginTestUsingInvalidCredentials(String username, String password)
    {
        loginPage.loginUsingCredentials(username, password);

        //Explicit Wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.
                xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")));

        loginPage.assertErrorMessageDisplayedForInvalidCredentials();
    }

    @Test
    public void loginTestWithEmptyFields()
    {
        loginPage.loginWithEmptyFields();

        //Explicit Wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.
                xpath("//div[@class='orangehrm-login-slot-wrapper']//div[1]//div[1]//span[1]")));

        loginPage.assertRequiredMessageDisplayedForEmptyFields();
    }

    @Test(dataProvider = "validLoginData", dataProviderClass = DataDriven.class)
    public void verifySideBarMenuAfterSuccessfulLogin(String username, String password)
    {
        loginPage.loginUsingCredentials(username, password);
        commonPage.assertSidebarMenuContainsExpectedItems();
    }
}