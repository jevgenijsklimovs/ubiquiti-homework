package tests.ui;

import core.AppConfig;
import core.BaseFunc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pages.UnifiConsolePage;


import java.util.Properties;

public class CheckKitFlowTests {

    private BaseFunc baseFunc = new BaseFunc();
    private Properties properties = AppConfig.getProperties();

    @Test
    public void checkKitFlowTest() throws InterruptedException {
        baseFunc.goToUrl(properties.getProperty("url"));

        UnifiConsolePage unifiConsolePage = new UnifiConsolePage(baseFunc);
        unifiConsolePage.selectItemByName("Dream Machine Pro + HDD-8TB");
        unifiConsolePage.selectItemByName("None");
        Thread.sleep(5000);
    }

    @AfterEach
    public void closeBrowser() {
        baseFunc.closeBrowser();
    }
}
