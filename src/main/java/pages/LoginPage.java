/** ==============================================**
 ** @Author: Kareem Taha Abd El-Fattah Mohammed
 ** @Category: Route Testing Diploma
 ** @brief: OrangeHRM Graduation Project
/** ==============================================**/
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage {
    // Driver
    private WebDriver driver;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
     private By usernameField = By.xpath("//input[@name='username']");
     private By passwordField = By.xpath("//input[@name='password']");
     private By loginButton = By.xpath("//button[normalize-space()='Login']");
     private By dashboardHeader = By.xpath("//h6[normalize-space()='Dashboard']");
     private By errorMessage = By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']");

    // Methods
    public LoginPage loginUsingCredentials(String username, String password){
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
        return this;
    }

    public LoginPage loginWithEmptyFields(){
        driver.findElement(loginButton).click();
        return this;
    }

    // Assertions
    public LoginPage assertUrlContainDashboardAfterSuccessfulLogin() {
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard/index"),
                "URL does not contain dashboard after successful login.");
        return this;
    }

    public LoginPage assertDashboardHeaderIsDisplayedAfterSuccessfulLogin() {
        Assert.assertTrue(driver.findElement(dashboardHeader).isDisplayed(),
                "Dashboard header is not displayed after successful login.");
        return this;
    }

    public LoginPage assertErrorMessageDisplayedForInvalidCredentials() {
        Assert.assertTrue(driver.findElement(errorMessage).isDisplayed(),
                "Error message for invalid credentials is not displayed.");
        Assert.assertEquals(driver.findElement(errorMessage).getText(), "Invalid credentials",
                "Error message text is not as expected.");
        return this;
    }

    public LoginPage assertRequiredMessageDisplayedForEmptyFields() {
        Assert.assertEquals(driver.findElement(By.xpath(
                        "//div[@class='orangehrm-login-slot-wrapper']//div[1]//div[1]//span[1]")).getText(),
                "Required",
                "Error message for empty username field is not displayed or incorrect.");

        Assert.assertEquals(driver.findElement(By.xpath(
                        "//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[2]/div/span")).getText(),
                "Required",
                "Error message for empty password field is not displayed or incorrect.");
        return this;
    }
}