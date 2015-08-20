package lk.score.androphsy.indexer.solr;

import lk.score.androphsy.exceptions.PropertyNotDefinedException;
import lk.score.androphsy.model.FileMetadata;
import lk.score.androphsy.model.MetadataFields;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class SolrIndexer {
    private final Logger logger = LogManager.getLogger(SolrIndexer.class);

    private SolrClientFactory solrClientFactory;

    public SolrIndexer() {
        solrClientFactory = new SolrClientFactory();
    }

    public void addMetadata(FileMetadata metadata,Map<String,String> customMetadata) throws IOException, SolrServerException, PropertyNotDefinedException {
        addData(metadata,customMetadata);
    }

    public void updateMetadata(FileMetadata metadata,Map<String,String> customMetadata) throws IOException, SolrServerException, PropertyNotDefinedException {
        addData(metadata,customMetadata);
    }
    /**
     * Adds data to Apache Solr
     * @param fileMetadata
     * @throws IOException
     * @throws SolrServerException
     */
    private void addData(FileMetadata fileMetadata,Map<String,String> customMetadata) throws IOException, SolrServerException, PropertyNotDefinedException {
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
        metadataDoc.addField(MetadataFields.CONTENT, fileMetadata.getContent());

        if(customMetadata!=null) {
            Object[] metadata_keys = customMetadata.keySet().toArray();
            String key = "";
            for (int i = 0; i < metadata_keys.length; i++) {
                key = (String) metadata_keys[i];
                if (key.equals("id"))
                    continue;
                metadataDoc.addField(key,
                        customMetadata.get(key)
                );
            }
        }
        metadataClient.add(metadataDoc,1);

        logger.info("Indexed Metadata file: "+ fileMetadata.getFilename());
    }
}
