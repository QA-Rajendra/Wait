# Selenium Waits, Alerts & Window Handling

Automation testers must understand how to handle **waits**, **alerts**, and **multiple windows/tabs** using Selenium WebDriver. This guide explains these topics in depth with real-world Java examples and interview questions.

---

## 🔷 Waits in Selenium

### ✅ Implicit Wait
Applies to all elements globally.
```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```

---

### ✅ Explicit Wait
Used for a specific condition on a specific element.
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit")));
```

---

### ✅ ExpectedConditions with Wait
```java
wait.until(ExpectedConditions.elementToBeClickable(By.id("loginBtn")));
wait.until(ExpectedConditions.titleContains("Dashboard"));
```

---

### ✅ PageLoadTimeout & ScriptTimeout
```java
driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));
```

---

### ✅ Thread.sleep() (Static Wait)
```java
Thread.sleep(3000);  // Not recommended for real-time use
```

---

### ✅ FluentWait (Custom Polling)
```java
Wait<WebDriver> fluentWait = new FluentWait<>(driver)
    .withTimeout(Duration.ofSeconds(20))
    .pollingEvery(Duration.ofSeconds(2))
    .ignoring(NoSuchElementException.class);

WebElement element = fluentWait.until(d -> d.findElement(By.id("email")));
```

---

### ✅ Polling Strategy (FluentWait)
```java
fluentWait.until(driver -> {
    WebElement e = driver.findElement(By.id("status"));
    return e.isDisplayed() ? e : null;
});
```

---

### ✅ WebDriverWait and Uses
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.presenceOfElementLocated(By.id("element")));
```

---

### ✅ Common WaitUntil Conditions
```java
ExpectedConditions.alertIsPresent()
ExpectedConditions.elementToBeClickable(By.id("submit"))
ExpectedConditions.invisibilityOfElementLocated(By.id("loader"))
```

---

### ✅ AJAX-Based Components Handling
```java
wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ajax-loader")));
```

---

## 🔷 Handling Alerts

### ✅ Simple Alert
```java
Alert alert = driver.switchTo().alert();
alert.accept();
```

---

### ✅ Confirmation Alert
```java
Alert alert = driver.switchTo().alert();
alert.dismiss(); // Cancel
```

---

### ✅ Prompt Alert
```java
Alert alert = driver.switchTo().alert();
alert.sendKeys("Test Input");
alert.accept();
```

---

## 🔷 Window & Tab Handling

### ✅ Set Interface for Window Handles
```java
Set<String> handles = driver.getWindowHandles();
```

---

### ✅ windowHandle vs windowHandles

| Method               | Return Type | Description                     |
|----------------------|-------------|---------------------------------|
| getWindowHandle()    | String      | Current window/tab ID           |
| getWindowHandles()   | Set<String> | All window/tab IDs              |

---

### ✅ Switching & Closing Windows/Tabs
```java
String mainWindow = driver.getWindowHandle();

for (String handle : driver.getWindowHandles()) {
    if (!handle.equals(mainWindow)) {
        driver.switchTo().window(handle);
    }
}

driver.close(); // close child
driver.switchTo().window(mainWindow); // back to parent
```

---

### ✅ Window ID Concept
```java
String windowId = driver.getWindowHandle();
System.out.println("Window ID: " + windowId);
```

---

### ✅ Extracting Window IDs with Reference
```java
Set<String> windows = driver.getWindowHandles();
List<String> winList = new ArrayList<>(windows);
driver.switchTo().window(winList.get(1)); // switch to second tab
```

---

## 🔷 Interview Questions & Answers

### ❓ What is the difference between Implicit and Explicit wait?
**Ans:**  
- Implicit: Wait applies to all elements globally.  
- Explicit: Waits for a specific condition on a specific element.

---

### ❓ What is FluentWait and when do you use it?
**Ans:**  
It allows custom timeout, polling frequency, and ignored exceptions. Useful for dynamic AJAX-heavy elements.

---

### ❓ How do you handle a confirmation alert?
```java
Alert alert = driver.switchTo().alert();
alert.dismiss(); // Cancel
```

---

### ❓ How to handle dynamic AJAX elements?
**Ans:**
```java
wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ajaxLoader")));
```

---

### ❓ Difference between getWindowHandle() and getWindowHandles()?
| Method              | Description                              |
|---------------------|------------------------------------------|
| getWindowHandle()   | Returns ID of current window/tab         |
| getWindowHandles()  | Returns IDs of all open windows/tabs     |

---

### ❓ How to switch between tabs?
**Ans:**
```java
Set<String> handles = driver.getWindowHandles();
for (String handle : handles) {
    if (!handle.equals(driver.getWindowHandle())) {
        driver.switchTo().window(handle);
    }
}
```

---

### ❓ Why use Set for window handles?
**Ans:**  
Set<String> ensures no duplicate window IDs, making it ideal for tracking open browser instances.

---
