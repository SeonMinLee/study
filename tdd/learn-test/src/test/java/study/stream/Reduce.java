package study.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class Reduce {

    @Test
    public void 대소비교하기() {
        List<String> words = Arrays.asList("하나", "둘", "셋",
                "넷", "다섯");

        Optional<String> longestString = words.stream()
                .reduce((word1, word2)
                        -> word1.length() >= word2.length()
                        ? word1 : word2);

        Optional<String> longestString2 = words.stream()
                .reduce((word1, word2)
                        -> word1.length() > word2.length()
                        ? word1 : word2);

        assertThat(longestString.get()).isEqualTo("하나");
        assertThat(longestString2.get()).isEqualTo("다섯");
    }

    @Test
    public void 문자열_합치기() {
        // String array
        String[] array = { "제이든은", "정말", "잘", "생겼다" };

        // reduce() is called may be empty.
        Optional<String> stringCombine = Arrays.stream(array)
                .reduce((str1, str2)
                        -> str1 + " " + str2);

        assertThat(stringCombine.get()).isEqualTo("제이든은 정말 잘 생겼다");
    }

    @Test
    public void 숫자_합_구하기() {
        List<Integer> array = Arrays.asList(-2, 0, 4, 6, 8);

        int sum = array.stream().reduce(0,
                (element1, element2) -> element1 + element2);

        assertThat(sum).isEqualTo(16);
    }
}
