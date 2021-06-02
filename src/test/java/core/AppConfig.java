package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    public static Properties getProperties() {
        Properties properties = new Properties();

        InputStream input;

        try {
            input = new FileInputStream("src/test/resources/config.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
