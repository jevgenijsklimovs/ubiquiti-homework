package tests.ui;

import core.AppConfig;
import core.BaseFunc;
import org.junit.jupiter.api.Test;

import java.util.Properties;

public class CompletedKitFlowTests {

    private BaseFunc baseFunc = new BaseFunc();
    private Properties props = AppConfig.getProperties();

    @Test
    public void completeFlow() {
        baseFunc.goToUrl(props.getProperty("url"));
        baseFunc.closeBrowser();
    }
}

