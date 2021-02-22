package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;
import lib.ui.android.AndroidSearchPageObject;

public class SearchPageObjectFactory
{
    public static SearchPageObject get(AppiumDriver driver)
    {
        return new AndroidSearchPageObject(driver);
    }
}
