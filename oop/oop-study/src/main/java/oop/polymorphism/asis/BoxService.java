package oop.polymorphism.asis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class BoxService {
    public InputStream getInputStream(String fileId) throws FileNotFoundException {
        return new FileInputStream(fileId);
    }
}
