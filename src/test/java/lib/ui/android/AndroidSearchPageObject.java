package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]";
        SEARCH_INPUT = "xpath://*[@resource-id='org.wikipedia:id/search_src_text']";
        SEARCH_CANCEL_BUTTON = "id:search_close_btn";
        SEARCH_RESULT_LIST = "id:org.wikipedia:id/search_results_list";
        SEARCH_RESULTS = "xpath://*[contains(@resource-id, 'search_results_list')]//*[contains(@resource-id, 'page_list_item_title')]";
        SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[contains(@text, 'No results')]";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[contains(@text, '{SUBSTRING}')]";
    }
    public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
