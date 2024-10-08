package sunset.i1_설계패러다임.chap07_분할정복.algorithm;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sunset.i1_설계패러다임.chap07_분할정복.algorithm.KaratsubaMultiply.DecimalByteArrayUtils.toNumberString;

public class KaratsubaMultiplyTest {

    @ParameterizedTest
    @CsvSource({
            // 작은 숫자들
            "1, 1, 1",
            "11, 11, 121",
            "111, 111, 12321",
            "1111, 1111, 1234321",
            "11111, 11111, 123454321",
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
    public void 카라츠바_알고리즘_테스트(String aNumberString, String bNumberString, String expected) {
        int maxLength = Math.max(aNumberString.length(), bNumberString.length());
        byte[] aNumber = KaratsubaMultiply.DecimalByteArrayUtils.numberStringToByteArrayWithZeroPadding(aNumberString, maxLength);
        byte[] bNumber = KaratsubaMultiply.DecimalByteArrayUtils.numberStringToByteArrayWithZeroPadding(bNumberString, maxLength);

        KaratsubaMultiply.Solution solution = new KaratsubaMultiply.Solution(aNumber, bNumber, 1, 0);
        assertEquals(expected, KaratsubaMultiply.DecimalByteArrayUtils.toNumberString(solution.solve()));
    }

    @ParameterizedTest
    @CsvSource({
            // 특이 케이스
            "0, 0, 0",
            "0, 123, 123",
            // 적당한 숫자들
            "123, 456, 579",
            // 큰 숫자들
            "99999, 99999, 199998",
            "99, 99999, 100098",
    })
    public void 배열_덧셈_테스트(String aNumberString, String bNumberString, String expected) {
        byte[] aNumber = KaratsubaMultiply.DecimalByteArrayUtils.numberStringToByteArrayWithZeroPadding(aNumberString, aNumberString.length());
        byte[] bNumber = KaratsubaMultiply.DecimalByteArrayUtils.numberStringToByteArrayWithZeroPadding(bNumberString, bNumberString.length());

        byte[] result = KaratsubaMultiply.DecimalByteArrayCalculator.add(aNumber, bNumber);
        assertEquals(expected, toNumberString(result));
    }

    @ParameterizedTest
    @CsvSource({
            // 특이 케이스
            "0, 0, 0",
            "123, 0, 123",
            // 적당한 숫자들
            "456, 123, 333",
            // 큰 숫자들
            "99999, 99999, 0",
            "99999, 99, 99900",
    })
    public void 배열_뺄셈_테스트(String aNumberString, String bNumberString, String expected) {
        byte[] aNumber = KaratsubaMultiply.DecimalByteArrayUtils.numberStringToByteArrayWithZeroPadding(aNumberString, aNumberString.length());
        byte[] bNumber = KaratsubaMultiply.DecimalByteArrayUtils.numberStringToByteArrayWithZeroPadding(bNumberString, bNumberString.length());

        byte[] result = KaratsubaMultiply.DecimalByteArrayCalculator.sub(aNumber, bNumber);
        assertEquals(expected, toNumberString(result));
    }

    @ParameterizedTest
    @CsvSource({
            // 작은 숫자들
            "1, 1, 1",
            "11, 11, 121",
            "111, 111, 12321",
            "1111, 1111, 1234321",
            "11111, 11111, 123454321",
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
    public void 배열_곱셈_테스트(String aNumberString, String bNumberString, String expected) {
        int maxLength = Math.max(aNumberString.length(), bNumberString.length());
        byte[] aNumber = KaratsubaMultiply.DecimalByteArrayUtils.numberStringToByteArrayWithZeroPadding(aNumberString, maxLength);
        byte[] bNumber = KaratsubaMultiply.DecimalByteArrayUtils.numberStringToByteArrayWithZeroPadding(bNumberString, maxLength);

        byte[] result = KaratsubaMultiply.DecimalByteArrayCalculator.multiply(aNumber, bNumber);
        assertEquals(expected, toNumberString(result));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 2, 0",
            "12, 0, 12",
            "12, 1, 120",
            "12, 2, 1200"
    })
    public void 배열_pow10_테스트(String aNumberString, String expString, String expected) {
        byte[] aNumber = KaratsubaMultiply.DecimalByteArrayUtils.numberStringToByteArrayWithZeroPadding(aNumberString, aNumberString.length());
        int exp = Integer.parseInt(expString);

        byte[] result = KaratsubaMultiply.DecimalByteArrayCalculator.pow10(aNumber, exp);
        assertEquals(expected, toNumberString(result));
    }
}
