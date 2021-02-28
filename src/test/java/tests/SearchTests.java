package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class SearchTests extends CoreTestCase
{
    @Test
    public void testSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput("Java");
        SearchPageObject.waitForArticleContainsDescription("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelButton();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

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

    @Test
    public void testCompareSearchInputText()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.assertDefaultValueInSearchInput();
    }

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

    @Test
    public void testCheckResultsContainSearchData() throws InterruptedException {
        String inputData = "Java";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput(inputData);

        Assert.assertTrue(
                "No data found by input value " + inputData,
                SearchPageObject.eachElementContainsText(inputData)
        );
    }

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
