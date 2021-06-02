package pages;

import core.BaseFunc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnifiConsolePage {

    private BaseFunc baseFunc;

    private final By PRODUCT_TITLE = By.xpath(".//h3[contains(@class, 'medium-down--hide add8bottom')]");
    private final By CONSOLE_TYPE = By.xpath("");
    private final By ACTIVE_MENU = By.xpath(".//div[contains(@class, 'comShopHeader__item__title active')]");

    private final Logger LOGGER = LogManager.getLogger(this.getClass());



    public UnifiConsolePage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;

        baseFunc.waitForText(PRODUCT_TITLE, "Complete Your UniFi OS Console");
        assertEquals("Network", getActiveMenuText(), "Wrong page");
        LOGGER.info("Console build page is opened");
    }

    private String getActiveMenuText(){
        return baseFunc.getElement(ACTIVE_MENU).getText();
    }
}
