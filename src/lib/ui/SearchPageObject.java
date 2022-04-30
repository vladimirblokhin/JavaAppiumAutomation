package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "//*[@resource-id='org.wikipedia:id/search_src_text']",
            SEARCH_CANCEL_BUTTON = "search_close_btn",
            SEARCH_RESULT_LIST = "org.wikipedia:id/search_results_list",
            SEARCH_RESULTS = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[contains(@class, 'ViewGroup')]",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[contains(@text, '{SUBSTRING}')]";


    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    public void initSearchInput() {
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find search input after clicking search init element");

        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find end click search init element",
                5);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find search cancel button",
                15);
    }

    public void clickSearchCancelButton() {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find and click cancel button",
                15);
    }

    public boolean areThereMoreSearchResultsThanX (int x) {

        List<WebElement> results = this.waitForElementsPresent(By.xpath(SEARCH_RESULTS),
                "Cannot find search results",
                5);

        if (results.size() > x) {
            return true;
        } else {
            return false;
        }
    }

    public void waitDisappearResultsAfterCancelingTheSearch () {
        this.waitForElementNotPresent(By.id(SEARCH_RESULT_LIST),
                "Search result list is still present",
                5);
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT),
                search_line,
                "Cannot find and type in search input", 5);
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath),
                "Cannot find search result with substring " + substring);
    }
}
