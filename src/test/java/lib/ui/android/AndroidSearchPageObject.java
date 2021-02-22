package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {
    static
    {
        SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]";
        SEARCH_INPUT = "xpath://*[contains(@text, 'Search…')]";
        SEARCH_INPUT_DEFAULT_TEXT = "id:org.wikipedia:id/search_src_text";
        SEARCH_RESULT_ARTICLE = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        SEARCH_RESULT_ARTICLE_TITLE_AND_DESCRIPTION_TPL = "xpath://*[@text='{TITLE}']/following-sibling::*[@text='{DESCRIPTION}']";
        SEARCH_RESULT_ARTICLE_DESCRIPTION_TPL = "xpath://*[@text='{SUBSTRING}'][@resource-id='org.wikipedia:id/page_list_item_description']";
        SEARCH_RESULT_ARTICLE_DESCRIPTION_CONTAINS_TPL = "xpath://*[contains(@text, '{SUBSTRING}')][@resource-id='org.wikipedia:id/page_list_item_description']";
        SEARCH_RESULT_ARTICLE_TITLE_TPL = "xpath://*[@text='{SUBSTRING}'][@resource-id='org.wikipedia:id/page_list_item_title']";
        SEARCH_RESULT_ARTICLE_TITLE_CONTAINS_TPL = "xpath://*[contains(@text,'{SUBSTRING}')][@resource-id='org.wikipedia:id/page_list_item_title']";
        SEARCH_RESULT_NO_RESULTS_FOUND = "xpath://*[@text='No results found']";
        SEARCH_RESULT_EMPTY_CONTAINER = "id:org.wikipedia:id/search_empty_container";
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
    }

    public AndroidSearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
