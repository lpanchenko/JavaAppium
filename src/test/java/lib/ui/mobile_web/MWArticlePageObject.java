package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_BUTTON = "//a[@id='ca-watch'][not(contains(@class, 'watched'))]";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "xpath://a[@id='ca-watch'][contains(@class, 'watched')]";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
