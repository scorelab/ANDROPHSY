package lk.score.androphsy.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lk.score.androphsy.exceptions.PropertyNotDefinedException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AndrophsyProperties {

    private final Logger logger = LogManager.getLogger(AndrophsyProperties.class);

    private Properties properties = new Properties();
    private InputStream stream = null;
    private static final String ANDROPHSY_PROPERTY_FILE = "conf/androphsy.properties";
    private static final String DEFAULT_ANDROPHSY_PROPERTY_FILE = "../conf/androphsy.properties";

    private static AndrophsyProperties androphsyProperties;

    public static AndrophsyProperties getInstance() {
        if(androphsyProperties == null)
            androphsyProperties = new AndrophsyProperties();

        return androphsyProperties;
    }

    private AndrophsyProperties() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            stream = loader.getResourceAsStream(ANDROPHSY_PROPERTY_FILE);
            if (stream == null) {
                logger.info("Using the default configuration file (androphsy.properties");
                stream = loader.getResourceAsStream(DEFAULT_ANDROPHSY_PROPERTY_FILE);
            }
            properties.load(stream);
            stream.close();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String getProperty(String key) throws PropertyNotDefinedException {
        final String result = (String) properties.get(key);
        if(result == null)
            throw new PropertyNotDefinedException(key);

        return result;
    }
}
