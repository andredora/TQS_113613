package tqs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.assertj.core.api.Assertions.assertThat;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;

import java.time.Duration;

@ExtendWith(SeleniumJupiter.class)
class BlazeDemoTest {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    void testBlazeDemo(WebDriver driver) { 
        String sutUrl = "https://blazedemo.com/";
        driver.get(sutUrl);
        log.debug("A aceder a página: {}", sutUrl);

        WebElement departureCity = driver.findElement(By.name("fromPort"));
        departureCity.sendKeys("Paris");
        WebElement destinationCity = driver.findElement(By.name("toPort"));
        destinationCity.sendKeys("London");

        WebElement findFlightsButton = driver.findElement(By.cssSelector("input[type='submit']"));
        findFlightsButton.click();
        log.debug("Clicou em 'Find Flights'");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement chooseFlightButton = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='submit']")));
        chooseFlightButton.click();
        log.debug("Escolheu um voo");

        WebElement nameField = driver.findElement(By.id("inputName"));
        nameField.sendKeys("João Silva");
        WebElement addressField = driver.findElement(By.id("address"));
        addressField.sendKeys("Rua Exemplo, 123");
        WebElement cityField = driver.findElement(By.id("city"));
        cityField.sendKeys("São Paulo");
        WebElement stateField = driver.findElement(By.id("state"));
        stateField.sendKeys("SP");
        WebElement zipCodeField = driver.findElement(By.id("zipCode"));
        zipCodeField.sendKeys("12345");
        WebElement creditCardNumberField = driver.findElement(By.id("creditCardNumber"));
        creditCardNumberField.sendKeys("1234567890123456");
        WebElement creditCardMonthField = driver.findElement(By.id("creditCardMonth"));
        creditCardMonthField.sendKeys("12");
        WebElement creditCardYearField = driver.findElement(By.id("creditCardYear"));
        creditCardYearField.sendKeys("2025");
        WebElement nameOnCardField = driver.findElement(By.id("nameOnCard"));
        nameOnCardField.sendKeys("João Silva");

        WebElement purchaseButton = driver.findElement(By.cssSelector("input[type='submit']"));
        purchaseButton.click();
        log.debug("Clicou em 'Purchase Flight'");

        String confirmationTitle = driver.getTitle();
        log.debug("Título da página de confirmação: {}", confirmationTitle);
        assertThat(confirmationTitle).isEqualTo("BlazeDemo Confirmation");

        WebElement confirmationMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        String messageText = confirmationMessage.getText();
        log.debug("Mensagem de confirmação: {}", messageText);
        assertThat(messageText).contains("Thank you for your purchase today!");

        WebElement reservationId = driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(2)"));
        String idText = reservationId.getText();
        log.debug("ID da reserva: {}", idText);
        assertThat(idText).isNotEmpty();

        log.debug("Teste concluído com sucesso");
    }
}