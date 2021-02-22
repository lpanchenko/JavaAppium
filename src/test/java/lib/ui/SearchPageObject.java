package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

abstract public class SearchPageObject extends MainPageObject {

     protected static String
         SEARCH_INIT_ELEMENT,
         SEARCH_INPUT,
         SEARCH_INPUT_DEFAULT_TEXT,
         SEARCH_RESULT_ARTICLE,
         SEARCH_RESULT_ARTICLE_TITLE_AND_DESCRIPTION_TPL,
         SEARCH_RESULT_ARTICLE_DESCRIPTION_TPL,
         SEARCH_RESULT_ARTICLE_DESCRIPTION_CONTAINS_TPL,
         SEARCH_RESULT_ARTICLE_TITLE_TPL,
         SEARCH_RESULT_ARTICLE_TITLE_CONTAINS_TPL,
         SEARCH_RESULT_NO_RESULTS_FOUND,
         SEARCH_RESULT_EMPTY_CONTAINER,
         SEARCH_CANCEL_BUTTON;

    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getArticleWithTitleAndDescription(String title, String description)
    {
        return SEARCH_RESULT_ARTICLE_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title)
                .replace("{DESCRIPTION}", description);
    }

    private static String getArticleWithTitle(String substring)
    {
        return SEARCH_RESULT_ARTICLE_TITLE_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getArticleWithTitleContainsText(String substring)
    {
        return SEARCH_RESULT_ARTICLE_TITLE_CONTAINS_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getArticleWithDescription(String substring)
    {
        return SEARCH_RESULT_ARTICLE_DESCRIPTION_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getArticleWithDescriptionContainsText(String substring)
    {
        return SEARCH_RESULT_ARTICLE_DESCRIPTION_CONTAINS_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATES METHODS */

    public void initSearchInput()
    {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element");

        this.waitForElementPresent(SEARCH_INIT_ELEMENT,
                "Cannot find search input after clicking on search init element",
                Duration.ofSeconds(10));
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON,
                "Cannot find search Cancel button",
                Duration.ofSeconds(5));
    }

    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON,
                Duration.ofSeconds(5));
    }

    public void clickCancelButton()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button");
    }

    public void enterDataToSearchInput(String search_line)
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT,
                search_line,
                "Cannot find and type into search input",
                Duration.ofSeconds(5));
    }

    public void waitForArticleWithDescription(String description)
    {
        String search_result = getArticleWithDescription(description);
        this.waitForElementPresent(search_result,
                "Cannot find search result with substring " + description,
                Duration.ofSeconds(5));
    }

    public void waitForArticleContainsDescription(String substring)
    {
        String search_result = getArticleWithDescriptionContainsText(substring);
        this.waitForElementPresent(search_result,
                "Cannot find search result with substring " + substring,
                Duration.ofSeconds(5));
    }

    public void clickByArticleWithDescription(String substring)
    {
        String search_result = getArticleWithDescription(substring);
        this.waitForElementAndClick(search_result,
                "Cannot find and click search result with substring" + substring);
    }

    public void clickByArticleWithTitle(String substring)
    {
        String search_result = getArticleWithTitle(substring);
        this.waitForElementAndClick(search_result,
                "Cannot find and click search result with substring" + substring);
    }

    public int getAmountOfArticles()
    {
        this.waitForElementPresent(SEARCH_RESULT_ARTICLE,
                "Cannot find anything by request ",
                Duration.ofSeconds(15)
        );

        return this.getAmountOfElements(SEARCH_RESULT_ARTICLE);
    }

    public int getAmountOfElements(String locator)
    {
        By by = getLocatorByString(locator);

        List elements = driver.findElements(by);
        return elements.size();
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(SEARCH_RESULT_NO_RESULTS_FOUND,
                "Cannot find empty result element",
                Duration.ofSeconds(15)
        );
    }

    public void waitForResultIsEmpty()
    {
        waitForElementPresent(SEARCH_RESULT_EMPTY_CONTAINER,
                "Search result should be empty",
                Duration.ofSeconds(15));
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ARTICLE,
                "We supposed not to find any results");
    }

    public void assertDefaultValueInSearchInput()
    {
        this.assertElementText(
                SEARCH_INPUT_DEFAULT_TEXT,
                "Search…",
                "Search Input contains unexpected text"
        );
    }

    public boolean eachElementContainsText(String text) throws InterruptedException
    {
        String search_result = getArticleWithTitleContainsText(text);
        waitForElementPresent(search_result);

        // Receive all elements in search result
        By by = getLocatorByString(search_result);
        List<WebElement> list = driver.findElements(by);

        // Check if each element contains text
        for (WebElement w: list) {
            if (!w.getAttribute("text").contains(text))
                return false;
        }
        return true;
    }

    public WebElement getElementByTitleAndDescription(String title, String description)
    {
        String search_result = getArticleWithTitleAndDescription(title, description);
        return waitForElementPresent(search_result,
                "Can not found article with title " + title + " and description " + description,
                Duration.ofSeconds(10)
        );
    }
}