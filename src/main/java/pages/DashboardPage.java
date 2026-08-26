/** ==============================================**
 ** @Author: Kareem Taha Abd El-Fattah Mohammed
 ** @Category: Route Testing Diploma
 ** @brief: OrangeHRM Graduation Project
/** ==============================================**/
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {
    // Driver
    private WebDriver driver;
    // Constructor
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By pimLinkText = By.xpath("//span[normalize-space()='PIM']");
    private By adminLinkText = By.linkText("Admin");

    // Methods
    public DashboardPage navigateToPIMLinkText(){
        driver.findElement(pimLinkText).click();
        return this;
    }

    public DashboardPage navigateToAdminLinkText(){
        driver.findElement(adminLinkText).click();
        return this;
    }
}