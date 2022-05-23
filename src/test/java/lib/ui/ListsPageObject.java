package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ListsPageObject  extends MainPageObject{

    protected static String
            LIST_BY_NAME_TPL,
            ARTICLE_TITLE_SUBSTRING_TPL;

    private static String getListXPathByName(String list_name) {
        return LIST_BY_NAME_TPL.replace("{LIST_NAME}", list_name);
    }

    private static String getArticleXPathBySubstring(String substring) {
        return ARTICLE_TITLE_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    public ListsPageObject(RemoteWebDriver driver) {
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
