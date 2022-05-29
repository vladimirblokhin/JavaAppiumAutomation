package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ListsPageObject  extends MainPageObject{

    protected static String
            LIST_BY_NAME_TPL,
            ARTICLE_TITLE_SUBSTRING_TPL,
            REMOVE_FROM_SAVED_BUTTON;

    private static String getListXPathByName(String list_name) {
        return LIST_BY_NAME_TPL.replace("{LIST_NAME}", list_name);
    }

    private static String getArticleXPathBySubstring(String substring) {
        return ARTICLE_TITLE_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getRemoveButtonByTitle(String article_title) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
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

        public void deleteArticleWithSwipe (String article_title){
            String article_title_xpath = getArticleXPathBySubstring(article_title);
            if (Platform.getInstance().isAndroid()) {
                this.swipeElementToLeft(
                        article_title_xpath,
                        "Cannot find article in list"
                );
            } else {
                String remove_locator = getRemoveButtonByTitle(article_title);
                this.waitForElementAndClick(
                        remove_locator,
                        "cannot click button to remove article from list",
                        10
                );
            }

            if (Platform.getInstance().isMW()) {
                driver.navigate().refresh();
            }
        }

        public void clickByArticleWithSubstring(String substring){
            String article_xpath = getArticleXPathBySubstring(substring);
            this.waitForElementAndClick(article_xpath,
                    "Cannot find and click search result with substring " + substring,
                    10);
        }
}


