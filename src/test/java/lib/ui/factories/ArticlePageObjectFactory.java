package lib.ui.factories;

import lib.ui.ArticlePageObject;
import lib.ui.android.AndroidArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ArticlePageObjectFactory
{
    public static ArticlePageObject get(RemoteWebDriver driver)
    {
        return new AndroidArticlePageObject(driver);
    }
}
