package adapter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class FileProperties implements FileIO {

    private Properties properties = new Properties();

    @Override
    public void readFromFile(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        properties.load(fileReader);
    }

    @Override
    public void writeToFile(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        properties.store(fileWriter, "written by FileProperties");
    }

    @Override
    public void setValue(String key, String value) {
        properties.setProperty(key, value);
    }

    @Override
    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
