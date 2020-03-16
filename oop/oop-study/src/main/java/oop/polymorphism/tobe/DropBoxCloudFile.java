package oop.polymorphism.tobe;

import oop.polymorphism.asis.DbFile;
import oop.polymorphism.asis.DropBoxClient;
import oop.polymorphism.asis.FakeRepository;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public class DropBoxCloudFile implements CloudFile{

    private DropBoxClient dbClient;
    private DbFile dbFile;

    public DropBoxCloudFile(DbFile dbFile, DropBoxClient dbClient) {
        this.dbClient = dbClient;
        this.dbFile = dbFile;
    }

    @Override
    public String getId() {
        return dbFile.getFileId();
    }

    @Override
    public String getName() {
        return dbFile.getFileName();
    }

    @Override
    public String getLength() {
        return dbFile.getLength();
    }

    @Override
    public boolean hasUrl() {
        return true;
    }

    @Override
    public String getUrl() {
        return dbFile.getFileUrl();
    }

    @Override
    public InputStream getInputStream() {
        try {
            return dbClient.createStreamOfFile(dbFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void write(OutputStream out) {
        // 구현 생략
    }

    @Override
    public void delete() {
        dbClient.deleteFile(dbFile.getFileId());
    }
}
