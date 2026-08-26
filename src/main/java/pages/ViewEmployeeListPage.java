/** ==============================================**
 ** @Author: Kareem Taha Abd El-Fattah Mohammed
 ** @Category: Route Testing Diploma
 ** @brief: OrangeHRM Graduation Project
/** ==============================================**/
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ViewEmployeeListPage {
    // Driver
    private WebDriver driver;

    // Constructor
    public ViewEmployeeListPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By employeeNameField = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/div/div/input");
    private By searchButton = By.xpath("//button[normalize-space()='Search']");
    private By addEmployeeButton = By.xpath("//a[normalize-space()='Add Employee']");

    // Methods
    public ViewEmployeeListPage searchEmployeeByName(String employeeName) {
        driver.findElement(employeeNameField).sendKeys(employeeName);
        driver.findElement(searchButton).click();
        return this;
    }

    public ViewEmployeeListPage clickAddEmployeeButton() {
        driver.findElement(addEmployeeButton).click();
        return this;
    }

    // Assertions
    public ViewEmployeeListPage assertExistanceEmployeeIsDisplayedInSearchResults(String employeeName) {
        String[] nameParts = employeeName.trim().split("\\s+");
        String firstName = nameParts[0];
        String lastName = nameParts[1];
        By employeeFistNameInResults = By.xpath("//div[contains(text(), '" + firstName + "')]");
        By employeeLastNameInResults = By.xpath("//div[contains(text(), '" + lastName + "')]");
        boolean isFistNameEmployeeDisplayed = driver.findElement(employeeFistNameInResults).isDisplayed();
        boolean isLastNameEmployeeDisplayed = driver.findElement(employeeLastNameInResults).isDisplayed();
        if (!isFistNameEmployeeDisplayed && !isLastNameEmployeeDisplayed) {
            throw new AssertionError("Employee with name '" + employeeName + "' is not displayed in search results.");
        }
        return this;
    }

    public ViewEmployeeListPage assertNonExistanceEmployeeAlertMessageIsDisplayed(String employeeName) {
        By noRecordsFoundAlert = By.xpath("//p[@class='oxd-text oxd-text--p oxd-text--toast-message oxd-toast-content-text']");
        boolean isAlertDisplayed = driver.findElement(noRecordsFoundAlert).isDisplayed();
        if (!isAlertDisplayed) {
            throw new AssertionError("Alert message for non-existence of employee with name '" + employeeName + "' is not displayed.");
        }
        return this;
    }
}