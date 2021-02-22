package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

abstract public class ArticlePageObject extends MainPageObject{

    protected static String
        TITLE,
        FOOTER_ELEMENT,
        OPTIONS_BUTTON,
        OPTIONS_ADD_TO_READING_LIST,
        ADD_TO_MY_LIST_OVERLAY,
        READING_LIST_NAME_INPUT,
        READING_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        READING_LIST_TPL;

    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getReadingList(String substring)
    {
        return READING_LIST_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATES METHODS */

    public WebElement waitForArticleTitle()
    {
        return this.waitForElementPresent(TITLE);
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitForArticleTitle();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter()
    {
        this.swipeUpToFindElement(
                FOOTER_ELEMENT,
                "Cannot find the end of article",
                20
        );
    }

    public void addArticleToReadingList(String name_of_folder) throws InterruptedException {
        this.waitForElementAndClick(OPTIONS_BUTTON,
                "Cannot find button to open article options",
                Duration.ofSeconds(10)
        );

        Thread.sleep(2000);

        this.waitForElementAndClick(OPTIONS_ADD_TO_READING_LIST,
                "Cannot find option to add article to reading list"
        );

        this.waitForElementAndClick(ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay"
        );

        this.waitForElementAndClear(READING_LIST_NAME_INPUT,
                "Cannot find input to set name of articles folder",
                Duration.ofSeconds(5));


        this.waitForElementAndSendKeys(READING_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                Duration.ofSeconds(5));

        this.waitForElementAndClick(READING_LIST_OK_BUTTON,
                "Cannot press OK button"
        );
    }

    public void addArticleToAlreadyExistedReadingList(String name_of_folder) throws InterruptedException
    {
        this.waitForElementAndClick(OPTIONS_BUTTON,
                "Cannot find button to open article options"
        );

        Thread.sleep(2000);

        this.waitForElementAndClick(OPTIONS_ADD_TO_READING_LIST,
                "Cannot find option to add article to reading list"
        );

        this.waitForElementAndClick(getReadingList(name_of_folder),
                "Cannot found folder " + name_of_folder + " in reading lists"
        );

        Thread.sleep(2000);
    }

    public void addArticleToMySaved()
    {
        this.waitForElementAndClick(OPTIONS_ADD_TO_READING_LIST,
                "Cannot find option to add article to reading list",
                Duration.ofSeconds(5));
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link"
        );
    }
}
