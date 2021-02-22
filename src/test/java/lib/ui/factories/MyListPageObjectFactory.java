package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class MyListPageObjectFactory
{
    public static MyListsPageObject get(AppiumDriver driver)
    {
        return new MyListsPageObject(driver);
    }
}
