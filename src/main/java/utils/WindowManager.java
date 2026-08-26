/** ==============================================**
 ** @Author: Kareem Taha Abd El-Fattah Mohammed
 ** @Category: Route Testing Diploma
 ** @brief: OrangeHRM Graduation Project
/** ==============================================**/
package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

public class WindowManager {
    //Driver
    private WebDriver driver;

    //Constructor
    public WindowManager(WebDriver driver) {
        this.driver =  driver;
    }

    //Methods
    public void goBack()
    {
        driver.navigate().back();
    }

    public void goForward()
    {
        driver.navigate().forward();
    }

    public void gotToURL(String url)
    {
        driver.navigate().to(url);
    }

    public void refreshPage()
    {
        driver.navigate().refresh();
    }

    public void maximizeWindow()
    {
        driver.manage().window().maximize();
    }

    public void switchTab(String title) {
        Set<String> windowHandles = driver.getWindowHandles();
        System.out.println("windowHandles: " + windowHandles.size());

        for (String windowHandle : windowHandles)
        {
            driver.switchTo().window(windowHandle);
            if(title.equals(driver.getTitle())) break;
        }
    }
}