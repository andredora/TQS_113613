package tqs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.assertj.core.api.Assertions.assertThat;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;

import java.time.Duration;

@ExtendWith(SeleniumJupiter.class)
class HarryPotterTest {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    void testBookSearch(ChromeDriver driver) {
        String sutUrl = "https://cover-bookstore.onrender.com/";
        driver.get(sutUrl);
        System.out.println("A aceder à página: " + sutUrl);

        // Esperar que a página carregue
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Localizar o campo de pesquisa e inserir o termo de pesquisa
        WebElement searchInput = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("input[data-testid='book-search-input']")));
        searchInput.sendKeys("Harry Potter");
        searchInput.sendKeys("\uE007"); // Simulate pressing the Enter key
        System.out.println("Pesquisa Harry Potter submetida");

        System.out.println("Pagina carregada");

        // Localizar o cartão de livro (único resultado)
        WebElement bookCard = driver.findElement(By.xpath("//*[@data-testid='book-search-item']"));
        System.out.println("Cartão de livro encontrado.");

        // Localizar o título e o autor dentro do cartão de livro
        String title = bookCard.findElement(By.cssSelector(".SearchList_bookTitle__1wo4a")).getText();
        String author = bookCard.findElement(By.cssSelector(".SearchList_bookAuthor__3g1PC")).getText();

        log.debug("Título do livro: {}", title);
        log.debug("Autor do livro: {}", author);

        // Verificar se o título e o autor correspondem ao esperado
        boolean found = title.contains("Harry Potter and the Sorcerer's Stone") && author.contains("J.K. Rowling");

        // Afirmar que o livro foi encontrado
        assertThat(found).isTrue();
        log.debug("Teste Passou: O livro 'Harry Potter and the Sorcerer's Stone' de J.K. Rowling foi encontrado nos resultados da pesquisa.");
    }
}