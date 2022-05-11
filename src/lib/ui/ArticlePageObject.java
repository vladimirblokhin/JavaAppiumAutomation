package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            FOOTER,
            SAVE_BUTTON,
            ADD_TO_LIST_BUTTON,
            LIST_NAME_INPUT,
            OK_BUTTON,
            LIST_NAME,
            ARTICLE_TITLE;

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(ARTICLE_TITLE,
                "Cannot find article title on page",
                15);
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                FOOTER,
                "Cannot find the end of article",
                30);
    }

    public void checkingArticleTitlePresentWithoutWaiting() {
        this.waitForElementPresent(ARTICLE_TITLE,
                "Cannot find title article without waiting",
                0);
    }

    public void addArticleToNewList(String list_name) {
        this.waitForMobileElementAndClick(
                SAVE_BUTTON,
                "Cannot find save button",
                15
        );

        this.waitForMobileElementAndClick(
                SAVE_BUTTON,
                "Cannot find save button",
                15
        );

        this.waitForElementAndClick(
                ADD_TO_LIST_BUTTON,
                "Cannot find button 'Add to List'",
                15
        );

        this.waitForElementAndSendKeys(
                LIST_NAME_INPUT,
                list_name,
                "Cannot find list name input line",
                5
        );

        this.waitForElementAndClick(
                OK_BUTTON,
                "Cannot find OK button to create list",
                5
        );
    }

    public void addArticleToExistingList () {
        this.waitForMobileElementAndClick(
                SAVE_BUTTON,
                "Cannot find save button",
                15
        );

        this.waitForMobileElementAndClick(
                SAVE_BUTTON,
                "Cannot find save button",
                15
        );

        this.waitForElementAndClick(
                ADD_TO_LIST_BUTTON,
                "Cannot find button 'Add to List'",
                5
        );

        this.waitForElementAndClick(
                LIST_NAME,
                "Cannot find list",
                5
        );
    }
}
