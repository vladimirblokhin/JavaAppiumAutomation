package lib.ui.android;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidArticlePageObject extends ArticlePageObject {

    static {
        FOOTER = "xpath://android.view.View[@content-desc='View article in browser']/android.widget.TextView";
        SAVE_BUTTON = "xpath://*[@resource-id='org.wikipedia:id/page_actions_tab_layout']/*[contains(@text, 'Save')]";
        ADD_TO_LIST_BUTTON = "xpath://*[@resource-id='org.wikipedia:id/snackbar_action']";
        LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
        OK_BUTTON = "id:android:id/button1";
        LIST_NAME = "xpath://*[@resource-id='org.wikipedia:id/item_title'][contains(@text, 'Learning Programming')]";
        ARTICLE_TITLE = "xpath://*[contains(@text, 'Java (programming language)')]";
    }
    public AndroidArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
