package lk.score.androphsy.indexer.ImageUtils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Map;

public class OffsetExtractorTester {
    private final Logger logger = LogManager.getLogger(OffsetExtractor.class);


    private OffsetExtractor offsetExtractor;
    private static final String FILE_NAME = "src/test/resources/imagefiles/offsetData.txt";
    @Before
    public void setup() {
        offsetExtractor = new OffsetExtractor();
    }

    @Test
    public void testGetOffsetValuePairs() {
        try {
            final Map<String, String> offsetValuePairs = offsetExtractor.getOffsetValuePairs(FILE_NAME);
            logger.info(offsetValuePairs);
        } catch (FileNotFoundException e) {
            logger.error("File Cannot be found: "+ FILE_NAME+ "\t"+e.getMessage());
        }
    }
}
