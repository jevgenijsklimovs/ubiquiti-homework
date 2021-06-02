package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class BaseFunc {

    private WebDriver driver;
    private WebDriverWait wait;
    private Properties props;

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    public BaseFunc() {
        props = AppConfig.getProperties();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        System.setProperty("webdriver.chrome.driver", getProperty("webdriver.location"));

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        if (driver.manage().window().getSize().width <= 1200) {
            Dimension dimension = new Dimension(1920, 1080);
            LOGGER.info("Setting windows size to 1920x1080 for headless mode");
            driver.manage().window().setSize(dimension);
        }

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 12);
    }

    public String getProperty(String key) {
        LOGGER.info("Getting \"" + key + "\" property");
        return props.getProperty(key);
    }

    public void setProperty(String key, String value) {
        LOGGER.info("Setting property " + key + " to " + value);
        props.setProperty(key, value);
    }

    public void goToUrl(String url) {
        url = !url.contains(getProperty("url")) ? getProperty("url") + url : url;

        url = !url.startsWith("http://") ? "http://" + url : url;
        LOGGER.info("Going to URL: " + url);
        driver.get(url);
    }


    public WebElement getElement(By locator) {
        return wait.until(visibilityOfElementLocated(locator));
    }

    public void waitForElementDisappears(By locator) {
        wait.until(invisibilityOfElementLocated(locator));
    }

    public WebElement getChildElement(By parent, By child) {
        WebElement we = getElement(parent);

        assertFalse(we.findElements(child).isEmpty(), "There is no child elements in parent");
        return we.findElement(child);
    }

    public List<WebElement> getChildElements(By parent, By child) {
        return getElement(parent).findElements(child);
    }

    public void waitForText(By locator, String text) {
        wait.until(textToBePresentInElementLocated(locator, text));
    }

    public void waitForTextNotToBe(By locator, String text) {
        wait.until(invisibilityOfElementWithText(locator, text));
    }

    public boolean waitForStyle(By locator, String key, String value) {
        return wait.until((ExpectedCondition<Boolean>) driver -> getElement(locator).getCssValue(key).equals(value));
    }

    public void waitForElementsCount(By locator, int count) {
        wait.until((ExpectedCondition<Boolean>) driver -> getElements(locator).size() == count);
    }

    public void waitForElementsCountAtLeast(By locator, int minCount) {
        wait.until((ExpectedCondition<Boolean>) driver -> getElements(locator).size() >= minCount);
    }


    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    public List<WebElement> getElements(By parent, By child) {
        return getElement(parent).findElements(child);
    }

    public boolean isElementPresent(By locator) {
        return !getElements(locator).isEmpty();
    }

    public boolean isElementVisible(By locator) {
        try {
            wait.until(visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementVisible(By parent, By child) {
        try {
            wait.until(visibilityOf(getChildElement(parent, child)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementVisible(WebElement parent, By child) {
        try {
            wait.until(visibilityOf(parent.findElement(child)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementInvisible(By locator) {
        try {
            wait.until(invisibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void click(By locator) {
        try {
            wait.until(elementToBeClickable(locator)).click();
        } catch (ElementClickInterceptedException e) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click()", getElement(locator));
        }
    }

    public void click(WebElement element) {
        try {
            wait.until(visibilityOf(element));
            wait.until(elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException e) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click()", element);
        }
    }

    public void clickChild(By parent, By child) {
        click(getChildElement(parent, child));
    }

    public void closeBrowser() {
        assertNotNull(driver, "Can't close browser! There is no driver");
        LOGGER.info("Closing browser window");
        driver.quit();
    }

    public void selectByText(By locator, String text) {
        selectByText(getElement(locator), text);
    }

    public void selectByText(WebElement we, String text) {
        Select select = new Select(we);
        select.selectByVisibleText(text);
    }

    public void selectByText(WebElement we, int text) {
        selectByText(we, String.valueOf(text));
    }

    public void chooseAdminFile(By locator, String path) {
        driver.findElement(locator).sendKeys(path);
    }

    public void selectByValue(By locator, String value) {
        Select select = new Select(getElement(locator));
        select.selectByValue(value);
    }

    public void clearFieldFromKeyboard(By locator) {
        getElement(locator).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }

    public void type(By locator, String text) {
        getElement(locator).clear();
        getElement(locator).sendKeys(text);
    }

    public void type(By locator, Integer text) {
        type(locator, String.valueOf(text));
    }

    public void type(By locator, Double text) {
        type(locator, String.valueOf(text));
    }

    public void type(WebElement we, String text) {
        assertNotNull(we, "Web Elements is null and can't be used");
        we.clear();
        we.sendKeys(text);
    }

    public void switchToIframe(By locator) {
        WebElement element = getElement(locator);
        driver.switchTo().frame(element);
    }

    public void switchFromIframe() {
        driver.switchTo().defaultContent();
    }

    public void hoverOn(By locator) {
        Actions action = new Actions(driver);
        action.moveToElement(getElement(locator)).build().perform();
    }

    public void smartSelect(WebElement we, String text) {
        click(we);

        if (!text.isEmpty() && !we.findElements(tagName("input")).isEmpty()) {
            we.findElement(tagName("input")).sendKeys(text);
        }

        for (WebElement option : we.findElements(tagName("li"))) {
            if (option.getText().equals(text)) {
                click(option);
                break;
            }
        }
    }

    public void smartSelect(By locator, String text) {
        smartSelect(getElement(locator), text);
    }

    public void selectTab(int tab) {
        LOGGER.info("Switching to tab Nr. " + tab);
        wait.until(numberOfWindowsToBe(tab));

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tab - 1));
    }

    public void closeTab(int tab) {
        LOGGER.info("Closing tab Nr. " + tab);
        selectTab(tab);
        driver.close();
        selectTab(tab - 1);
    }

    public void moveTo(By locator, int moveToX, int moveToY) {
        LOGGER.info("Moving element to " + moveToX + " x " + moveToY);
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(getElement(locator), moveToX, moveToY).perform();
    }

    public void refresh() {
        LOGGER.info("Refreshing Page");
        driver.navigate().refresh();
    }
}
