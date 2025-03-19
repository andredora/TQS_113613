package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class ExtendedBasePage {
    protected WebDriver driver;
    private WebDriverWait wait;

    public ExtendedBasePage(String browser) {
        this.driver = WebDriverFactory.getDriver(browser);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void visit(String url) {
        driver.get(url);
    }

    public void setTimeoutSec(int timeoutSec) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
    }

    public void waitForElementVisible(org.openqa.selenium.WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
