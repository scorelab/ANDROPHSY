package lk.score.androphsy.model;

public class FileMetadata {
    private String filename;
    private String owner;
    private String content;
    private String id;

    public FileMetadata() {
    }

    public FileMetadata(String filename, String owner, String content, String id) {
        this.filename = filename;
        this.owner = owner;
        this.content = content;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
