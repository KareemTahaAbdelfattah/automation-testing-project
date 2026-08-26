/** ==============================================**
 ** @Author: Kareem Taha Abd El-Fattah Mohammed
 ** @Category: Route Testing Diploma
 ** @brief: OrangeHRM Graduation Project
/** ==============================================**/
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AddEmployeePage {
    // Driver
    private WebDriver driver;

    // Constructor
    public AddEmployeePage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By firstNameField = By.xpath("//input[@placeholder='First Name']");
    private By lastNameField = By.xpath("//input[@placeholder='Last Name']");
    private By saveButton = By.xpath("//button[normalize-space()='Save']");
    private By requiredErrorMessage = By.xpath("//div[@class='oxd-input-group']//div[1]//span[1]");
    private By personalDetails = By.xpath("//a[normalize-space()='Personal Details']");
    private By employeeListButton = By.xpath("//a[normalize-space()='Employee List']");

    // Methods
    public AddEmployeePage AddEmployee(String firstName, String lastName) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(saveButton).click();
        return this;
    }

    public AddEmployeePage navigateToEmployeeListPage() {
        driver.findElement(employeeListButton).click();
        return this;
    }

    // Assertions
    public AddEmployeePage assertValidationErrorWhenEmptyFields(){
        Assert.assertTrue(driver.findElement(requiredErrorMessage)
                .isDisplayed(), "Validation error message Required is not displayed for empty fields.");
        return this;
    }

    public AddEmployeePage assertPersonalDetailsPageOpenAfterNewEpmployeeAdded(){
        Assert.assertTrue(driver.findElement(personalDetails).isDisplayed(),
                "Personal Details page is not displayed after adding a new employee.");
        return this;
    }
}