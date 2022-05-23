package lib.ui.android;

import lib.ui.ListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidListsPageObject extends ListsPageObject {
    static {
        LIST_BY_NAME_TPL = "xpath://*[contains(@text, '{LIST_NAME}')]";
        ARTICLE_TITLE_SUBSTRING_TPL = "xpath://*[contains(@text, '{SUBSTRING}')]";
    }

    public AndroidListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
