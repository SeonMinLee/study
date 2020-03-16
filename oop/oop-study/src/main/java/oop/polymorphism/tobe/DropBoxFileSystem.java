package oop.polymorphism.tobe;

import oop.polymorphism.asis.CloudId;
import oop.polymorphism.asis.DbFile;
import oop.polymorphism.asis.DropBoxClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DropBoxFileSystem implements CloudFileSystem {

    @Override
    public List<CloudFile> getFiles() {
        DropBoxClient dbClient = new DropBoxClient();
        List<DbFile> dbFiles = dbClient.getFiles();
        List<CloudFile> results = new ArrayList<>(dbFiles.size());
        for (DbFile file : dbFiles) {
            DropBoxCloudFile cf = new DropBoxCloudFile(file, dbClient);
            results.add(cf);
        }
        return results;
    }

    @Override
    public List<CloudFile> search(String query) {
        return null;
    }

    @Override
    public CloudFile getFile(String fileId) {
        return null;
    }

    @Override
    public void addFile(String name, File file) {

    }

    @Override
    public void write(FileOutputStream out) {

    }

}
