package oop.polymorphism.asis;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class DropBoxClient {
    public void copy(String fileId, FileOutputStream out) {
    }

    public List<DbFile> getFiles() {
        return Collections.emptyList();
    }

    public InputStream createStreamOfFile(DbFile dbFile) throws FileNotFoundException {
        return new FileInputStream(new File(dbFile.getFileName()));
    }

    public void deleteFile(String fileId) {
    }
}
