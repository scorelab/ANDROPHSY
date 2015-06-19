package lk.score.androphsy.indexer.solr;

import lk.score.androphsy.exceptions.PropertyNotDefinedException;
import lk.score.androphsy.indexer.IDataModel;
import lk.score.androphsy.indexer.ImageUtils.OffsetExtractor;
import lk.score.androphsy.model.FileMetadata;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class SolrConnectionTester {
    private final Logger log = LogManager.getLogger(SolrConnectionTester.class);

    private static final String FILE = "src/test/resources/imagefiles/offsetData.txt";

    private SolrDataModel solrDataModel;

	@Before
	public void setup() {
		solrDataModel = new SolrDataModel();
	}

	@Test
	public void testAddMetadata() {

        //creating dummy data
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setContent("hello world To Test the Content");
		fileMetadata.setFilename("hello.txt");
		fileMetadata.setOwner("Sachith");

        //adding it to the solr database
        try {
            solrDataModel.addMetadata(fileMetadata,null);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (SolrServerException e) {
            log.error(e.getMessage());
        } catch (PropertyNotDefinedException e) {
            log.error(e.getMessage());
        }
    }

    @Test
    public void testAddCustomMetadata() {

        //creating dummy data
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setContent("hello world To Test the Content");
        fileMetadata.setFilename("hello.txt");
        fileMetadata.setOwner("Sachith");

        //adding it to the solr database
        OffsetExtractor offsetExtractor = new OffsetExtractor();
        try {
            final Map<String, String> offsetValuePairs = offsetExtractor.getOffsetValuePairs(FILE);
            solrDataModel.addMetadata(fileMetadata,offsetValuePairs);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (SolrServerException e) {
            log.error(e.getMessage());
        } catch (PropertyNotDefinedException e) {
            log.error(e.getMessage());
        }
    }
}
