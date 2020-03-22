package adapter;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public class FileProperties implements FileIO {

    private Properties properties = new Properties();

    @Override
    public void readFromFile(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String data;
        while ((data = bufferedReader.readLine()) != null) {
            System.out.println(data);
        }

        bufferedReader.close();
        fileReader.close();
    }

    @Override
    public void writeToFile(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for (Map.Entry<Object, Object> objectObjectEntry : properties.entrySet()) {
            String str = objectObjectEntry.getKey() + "=" + objectObjectEntry.getValue();
            System.out.println(str);
            bufferedWriter.write(str);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
        fileWriter.close();
    }

    @Override
    public void setValue(String key, String value) {
        properties.setProperty(key, value);
    }

    @Override
    public String getValue(String key) {
        return properties.get(key).toString();
    }
}
