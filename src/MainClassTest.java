import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    MainClass Main = new MainClass();

    @Test
    public void testGetLocalNumber () {
        int expected = 14;
        int actual = Main.getLocalNumber();

        Assert.assertTrue("Метод getLocalNumber должен возвращать 14", actual == expected);
    }
}
