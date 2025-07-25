# ✅ Selenium Waits, Alerts & Window Handling

Mastering waits, alerts, and window handling in Selenium is crucial for stable automation. Below is a breakdown of each concept with code examples and interview questions.

---

## 🔢 Concepts with Real Code

| # | Concept              | Code Reference                                 | Explanation |
|--:|----------------------|------------------------------------------------|-------------|
| 1 | **Implicit Wait**    | `driver.manage().timeouts().implicitlyWait()`  | Global wait applied to all elements. |
| 2 | **Explicit Wait**    | `WebDriverWait wait = new WebDriverWait(...); wait.until(ExpectedConditions.visibilityOf(...));` | Waits for a specific condition. |
| 3 | **Page Load Timeout**| `driver.manage().timeouts().pageLoadTimeout()` | Waits for the entire page to load. |
| 4 | **Script Timeout**   | `driver.manage().timeouts().setScriptTimeout()`| Waits for async JS to complete. |
| 5 | **Thread.sleep**     | `Thread.sleep(2000);`                          | Pauses thread; not recommended for dynamic waits. |
| 6 | **Get Window ID**    | `String mainWin = driver.getWindowHandle();`   | Gets current tab/window handle. |
| 7 | **All Windows**      | `Set<String> allWins = driver.getWindowHandles();`| Gets all open tabs/windows. |
| 8 | **Set Interface**    | `Set<String> handles = new HashSet<>();`       | Used to store window handles uniquely. |
| 9 | **Switching Window** | `driver.switchTo().window(handle);`            | Switches control to another tab. |
|10 | **Closing Tab**      | `driver.close();`                              | Closes current browser tab. |
|11 | **Switching Back**   | `driver.switchTo().window(mainWin);`           | Returns control to main window. |
|12 | **Simple Alert**     | `driver.switchTo().alert().accept();`          | Clicks OK on alert. |
|13 | **Confirmation Alert**| `driver.switchTo().alert().dismiss();`        | Clicks Cancel on alert. |
|14 | **Prompt Alert**     | `Alert alert = driver.switchTo().alert(); alert.sendKeys("Yes"); alert.accept();` | Sends input and accepts alert. |
|15 | **Fluent Wait**      | `FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);` | Flexible wait with polling. |
|16 | **Polling Strategy** | `wait.until(driver -> driver.findElement(...));` | Repeatedly checks for a condition. |

---

## ✅ Interview Questions & Answers

| Question | Answer |
|---------|--------|
| **What is the difference between implicit and explicit wait?** | Implicit is global, applied to all elements; explicit waits for a specific condition. |
| **What is FluentWait?** | A type of explicit wait with customizable timeout, polling interval, and exception handling. |
| **How to handle alert pop-ups in Selenium?** | Use `switchTo().alert()`, then `accept()`, `dismiss()`, or `sendKeys()`. |
| **What is Set in Java used for?** | To store unique items — in Selenium, it stores distinct window handles. |
| **How to switch tabs in Selenium?** | Get all window handles and iterate with `switchTo().window(handle)`. |
| **Difference between getWindowHandle() and getWindowHandles()?** | `getWindowHandle()` gets current window ID; `getWindowHandles()` returns all open window IDs. |

---

## ✅ Bonus Code Snippet: Switching Between Tabs

```java
String mainWin = driver.getWindowHandle();
Set<String> allWins = driver.getWindowHandles();

for (String win : allWins) {
    if (!win.equals(mainWin)) {
        driver.switchTo().window(win);
        System.out.println("Switched to new tab: " + driver.getTitle());
    }
}
driver.close(); // Close new tab
driver.switchTo().window(mainWin); // Back to original




# ✅ Selenium FluentWait Example (with Explanation)

FluentWait in Selenium is a flexible type of wait that lets you specify:
- Total timeout duration
- Polling interval
- Exceptions to ignore

It's useful when elements load dynamically and unpredictably.

---

## ✅ Code Snippet: FluentWait (Java)

```java
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.function.Function;

public class FluentWaitExample {
    WebDriver driver;

    public void useFluentWait() {
        // Assume driver is already initialized and navigated to target page.

        // Create FluentWait with custom timeout, polling, and ignored exceptions
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))           // Total wait time
                .pollingEvery(Duration.ofSeconds(2))           // Polling interval
                .ignoring(NoSuchElementException.class);       // Ignore if element not found

        // Apply FluentWait to locate the dynamic element
        WebElement dynamicElement = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id("dynamic"));   // Replace with your actual element locator
            }
        });

        // Now you can safely interact with the dynamic element
        dynamicElement.click();
    }
}

