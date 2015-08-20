package lk.score.androphsy.indexer.solr;

import lk.score.androphsy.exceptions.PropertyNotDefinedException;
import lk.score.androphsy.util.AndrophsyConstants;
import lk.score.androphsy.util.AndrophsyProperties;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

public class SolrClientFactory {
	public HttpSolrServer createSolrFileStoreClient() throws PropertyNotDefinedException {
		HttpSolrServer server = null;

		server =
		         new HttpSolrServer(
		                            AndrophsyProperties.getInstance()
		                                               .getProperty(AndrophsyConstants.SOLR_FILESTORE_URL));
		HttpClientUtil.setBasicAuth((DefaultHttpClient) server.getHttpClient(),
		                            AndrophsyProperties.getInstance()
		                                               .getProperty(AndrophsyConstants.SOLR_USERNAME),
		                            AndrophsyProperties.getInstance()
		                                               .getProperty(AndrophsyConstants.SOLR_PASSWORD));

		return server;
	}
}
