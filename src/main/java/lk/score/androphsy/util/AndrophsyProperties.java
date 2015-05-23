package lk.score.androphsy.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lk.score.androphsy.exceptions.PropertyNotDefinedException;

public class AndrophsyProperties {
    private Properties properties = new Properties();
    private InputStream stream = null;
    private static final String ANDROPHSY_PROPERTY_FILE = "androphsy.properties";

    public AndrophsyProperties() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            stream = loader.getResourceAsStream(ANDROPHSY_PROPERTY_FILE);
            properties.load(stream);
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) throws PropertyNotDefinedException {
        final String result = (String) properties.get(key);
        if(result == null)
            throw new PropertyNotDefinedException(key);

        return result;
    }
}
