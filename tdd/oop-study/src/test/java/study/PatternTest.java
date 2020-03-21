package study;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class PatternTest {

    //    private static final Pattern COMMA_PATTERN = Pattern.compile("(?:,)(?:|$)");
    private static final Pattern COMMA_PATTERN = Pattern.compile("(?:,|$)");

    @Test
    public void test() {
        String input = "이정원+jayden+조하나";
        boolean b = COMMA_PATTERN.matcher(input).find();

        String[] split = input.split(",");

        assertThat(b).isTrue();
        assertThat(split).hasSize(1);
    }

    @Test
    public void test2() {
        String input = "이정원+jayden+조하,나";
        boolean b = COMMA_PATTERN.matcher(input).find();

        String[] split = input.split(",");

        assertThat(b).isTrue();
        assertThat(split).hasSize(2);
    }

    @Test
    public void test3() {
        String input = "이정,원+jayden+조하,나";
        boolean b = COMMA_PATTERN.matcher(input).find();

        String[] split = input.split(",");

        assertThat(b).isTrue();
        assertThat(split).hasSize(3);
    }

    @Test
    public void test4() {
        String input = "aaa,bbb,ccc,ddd";
        boolean b = COMMA_PATTERN.matcher(input).find();

        String[] split = input.split(",");

        assertThat(b).isTrue();
        assertThat(split).hasSize(4);
    }

    @Test
    public void test5() {
        String input = "";
        boolean b = COMMA_PATTERN.matcher(input).find();

        String[] split = input.split(",");

        assertThat(b).isTrue();
        assertThat(split).hasSize(1);
    }
}
