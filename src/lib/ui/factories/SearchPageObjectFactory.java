package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;
import lib.ui.android.AndroidSearchPageObject;

public class SearchPageObjectFactory {
    public static SearchPageObject get(AppiumDriver driver){
      //  if (Platform.getInstance().isAndroid()) {   TODO: ветвление для других платформ
            return new AndroidSearchPageObject(driver);
      //  }
    }
}
