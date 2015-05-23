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
        } catch (PropertyNotDefinedException e) {
            Assert.fail("Exception shouldn't have occurred!!");
        }
    }

    @Test(expected = PropertyNotDefinedException.class)
    public void testNullProperties() throws PropertyNotDefinedException {
        androphsyProperties.getProperty("Foo");
    }

}
