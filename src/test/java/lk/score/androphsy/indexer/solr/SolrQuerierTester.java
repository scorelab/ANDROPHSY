package lk.score.androphsy.indexer.solr;

import lk.score.androphsy.exceptions.PropertyNotDefinedException;
import lk.score.androphsy.model.FileMetadata;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

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

    @Test
    public void testHighlightedQuerying() {
        String dummyQuery = "printf";
        try {
            final Map<String, Set<String>> highlightedQuery = solrQuerier.getHighlightedQuery(dummyQuery);

            Iterator it = highlightedQuery.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String,Set<String>> pair = (Map.Entry)it.next();
                log.info("Filename: " + pair.getKey());
                for (String matchingField : pair.getValue()) {
                    log.info("Field: "+matchingField);
                }
                log.info("\n");
                it.remove(); // avoids a ConcurrentModificationException
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
