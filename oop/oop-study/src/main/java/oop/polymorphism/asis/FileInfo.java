package oop.polymorphism.asis;

public class FileInfo {
    private CloudId cloudId;
    private String fileId;
    private String name;
    private long length;

    public CloudId getCloudId() {
        return cloudId;
    }

    public void setCloudId(CloudId cloudId) {
        this.cloudId = cloudId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
