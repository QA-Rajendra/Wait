package org.example.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.*;

public class WaitsAlertsWindowExample {

    WebDriver driver;                // WebDriver to control browser
    WebDriverWait wait;             // Explicit wait object

    @BeforeClass
    public void setup() {
        // Start Chrome browser
        driver = new ChromeDriver();

        // 1️⃣ Set Implicit Wait: applies to all findElement() calls
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // 2️⃣ Maximize browser window
        driver.manage().window().maximize();

        // 3️⃣ Explicit Wait: to be used with specific ExpectedConditions
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 4️⃣ Set Page Load Timeout: wait max 30 seconds for full page to load
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        // 5️⃣ Set Script Timeout: wait max 20 seconds for async JavaScript
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));
    }

    @Test(priority = 1)
    public void handleMultipleWindows() {
        // Navigate to site that opens a new tab
        driver.get("https://demoqa.com/browser-windows");

        // 6️⃣ Store current window ID (Parent)
        String mainWindow = driver.getWindowHandle();

        // Click button to open new tab
        driver.findElement(By.id("tabButton")).click();

        // 7️⃣ Get all open windows/tabs
        Set<String> allWindows = driver.getWindowHandles();

        // 8️⃣ Loop through window handles and switch
        for (String handle : allWindows) {
            if (!handle.equals(mainWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // 9️⃣ Wait for heading in new tab to appear
        WebElement heading = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("sampleHeading"))
        );

        // Print text from new tab
        System.out.println("Text in new tab: " + heading.getText());

        // 1️⃣0️⃣ Close child tab
        driver.close();

        // 1️⃣1️⃣ Switch back to parent
        driver.switchTo().window(mainWindow);
    }

    @Test(priority = 2)
    public void handleAlerts() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");

        // 1️⃣2️⃣ Handle Simple Alert
        driver.findElement(By.id("alertButton")).click();
        Alert simpleAlert = wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("Simple Alert Text: " + simpleAlert.getText());
        simpleAlert.accept(); // OK

        // 1️⃣3️⃣ Handle Confirmation Alert
        driver.findElement(By.id("confirmButton")).click();
        Alert confirmAlert = wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("Confirmation Alert Text: " + confirmAlert.getText());
        confirmAlert.dismiss(); // Cancel

        // 1️⃣4️⃣ Handle Prompt Alert
        driver.findElement(By.id("promtButton")).click();
        Alert promptAlert = wait.until(ExpectedConditions.alertIsPresent());
        promptAlert.sendKeys("Selenium Tester");
        promptAlert.accept();

        // Wait to observe result (only for demo)
        Thread.sleep(2000);
    }

    @Test(priority = 3)
    public void fluentWaitExample() {
        driver.get("https://chercher.tech/practice/explicit-wait-sample-selenium-webdriver");

        // 1️⃣5️⃣ Click button that triggers delayed alert
        driver.findElement(By.id("alert")).click();

        // 1️⃣6️⃣ Use FluentWait to wait for alert (custom polling)
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10)) // Max wait
                .pollingEvery(Duration.ofSeconds(1)) // Polling interval
                .ignoring(NoAlertPresentException.class); // Ignore exception

        // 1️⃣7️⃣ Poll every second to check if alert is present
        fluentWait.until(driver -> {
            try {
                Alert alert = driver.switchTo().alert();
                System.out.println("Alert text: " + alert.getText());
                alert.accept(); // Close the alert
                return true;
            } catch (NoAlertPresentException e) {
                return false;
            }
        });
    }

    @AfterClass
    public void tearDown() {
        // Close browser
        if (driver != null) {
            driver.quit();
        }
    }
}
