package lib.ui.factories;

import lib.ui.NavigationUI;
import lib.ui.android.AndroidNavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NavigationUIFactory
{
    public static NavigationUI get(RemoteWebDriver driver)
    {
        return new AndroidNavigationUI(driver);
    }
}