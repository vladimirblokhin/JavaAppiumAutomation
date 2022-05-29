package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject{

    protected static String
            RETURN_BUTTON,
            NAVIGATE_UP,
            SAVED_BUTTON,
            MY_LISTS_LINK,
            MOBILE_VIEW_LINK,
            OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    public void backToSearchResults() {
        this.waitForElementAndClick(
                NAVIGATE_UP,
                "Cannot find navigate up button",
                5
        );
    }

    public void backToStartPage() {
        this.waitForElementAndClick(
                RETURN_BUTTON,
                "Cannot find <- button",
                10
        );
    }

    public void moveToListsPage() {
        this.waitForElementAndClick(
                SAVED_BUTTON,
                "Cannot find 'Saved' button",
                15
        );
    }

    public void openNavigation() {
        if (Platform.getInstance().isMW()) {
            this.waitForElementAndClick(OPEN_NAVIGATION, "Cannot find menu button", 5);
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void clickMyLists() {
        if (Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttempts(
                    MY_LISTS_LINK,
                    "Cannot find navigation button to My list",
                    5);
        }
    }

    public void switchToMobileView() {
        if (Platform.getInstance().isMW()) {
            this.waitForElementAndClick(MOBILE_VIEW_LINK, "Cannot find 'Mobile view' link", 5);
        } else {
            System.out.println("Method switchToMobileView() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }
}
