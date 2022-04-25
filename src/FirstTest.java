import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
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
        driver.rotate(ScreenOrientation.PORTRAIT);

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

    @Test
    public void testSwipeArticle() {
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

      /**  waitForElementPresent(
                By.xpath("//android.view.View[@content-desc='Java (programming language)']"),
                "Cannot find article title",
                15
        ); **/

        swipeUpToFindElement(
                By.xpath("//android.view.View[@content-desc='View article in browser']/android.widget.TextView"),
                "Cannot find element ",
                30
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find element to init search",
                5
        );

        String search_line = "Linkin Park discography";
        waitForElementAndSendKeys(
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

        int amount_of_search_result = getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue(
                "We found too few results",
                amount_of_search_result > 0
        );
    }

    @Test
    public void amountOfEmptySearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find element to init search",
                5
        );

        String search_line = "zdfsdvssf";
        waitForElementAndSendKeys(
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

        assertElementNotFound(
                By.xpath(search_result_locator),
                "we found some results by request " + search_line
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find element to init search",
                5
        );

        String search_line = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                search_line,
                "Cannot find element to enter search line",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot find that article",
                10
        );

        String title_before_rotation = waitForElementAndGetAttribute(
                By.xpath("//*[@class='TextView'][contains(@text, 'Java')]"),
                "text",
                "Cannot find title",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttribute(
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

        String title_after_second_rotation = waitForElementAndGetAttribute(
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

        String title = "Object-oriented programming language";
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Object-oriented programming language')]"),
                "Cannot find that article",
                10
        );

        Assert.assertTrue("Cannot find article title",
                getAmountOfElements(
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

        waitForMobileElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_actions_tab_layout']/*[contains(@text, 'Save')]"),
                "Cannot find save button",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_action']"),
                "Cannot find button 'Ad to List'",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "Learning Programming",
                "Cannot find listname input line",
                5
        );

        waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot find OK button to create list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@accessibility id = 'Navigate up']"),
                "Cannot find navigate up button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'High-level programming language')]"),
                "Cannot find Javascript article",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_actions_tab_layout]/*[contains(@text, 'Save')]"),
                "Cannot find save button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_action']"),
                "Cannot find button 'Ad to List'",
                5
        );

        waitForElementAndClick(
                By.xpath(("//*[@id='org.wikipedia:id/item_title'][contains(@text, 'Learning Programming')]")),
                "Cannot find list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@accessibility id = 'Navigate up']"),
                "Cannot find navigate up button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@id='org.wikipedia:id/search_toolbar']/*[@class='android.widget.ImageButton']"),
                "Cannot find <- button",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[@accessibility id = 'Saved']"),
                "Cannot find 'Saved' button",
                10
        );

        waitForElementAndClick(
                By.xpath("//[*contains(@text, 'Learning Programming')]"),
                "Cannot find List",
                10
        );

        swipeElementToLeft(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][contains(@text, 'Java (programming language)')]"),
                "Cannot find article in list"
        );

        String title_article_in_list = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/page_list_item_title"),
                "text",
                "Cannot find title of the second article",
                5
        );

        waitForElementAndClick(
                By.xpath("//[*contains(@text, " + title_article_in_list + ")]"),
                "Cannot find article in List",
                1
        );

        String title_saved_article = waitForElementAndGetAttribute(
                By.xpath("//*[@class='android.widget.TextView'][contains(@text, 'Javascript')]"),
                "text",
                "Cannot find title of the second article",
                5
        );

        Assert.assertEquals(
                "Titles are not equals",
                title_saved_article,
                title_article_in_list
        );
    }


    private List<WebElement> waitForElementsPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
        );
    }

    private MobileElement waitForMobileElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return (MobileElement) wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
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

    private MobileElement waitForMobileElementAndClick(By by, String errorMessage, long timeout) {
        MobileElement mobElement = waitForMobileElementPresent(by, errorMessage, timeout);
        mobElement.click();
        return mobElement;
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

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.7);
        int end_y = (int) (size.height * 0.3);


        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;

        while (driver.findElements(by).size() == 0) {
            swipeUpQuick();

            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(
                        by,
                        "cannot find element by swiping up \n" + errorMessage,
                        5
                        );
                return;
            }

            ++alreadySwiped;
        }
    }

    protected  void swipeElementToLeft(By by, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage, 10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(150)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotFound(By by, String errorMessage) {
        int amount_of_elements = getAmountOfElements(by);

        if (amount_of_elements > 0) {
            String default_message = "An element " + by.toString() + " supposed to be not present";
            throw new AssertionError(default_message + " " + errorMessage);
        }
    }

    private String waitForElementAndGetAttribute (By by, String attribute, String errorMessage, long timeout) {
        WebElement element = waitForElementPresent(by, errorMessage);
        element.getAttribute(attribute);
        return attribute;
    }
}
