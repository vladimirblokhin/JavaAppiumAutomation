package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    public static final String
            FOOTER = "//android.view.View[@content-desc='View article in browser']/android.widget.TextView",
            SAVE_BUTTON = "//*[@resource-id='org.wikipedia:id/page_actions_tab_layout']/*[contains(@text, 'Save')]",
            ADD_TO_LIST_BUTTON = "//*[@resource-id='org.wikipedia:id/snackbar_action']",
            LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            OK_BUTTON = "android:id/button1",
            LIST_NAME = "//*[@resource-id='org.wikipedia:id/item_title'][contains(@text, 'Learning Programming')]",
            ARTICLE_TITLE ="//*[contains(@text, 'Java (programming language)')]";

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(By.xpath(ARTICLE_TITLE),
                "Cannot find article title on page",
                15);
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                By.xpath(FOOTER),
                "Cannot find the end of article",
                30);
    }

    public void checkingArticleTitlePresentWithoutWaiting() {
        this.waitForElementPresent(By.xpath(ARTICLE_TITLE),
                "Cannot find title article without waiting",
                0);
    }

    public void addArticleToNewList(String list_name) {
        this.waitForMobileElementAndClick(
                By.xpath(SAVE_BUTTON),
                "Cannot find save button",
                15
        );

        this.waitForMobileElementAndClick(
                By.xpath(SAVE_BUTTON),
                "Cannot find save button",
                15
        );

        this.waitForElementAndClick(
                By.xpath(ADD_TO_LIST_BUTTON),
                "Cannot find button 'Add to List'",
                5
        );

        this.waitForElementAndSendKeys(
                By.id(LIST_NAME_INPUT),
                list_name,
                "Cannot find list name input line",
                5
        );

        this.waitForElementAndClick(
                By.id(OK_BUTTON),
                "Cannot find OK button to create list",
                5
        );
    }

    public void addArticleToExistingList () {
        this.waitForMobileElementAndClick(
                By.xpath(SAVE_BUTTON),
                "Cannot find save button",
                15
        );

        this.waitForMobileElementAndClick(
                By.xpath(SAVE_BUTTON),
                "Cannot find save button",
                15
        );

        this.waitForElementAndClick(
                By.xpath(ADD_TO_LIST_BUTTON),
                "Cannot find button 'Add to List'",
                5
        );

        this.waitForElementAndClick(
                By.xpath((LIST_NAME)),
                "Cannot find list",
                5
        );
    }
}
