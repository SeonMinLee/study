package study.stream;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlatMap {

    @Test
    public void 매핑펑션으로서_flatMap() {

        // Creating a List of Strings
        List<String> list = Arrays.asList("5.6", "7.4", "4",
                "1", "2.3");

        // Using Stream flatMap(Function mapper)
        list.stream().flatMap(num -> Stream.of(num)).
                forEach(System.out::println);

    }

    @Test
    public void 맵과_플랫맵_비교() {
        List<String> list = Arrays.asList("Jayden", "JAYDEN",
                "MyNameIsJayden", "hello");

        list.stream().flatMap(str ->
                Stream.of(str.charAt(2))).
                forEach(System.out::println);

        list.stream().map(str ->
                Stream.of(str.charAt(2)))
                .forEach(System.out::println);

        list.stream().map(str ->
                Stream.of(str.charAt(2)))
                .forEach(e ->
                        e.forEach(System.out::println));
    }

    @Test
    public void 평탄화_테스트() {
        List<Integer> primeNumbers = Arrays.asList(5, 7, 11,13);

        List<Integer> oddNumbers = Arrays.asList(1, 3, 5);

        List<Integer> evenNumbers = Arrays.asList(2, 4, 6, 8);

        List<List<Integer>> listOfListOfInts =
                Arrays.asList(primeNumbers, oddNumbers, evenNumbers);

        System.out.println("평탄화(flatten) 이전의 구조: \n" + listOfListOfInts);

        List<Integer> listOfInts = listOfListOfInts.stream()
                .flatMap(list -> list.stream())
                .collect(Collectors.toList());

        System.out.println("평탄화(flatten) 이후의 구조: \n" + listOfInts);
    }
}
