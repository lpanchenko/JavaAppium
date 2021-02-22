package tests;

import lib.CoreTestCase;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase
{
    @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput("Java");
        SearchPageObject.clickByArticleWithDescription("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForArticleTitle();

        String article_title = ArticlePageObject.getArticleTitle();

        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToReadingList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyList();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openReadingListByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testRemoveArticleFromSavedList() throws Exception {

        String firstArticle = "Persian cat";
        String secondArticle = "Turkish Angora";
        String name_of_folder = "Favorite breeds";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput(firstArticle);
        SearchPageObject.clickByArticleWithTitle(firstArticle);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForArticleTitle();
        ArticlePageObject.addArticleToReadingList(name_of_folder);
        ArticlePageObject.closeArticle();

        SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput(secondArticle);
        SearchPageObject.clickByArticleWithTitle(secondArticle);

        ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForArticleTitle();
        ArticlePageObject.addArticleToAlreadyExistedReadingList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyList();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openReadingListByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(firstArticle);
        MyListsPageObject.waitForArticleToDisappearByTitle(firstArticle);
        MyListsPageObject.openArticle(secondArticle);

        ArticlePageObject = ArticlePageObjectFactory.get(driver);
        assertEquals( "We see unexpected title",
                secondArticle,
                ArticlePageObject.getArticleTitle()
        );
    }
}
