package lk.score.androphsy.indexer.solr;

import lk.score.androphsy.exceptions.PropertyNotDefinedException;
import lk.score.androphsy.model.FileMetadata;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class SolrQuerierTester {
    private final Logger log = LogManager.getLogger(SolrQuerierTester.class);

    private SolrQuerier solrQuerier;
    @Before
    public void setup() {
        solrQuerier = new SolrQuerier();
    }

    @Test
    public void testSolrQuerying() {
        String dummyQuery = "hello";
        try {
            final List<FileMetadata> fileMetadataList = solrQuerier.querySolr(dummyQuery);
            for (FileMetadata file : fileMetadataList) {
                log.info(file.getFilename() +"\t" + file.getContent()+"\n");
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (PropertyNotDefinedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
