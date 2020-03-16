package oop.polymorphism.tobe;

import oop.polymorphism.asis.CloudId;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class CloudFileManager {

    public List<CloudFile> getFileInfos(CloudId cloudId) {
        CloudFileSystem fileSystem =
                CloudFileSystemFactory.getFileSystem(cloudId);
        return fileSystem.getFiles();
    }

    public void download(CloudFileSystem file, File localTarget) {
        try {
            file.write(new FileOutputStream(localTarget));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
