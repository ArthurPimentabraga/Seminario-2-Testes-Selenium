import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class GoogleFinancasTest {

    WebDriver driver;

    private static final String URL_HOME = "https://www.google.com/finance/?hl=pt";

    @Before
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src/driver/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    @DisplayName("Ao solicitar o login, deve abrir a tela de login")
    public void test01() throws InterruptedException {
        var currentUrl = goToUrl(URL_HOME);
        assertEquals(URL_HOME, currentUrl);

        WebElement loginButton = driver.findElement(By.xpath("//*[text()='Faça login']"));
        loginButton.click();

        Thread.sleep(1000);

        var emailTextBox = driver.findElement(By.xpath("//input[@jsname = 'YPqjbf']"));
        emailTextBox.sendKeys("test@gmail.com");

        Thread.sleep(1000);

        var title = driver.getTitle();
        assertEquals("Fazer login nas Contas do Google", title);

        driver.quit();
    }

    @Test
    @DisplayName("Verificar existencia de conteúdo principal")
    public void test02() {
        var currentUrl = goToUrl(URL_HOME);
        assertEquals(URL_HOME, currentUrl);

        driver.findElement(By.xpath("//*[text()='Talvez você se interesse por']"));
        driver.findElement(By.xpath("//*[text()='Tendências de mercado']"));
        driver.findElement(By.xpath("//*[text()='Agenda de lucros']"));

        driver.quit();
    }

    @Test
    @DisplayName("Ao selecionar uma tendência, a página deve ser filtrada")
    public void test03() throws InterruptedException {
        var currentUrl = goToUrl(URL_HOME);
        assertEquals(URL_HOME, currentUrl);

        var tendenciaButton = driver.findElement(By.xpath("//*[text()='Mais ativos']"));
        tendenciaButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        Thread.sleep(2000);

        var test = "Os títulos ou fundos com o maior número de transações (em ações) durante a sessão de negociação atual";
        var tendenciasTitle = driver.findElement(By.className("MzhJl"));
        var title = tendenciasTitle.getText();
        assertEquals(test, title);

        driver.quit();
    }

    @Test
    @DisplayName("Ao solicitar o compartilhamento de uma tendência, deve abrir um popup com a opção de copiar o link")
    public void test04() throws InterruptedException {
        goToUrl(URL_HOME);

        var tendenciaButton = driver.findElement(By.xpath("//*[text()='Mais ativos']"));
        tendenciaButton.click();

        Thread.sleep(2000);

        var shareButton = driver.findElement(By.xpath("//*[text()='Compartilhar']"));
        shareButton.click();

        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[text()='Compartilhar']"));
        driver.findElement(By.xpath("//*[text()='Copiar link']"));

        driver.quit();
    }

    @Test
    @DisplayName("Ao tentar seguir um índice sem estar logado, deve redirecionar para a tela de login")
    public void test05() throws InterruptedException {
        goToUrl(URL_HOME);

        var followButton = driver.findElement(By.xpath("//div[@jsname = 'pzCKEc']"));
        followButton.click();

        Thread.sleep(2000);

        var title = driver.getTitle();
        assertEquals("Fazer login nas Contas do Google", title);

        driver.quit();
    }

    private String goToUrl(String url) {
        driver.get(url);
        return driver.getCurrentUrl();
    }

}
