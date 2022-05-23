package lib.ui.android;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidNavigationUI extends NavigationUI {

    static {
        RETURN_BUTTON = "xpath://*[@resource-id='org.wikipedia:id/search_toolbar']/android.widget.ImageButton";
        NAVIGATE_UP = "xpath://*[@content-desc = 'Navigate up']";
        SAVED_BUTTON = "xpath://*[@content-desc = 'Saved']";
    }

    public AndroidNavigationUI(RemoteWebDriver driver){
        super(driver);
    }
}
