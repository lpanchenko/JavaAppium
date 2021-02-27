package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "id:section_0";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_ADD_TO_READING_LIST = "xpath://a[@id='ca-watch'][not(contains(@class, 'watched'))]";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "xpath://a[@id='ca-watch'][contains(@class, 'watched')]";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
