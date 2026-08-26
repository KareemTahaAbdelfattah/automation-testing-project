/** ==============================================**
 ** @Author: Kareem Taha Abd El-Fattah Mohammed
 ** @Category: Route Testing Diploma
 ** @brief: OrangeHRM Graduation Project
/** ==============================================**/
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ViewSystemUsersPage {
    // Driver
    private WebDriver driver;

    // Constructor
    public ViewSystemUsersPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By userManagementList = By.xpath("//span[normalize-space()='User Management']");
    private By usersInManagementList = By.linkText("Users");
    private By addUserButton = By.xpath("//button[normalize-space()='Add']");


    // Methods
    public ViewSystemUsersPage navigateToUserManagement(){
        driver.findElement(userManagementList).click();
        return this;
    }

    public ViewSystemUsersPage navigateToUsersInManagementList(){
        driver.findElement(usersInManagementList).click();
        return this;
    }

    public ViewSystemUsersPage clickAddUserButton() {
        driver.findElement(addUserButton).click();
        return this;
    }

    // Assertions
    public ViewSystemUsersPage assertAddUserFormFieldsDisplayed() {
        // Implementation for asserting the add user form
        return this;
    }
}