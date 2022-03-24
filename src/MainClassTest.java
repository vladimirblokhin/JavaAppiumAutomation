import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    MainClass Main = new MainClass();

    @Test
    public void testGetLocalNumber () {
        int expected = 14;
        int actual = Main.getLocalNumber();

        Assert.assertEquals("Метод getLocalNumber должен возвращать 14", actual, expected);
    }

    @Test
    public void testGetClassNumber() {
        Assert.assertTrue("метод getClassNumber должен возвращать число больше 45", Main.getClassNumber() > 45);
    }
}
