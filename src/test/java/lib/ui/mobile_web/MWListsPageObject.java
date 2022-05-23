package lib.ui.mobile_web;

import lib.ui.ListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWListsPageObject extends ListsPageObject {
    static {
        ARTICLE_TITLE_SUBSTRING_TPL = "xpath://ul[contains(@class, 'watchlist')]//h3[contains(text(), '{SUBSTRING}')]";
        REMOVE_FROM_SAVED_BUTTON = "xpath://ul[contains(@class,'watchlist')]//h3[contains(@text(),'{SUBSTRING}')]/../../div[contains(@class,'watched')]";
    }

    public MWListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
