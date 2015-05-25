package lk.score.androphsy.indexer.solr;

import lk.score.androphsy.exceptions.PropertyNotDefinedException;
import lk.score.androphsy.indexer.IDataModel;
import lk.score.androphsy.model.FileMetadata;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class SolrConnectionTester {
    private final Logger log = LogManager.getLogger(SolrConnectionTester.class);

    private SolrDataModel solrDataModel;

	@Before
	public void setup() {
		solrDataModel = new SolrDataModel();
	}

	@Test
	public void testAddMetadata() {

        //creating dummy data
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setContent("Hello World");
		fileMetadata.setFilename("Hello.txt");
		fileMetadata.setOwner("Sachith");

        //adding it to the solr database
        try {
            solrDataModel.addMetadata(fileMetadata);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (SolrServerException e) {
            log.error(e.getMessage());
        } catch (PropertyNotDefinedException e) {
            log.error(e.getMessage());
        }
    }
}
