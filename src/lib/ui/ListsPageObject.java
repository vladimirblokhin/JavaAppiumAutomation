package lib.ui;

import io.appium.java_client.AppiumDriver;

public class ListsPageObject  extends MainPageObject{

    public static final String
            LIST_BY_NAME_TPL = "xpath://*[contains(@text, '{LIST_NAME}')]",
            ARTICLE_TITLE_SUBSTRING_TPL = "xpath://*[contains(@text, '{SUBSTRING}')]";

    private static String getListXPathByName(String list_name) {
        return LIST_BY_NAME_TPL.replace("{LIST_NAME}", list_name);
    }

    private static String getArticleXPathBySubstring(String substring) {
        return ARTICLE_TITLE_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    public ListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openListByName(String list_name) {
        String list_name_xpath = getListXPathByName(list_name);
        this.waitForElementAndClick(
                list_name_xpath,
                "Cannot find List",
                15
        );
    }

    public void deleteArticleWithSwipe(String article_title) {
        String article_title_xpath = getArticleXPathBySubstring(article_title);
        this.swipeElementToLeft(
                article_title_xpath,
                "Cannot find article in list"
        );
    }

    public void clickByArticleWithSubstring(String substring) {
        String article_xpath = getArticleXPathBySubstring(substring);
        this.waitForElementAndClick(article_xpath,
                "Cannot find and click search result with substring " + substring,
                10);
    }
}