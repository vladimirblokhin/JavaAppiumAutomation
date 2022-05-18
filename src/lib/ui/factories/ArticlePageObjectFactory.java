package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;
import lib.ui.android.AndroidArticlePageObject;

public class ArticlePageObjectFactory {
    public static ArticlePageObject get(AppiumDriver driver){
        //  if (Platform.getInstance().isAndroid()) {   TODO: ветвление для других платформ
        return new AndroidArticlePageObject(driver);
        //  }
    }
}
