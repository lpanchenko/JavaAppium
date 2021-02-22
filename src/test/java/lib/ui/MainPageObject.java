package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Pattern;

public class MainPageObject {

    protected static String
            ELEMENT_CONTAINS_TEXT_TPL = "xpath://*[contains(@text, '{TEXT}')]";

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver)
    {
        this.driver = driver;
    }

    /* TEMPLATES METHODS */
    protected static String getElementContainsText(String text)
    {
        return ELEMENT_CONTAINS_TEXT_TPL.replace("{TEXT}", text);
    }
    /* TEMPLATES METHODS */

    public WebElement waitForElementPresent(String locator, String error_msg, Duration seconds)
    {
        By by = this.getLocatorByString(locator);

        WebDriverWait wait = new WebDriverWait(driver, seconds.getSeconds());
        wait.withMessage(error_msg);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(String locator)
    {
        String error_msg = "Web element with locator " + locator + " is not found";
        return waitForElementPresent(locator, error_msg, Duration.ofSeconds(5));
    }

    public boolean waitForElementNotPresent(String locator, Duration seconds)
    {
        By by = this.getLocatorByString(locator);

        WebDriverWait wait = new WebDriverWait(driver, seconds.getSeconds());
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void assertElementNotPresent(String locator, String error_msg)
    {
        if (!waitForElementNotPresent(locator, Duration.ofSeconds(10)))
        {
            String default_msg = "An element " + locator + " supposed to be not present";
            throw new AssertionError(default_msg + " " + error_msg);
        }
    }

    public WebElement waitForElementAndClick(String locator, String error_message, Duration seconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, seconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndClick(String locator, String error_message)
    {
        return waitForElementAndClick(locator, error_message, Duration.ofSeconds(10));
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, Duration seconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, seconds);
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementAndClear(String locator, String error_message, Duration seconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, seconds);
        element.clear();
        return element;
    }

    public void assertElementText(String locator, String expected_text, String error_message)
    {
        WebElement element = waitForElementPresent(locator);
        String actual_text = element.getAttribute("text");
        Assert.assertEquals(
                error_message,
                expected_text,
                actual_text);
    }

    public void swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int x = size.width / 2;
        int start_y = (int)(size.height * 0.8);
        int end_y = (int)(size.height * 0.2);

        action.press(PointOption.point(x, start_y));
        action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)));
        action.moveTo(PointOption.point(x, end_y));
        action.release();
        action.perform();
    }

    public void swipeUpQuick()
    {
        swipeUp(200);
    }

    public void swipeUpToFindElement(String locator, String error_message, int max_swipes)
    {
        int already_swiped = 0;

        By by = this.getLocatorByString(locator);
        while (driver.findElements(by).size() == 0){
            if (already_swiped > max_swipes)
            {
                waitForElementPresent(locator,
                        "Cannot find element by swiping up. \n" + error_message,
                        Duration.ofSeconds(0));
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public void swipeUpTillElementAppear(String locator, String error_message, int max_swipes)
    {
        int already_swiped = 0;

        while (!this.isElementLocatedOnTheScreen(locator)){
            if (already_swiped > max_swipes)
            {
                Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public boolean isElementLocatedOnTheScreen(String locator)
    {
        int element_location_by_y = this.waitForElementPresent(locator,
                "Cannot find element by locator",
                Duration.ofSeconds(1))
                .getLocation().getY();

        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        return element_location_by_y < screen_size_by_y;
    }

    public void swipeElementToLeft(String locator, String error_message)
    {
        WebElement element = waitForElementPresent(
                locator,
                error_message,
                Duration.ofSeconds(10));
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();

        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y)/2;

        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(right_x, middle_y));
        action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)));
        action.moveTo(PointOption.point(left_x, middle_y));
        action.release();
        action.perform();
    }

    public By getLocatorByString(String locator_with_type)
    {
        String [] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if (by_type.equals("xpath"))
        {
            return By.xpath(locator);
        }
        if (by_type.equals("id"))
        {
            return By.id(locator);
        }
        else
        {
            throw new IllegalArgumentException("Wrong type of locator. Locator: " + locator_with_type);
        }
    }
}
