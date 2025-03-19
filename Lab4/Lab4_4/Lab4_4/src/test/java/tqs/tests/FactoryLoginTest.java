package tqs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.FactoryLoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FactoryLoginTest {
    private FactoryLoginPage loginPage;

    @BeforeEach
    public void setUp() {
        loginPage = new FactoryLoginPage("chrome", 10);
    }

    @Test
    public void testLoginComSucesso() {
        loginPage.enterUsername("user");
        loginPage.enterPassword("password");
        loginPage.clickLogin();

        assertTrue(loginPage.isLoginSuccessful());
    }

    @AfterEach
    public void tearDown() {
        loginPage.closeBrowser();
    }
}
