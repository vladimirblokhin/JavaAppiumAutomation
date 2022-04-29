import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;


public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCompareArticleTitle() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find element to init search",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Java",
                "Cannot find element to enter search line",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot find that article",
                10
        );

        WebElement title_element = waitForElementPresent(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("content-desc");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testCompareSearchlinePlaceholderText() {

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search line",
                15
        );

        MainPageObject.assertElementHaveText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search Wikipedia",
                "We see unexpected placeholder text"
        );
    }

    @Test
    public void testCancelSearch() {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element to enter search line",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("search_src_text"),
                "Java",
                "Cannot find element to enter search line",
                5
        );

        WebElement search_result = driver.findElementById("org.wikipedia:id/search_results_list");

        Assert.assertTrue(search_result.findElements(By.xpath("//*[contains(@class, 'ViewGroup')]")).size() > 1);

        MainPageObject.waitForElementAndClick(
                By.id("search_close_btn"),
                "Cannot find X",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Search result is still visible",
                5
        );
    }

    @Test
    public void testEachArticleContainsSearchText () {
        final String input_text = "Java";

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element to enter search line",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("search_src_text"),
                input_text,
                "Cannot find element to enter search line",
                5
        );

        List<WebElement> articleTitles =  MainPageObject.waitForElementsPresent(
                By.xpath("//*[contains(@resource-id, 'search_results_list')]//*[contains(@resource-id, 'page_list_item_title')]"),
                "Cannot find article titles",
                10
        );

        for (WebElement articleTitle: articleTitles) {
            Assert.assertTrue("Cannot find search text in article titles" ,articleTitle.getText().contains(input_text));
        }
    }

    @Test
    public void testSwipeArticle() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find element to init search",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Java",
                "Cannot find element to enter search line",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot find that article",
                10
        );

      /**  waitForElementPresent(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "Cannot find article title",
                15
        ); **/

        MainPageObject.swipeUpToFindElement(
                By.xpath("//android.view.View[@content-desc='View article in browser']/android.widget.TextView"),
                "Cannot find element ",
                30
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find element to init search",
                5
        );

        String search_line = "Linkin Park discography";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                search_line,
                "Cannot find element to enter search line",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']";
        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by search " + search_line,
                15
        );

        int amount_of_search_result = MainPageObject.getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue(
                "We found too few results",
                amount_of_search_result > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find element to init search",
                5
        );

        String search_line = "zdfsdvssf";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                search_line,
                "Cannot find element to enter search line",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']";
        String empty_result_label = "//*[contains(@text, 'No results')]";

        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find anything by search " + search_line,
                15
        );

        MainPageObject.assertElementNotFound(
                By.xpath(search_result_locator),
                "we found some results by request " + search_line
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find element to init search",
                5
        );

        String search_line = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                search_line,
                "Cannot find element to enter search line",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot find that article",
                10
        );

        String title_before_rotation = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[@class='TextView'][contains(@text, 'Java')]"),
                "text",
                "Cannot find title",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[@class='TextView'][contains(@text, 'Java')]"),
                "text",
                "Cannot find title",
                15
        );

        Assert.assertEquals(
                "Titles before and after rotation are not equals",
                title_before_rotation,
                title_after_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[@class='TextView'][contains(@text, 'Java')]"),
                "text",
                "Cannot find title",
                15
        );

        Assert.assertEquals(
                "Titles before and after rotation are not equals",
                title_after_rotation,
                title_after_second_rotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find element to init search",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Java",
                "Cannot find element to enter search line",
                5
        );

        waitForElementPresent(
                By.xpath("//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot find that article",
                10
        );

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot find article after returning from background",
                10
        );
    }

    @Test
    public void testArticleTitlePresentsCheck() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find element to init search",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Java",
                "Cannot find element to enter search line",
                5
        );

        String title = "Object-oriented programming language";
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot find that article",
                10
        );

        Assert.assertTrue("Cannot find article title",
                MainPageObject.getAmountOfElements(
                        By.xpath("//*[@class='TextView'][contains(@text, 'Object-oriented programming language')]")
                ) > 0);
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    @Test
    public void testSaveTwoArticles () {

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find element to init search",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Java",
                "Cannot find element to enter search line",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot find that article",
                10
        );

        MainPageObject.waitForMobileElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_actions_tab_layout']/*[contains(@text, 'Save')]"),
                "Cannot find save button",
                15
        );

        MainPageObject.waitForMobileElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_actions_tab_layout']/*[contains(@text, 'Save')]"),
                "Cannot find save button",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_action']"),
                "Cannot find button 'Ad to List'",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "Learning Programming",
                "Cannot find listname input line",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot find OK button to create list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc = 'Navigate up']"),
                "Cannot find navigate up button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'High-level programming language')]"),
                "Cannot find Javascript article",
                10
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_actions_tab_layout']/*[contains(@text, 'Save')]"),
                "Cannot find save button",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_actions_tab_layout']/*[contains(@text, 'Save')]"),
                "Cannot find save button",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_action']"),
                "Cannot find button 'Ad to List'",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(("//*[@resource-id='org.wikipedia:id/item_title'][contains(@text, 'Learning Programming')]")),
                "Cannot find list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc = 'Navigate up']"),
                "Cannot find navigate up button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_toolbar']/android.widget.ImageButton"),
                "Cannot find <- button",
                10
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc = 'Saved']"),
                "Cannot find 'Saved' button",
                10
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Learning Programming')]"),
                "Cannot find List",
                15
        );

        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][contains(@text, 'Java (programming language)')]"),
                "Cannot find article in list"
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][contains(@text, 'JavaScript')]"),
                "Cannot find title of the second article",
                15
        );

        String title_article_in_list = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][contains(@text, 'JavaScript')]"),
                "text",
                "Cannot find title of the second article",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'High-level programming language')]"),
                "Cannot find article in List",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'High-level programming language')]"),
                "Cannot find article in List",
                25
        );

        String title_saved_article = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[contains(@text, 'JavaScript')]"),
                "text",
                "Cannot find title inside of the second article",
                15
        );

        Assert.assertEquals(
                "Titles are not equals",
                title_saved_article,
                title_article_in_list
        );
    }
}
