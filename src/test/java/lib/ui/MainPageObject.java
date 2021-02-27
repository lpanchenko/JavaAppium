package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected static String
            ELEMENT_CONTAINS_TEXT_TPL = "xpath://*[contains(@text, '{TEXT}')]";

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver)
    {
        Sleep();
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

    public int getAmountOfElements(String locator)
    {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public boolean isElementPresent(String locator)
    {
        return getAmountOfElements(locator) > 0;
    }

    public void tryClickElementWithFewAttempts(String locator, String error_message)
    {
        int current_attempts = 0;
        boolean need_more_attempts = true;

        while (need_more_attempts)
        {
            try {
                this.waitForElementAndClick(locator, error_message, Duration.ofSeconds(1));
                need_more_attempts = false;
            } catch (Exception e){
                if (current_attempts > 5)
                {
                    this.waitForElementAndClick(locator, error_message);
                    System.out.println("Try to Click on element with locator " + locator
                            + " with " + current_attempts + " attempts");
                }
            }
            current_attempts++;
        }
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
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver)driver);
            Dimension size = driver.manage().window().getSize();

            int x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);

            action.press(PointOption.point(x, start_y));
            action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)));
            action.moveTo(PointOption.point(x, end_y));
            action.release();
            action.perform();
        } else
            System.out.println("Method swipeUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
    }

    public void swipeUpQuick()
    {
        swipeUp(200);
    }

    public void scrollWebPageUp()
    {
        if (Platform.getInstance().isMW())
        {
            JavascriptExecutor JSExecutor = driver;
            JSExecutor.executeScript("window.scrollBy(0, 250)");
        } else
            System.out.println("Method scrollWebPageUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
    }

    public void scrollWebPageTillElementNotVisible(String locator, String error_message, int max_swiped)
    {
        int already_swiped = 0;

        WebElement element = this.waitForElementPresent(locator, error_message, Duration.ofSeconds(5));
        while (!this.isElementLocatedOnTheScreen(locator))
        {
            scrollWebPageUp();
            ++already_swiped;
            if (already_swiped > max_swiped)
            {
                Assert.assertTrue(error_message, element.isDisplayed());
            }
        }
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
        if (Platform.getInstance().isMW())
        {
            JavascriptExecutor JSExecutor = driver;
            Object js_result = JSExecutor.executeScript("return window.pageYOffset");
            element_location_by_y -= Integer.parseInt(js_result.toString());
        }
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        return element_location_by_y < screen_size_by_y;
    }

    public void swipeElementToLeft(String locator, String error_message)
    {
        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(
                    locator,
                    error_message,
                    Duration.ofSeconds(10));
            int left_x = element.getLocation().getX();
            int right_x = left_x + element.getSize().getWidth();

            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;

            TouchAction action = new TouchAction((AppiumDriver)driver);
            action.press(PointOption.point(right_x, middle_y));
            action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)));
            action.moveTo(PointOption.point(left_x, middle_y));
            action.release();
            action.perform();
        } else
            System.out.println("Method swipeElementToLeft() does nothing for platform " + Platform.getInstance().getPlatformVar());
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
        if (by_type.equals("css"))
        {
            return By.cssSelector(locator);
        }
        else
        {
            throw new IllegalArgumentException("Wrong type of locator. Locator: " + locator_with_type);
        }
    }

    public void Sleep()
    {
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {}
    }

}
