package lk.score.androphsy.indexer.solr;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import lk.score.androphsy.exceptions.PropertyNotDefinedException;

import lk.score.androphsy.model.FileMetadata;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SolrQuerier {
	private final Logger log = LogManager.getLogger(SolrQuerier.class);
	// to convert the json response into POJO
	private ObjectMapper mapper;

	private SolrClientFactory solrClientFactory;

	public SolrQuerier() {
		solrClientFactory = new SolrClientFactory();
		mapper = new ObjectMapper();
	}

	public List<FileMetadata> querySolr(String queryString) throws SolrServerException,
	                                         PropertyNotDefinedException, IOException {
		SolrQuery query = new SolrQuery();
		query.setQuery(queryString);
		query.setStart(0);
		query.set("defType", "edismax");
        final List<FileMetadata> results = getResults(query);
        return results;
    }

	/**
	 * executes the query and return the results
	 * 
	 * @param solrQuery
	 * @throws IOException
	 * @throws SolrServerException
	 * @throws PropertyNotDefinedException
	 */
	private List<FileMetadata> getResults(SolrQuery solrQuery) throws IOException, SolrServerException,
	                                            PropertyNotDefinedException {

        //execute the query
		HttpSolrServer server = solrClientFactory.createSolrFileStoreClient();
		QueryResponse solrResponse = server.query(solrQuery);

        //process the response
		SolrDocumentList docList = solrResponse.getResults();
        List<FileMetadata> fileMetadataList = new ArrayList<FileMetadata>();
        for (Map doc : docList) {
            fileMetadataList.add(convertJsonToPojo(new JSONObject(doc)));
        }

        return fileMetadataList;
    }

    private FileMetadata convertJsonToPojo(JSONObject document) throws IOException {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FileMetadata fileMetadata = mapper.readValue(String.valueOf(document), FileMetadata.class);
        return fileMetadata;
    }

	public Map<String, Set<String>> getHighlightedQuery(String queryString) throws PropertyNotDefinedException, SolrServerException,IOException {
		SolrQuery query = new SolrQuery();
		query.setQuery(queryString);
		query.setHighlight(true).setHighlightSnippets(1); //set other params as
		query.setParam("hl.fl", "*");

		//execute the query
		HttpSolrServer server = solrClientFactory.createSolrFileStoreClient();
		QueryResponse solrResponse = server.query(query);

		final SolrDocumentList results = solrResponse.getResults();

		if(results.size() <1)
			return null;

		//add the results to a id-> Object Map
		Map<String, FileMetadata> fileMetadataHashMap = new HashMap<String, FileMetadata>();
		FileMetadata tempFileMetadata = new FileMetadata();
		for (Map doc : results) {
			tempFileMetadata = convertJsonToPojo(new JSONObject(doc));
			fileMetadataHashMap.put(tempFileMetadata.getId(), tempFileMetadata);
		}


		//get the highlighted fields for each file
		Map<String, Set<String>> resultsMap = new HashMap<String, Set<String>>();

		//returned highlighted MAP
		Map<String, List<String>> stringListMap = new HashMap<String, List<String>>();

		for (Map.Entry<String,FileMetadata> entry : fileMetadataHashMap.entrySet()) {
			stringListMap = solrResponse.getHighlighting().get(entry.getKey());
			resultsMap.put(entry.getValue().getFilename(),stringListMap.keySet());
		}

		return resultsMap;
	}
}
