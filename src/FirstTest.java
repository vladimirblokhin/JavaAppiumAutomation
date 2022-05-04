import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
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
        this.hideKeyboard();
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

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        String title = "Object-oriented programming language";
        SearchPageObject.clickByArticleWithSubstring(title);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.checkingArticleTitlePresentWithoutWaiting();
    }

    @Test
    public void testSaveTwoArticles () {

        String list_name = "Learning Programming",
                substring_of_first_article = "Object-oriented programming language",
                substring_of_second_article = "High-level programming language";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        NavigationUI NavigationUI = new NavigationUI(driver);
        ListsPageObject ListsPageObject = new ListsPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring(substring_of_first_article);

        ArticlePageObject.addArticleToNewList(list_name);
        NavigationUI.backToSearchResults();

        SearchPageObject.clickByArticleWithSubstring(substring_of_second_article);
        String article_title = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addArticleToExistingList();

        NavigationUI.backToSearchResults();
        NavigationUI.backToStartPage();
        NavigationUI.moveToListsPage();

        ListsPageObject.openListByName(list_name);
        ListsPageObject.deleteArticleWithSwipe("Java (programming language)");

        ListsPageObject.clickByArticleWithSubstring(substring_of_second_article);
        String title_saved_article = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Titles are not equals",
                title_saved_article,
                article_title
        );
    }
}
