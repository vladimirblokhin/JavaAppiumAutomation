package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject{

    public static final String
            RETURN_BUTTON = "xpath://*[@resource-id='org.wikipedia:id/search_toolbar']/android.widget.ImageButton",
            NAVIGATE_UP = "xpath://*[@content-desc = 'Navigate up']",
            SAVED_BUTTON = "xpath://*[@content-desc = 'Saved']";

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
