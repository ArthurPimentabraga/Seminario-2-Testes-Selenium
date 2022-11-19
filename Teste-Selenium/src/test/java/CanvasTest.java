import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class CanvasTest {

    WebDriver driver;

    private static final String URL_LOGIN = "https://pucminas.instructure.com/login/canvas";
    private static final String URL_HOME = "https://pucminas.instructure.com/";

    @Before
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src/driver/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    @DisplayName("Quando não existe usuário ou senha, não deve permitir o login")
    public void test01() throws InterruptedException {
        driver.get(URL_LOGIN);

        var currentUrl = driver.getCurrentUrl();
        assertEquals(URL_LOGIN, currentUrl);

        Thread.sleep(1000);

        WebElement userTextBox = driver.findElement(By.id("pseudonym_session_unique_id"));
        WebElement pwdTextBox = driver.findElement(By.id("pseudonym_session_password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button"));

        userTextBox.sendKeys("1278661");
        pwdTextBox.sendKeys("123456");

        Thread.sleep(1000);

        loginButton.click();

        var finalUrl = driver.getCurrentUrl();
        assertEquals(URL_LOGIN, finalUrl);

        driver.quit();
    }

}
