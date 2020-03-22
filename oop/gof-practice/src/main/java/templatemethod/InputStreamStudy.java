package templatemethod;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamStudy {

    public static void main(String[] args) {
        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
    }
}
