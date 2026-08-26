/** ==============================================**
 ** @Author: Kareem Taha Abd El-Fattah Mohammed
 ** @Category: Route Testing Diploma
 ** @brief: OrangeHRM Graduation Project
/** ==============================================**/
package base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import utility.ConfigReader;
import utils.WindowManager;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Arrays;

public class Base {

    protected ThreadLocal<WebDriver> driver;
    protected ThreadLocal<WebDriverWait> wait;

    @BeforeTest
    public void beforeTest() {
        clearAllureHistory();
    }

    @BeforeMethod
    public void setup() throws InterruptedException {
        driver = new ThreadLocal<>();
        wait = new ThreadLocal<>();

        String browser = ConfigReader.getProperty("browser");
        String baseUrl = ConfigReader.getProperty("base.url");
        int explicitWait = ConfigReader.getIntProperty("explicit.wait");

        if (browser.equalsIgnoreCase("chrome")) {
            driver.set(new ChromeDriver(getChromeOptions()));
        } else {
            throw new RuntimeException("Unsupported browser: " + browser);
        }

        wait.set(new WebDriverWait(driver.get(), Duration.ofSeconds(explicitWait)));

        driver.get().manage().window().maximize();
        driver.get().get(baseUrl);
        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            captureScreenshot(result.getName());
        }
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
        wait.remove();
    }

    @AfterTest
    public void openAllureReports() {
        generateAllureReport();
    }

    private void captureScreenshot(String testName) {
        File screenshotsDir = new File("src/main/resources/screenshots");
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }

        var screenshot = (TakesScreenshot) driver.get();
        File screenFile = screenshot.getScreenshotAs(OutputType.FILE);

        try {
            Files.move(
                    screenFile.toPath(),
                    new File(screenshotsDir, testName + ".png").toPath()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateAllureReport() {
        try {
            String projectDir = System.getProperty("user.dir");
            String resultsPath = projectDir + "\\target\\allure-results";
            String reportPath = projectDir + "\\target\\allure-report";

            File resultsDir = new File(resultsPath);

            if (!resultsDir.exists() || resultsDir.listFiles() == null
                    || resultsDir.listFiles().length == 0) {
                System.out.println(
                        "No Allure result files found in: "
                                + resultsDir.getAbsolutePath()
                );
                return;
            }

            Process generate = new ProcessBuilder(
                    "cmd", "/c",
                    "allure", "generate",
                    resultsPath,
                    "--clean",
                    "-o",
                    reportPath
            )
                    .directory(new File(projectDir))
                    .inheritIO()
                    .start();

            int generateExitCode = generate.waitFor();

            if (generateExitCode != 0) {
                throw new RuntimeException(
                        "Allure generation failed with exit code: "
                                + generateExitCode
                );
            }

            Process open = new ProcessBuilder(
                    "cmd", "/c",
                    "allure", "open",
                    reportPath
            )
                    .directory(new File(projectDir))
                    .inheritIO()
                    .start();

            open.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private void clearAllureHistory() {
        File resultsDir = new File(System.getProperty("user.dir") + "/target/allure-results");
        if (resultsDir.exists() && resultsDir.listFiles() != null) {
            for (File file : resultsDir.listFiles()) {
                file.delete();
            }
        }
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        return options;
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public WebDriverWait getWait() {
        return wait.get();
    }

    public WindowManager getWindowManager() {
        return new WindowManager(driver.get());
    }
}