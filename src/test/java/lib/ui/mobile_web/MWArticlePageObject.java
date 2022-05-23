package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        FOOTER = "css:footer";
        ADD_TO_LIST_BUTTON = "css:#ca-watch";
        ARTICLE_TITLE = "css:#content h1";
    }
    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
