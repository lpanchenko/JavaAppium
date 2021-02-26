package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
    static
    {
        SEARCH_INIT_ELEMENT = "id:searchInput";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_INPUT_DEFAULT_TEXT = "id:org.wikipedia:id/search_src_text";
        SEARCH_RESULT_ARTICLE = "css:ul.page-list>li.page-summary";
        SEARCH_RESULT_ARTICLE_TITLE_AND_DESCRIPTION_TPL = "xpath://*[@text='{TITLE}']/following-sibling::*[@text='{DESCRIPTION}']";
        SEARCH_RESULT_ARTICLE_DESCRIPTION_CONTAINS_TPL = "xpath://*[contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULT_ARTICLE_TITLE_CONTAINS_TPL = "xpath://div[contains(@class,'wikipedia-description')][contains(text(), '{SUBSTRING}')]";
        SEARCH_RESULT_NO_RESULTS_FOUND = "xpath://*[@text='No results found']";
        SEARCH_RESULT_EMPTY_CONTAINER = "css:p.without-results";
        SEARCH_CANCEL_BUTTON = "xpath://div[@class='header-action']//button[@type='button'][contains(text(),'Close')]";
    }

    public MWSearchPageObject(RemoteWebDriver driver) { super(driver); }
}
