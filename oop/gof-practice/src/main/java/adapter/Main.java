package adapter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FileIO f = new FileProperties();
        try {
            f.readFromFile("./file.txt");
            f.setValue("year", "2020");
            f.setValue("month", "3");
            f.setValue("day", "23");
            f.writeToFile("./newFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
