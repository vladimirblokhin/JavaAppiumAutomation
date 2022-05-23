package lib.ui.mobile_web;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {
    static {
        MY_LISTS_LINK = "css:a[data-event-name='watchList']";
        OPEN_NAVIGATION = "css:#mw-mf-main-menu-button";
        MOBILE_VIEW_LINK = "css:#footer-places-mobileview > a";
    }

    public MWNavigationUI(RemoteWebDriver driver){
        super(driver);
    }
}
