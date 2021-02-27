package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
    static
    {
        SEARCH_INIT_ELEMENT = "id:searchInput";
        SEARCH_INPUT = "xpath://*[@class='search'][@type='search']";
        SEARCH_INPUT_DEFAULT_TEXT = "id:org.wikipedia:id/search_src_text";
        SEARCH_RESULT_ARTICLE = "css:ul.page-list>li.page-summary";
        SEARCH_RESULT_ARTICLE_TITLE_AND_DESCRIPTION_TPL = "xpath://*[contains(@data-title,'{TITLE}')]/following::*[contains(text(),'{DESCRIPTION}')]";
        SEARCH_RESULT_ARTICLE_DESCRIPTION_CONTAINS_TPL = "xpath://*[@class='wikidata-description'][contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULT_ARTICLE_TITLE_CONTAINS_TPL = "xpath://*[contains(@class, 'page-summary')][contains(@title,'{SUBSTRING}')]";
        SEARCH_RESULT_NO_RESULTS_FOUND = "xpath://*[contains(text(),'No page with this title.')]";
        SEARCH_RESULT_EMPTY_CONTAINER = "css:p.without-results";
        SEARCH_CANCEL_BUTTON = "xpath://div[@class='header-action']//button[@type='button'][contains(text(),'Close')]";
    }

    public MWSearchPageObject(RemoteWebDriver driver) { super(driver); }
}
