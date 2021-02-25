package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MyListPageObjectFactory
{
    public static MyListsPageObject get(RemoteWebDriver driver)
    {
        return new MyListsPageObject(driver);
    }
}
