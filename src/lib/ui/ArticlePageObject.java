package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    public static final String
            FOOTER = "xpath://android.view.View[@content-desc='View article in browser']/android.widget.TextView",
            SAVE_BUTTON = "xpath://*[@resource-id='org.wikipedia:id/page_actions_tab_layout']/*[contains(@text, 'Save')]",
            ADD_TO_LIST_BUTTON = "xpath://*[@resource-id='org.wikipedia:id/snackbar_action']",
            LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
            OK_BUTTON = "id:android:id/button1",
            LIST_NAME = "xpath://*[@resource-id='org.wikipedia:id/item_title'][contains(@text, 'Learning Programming')]",
            ARTICLE_TITLE ="xpath://*[contains(@text, 'Java (programming language)')]";

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
