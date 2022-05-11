package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class NavigationUI extends MainPageObject{

    protected static String
            RETURN_BUTTON,
            NAVIGATE_UP,
            SAVED_BUTTON;

    public NavigationUI(AppiumDriver driver) {
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
}
