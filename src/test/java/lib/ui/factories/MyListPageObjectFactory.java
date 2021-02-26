package lib.ui.factories;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MyListPageObjectFactory
{
    public static MyListsPageObject get(RemoteWebDriver driver)
    {
        return new MyListsPageObject(driver);
    }
}
