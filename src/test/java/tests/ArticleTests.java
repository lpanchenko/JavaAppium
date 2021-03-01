package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for articles")
public class ArticleTests extends CoreTestCase
{
    @Test
    @Features(value = {@Feature(value="Search"), @Feature(value="Article")})
    @DisplayName("Compare article title with expected one")
    @Description("We open 'Java object-oriented programming language article and make sure the title is as expected'")
    @Step("Starting test test testCompareArticleTitle")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCompareArticleTitle()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput("Java");
        SearchPageObject.clickByArticleWithDescription("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    @DisplayName("Swipe article to the footer")
    @Description("We open an article and swipe it to the footer")
    @Step("Starting test test testSwipeArticle")
    @Severity(value = SeverityLevel.MINOR)
    public void testSwipeArticle()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput("Appium");
        SearchPageObject.clickByArticleWithTitle("Appium");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForArticleTitle();
        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testCheckArticleTitleExistence()
    {
        String article = "Turkish Angora";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput(article);

        SearchPageObject.clickByArticleWithTitle(article);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        Assert.assertNotNull("Article should not be null", ArticlePageObject.waitForArticleTitle());
    }
}
