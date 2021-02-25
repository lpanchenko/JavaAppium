package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject{

    protected static String
        MY_LISTS_LINK;

    public NavigationUI(RemoteWebDriver driver)
    {
        super(driver);
    }

    public void clickMyList()
    {
        this.waitForElementAndClick(MY_LISTS_LINK,
                "Cannot found navigation button to My list"
        );
    }
}
