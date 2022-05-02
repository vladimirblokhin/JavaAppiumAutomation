package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static junit.framework.TestCase.assertTrue;

public class ArticlePageObject extends MainPageObject {

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "Cannot find article title on page",
                15);
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("content-desc");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                By.xpath("//android.view.View[@content-desc='View article in browser']/android.widget.TextView"),
                "Cannot find the end of article",
                30);
    }

    public void checkingArticleTitlePresentWithoutWaiting() {
        this.waitForElementPresent(By.xpath("//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot find title article without waiting",
                0);
    }

}
