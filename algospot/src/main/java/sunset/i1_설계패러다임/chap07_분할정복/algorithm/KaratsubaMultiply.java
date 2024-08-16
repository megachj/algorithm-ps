package sunset.i1_설계패러다임.chap07_분할정복.algorithm;

import sunset.library.DividableRange;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class KaratsubaMultiply {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String a = br.readLine();
        String b = br.readLine();

        String result = multiply(a, b);
        System.out.printf("%s * %s = %s\n", a, b, result);
    }

    /**
     * 카라츠바 빠른곱셈 알고리즘으로 a * b 연산을 한다.<br>
     * 시간 복잡도: O(n^(lg3))<br>
     *
     * @param aNumber 정수 문자열
     * @param bNumber 정수 문자열
     * @return 곱셉 결과 문자열
     */
    public static String multiply(String aNumber, String bNumber) {
        if (aNumber == null || bNumber == null || aNumber.isEmpty() || bNumber.isEmpty()) {
            throw new IllegalArgumentException("파라미터가 이상합니다.");
        }

        int n = Math.max(aNumber.length(), bNumber.length());
        byte[] a = numberStringToByteArray(aNumber, n);
        byte[] b = numberStringToByteArray(bNumber, n);

        byte[] result;
        if (isGreaterOrEqual(a, b)) {
            result = solveDivideConquer(a, DividableRange.of(0, n), b, DividableRange.of(0, n));
        } else {
            result = solveDivideConquer(b, DividableRange.of(0, n), a, DividableRange.of(0, n));
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = result.length - 1; i >= 0; i--) {
            stringBuilder.append(result[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * 숫자 문자열을 n 사이즈인 byte 배열로 변환한다.
     */
    private static byte[] numberStringToByteArray(String numberString, int n) {
        // 문자열의 길이만큼의 byte 배열 생성
        byte[] byteArray = new byte[n];

        // 각 문자를 byte로 변환하여 배열에 저장
        int numberLength = numberString.length();
        for (int i = 0; i < numberLength; i++) {
            byteArray[i] = (byte) (numberString.charAt(numberLength - i - 1) - '0');
        }

        return byteArray;
    }

    /**
     * a >= b 인지 확인한다.
     */
    private static boolean isGreaterOrEqual(byte[] a, byte[] b) {
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] > b[i]) {
                return true;
            }
            if (a[i] < b[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * a >= b 임을 가정하고 a * b 연산을 한다.(재귀)
     */
    private static byte[] solveDivideConquer(
            byte[] a, DividableRange aRange, byte[] b, DividableRange bRange
    ) {
        boolean aRangeDividable = aRange.isDividable();

        // 쪼개지는 경우
        if (aRangeDividable) {
            DividableRange a1Range = aRange.getDividedRight();
            DividableRange a0Range = bRange.getDividedLeft();

            DividableRange b1Range = bRange.getDividedRight();
            DividableRange b0Range = bRange.getDividedLeft();

            int halfN = (aRange.middle - aRange.start);

            // 곱셈1: a1 * b1
            byte[] a1b1 = solveDivideConquer(a, a1Range, b, b1Range);
            // 곱셈2: a0 * b0
            byte[] a0b0 = solveDivideConquer(a, a0Range, b, b0Range);

            // a1 + a0
            byte[] aPlus = plusBytes(
                    a, a1Range.start, a1Range.endExclusive, a, a0Range.start, a0Range.endExclusive);
            // b1 + b0
            byte[] bPlus = plusBytes(
                    b, b1Range.start, b1Range.endExclusive, b, b0Range.start, b0Range.endExclusive);

            int n = Math.max(aPlus.length, bPlus.length);
            byte[] aPlusArray = upSizing(aPlus, n);
            byte[] bPlusArray = upSizing(bPlus, n);

            // 곱셈3: (a1 + a0)*(b1 + b0) = a1*b1 + a1*b0 + a0*b1 + a0*b0
            byte[] aPlusBPlus = solveDivideConquer(
                    aPlusArray, DividableRange.of(0, aPlusArray.length), bPlusArray, DividableRange.of(0, bPlusArray.length)
            );

            // a1b1 * 10^n
            byte[] z1 = multiplyDecimalExp(a1b1, halfN * 2);
            // a0b0
            byte[] z3 = a0b0;
            // ((a1+a0)(b1+b0)-(a1b1+a0b0)) * 10^(n/2)
            byte[] z2 = plusBytes(a1b1, 0, a1b1.length, a0b0, 0, a0b0.length);
            z2 = minusBytes(aPlusBPlus, 0, aPlusBPlus.length, z2, 0, z2.length);
            z2 = multiplyDecimalExp(z2,  halfN);

            byte[] result = plusBytes(z1, 0, z1.length, z2, 0, z2.length);
            result = plusBytes(result, 0, result.length, z3, 0, z3.length);
            return result;
        }
        // 안 쪼개지는 경우. 즉 원소가 1개씩일 때
        else {
            int value = a[aRange.start] * b[bRange.start];
            if (value > 10) {
                byte[] result = new byte[2];
                result[0] = (byte)(value % 10);
                result[1] = (byte)(Math.floorDiv(value, 10));
                return result;
            } else {
                byte[] result = new byte[1];
                result[0] = (byte) value;
                return result;
            }
        }
    }

    /**
     * 배열 사이즈를 늘린다.
     */
    private static byte[] upSizing(byte[] a, int n) {
        if (a.length > n) {
            throw new IllegalArgumentException("업사이징하려는 사이즈가 더 작음");
        }

        if (a.length == n) {
            return a;
        }

        byte[] upsizingArray = new byte[n];
        for (int i = 0; i < n; i++) {
            if (i < a.length) {
                upsizingArray[i] = a[i];
            } else {
                upsizingArray[i] = 0;
            }
        }
        return upsizingArray;
    }

    /**
     * a + b 연산을 한다.
     */
    private static byte[] plusBytes(
            byte[] a, int aStart, int aEndExclusive,
            byte[] b, int bStart, int bEndExclusive
    ) {
        int aLength = aEndExclusive - aStart;
        int bLength = bEndExclusive - bStart;

        byte[] resultArray = new byte[Math.max(aLength, bLength) + 1];

        // 각 자릿수에 맞춰 덧셈 연산을 한다.
        for (int i = 0; i < resultArray.length; ++i) {
            byte aValue = i < aLength ? a[aStart + i] : 0;
            byte bValue = i < bLength ? b[bStart + i] : 0;

            resultArray[i] = (byte) (aValue + bValue);
        }

        // 10진법에 맞게 각 자릿수를 조정한다.
        resultArray = normalize(resultArray);

        return trimBytes(resultArray);
    }

    /**
     * a >= b 인 수에 대해 a - b 연산을 한다.<br>
     * 만약 b 가 더 큰 경우는 고려하지 않는다. 따라서 b 가 더 큰 경우는 자릿수에 음수가 포함되게 된다.<br>
     */
    private static byte[] minusBytes(
            byte[] a, int aStart, int aEndExclusive,
            byte[] b, int bStart, int bEndExclusive
    ) {
        int aLength = aEndExclusive - aStart;
        int bLength = bEndExclusive - bStart;

        byte[] resultArray = new byte[Math.max(aLength, bLength) + 1];

        // 각 자릿수에 맞춰 뺄셈 연산을 한다.
        for (int i = 0; i < resultArray.length; ++i) {
            byte aValue = i < aLength ? a[aStart + i] : 0;
            byte bValue = i < bLength ? b[bStart + i] : 0;

            resultArray[i] = (byte) (aValue - bValue);
        }

        // 각 자릿수의 값이 음수이면 윗 자릿수에서 빌려와서 양수로 만들어준다.
        for (int i = 0; i < resultArray.length; ++i) {
            if (resultArray[i] < 0) {
                resultArray[i+1] -= 1;
                resultArray[i] += 10;
            }
        }

        // 10진법에 맞게 각 자릿수를 조정한다.
        resultArray = normalize(resultArray);

        return trimBytes(resultArray);
    }

    /**
     * 10진법에 맞게 자릿수들에서 올림을 통해 값을 조정한다.
     */
    private static byte[] normalize(byte[] a) {
        byte curPos, nextPos;
        for (int i = 0; i < a.length; ++i) {
            curPos = (byte) (a[i] % 10);
            nextPos = (byte) Math.floorDiv(a[i], 10);

            a[i] = curPos;
            if (i+1 < a.length) {
                a[i + 1] += nextPos;
            } else if (nextPos > 0) {
                throw new IllegalStateException("배열 길이가 필요한 것보다 짧습니다! 확인이 필요해요.");
            }
        }
        return trimBytes(a);
    }

    /**
     * a * 10^(decimalExp)
     */
    private static byte[] multiplyDecimalExp(byte[] a, int decimalExp) {
        byte[] result = new byte[a.length + decimalExp];
        for (int i = 0; i < decimalExp; i++) {
            result[i] = 0;
        }
        System.arraycopy(a, 0, result, decimalExp + 0, a.length);

        return trimBytes(result);
    }

    private static byte[] trimBytes(byte[] a) {
        // 가장 큰 자릿수의 인덱스를 구한다.
        int lastIndex = a.length - 1;
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] != 0) {
                lastIndex = i;
                break;
            }
            if (i == 0 && a[i] == 0) {
                lastIndex = 0;
            }
        }

        // 큰 자릿수들이 0으로 채워져있으면 배열을 조정한다.
        if (lastIndex == a.length - 1) {
            return a;
        } else {
            byte[] subarray = new byte[lastIndex + 1];
            System.arraycopy(a, 0, subarray, 0, lastIndex + 1);
            return subarray;
        }
    }
}
