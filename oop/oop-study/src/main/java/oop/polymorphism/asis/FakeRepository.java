package oop.polymorphism.asis;

import java.util.ArrayList;
import java.util.List;

public class FakeRepository {
    public List<DbFile> getFiles() {
        return new ArrayList<DbFile>();
    }
}
