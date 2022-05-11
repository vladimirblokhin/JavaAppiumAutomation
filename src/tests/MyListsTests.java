package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.ListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
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
