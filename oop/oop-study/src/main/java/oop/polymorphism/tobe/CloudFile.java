package oop.polymorphism.tobe;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public interface CloudFile {
    String getId();
    String getName();
    String getLength();
    boolean hasUrl();
    String getUrl();
    InputStream getInputStream();
    void write(OutputStream out);
    void delete();
}
