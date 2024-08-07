package sunset.i1_설계패러다임.chap07_분할정복.algorithm;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KaratsubaMultiplyTest {

    @ParameterizedTest
    @CsvSource({
            // 작은 숫자들
            "1234, 5678, 7006652",
            "12, 34, 408",
            "56, 78, 4368",
            // 중간 크기 숫자들
            "12345, 67890, 838102050",
            "98765, 43210, 4267635650",
            "11111, 22222, 246908642",
            // 큰 숫자들
            "123456789, 987654321, 121932631112635269",
            "111111111, 999999999, 111111110888888889",
            "987654321, 123456789, 121932631112635269",
            // 매우 큰 숫자들 (천만 단위 이상)
            "12345678901234567890, 98765432109876543210, 1219326311370217952237463801111263526900",
            "99999999999999999999, 88888888888888888888, 8888888888888888888711111111111111111112",
            // 특수 케이스들
            "0, 123456, 0",
            "123456, 0, 0",
            "123456789, 1, 123456789",
            "1, 987654321, 987654321"
    })
    public void 카라츠바_알고리즘_테스트(String aNumber, String bNumber, String expected) {
        String result = KaratsubaMultiply.multiply(aNumber, bNumber);
        assertEquals(expected, result);
    }
}
