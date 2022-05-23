package tests;

import lib.CoreTestCase;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.ListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    private static final String
            login = "Vladimirblokhin",
            pass = "4221Wiki";

    @Test
    public void testSaveTwoArticles () {

        String list_name = "Learning Programming",
                substring_of_first_article = "Object-oriented programming language",
                substring_of_second_article = "High-level programming language";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        ListsPageObject ListsPageObject = ListsPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring(substring_of_first_article);

        if (Platform.isAndroid()) {
            ArticlePageObject.addArticleToNewList(list_name);
        } else {
            NavigationUI.openNavigation();
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, pass);
            Auth.submitForm();
            NavigationUI.switchToMobileView();
            ArticlePageObject.waitForTitleElement();
            ArticlePageObject.addArticleToMySaved();
        }
        NavigationUI.backToSearchResults();

        SearchPageObject.clickByArticleWithSubstring(substring_of_second_article);
        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.isAndroid()) {
            ArticlePageObject.addArticleToExistingList();
            NavigationUI.backToSearchResults();
            NavigationUI.backToStartPage();
            NavigationUI.moveToListsPage();
        } else {
            ArticlePageObject.waitForTitleElement();
            ArticlePageObject.addArticleToMySaved();
            NavigationUI.openNavigation();
            NavigationUI.clickMyLists();
        }


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
