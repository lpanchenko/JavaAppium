package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class MyListsPageObject extends MainPageObject {

    protected static String
        FOLDER_BY_NAME_TPL,
        ARTICLE_BY_TITLE_TPL;

    private static String getFolderByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleByTitle(String name_of_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", name_of_title);
    }

    public MyListsPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    public void openReadingListByName(String name_of_folder)
    {
        String folder_name = getFolderByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name,
                "Cannot find folder by name " + name_of_folder
        );
    }

    public void swipeByArticleToDelete(String article_title)
    {
        String article = getSavedArticleByTitle(article_title);

        this.waitForArticleToAppearByTitle(article_title);
        this.swipeElementToLeft(
                article,
                "Cannot find saved article"
        );
        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void waitForArticleToDisappearByTitle(String article_title)
    {
        String article = getSavedArticleByTitle(article_title);
        this.waitForElementNotPresent(article,
                Duration.ofSeconds(15)
        );
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article = getSavedArticleByTitle(article_title);
        this.waitForElementPresent(article,
                "Cannot find saved article by title " + article_title,
                Duration.ofSeconds(15)
        );
    }

    public void openArticle(String title)
    {
        String article = getSavedArticleByTitle(title);
        this.waitForElementAndClick(article,
                "Cannot find saved article by title " + title
        );
    }
}
