package lk.score.androphsy.indexer.solr;

import lk.score.androphsy.model.FileMetadata;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * This class is used for all the interactions between the system and the SOLR database
 * It supports adding, updating, deleting and querying the database
 */
public class SolrDataModel {
    private SolrIndexer indexer;

    public SolrDataModel() {
        this.indexer = new SolrIndexer();
    }

    /**
     * Adds meta data to be indexed into the SOLR database
     * @param fileMetadata
     * @throws IOException
     * @throws SolrServerException
     */
    public void addMetadata(FileMetadata fileMetadata) throws IOException, SolrServerException {
        indexer.addMetadata(fileMetadata);
    }

    /**
     * updates the filemetadata of a file in the SOLR database
     * @param fileMetadata
     * @throws IOException
     * @throws SolrServerException
     */
    public void updateMetadata(FileMetadata fileMetadata) throws IOException, SolrServerException {
        indexer.updateMetadata(fileMetadata);
    }
}
