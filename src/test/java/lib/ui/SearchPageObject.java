package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;
import java.util.List;

abstract public class SearchPageObject extends MainPageObject {

     protected static String
         SEARCH_INIT_ELEMENT,
         SEARCH_INPUT,
         SEARCH_INPUT_DEFAULT_TEXT,
         SEARCH_RESULT_ARTICLE,
         SEARCH_RESULT_ARTICLE_TITLE_AND_DESCRIPTION_TPL,
         SEARCH_RESULT_ARTICLE_DESCRIPTION_CONTAINS_TPL,
         SEARCH_RESULT_ARTICLE_TITLE_CONTAINS_TPL,
         SEARCH_RESULT_NO_RESULTS_FOUND,
         SEARCH_RESULT_EMPTY_CONTAINER,
         SEARCH_CANCEL_BUTTON;

    public SearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    private static String getArticleWithTitleAndDescription(String title, String description)
    {
        return SEARCH_RESULT_ARTICLE_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title)
                .replace("{DESCRIPTION}", description);
    }

    private static String getArticleWithTitleContainsText(String substring)
    {
        return SEARCH_RESULT_ARTICLE_TITLE_CONTAINS_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getArticleWithDescriptionContainsText(String substring)
    {
        return SEARCH_RESULT_ARTICLE_DESCRIPTION_CONTAINS_TPL.replace("{SUBSTRING}", substring);
    }

    @Step("Initializing the search field")
    public void initSearchInput()
    {
        // This method work unstable with MW, temporary change steps
        // At first code click to search text box and in case when search was not initialized click to search text box again
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "", Duration.ofSeconds(5));

        WebElement inputTextBox = driver.findElement(getLocatorByString(SEARCH_INIT_ELEMENT));
        inputTextBox.click();

        if (driver.findElements(getLocatorByString(SEARCH_INPUT)).size() == 0)
        {
            inputTextBox = driver.findElement(getLocatorByString(SEARCH_INIT_ELEMENT));
            inputTextBox.click();
        }
    }

    @Step("Wait to cancel button to appear")
    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON,
                "Cannot find search Cancel button",
                Duration.ofSeconds(5));
    }

    @Step("Wait to cancel button to disappear")
    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON,
                Duration.ofSeconds(5));
    }

    @Step("Click on the cancel button")
    public void clickCancelButton()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button");
    }

    @Step("Enter '{search_line}' to the search input")
    // This method works unstable with MW, temporary change wait with condition to find element method
    public void enterDataToSearchInput(String search_line)
    {
        this.waitForElementPresent(SEARCH_INPUT, "Cannot find and type into search input", Duration.ofSeconds(5));
        WebElement w = driver.findElement(getLocatorByString(SEARCH_INPUT));
        w.sendKeys(search_line);
    }

    @Step("Wait for the article contains description")
    public void waitForArticleContainsDescription(String substring)
    {
        String search_result = getArticleWithDescriptionContainsText(substring);
        this.waitForElementPresent(search_result,
                "Cannot find search result with substring " + substring,
                Duration.ofSeconds(5));
    }

    @Step("Click by article with description")
    public void clickByArticleWithDescription(String substring)
    {
        String search_result = getArticleWithDescriptionContainsText(substring);
        this.waitForElementAndClick(search_result,
                "Cannot find and click search result with substring" + substring);
    }

    @Step("Click by article with title")
    public void clickByArticleWithTitle(String substring)
    {
        String search_result = getArticleWithTitleContainsText(substring);
        this.waitForElementAndClick(search_result,
                "Cannot find and click search result with substring" + substring);
    }

    @Step("Get amount of articles")
    public int getAmountOfArticles()
    {
        this.waitForElementPresent(SEARCH_RESULT_ARTICLE,
                "Cannot find anything by request ",
                Duration.ofSeconds(15)
        );

        return this.getAmountOfElements(SEARCH_RESULT_ARTICLE);
    }

    @Step("Get amount of elements")
    public int getAmountOfElements(String locator)
    {
        By by = getLocatorByString(locator);

        List elements = driver.findElements(by);
        return elements.size();
    }

    @Step("Wait for empty results label")
    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(SEARCH_RESULT_NO_RESULTS_FOUND,
                "Cannot find empty result element",
                Duration.ofSeconds(15)
        );
    }

    @Step("Wait for empty results label")
    public void waitForResultIsEmpty()
    {
        waitForElementPresent(SEARCH_RESULT_EMPTY_CONTAINER,
                "Search result should be empty",
                Duration.ofSeconds(15));
    }

    @Step("Assert there is no results of search")
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

    public boolean eachElementContainsText(String text)
    {
        String search_result = getArticleWithTitleContainsText(text);
        waitForElementPresent(search_result);

        // Receive all elements in search result
        By by = getLocatorByString(search_result);
        List<WebElement> list = driver.findElements(by);

        // Check if each element contains text
        for (WebElement w: list) {
            if (w.findElement(By.xpath("//*[contains(text(), Java)]")) == null)
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