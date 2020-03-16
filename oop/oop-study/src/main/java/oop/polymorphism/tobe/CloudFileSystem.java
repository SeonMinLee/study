package oop.polymorphism.tobe;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public interface CloudFileSystem {

    List<CloudFile> getFiles();

    List<CloudFile> search(String query);

    CloudFile getFile(String fileId);

    void addFile(String name, File file);

    void write(FileOutputStream out);
}
