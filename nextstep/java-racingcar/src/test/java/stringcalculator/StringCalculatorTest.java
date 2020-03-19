package stringcalculator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


public class StringCalculatorTest {

    public static Map<String, Calculator> calculatorMap = new HashMap<>();

    static {
        calculatorMap.put("+",  Calculator.getInstance());
        calculatorMap.put("-",  Calculator.getInstance());
        calculatorMap.put("*",  Calculator.getInstance());
        calculatorMap.put("/",  Calculator.getInstance());
    }

    @Test
    public void secondTest() {
        Assertions.assertThat(calculatorMap).hasSize(4);

        int add = calculatorMap.get("+").add(1, 2);
        assertThat(add).isEqualTo(3);
    }

    @Test
    public void firstTest() {
        //given
        String source = "2 + 3 * 4 / 2";
        Queue<String> strings = Stream.of(source.split(" "))
                .collect(Collectors.toCollection(LinkedList::new));

        // F 2 3 4 2 L
        List<Integer> operands = new ArrayList<>();

        // F + * / L
        List<String> operators = new ArrayList<>();

        for (String string : strings) {
            findSymbol(string, operands, operators);
        }

//        Stream.of(strings).forEach(e -> findSymbol(e.poll(), operands, operators));

        for (int i = 0; i < operators.size(); i++) {

            System.out.println("index: " + 0);

            System.out.println("operator.size: " + operators.size());
            System.out.println("operand.size: " + operands.size());

            if (operators.get(i).equals("+")) {
                int leftOperand = operands.get(0);
                operands.remove(0);
                int rightOperand = operands.get(0);
                operands.remove(0);

                int e = leftOperand + rightOperand;
                System.out.println(leftOperand + " + " + rightOperand + " = " + e);
                operands.add(0, e);
            } else if (operators.get(i).equals("-")) {
                int leftOperand = operands.get(0);
                operands.remove(0);
                int rightOperand = operands.get(0);
                operands.remove(0);

                int e = leftOperand - rightOperand;
                System.out.println(leftOperand + " - " + rightOperand + " = " + e);
                operands.add(0, e);
            } else if (operators.get(i).equals("*")) {
                int leftOperand = operands.get(0);
                operands.remove(0);
                int rightOperand = operands.get(0);
                operands.remove(0);

                int e = leftOperand * rightOperand;
                System.out.println(leftOperand + " * " + rightOperand + " = " + e);
                operands.add(0, e);
            } else if (operators.get(i).equals("/")) {
                int leftOperand = operands.get(0);
                operands.remove(0);
                int rightOperand = operands.get(0);
                operands.remove(0);

                int e = leftOperand / rightOperand;
                System.out.println(leftOperand + " / " + rightOperand + " = " + e);
                operands.add(0, e);
            }
        }

        assertThat(operands).hasSize(1);
        assertThat(operands.get(0)).isEqualTo(10);
    }

    private void findSymbol(String item, List<Integer> operands, List<String> operators) {

        System.out.println("e: " + item);

        try {
            operands.add(Integer.parseInt(item));
        } catch (NumberFormatException e) {
            operators.add(item);
        }
    }

    @Test
    public void listTest() {
        //given
        List<String> testList = new ArrayList<>();
        testList.add("1");
        testList.add("2");

        testList.remove(0);
        testList.remove(0);

        testList.add("3");

        assertThat(testList).contains("3");
    }
}
