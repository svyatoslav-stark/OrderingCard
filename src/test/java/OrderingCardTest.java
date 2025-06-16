import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class OrderingCardTest {

    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
//        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSendForm() {
        WebElement nameField = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        nameField.sendKeys("Иван Иванов");

        WebElement phoneField = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phoneField.sendKeys("+79991234567");

        WebElement agreementCheckbox = driver.findElement(By.cssSelector("[data-test-id='agreement']"));
        agreementCheckbox.click();

        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();

        WebElement successMessage = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        Assertions.assertTrue(successMessage.isDisplayed());
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", successMessage.getText().trim());
    }
}
