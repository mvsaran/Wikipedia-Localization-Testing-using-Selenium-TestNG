package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;

public class WikipediaLocalizationTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        // 🧭 Launch Chrome
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().window().maximize();
        System.out.println("🚀 Browser launched successfully.\n");
    }

    // 🌍 DataProvider – list of locales, URLs, and expected titles
    @DataProvider(name = "wikipediaLocales")
    public Object[][] getLocales() {
        return new Object[][]{
                {"en", "https://en.wikipedia.org/wiki/Main_Page", "Wikipedia"},
                {"fr", "https://fr.wikipedia.org/wiki/Wikip%C3%A9dia:Accueil_principal", "Wikipédia"},
                {"es", "https://es.wikipedia.org/wiki/Wikipedia:Portada", "Wikipedia"},
                {"hi", "https://hi.wikipedia.org/wiki/मुखपृष्ठ", "विकिपीडिया"}
        };
    }

    @Test(dataProvider = "wikipediaLocales")
    public void testWikipediaLocalization(String locale, String url, String expectedTitle) {
        System.out.println("🌍 Visiting locale: " + locale.toUpperCase() + " → " + url);

        try {
            // Open Wikipedia locale URL
            driver.get(url);

            // Wait for <body> to be visible
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

            // Validate page title
            String actualTitle = driver.getTitle();
            System.out.println("🔎 Page title: " + actualTitle);

            Assert.assertTrue(actualTitle.contains(expectedTitle),
                    "❌ Title mismatch for " + locale + " | Expected: " + expectedTitle + " | Found: " + actualTitle);

            // Capture screenshot
            takeScreenshot(locale);

            System.out.println("✅ " + locale.toUpperCase() + " Wikipedia validated successfully.\n");

        } catch (Exception e) {
            System.err.println("⚠️ Test failed for " + locale + " due to: " + e.getMessage());
            takeScreenshot(locale + "-error");
            Assert.fail("Failed to validate locale: " + locale, e);
        }
    }

    // 📸 Screenshot helper
    private void takeScreenshot(String locale) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.createDir(new File("screenshots"));
            FileHandler.copy(screenshot, new File("screenshots/Wikipedia-" + locale + "-homepage.png"));
            System.out.println("📸 Screenshot saved for locale: " + locale);
        } catch (Exception e) {
            System.err.println("⚠️ Could not capture screenshot for " + locale + ": " + e.getMessage());
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("🧹 Browser closed. All tests completed.");
        }
    }
}
