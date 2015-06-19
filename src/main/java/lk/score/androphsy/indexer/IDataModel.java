package lk.score.androphsy.indexer;

import lk.score.androphsy.exceptions.PropertyNotDefinedException;
import lk.score.androphsy.model.FileMetadata;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDataModel {
    void addMetadata(FileMetadata metadata) throws IOException, SolrServerException, PropertyNotDefinedException;

    void updateMetadata(FileMetadata metadata) throws IOException, SolrServerException, PropertyNotDefinedException;

    List<FileMetadata> getResultsFromQueryString(String queryString) throws SolrServerException, PropertyNotDefinedException, IOException;

    Map<String, Set<String>> getMatchingFieldsFromQury(String queryString) throws SolrServerException, PropertyNotDefinedException, IOException;
}
