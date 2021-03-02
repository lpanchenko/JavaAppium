package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class SearchTests extends CoreTestCase
{
    @Features(value = {@Feature(value="Search"), @Feature(value="Article")})
    @DisplayName("Search for an Article")
    @Description("Enter article name to a search field, check that article with description was found")
    @Step("Starting testSearch")
    @Severity(value = SeverityLevel.CRITICAL)
    @Test
    public void testSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput("Java");
        SearchPageObject.waitForArticleContainsDescription("Object-oriented programming language");
    }

    @Features(value = {@Feature(value="Search"), @Feature(value="Article")})
    @DisplayName("Cancel search")
    @Description("Init search, press cancel button and check that it disappeared")
    @Step("Starting testCancelSearch")
    @Severity(value = SeverityLevel.NORMAL)
    @Test
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelButton();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Features(value = {@Feature(value="Search"), @Feature(value="Article")})
    @DisplayName("Search by valid input")
    @Description("Init search, check that search results are not empty")
    @Step("Starting testAmountOfNotEmptySearch")
    @Severity(value = SeverityLevel.CRITICAL)
    @Test
    public void testAmountOfNotEmptySearch()
    {
        String search_line = "Linkin Park Discography";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfArticles();

        Assert.assertTrue(
                "We found a few results instead of one",
                amount_of_search_results > 0
        );
    }

    @Features(value = {@Feature(value="Search"), @Feature(value="Article")})
    @DisplayName("Search by invalid input")
    @Description("Init search, check that search results are empty")
    @Step("Starting testAmountOfEmptySearch")
    @Severity(value = SeverityLevel.CRITICAL)
    @Test
    public void testAmountOfEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "fsdfsdfsdfsd";
        SearchPageObject.enterDataToSearchInput(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Features(value = {@Feature(value="Search"), @Feature(value="Article")})
    @DisplayName("Default value in search input")
    @Description("Init search, a default value in search box corresponds expected")
    @Step("Starting testCompareSearchInputText")
    @Severity(value = SeverityLevel.NORMAL)
    @Test
    public void testCompareSearchInputText()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.assertDefaultValueInSearchInput();
    }

    @Features(value = {@Feature(value="Search"), @Feature(value="Article")})
    @DisplayName("Cancel search")
    @Description("Init search, cancel search and check that test results are empty")
    @Step("Starting testCompareSearchResult")
    @Severity(value = SeverityLevel.NORMAL)
    @Test
    public void testCompareSearchResult() {
        String inputData = "Java";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput(inputData);

        int amount_of_search_results = SearchPageObject.getAmountOfArticles();
        Assert.assertTrue(
                "No data found by input value " + inputData,
                amount_of_search_results > 0
        );

        SearchPageObject.clickCancelButton();

        SearchPageObject.waitForResultIsEmpty();
    }

    @Features(value = {@Feature(value="Search"), @Feature(value="Article")})
    @DisplayName("All test result has input value")
    @Description("Init search, check that each test result has this value")
    @Step("Starting testCheckResultsContainSearchData")
    @Severity(value = SeverityLevel.NORMAL)
    @Test
    public void testCheckResultsContainSearchData() {
        String inputData = "Java";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput(inputData);

        Assert.assertTrue(
                "No data found by input value " + inputData,
                SearchPageObject.eachElementContainsText(inputData)
        );
    }

    @Features(value = {@Feature(value="Search"), @Feature(value="Article")})
    @DisplayName("All test result has correct article and description")
    @Description("Init search, check that each result has correct article and description")
    @Step("Starting testCompareArticlesTitleAndDescription")
    @Severity(value = SeverityLevel.NORMAL)
    @Test
    public void testCompareArticlesTitleAndDescription() {
        String inputData = "Java";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput(inputData);

        Assert.assertNotNull("",
                SearchPageObject.getElementByTitleAndDescription("Java","island")
        );

        Assert.assertNotNull("",
                SearchPageObject.getElementByTitleAndDescription("JavaScript","rogramming language")
        );

        Assert.assertNotNull("",
                SearchPageObject.getElementByTitleAndDescription("Java (programming language)","rogramming language")
        );
    }
}
