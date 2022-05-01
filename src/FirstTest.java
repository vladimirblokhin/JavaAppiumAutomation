import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        ArticlePageObject.waitForTitleElement();
        String article_title =ArticlePageObject.getArticleTitle();

        assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testCompareSearchlinePlaceholderText() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.assertThereIsPlaceHolderTextInSearchLine("Search Wikipedia");
    }

    @Test
    public void testCancelSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        assertTrue(SearchPageObject.areThereMoreSearchResultsThanX(1));
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickSearchCancelButton();
        SearchPageObject.waitDisappearResultsAfterCancelingTheSearch();
    }

    @Test
    public void testEachArticleContainsSearchText () {
        final String input_text = "Java";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(input_text);

        List<WebElement> articleTitles = SearchPageObject.getSearchResults();

        for (WebElement articleTitle: articleTitles) {
            assertTrue("Cannot find search text in article titles" ,articleTitle.getText().contains(input_text));
        }
    }

    @Test
    public void testSwipeArticle() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_result = SearchPageObject.getAmountOfArticles();

        assertTrue(
                "We found too few results",
                amount_of_search_result > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "zdfsdvssf";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String title_before_rotation = ArticlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String title_after_rotation = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Titles before and after rotation are not equals",
                title_before_rotation,
                title_after_rotation
        );

        this.rotateScreenPortrait();

        String title_after_second_rotation = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Titles before and after rotation are not equals",
                title_after_rotation,
                title_after_second_rotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
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

        assertTrue("Cannot find article title",
                MainPageObject.getAmountOfElements(
                        By.xpath("//*[@class='TextView'][contains(@text, 'Object-oriented programming language')]")
                ) > 0);
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

        MainPageObject.waitForElementPresent(
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

        assertEquals(
                "Titles are not equals",
                title_saved_article,
                title_article_in_list
        );
    }
}
