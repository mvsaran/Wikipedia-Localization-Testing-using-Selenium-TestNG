# 🌐 Wikipedia Localization Testing using Selenium Java 🧪

## 🧭 Overview

This project automates **localization testing** for Wikipedia in different languages using **Selenium WebDriver** and **TestNG**.  
It verifies that Wikipedia loads correctly in multiple locales (English, French, Spanish, Hindi) and ensures that the page title matches the expected localized text.

---

## ⚙️ Technologies Used

| Component | Description |
|------------|--------------|
| ☕ **Java** | Programming language for test implementation |
| 🧪 **TestNG** | Testing framework for parameterized and structured test execution |
| 🌍 **Selenium WebDriver** | Automates browser interactions |
| 🖼️ **ChromeDriver** | WebDriver implementation for Google Chrome |
| 💾 **FileHandler** | Saves screenshots for each locale validation |

---

## 🧩 Project Structure

```
WikipediaLocalizationTest/
├── src/
│   └── test/
│       └── java/
│           └── tests/
│               └── WikipediaLocalizationTest.java
├── screenshots/  ← Captures screenshots for each locale
└── pom.xml       ← Maven dependencies (Selenium, TestNG)
```

---

## 🚀 Test Workflow

1. **Browser Launch**  
   Chrome browser is launched and maximized before test execution.

2. **Locale Data Provider**  
   A TestNG `@DataProvider` supplies multiple Wikipedia locales with their URLs and expected titles.

3. **Visit Locale & Validation**  
   For each locale:
   - Open the Wikipedia URL (e.g., `https://fr.wikipedia.org/wiki/Wikip%C3%A9dia:Accueil_principal`).
   - Wait until the body is visible.
   - Validate the page title against the expected localized string.
   - Capture a screenshot of the localized homepage.

4. **Result Logging**  
   The console logs the validation results with status emojis for easy readability.

5. **Teardown**  
   Closes the browser after all tests are completed.

---

## 🧠 Code Explanation

### 🧩 Setup Section (`@BeforeClass`)
Initializes the Chrome browser and sets a timeout for page loading.
```java
driver = new ChromeDriver();
driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
driver.manage().window().maximize();
```

### 🌍 Data Provider
Defines an array of Wikipedia locales with their respective URLs and expected titles.
```java
@DataProvider(name = "wikipediaLocales")
public Object[][] getLocales() {
    return new Object[][]{
        {"en", "https://en.wikipedia.org/wiki/Main_Page", "Wikipedia"},
        {"fr", "https://fr.wikipedia.org/wiki/Wikip%C3%A9dia:Accueil_principal", "Wikipédia"},
        {"es", "https://es.wikipedia.org/wiki/Wikipedia:Portada", "Wikipedia"},
        {"hi", "https://hi.wikipedia.org/wiki/मुखपृष्ठ", "विकिपीडिया"}
    };
}
```

### ✅ Test Execution
Navigates to each locale, validates the title, and captures a screenshot.
```java
driver.get(url);
new WebDriverWait(driver, Duration.ofSeconds(15))
    .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
Assert.assertTrue(actualTitle.contains(expectedTitle));
takeScreenshot(locale);
```

### 📸 Screenshot Utility
Saves the localized page screenshot in the `screenshots` folder.
```java
File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
FileHandler.copy(screenshot, new File("screenshots/Wikipedia-" + locale + "-homepage.png"));
```

### 🧹 Teardown
Ensures browser closure after all test cases.
```java
driver.quit();
```

---

## 🏁 Sample Output

```
🌍 Visiting locale: en → https://en.wikipedia.org/wiki/Main_Page
✅ EN Wikipedia validated successfully.
🌍 Visiting locale: fr → https://fr.wikipedia.org/wiki/Wikip%C3%A9dia:Accueil_principal
✅ FR Wikipedia validated successfully.
🌍 Visiting locale: es → https://es.wikipedia.org/wiki/Wikipedia:Portada
✅ ES Wikipedia validated successfully.
🌍 Visiting locale: hi → https://hi.wikipedia.org/wiki/मुखपृष्ठ
✅ HI Wikipedia validated successfully.
🧹 Browser closed. All tests completed.
```

---

## 📸 Screenshot Samples

Screenshots are saved in the `screenshots` folder as:

```
screenshots/
├── Wikipedia-en-homepage.png
├── Wikipedia-fr-homepage.png
├── Wikipedia-es-homepage.png
└── Wikipedia-hi-homepage.png
```

---

## 🏆 Benefits of Localization Testing

| Feature | Description |
|----------|--------------|
| 🌐 Multi-language Coverage | Ensures localized versions of Wikipedia render correctly |
| 🧪 Automation | Reusable test setup for different locales |
| 📸 Visual Proof | Screenshots provide visual validation |
| 🚀 Scalability | Easily extendable for new locales or other global apps |

---

## 👨‍💻 Author

**Saran Kumar** ✨  
*Automation SDET | AI-Augmented Testing Enthusiast*  
