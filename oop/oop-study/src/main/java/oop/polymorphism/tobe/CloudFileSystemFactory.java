package oop.polymorphism.tobe;

import oop.polymorphism.asis.CloudId;

import static oop.polymorphism.asis.CloudId.*;

public class CloudFileSystemFactory {

    static CloudFileSystem getFileSystem(CloudId cloudId) {
        if (cloudId == DROPBOX) {
            return new DropBoxFileSystem();
        }
        return null;
    }
}
