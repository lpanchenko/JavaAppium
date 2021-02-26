package lib.ui.factories;

import lib.ui.SearchPageObject;
import lib.ui.android.AndroidSearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SearchPageObjectFactory
{
    public static SearchPageObject get(RemoteWebDriver driver)
    {
        return new AndroidSearchPageObject(driver);
    }
}
