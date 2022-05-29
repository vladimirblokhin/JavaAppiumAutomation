package lib.ui.factories;

import lib.Platform;
import lib.ui.ListsPageObject;
import lib.ui.mobile_web.MWListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;
import lib.ui.android.AndroidListsPageObject;

public class ListsPageObjectFactory {
    public static ListsPageObject get(RemoteWebDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidListsPageObject(driver);
        } else {
            return new MWListsPageObject(driver);
        }
    }
}
