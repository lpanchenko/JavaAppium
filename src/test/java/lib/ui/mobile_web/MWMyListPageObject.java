package lib.ui.mobile_web;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListPageObject extends MyListsPageObject {
    static {
        ARTICLE_BY_TITLE_TPL = "xpath://h3[contains(text(),'{TITLE}')]";
        REMOVE_FROM_SAVED_BUTTON = "xpath://a[contains(@title, 'Remove this page from your watchlist')]";
        UNWATCH_TPL = "xpath://li[contains(@title,'{TITLE}')]//a[contains(@class,'watch-this-article watched')]";
        WATCH_TPL = "xpath://li[contains(@title,'{TITLE}')]//a[contains(@class, 'watch-this-article')][@title='Add this page to your watchlist']";
    }

    public MWMyListPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
