/** ==============================================**
 ** @Author: Kareem Taha Abd El-Fattah Mohammed
 ** @Category: Route Testing Diploma
 ** @brief: OrangeHRM Graduation Project
/** ==============================================**/
package tests;

import base.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.DataDriven;

import java.time.Duration;

public class DashboardTest extends Base {

    private LoginPage loginPage;
    private CommonPage commonPage;
    private WebDriverWait waitt;

    @BeforeMethod
    public void beforeMethod()
    {
        waitt = new WebDriverWait(driver.get(), Duration.ofSeconds(5));
        loginPage = new LoginPage(driver.get());
        commonPage = new CommonPage(driver.get(), waitt);
    }

    @Test
    public void verifyTheOrangeHRMFooterLink()
    {
        loginPage.loginUsingCredentials(DataDriven.jsonReader("validLoginData").get("username"),
                DataDriven.jsonReader("validLoginData").get("password"));

        // Explicit wait
        waitt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));

        commonPage.assertFooterContainsOrangeHRMInc();
    }
}