package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase
{
    @Features(value = {@Feature(value="ScreenRotation"), @Feature(value="Article")})
    @DisplayName("Check article title after rotation")
    @Description("Open article 'Java' and check that title shows correctly after rotation")
    @Step("Starting testChangeScreenOrientationOnSearchResult")
    @Severity(value = SeverityLevel.CRITICAL)
    @Test
    public void testChangeScreenOrientationOnSearchResult()
    {
        if (Platform.getInstance().isMW()) return;

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput("Java");
        SearchPageObject.clickByArticleWithDescription("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String title_before_rotation = ArticlePageObject.getArticleTitle();
        this.rotateScreenLANDSCAPE();
        String title_after_rotation = ArticlePageObject.getArticleTitle();
        this.rotateScreenPortrait();

        Assert.assertEquals("Article title has been changed after rotation",
                title_before_rotation,
                title_after_rotation);

        this.rotateScreenPortrait();
        String title_after_second_rotation = ArticlePageObject.getArticleTitle();

        Assert.assertEquals("Article title has been changed after rotation",
                title_before_rotation,
                title_after_second_rotation);
    }

    @Features(value = {@Feature(value="Background"), @Feature(value="Article")})
    @DisplayName("Check article title after returned from Background mode")
    @Description("Open article 'Java' and check that title shows correctly after returned from a Background mode")
    @Step("Starting testCheckSearchArticleInBackground")
    @Severity(value = SeverityLevel.CRITICAL)
    @Test
    public void testCheckSearchArticleInBackground()
    {
        if (Platform.getInstance().isMW()) return;

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput("Java");
        SearchPageObject.waitForArticleContainsDescription("Object-oriented programming language");
        this.backgroundApp(2);
        SearchPageObject.waitForArticleContainsDescription("Object-oriented programming language");
    }
}
