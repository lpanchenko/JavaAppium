package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;
import lib.ui.android.AndroidNavigationUI;

public class NavigationUIFactory
{
    public static NavigationUI get(AppiumDriver driver)
    {
        return new AndroidNavigationUI(driver);
    }
}