package org.myapp.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();
    private static final String PATH_FILL = "application.properties";

    static {
        loadProperties();
    }

    private PropertiesUtil() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(PATH_FILL)) {
            PROPERTIES.load(resourceAsStream);
        } catch (IOException e) {
            Logger.getLogger(PropertiesUtil.class.getName()).log(Level.WARNING, "can't load properties", e);
        }
    }

}
