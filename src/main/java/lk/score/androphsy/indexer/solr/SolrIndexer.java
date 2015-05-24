package lk.score.androphsy.indexer.solr;

import lk.score.androphsy.model.FileMetadata;
import lk.score.androphsy.model.MetadataFields;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.UUID;

public class SolrIndexer {
    private final Logger logger = LogManager.getLogger(SolrIndexer.class);

    private SolrClientFactory solrClientFactory;

    public SolrIndexer() {
        solrClientFactory = new SolrClientFactory();
    }

    public void addMetadata(FileMetadata metadata) throws IOException, SolrServerException {
        addData(metadata);
    }

    public void updateMetadata(FileMetadata metadata) throws IOException, SolrServerException {
        addData(metadata);
    }
    /**
     * Adds data to Apache Solr
     * @param fileMetadata
     * @throws IOException
     * @throws SolrServerException
     */
    private void addData(FileMetadata fileMetadata) throws IOException, SolrServerException {
        HttpSolrServer metadataClient = solrClientFactory.createSolrFileStoreClient();

        SolrInputDocument metadataDoc = new SolrInputDocument();

        if (fileMetadata.getId() == "") {
            String primaryKey = UUID.randomUUID().toString();
            metadataDoc.addField(MetadataFields.ID, primaryKey);
        } else {
            metadataDoc.addField(MetadataFields.ID, fileMetadata.getId());
        }

        metadataDoc.addField(MetadataFields.FILE_NAME, fileMetadata.getFilename());
        metadataDoc.addField(MetadataFields.OWNER, fileMetadata.getOwner());

        metadataClient.add(metadataDoc,1);

        logger.info("Indexed Metadata file: "+ fileMetadata.getFilename());
    }
}
