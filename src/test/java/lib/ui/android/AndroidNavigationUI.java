package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;

public class AndroidNavigationUI extends NavigationUI {

    static {
        RETURN_BUTTON = "xpath://*[@resource-id='org.wikipedia:id/search_toolbar']/android.widget.ImageButton";
        NAVIGATE_UP = "xpath://*[@content-desc = 'Navigate up']";
        SAVED_BUTTON = "xpath://*[@content-desc = 'Saved']";
    }

    public AndroidNavigationUI(AppiumDriver driver){
        super(driver);
    }
}
