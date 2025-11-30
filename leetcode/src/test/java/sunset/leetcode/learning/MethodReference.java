package sunset.leetcode.learning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MethodReference {

    @DisplayName("static 메소드를 클래스로 참조")
    @Test
    public void referenceToStaticMethod() {
        Comparator<Integer> lambda = (a, b) -> a - b;
        Comparator<Integer> methodReference = Integer::compare;

        Assertions.assertEquals(lambda.compare(1, 2),  methodReference.compare(1, 2));
    }

    @DisplayName("instance 메소드를 객체로 참조")
    @Test
    public void referenceToBoundInstanceMethod() {
        String prefix = "Hello";
        String targetString = "HelloWorld";

        Predicate<String> lambda = pre -> targetString.startsWith(pre);
        Predicate<String> methodReference = targetString::startsWith;

        Assertions.assertEquals(lambda.test(prefix),  methodReference.test(prefix));
    }

    @DisplayName("instance 메소드를 클래스로 참조. 첫번째 파라미터로 객체 receiver 가 전달된다.")
    @Test
    public void referenceToUnboundInstanceMethod() {
        /*
        예제1
         */
        // Predicate<String>은 String 인스턴스를 하나 받습니다.
        Predicate<String> checkEmpty = String::isEmpty;

        // checkEmpty.test("Java") 호출 시: "Java".isEmpty() 실행 (false)
        boolean result1 = checkEmpty.test("Java");
        Assertions.assertFalse(result1);

        // checkEmpty.test("") 호출 시: "".isEmpty() 실행 (true)
        boolean result2 = checkEmpty.test("");
        Assertions.assertTrue(result2);

        /*
        예제2
         */
        // BiPredicate<List<Integer>, Integer>는 List와 Integer를 받습니다.
        BiPredicate<List<Integer>, Integer> listChecker = List::contains;

        List<Integer> numbers = Arrays.asList(10, 20, 30);

        // listChecker.test(numbers, 20) 호출 시: numbers.contains(20) 실행 (true)
        boolean has20 = listChecker.test(numbers, 20);
        Assertions.assertTrue(has20);

        // listChecker.test(numbers, 50) 호출 시: numbers.contains(50) 실행 (false)
        boolean has50 = listChecker.test(numbers, 50);
        Assertions.assertFalse(has50);
    }

    @DisplayName("생성자를 참조")
    public void referenceToConstructor() {
        Supplier<List<String>> lambda = () -> new ArrayList<>();
        Supplier<List<String>> methodReference = ArrayList::new;

        // Function<String, StringBuilder>의 추상 메소드: StringBuilder apply(String s) (인자 1개)
        Function<String, StringBuilder> lambda1 = (str) -> new StringBuilder(str);
        Function<String, StringBuilder> methodRefence1 = StringBuilder::new;
    }
}
