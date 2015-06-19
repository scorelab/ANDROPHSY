package lk.score.androphsy.indexer.solr;

import lk.score.androphsy.exceptions.PropertyNotDefinedException;
import lk.score.androphsy.indexer.IDataModel;
import lk.score.androphsy.model.FileMetadata;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is used for all the interactions between the system and the SOLR database
 * It supports adding, updating, deleting and querying the database
 */
public class SolrDataModel implements IDataModel{
    private SolrIndexer indexer;
    private SolrQuerier querier;

    public SolrDataModel() {
        this.indexer = new SolrIndexer();
    }

    /**
     * Adds meta data to be indexed into the SOLR database
     * @param fileMetadata
     * @throws IOException
     * @throws SolrServerException
     */
    public void addMetadata(FileMetadata fileMetadata) throws IOException, SolrServerException, PropertyNotDefinedException {
        indexer.addMetadata(fileMetadata);
    }

    /**
     * updates the filemetadata of a file in the SOLR database
     * @param fileMetadata
     * @throws IOException
     * @throws SolrServerException
     */
    public void updateMetadata(FileMetadata fileMetadata) throws IOException, SolrServerException, PropertyNotDefinedException {
        indexer.updateMetadata(fileMetadata);
    }

    /**
     * This method queries the Solr Database with the given query string
     * @param queryString
     * @return List of FileMetadataObjects
     * @throws SolrServerException
     * @throws PropertyNotDefinedException
     * @throws IOException
     */
    public List<FileMetadata> getResultsFromQueryString(String queryString) throws SolrServerException, PropertyNotDefinedException, IOException {
        return querier.querySolr(queryString);
    }

    /**
     * Returns only the matching list of fields for a given query
     * @param queryString
     * @return
     * @throws SolrServerException
     * @throws PropertyNotDefinedException
     * @throws IOException
     */
    public Map<String, Set<String>> getMatchingFieldsFromQury(String queryString) throws SolrServerException, PropertyNotDefinedException, IOException {
        return querier.getHighlightedQuery(queryString);
    }


}
