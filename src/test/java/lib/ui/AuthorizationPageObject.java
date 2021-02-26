package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class AuthorizationPageObject extends MainPageObject{

    private static final String
        LOGIN_BUTTON = "xpath://a[contains(text(),'Log in')]",
        LOGIN_INPUT = "id:wpName1",
        PASSWORD_INPUT = "id:wpPassword1",
        SUBMIT_BUTTON = "id:wpLoginAttempt";

    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void clickAuthButton()
    {
        this.waitForElementPresent(LOGIN_BUTTON, "Cannot find auth button", Duration.ofSeconds(5));
        this.waitForElementAndClick(LOGIN_BUTTON, "Cannot find and click auth button", Duration.ofSeconds(5));
    }

    public void enterLoginData(String login, String password)
    {
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Cannot find and put a login to the login input", Duration.ofSeconds(5));
        this.waitForElementAndSendKeys(PASSWORD_INPUT, login, "Cannot find and put a password to the password input", Duration.ofSeconds(5));
    }

    public void submitForm()
    {
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click on submit auth button");
    }
}
