package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

abstract public class ArticlePageObject extends MainPageObject{

    protected static String
        TITLE,
        FOOTER_ELEMENT,
        OPTIONS_BUTTON,
        OPTIONS_ADD_TO_READING_LIST,
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        READING_LIST_NAME_INPUT,
        READING_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        READING_LIST_TPL;

    public ArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    private static String getReadingList(String substring)
    {
        return READING_LIST_TPL.replace("{SUBSTRING}", substring);
    }

    @Step("Wait for article title")
    public WebElement waitForArticleTitle()
    {
        return this.waitForElementPresent(TITLE);
    }

    @Step("Get article title")
    public String getArticleTitle()
    {
        WebElement title_element = waitForArticleTitle();
        screenshot(this.takeScreenshot("article_title"));
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else {
            return title_element.getText();
        }
    }

    @Step("Swipe to footer")
    public void swipeToFooter()
    {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        } else {
            this.scrollWebPageTillElementNotVisible(FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40);
        }
    }

    @Step("Add article to reading list")
    public void addArticleToReadingList(String name_of_folder) throws InterruptedException {
        this.waitForElementAndClick(OPTIONS_ADD_TO_READING_LIST,
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

    @Step("Add article to already existed reading list")
    public void addArticleToAlreadyExistedReadingList(String name_of_folder) throws InterruptedException
    {
        if (Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(OPTIONS_ADD_TO_READING_LIST,
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
        else {
           addArticleToMySaved();
        }
    }

    @Step("Add article to my saved")
    public void addArticleToMySaved()
    {
        if (Platform.getInstance().isMW())
        {
            this.removeArticleFromSavedIfItAdded();
        }
        this.waitForElementPresent(OPTIONS_ADD_TO_READING_LIST);

        WebElement button = driver.findElement(getLocatorByString(OPTIONS_ADD_TO_READING_LIST));
        button.click();
    }

    @Step("Remove article from my saved")
    public void removeArticleFromSavedIfItAdded()
    {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON))
        {
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from saved",
                    Duration.ofSeconds(5)
            );
            // Check method name is it add to reading list or ass to my list
            this.waitForElementPresent(
                    OPTIONS_ADD_TO_READING_LIST,
                    "Cannot find button to add an article to saved list after removed it from this list before",
                    Duration.ofSeconds(5)
            );
        }
    }

    @Step("Close article")
    public void closeArticle()
    {
        if (Platform.getInstance().isAndroid())
        {
        this.waitForElementAndClick(CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link");
        }
        else
            System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());
    }
}
