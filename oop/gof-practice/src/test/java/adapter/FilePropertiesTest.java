package adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FilePropertiesTest {

    @Test
    public void test() {
        String s = "year=1999";

        String[] split = s.split("=");

        System.out.println(s);

        Assertions.assertEquals(split[0], "year", "split[0] is year");
        Assertions.assertEquals(split[1], "1999", "split[1] is 1999");
    }

}
