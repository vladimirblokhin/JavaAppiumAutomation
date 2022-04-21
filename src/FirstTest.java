import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.util.List;


public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","D:\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        WebElement skip_button = driver.findElementById("fragment_onboarding_skip_button");
        skip_button.click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find element to init search",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Java",
                "Cannot find element to enter search line",
                5
        );

        waitForElementPresent(
                By.xpath("//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot 'find Object-oriented programming language' topic searching by 'Java'",
                15
        );
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find element to init search",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Java",
                "Cannot find element to enter search line",
                5
        );

        waitForElementAndClick(
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

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search line",
                15
        );

        assertElementHaveText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search Wikipedia",
                "We see unexpected placeholder text"
        );
    }

    @Test
    public void testCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element to enter search line",
                5
        );

        waitForElementAndSendKeys(
                By.id("search_src_text"),
                "Java",
                "Cannot find element to enter search line",
                5
        );

        WebElement search_result = driver.findElementById("org.wikipedia:id/search_results_list");

        Assert.assertTrue(search_result.findElements(By.xpath("//*[contains(@class, 'ViewGroup')]")).size() > 1);

        waitForElementAndClick(
                By.id("search_close_btn"),
                "Cannot find X",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Search result is still visible",
                5
        );
    }

    @Test
    public void testEachArticleContainsSearchText () {
        final String input_text = "Java";

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find element to enter search line",
                5
        );

        waitForElementAndSendKeys(
                By.id("search_src_text"),
                input_text,
                "Cannot find element to enter search line",
                5
        );

        List<WebElement> articleTitles =  waitForElementsPresent(
                By.xpath("//*[contains(@resource-id, 'search_results_list')]//*[contains(@resource-id, 'page_list_item_title')]"),
                "Cannot find article titles",
                10
        );

        for (WebElement articleTitle: articleTitles) {
            Assert.assertTrue("Cannot find search text in article titles" ,articleTitle.getText().contains(input_text));
        }
    }



    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private List<WebElement> waitForElementsPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
        );
    }


    private WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value , String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(by, errorMessage, timeout);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private void assertElementHaveText (By by, String expected_text, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage);

        String actual_text = element.getAttribute("text");
        Assert.assertEquals(
                errorMessage,
                expected_text,
                actual_text
        );
    }
}
