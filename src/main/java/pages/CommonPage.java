package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.WindowManager;

import java.util.List;
import java.util.Objects;

public class CommonPage{

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final WindowManager windowManager;

    private final By footerOrangeHRMLinkText = By.linkText("OrangeHRM, Inc");

    public CommonPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        windowManager = new WindowManager(driver);
    }

    public void assertFooterContainsOrangeHRMInc() {

        String parentWindow = driver.getWindowHandle();
        int numberOfWindowsBeforeClick = driver.getWindowHandles().size();

        WebElement footer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(footerOrangeHRMLinkText)
        );

        new Actions(driver)
                .scrollToElement(footer)
                .perform();

        String actualFooterText = footer.getText().trim();

        Assert.assertTrue(
                actualFooterText.contains("OrangeHRM, Inc"),
                "Footer text does not contain 'OrangeHRM, Inc'. Actual text: " + actualFooterText
        );

        driver.findElement(footerOrangeHRMLinkText).click();

        wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindowsBeforeClick + 1));

        windowManager.switchTab("OrangeHRM: All in One HR Software for Businesses | OrangeHRM");

        wait.until(ExpectedConditions.urlContains("orangehrm.com"));

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("orangehrm.com")
                , "URL does not contain 'orangehrm.com'");
    }

    public void assertSidebarMenuContainsExpectedItems() {

        List<String> expectedMenuItems = List.of(
                "Admin",
                "PIM",
                "Leave",
                "Time",
                "Recruitment",
                "My Info",
                "Performance",
                "Dashboard",
                "Directory"
        );

        for (String menuItem : expectedMenuItems) {

            By menuItemLocator = By.xpath(
                    "//span[normalize-space()='" + menuItem + "']"
            );

            boolean isDisplayed = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(menuItemLocator)
            ).isDisplayed();

            Assert.assertTrue(
                    isDisplayed,
                    "Sidebar menu item is not displayed: " + menuItem
            );
        }
    }
}