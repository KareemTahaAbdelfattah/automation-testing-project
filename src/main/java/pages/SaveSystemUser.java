/** ==============================================**
 ** @Author: Kareem Taha Abd El-Fattah Mohammed
 ** @Category: Route Testing Diploma
 ** @brief: OrangeHRM Graduation Project
/** ==============================================**/
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class SaveSystemUser {
    // Driver
    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public SaveSystemUser(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Locators
    private By userRoleField = By.xpath("//label[normalize-space()='User Role']");
    private By employeeNameField = By.xpath("//input[@placeholder='Type for hints...']");
    private By statusField = By.xpath("(//div)[44]");
    private By userNameField = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
    private By passwordField =  By.xpath("(//input[@type='password'])[1]");
    private By confirmPasswordField = By.xpath("(//input[@type='password'])[2]");


    // Assertions
    public SaveSystemUser assertaddUserFormFieldsIsDisplayed()
    {
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(userRoleField))
                .isDisplayed(), "User Role field is not displayed" );
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameField))
                .isDisplayed(), "Employee Name field is not displayed" );
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(statusField))
                .isDisplayed(), "Status field is not displayed" );
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(userNameField))
                .isDisplayed(), "User Name field is not displayed" );
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField))
                .isDisplayed(), "Password field is not displayed");
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordField))
                .isDisplayed(), "Confirm Password field is not displayed");
        return this;
    }
}