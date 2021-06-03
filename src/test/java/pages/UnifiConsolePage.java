package pages;

import core.BaseFunc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnifiConsolePage {

    private BaseFunc baseFunc;

    private final By PRODUCT_TITLE = By.xpath(".//h3[contains(@class, 'medium-down--hide add8bottom')]");
    private final By ITEM_CARD = By.xpath(".//button[contains(@class, 'enhanced-wizard-card relative flex flex-col flex-jc-center enhanced-wizard-card')]/div/div");
    private final By ACTIVE_MENU = By.xpath(".//div[contains(@class, 'comShopHeader__item__title active')]");

    private final By LOL = By.xpath("//input[contains(@class, 'quantity-container__input')]");
    private final By LOL2 = By.xpath(".//div[@class='center enhanced-wizard-card__secondary-title']");
    private final By LOL3 = By.xpath(".//div[contains(@class, 'add4bottom comProductTile__label comProductTile__label--ea at-center')]");

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    public UnifiConsolePage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;

        baseFunc.waitForText(PRODUCT_TITLE, "Complete Your UniFi OS Console");
        assertEquals("NETWORK", getActiveMenuText(), "Wrong page");
        LOGGER.info("Console build page is opened");
    }

    public String getActiveMenuText() {
        return baseFunc.getElement(ACTIVE_MENU).getText();
    }

    public void selectItemByName(String name) {
        List<WebElement> elements = baseFunc.getElements(ITEM_CARD);

        for (WebElement element : elements) {
            if (element.getText().equals(name)) {
                baseFunc.move(element);
                if (!element.findElement(LOL2).isDisplayed()) {
                    if (!element.findElement(LOL3).isDisplayed()) {
                        element.click();
                        break;
                    }
                }
            }
        }
    }
}
