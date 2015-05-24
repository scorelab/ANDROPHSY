package lk.score.androphsy.indexer;

import lk.score.androphsy.model.FileMetadata;

public interface IDataModel {
    public void addMetadata(FileMetadata metadata);

    public void updateMetadata(FileMetadata metadata);
}
