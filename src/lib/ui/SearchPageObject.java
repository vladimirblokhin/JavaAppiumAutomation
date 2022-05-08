package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "xpath://*[@resource-id='org.wikipedia:id/search_src_text']",
            SEARCH_CANCEL_BUTTON = "id:search_close_btn",
            SEARCH_RESULT_LIST = "id:org.wikipedia:id/search_results_list",
            SEARCH_RESULTS = "xpath://*[contains(@resource-id, 'search_results_list')]//*[contains(@resource-id, 'page_list_item_title')]",
            SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']",
            SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[contains(@text, 'No results')]",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[contains(@text, '{SUBSTRING}')]";


    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    public void initSearchInput() {
        this.waitForElementPresent(SEARCH_INIT_ELEMENT,
                "Cannot find search input after clicking search init element");

        this.waitForElementAndClick(SEARCH_INIT_ELEMENT,
                "Cannot find end click search init element",
                5);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button",
                15);
    }

    public void clickSearchCancelButton() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON,
                "Cannot find and click cancel button",
                15);
    }

    public boolean areThereMoreSearchResultsThanX (int x) {

        List<WebElement> results = this.waitForElementsPresent(SEARCH_RESULTS,
                "Cannot find search results",
                5);

        if (results.size() > x) {
            return true;
        } else {
            return false;
        }
    }

    public void waitDisappearResultsAfterCancelingTheSearch () {
        this.waitForElementNotPresent(SEARCH_RESULT_LIST,
                "Search result list is still present",
                5);
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(SEARCH_INPUT,
                search_line,
                "Cannot find and type in search input", 5);
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath,
                "Cannot find search result with substring " + substring);
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath,
                "Cannot find and click search result with substring " + substring,
                10);
    }

    public int getAmountOfArticles() {
        waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by search " ,
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultLabel() {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15
        );
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotFound(SEARCH_RESULT_ELEMENT,
                "We supposed not to find any results");
    }

    public void assertThereIsPlaceHolderTextInSearchLine(String expected_text) {
        this.assertElementHaveText(SEARCH_INPUT,
                expected_text,
                "We see unexpected placeholder text");
    }

    public List<WebElement> getSearchResults () {
       List results = this.waitForElementsPresent(SEARCH_RESULTS,
                "Cannot find article titles",
                10);

       return results;
    }
}
