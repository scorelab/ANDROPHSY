package lk.score.androphsy.util;

import junit.framework.Assert;
import lk.score.androphsy.exceptions.PropertyNotDefinedException;
import org.junit.Before;
import org.junit.Test;

public class AndrophsyPropertiesTester {
    AndrophsyProperties androphsyProperties;

    @Before
    public void setup() {
        androphsyProperties = new AndrophsyProperties();
    }

    @Test
    public void testGetProperties() {
        try {
            androphsyProperties.getProperty(AndrophsyConstants.DATABASE_DRIVER);
            androphsyProperties.getProperty(AndrophsyConstants.DATABASE_USERNAME);
            androphsyProperties.getProperty(AndrophsyConstants.DATABASE_PASSWORD);
            androphsyProperties.getProperty(AndrophsyConstants.DATABASE_URL);
        } catch (PropertyNotDefinedException e) {
            Assert.fail("Exception shouldn't have occurred!!"+ e.getMessage());
        }
    }

    @Test(expected = PropertyNotDefinedException.class)
    public void testNullProperties() throws PropertyNotDefinedException {
        androphsyProperties.getProperty("Foo");
    }

}
