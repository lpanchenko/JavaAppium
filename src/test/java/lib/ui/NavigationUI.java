package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject{

    protected static String
        MY_LISTS_LINK,
        OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver)
    {
        super(driver);
    }

    public void openNavigation()
    {
        if (Platform.getInstance().isMW())
        {
            this.waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigation button");
        }
        else
            System.out.println("Method openNavigation() does nothing for platform " + Platform.getInstance().getPlatformVar());

        this.Sleep();
    }

    public void clickMyList()
    {
        this.tryClickElementWithFewAttempts(MY_LISTS_LINK,"Cannot found navigation button to My list");
    }
}