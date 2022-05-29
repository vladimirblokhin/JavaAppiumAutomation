package lib.ui;

import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            FOOTER,
            SAVE_BUTTON,
            ADD_TO_LIST_BUTTON,
            LIST_NAME_INPUT,
            OK_BUTTON,
            LIST_NAME,
            ARTICLE_TITLE,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            OPTIONS_REMOVE_FROM_MY_LIST_BUTTON;

    public ArticlePageObject(RemoteWebDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(ARTICLE_TITLE,
                "Cannot find article title on page",
                15);
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else {
            return title_element.getText();
        }
    }

    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER,
                    "Cannot find the end of article",
                    30);
        } else {
            this.scrollWebPageTilElementNotVisible(
                    FOOTER,
                    "Cannot find footer",
                    40);
        }
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

    public void addArticleToMySaved() {
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromSavedIfItAdded();
        }
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                15);
    }

    public void removeArticleFromSavedIfItAdded() {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from saved",
                    5);
        }
        this.waitForElementPresent(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find button to add an article to saved list after removing it from this list before",
                5);
    }
}
