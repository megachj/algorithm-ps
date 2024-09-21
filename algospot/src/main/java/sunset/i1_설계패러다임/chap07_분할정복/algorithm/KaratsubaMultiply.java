package sunset.i1_설계패러다임.chap07_분할정복.algorithm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @see <a href="https://www.acmicpc.net/problem/13277">참고) baekjoon</a> 해당 문제를 카라츠바 알고리즘으로 풀면 시간 초과가 발생한다. FFT 알고리즘을 이용해 nlogn 시간으로 해결해야 한다.
 */
public class KaratsubaMultiply {

    // 디버그 로그 활성화 플래그
    private static final boolean ENABLED_DEBUG = false;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        String aNumberString = input[0];
        String bNumberString = input[1];
        int maxLength = Math.max(aNumberString.length(), bNumberString.length());

        byte[] aNumber = DecimalByteArrayUtils.numberStringToByteArrayWithZeroPadding(aNumberString, maxLength);
        byte[] bNumber = DecimalByteArrayUtils.numberStringToByteArrayWithZeroPadding(bNumberString, maxLength);

        Solution solution = new Solution(aNumber, bNumber, 1, 0);
        System.out.println(DecimalByteArrayUtils.toNumberString(solution.solve()));
    }

    public static class Solution {

        private final byte[] aNumber;
        private final byte[] bNumber;

        private final int threshold; // 재귀호출을 멈출 임계값
        private int recursiveDepth; // 재귀호출 깊이
        private final boolean isOriginalObject;

        public Solution(byte[] aNumber, byte[] bNumber, int threshold, int recursiveDepth) {
            if (aNumber == null || bNumber == null || aNumber.length == 0) {
                throw new IllegalArgumentException("파라미터가 이상합니다.");
            }
            if (threshold < 1) {
                throw new IllegalArgumentException("thredhold는 1 이상이어야 합니다.");
            }

            if (aNumber.length == bNumber.length) {
                this.aNumber = aNumber;
                this.bNumber = bNumber;
            } else if (aNumber.length < bNumber.length) {
                this.aNumber = new byte[bNumber.length];
                System.arraycopy(aNumber, 0, this.aNumber, 0, aNumber.length);
                // 나머지는 0으로 채운다.
                for (int i = aNumber.length; i < bNumber.length; i++) {
                    this.aNumber[i] = 0;
                }
                this.bNumber = bNumber;
            } else {
                this.bNumber = new byte[aNumber.length];
                System.arraycopy(bNumber, 0, this.bNumber, 0, bNumber.length);
                // 나머지는 0으로 채운다.
                for (int i = bNumber.length; i < aNumber.length; i++) {
                    this.bNumber[i] = 0;
                }
                this.aNumber = aNumber;
            }
            this.threshold = threshold;
            this.recursiveDepth = recursiveDepth;

            this.isOriginalObject = recursiveDepth == 0;
        }

        /**
         * 길이가 같은 두 정수 문자열을 곱한다.<br>
         * 카라츠바 빠른곱셈 알고리즘으로 O(n^(lg3)) 시간이 걸린다.<br>
         *
         * @return 곱셈 결과
         */
        public byte[] solve() {
            return multiplyKaratsuba(0, aNumber.length - 1);
        }

        /**
         * 카라츠바 빠른곱셈을 수행한다.
         *
         * @param startIdx  시작인덱스(포함)
         * @param endIdx    끝인덱스(포함)
         * @return
         */
        private byte[] multiplyKaratsuba(int startIdx, int endIdx) {
            ++recursiveDepth;

            if (endIdx - startIdx + 1 <= threshold) {
                byte[] targetA = DecimalByteArrayUtils.copy(aNumber, startIdx, endIdx);
                byte[] targetB = DecimalByteArrayUtils.copy(bNumber, startIdx, endIdx);
                byte[] result = DecimalByteArrayCalculator.multiply(targetA, targetB);
                logForDebug(
                        String.format("Step %d 결과(thredhold 이하라 그냥 곱셈을 합니다): a%s * b%s = %s",
                                recursiveDepth,
                                DecimalByteArrayUtils.toString(targetA),
                                DecimalByteArrayUtils.toString(targetB),
                                DecimalByteArrayUtils.toString(result))
                );
                logForDebug(String.format("---------------------- Step %d 종료 ----------------------\n", recursiveDepth));
                --recursiveDepth;
                return result;
            }

            int middleIdx = calculateMiddleIdx(startIdx, endIdx);

            // z0 = a0 * b0
            byte[] z0 = multiplyKaratsuba(startIdx, middleIdx);

            // z2 = a1 * b1
            byte[] z2 = multiplyKaratsuba(middleIdx + 1, endIdx);

            // z1 = (a0 + a1) * (b0 + b1) - z0 - z2 = a1*b0 + a0*b1
            byte[] z1 = calculateZ1(startIdx, middleIdx, endIdx, z0, z2);

            // result
            return mergeResult(z0, z1, z2, startIdx, endIdx);
        }

        private int calculateMiddleIdx(int startIdx, int endIdx) {
            return (startIdx + endIdx) / 2;
        }

        // z1 = (a0 + a1) * (b0 + b1) - z0 - z2
        //    = a1*b0 + a0*b1
        private byte[] calculateZ1(int startIdx, int middleIdx, int endIdx, byte[] z0, byte[] z2) {
            byte[] a1 = DecimalByteArrayUtils.copy(aNumber, middleIdx + 1, endIdx);
            byte[] a0 = DecimalByteArrayUtils.copy(aNumber, startIdx, middleIdx);
            byte[] b1 = DecimalByteArrayUtils.copy(bNumber, middleIdx + 1, endIdx);
            byte[] b0 = DecimalByteArrayUtils.copy(bNumber, startIdx, middleIdx);

            byte[] a1AddA0 = DecimalByteArrayCalculator.add(a1, a0);
            byte[] b1AddB0 = DecimalByteArrayCalculator.add(b1, b0);

            Solution solution = new Solution(a1AddA0, b1AddB0, this.threshold, this.recursiveDepth);
            byte[] a1a0b1b0 = solution.solve();
            byte[] midResult = DecimalByteArrayCalculator.sub(a1a0b1b0, z0);

            return DecimalByteArrayCalculator.sub(midResult, z2);
        }

        private byte[] mergeResult(byte[] z0, byte[] z1, byte[] z2, int startIdx, int endIdx) {
            int middleIdx = calculateMiddleIdx(startIdx, endIdx);
            int halfN = middleIdx - startIdx + 1;
            int fullN = halfN * 2;

            byte[] z2Pow = DecimalByteArrayCalculator.pow10(z2, fullN);
            byte[] z1Pow = DecimalByteArrayCalculator.pow10(z1, halfN);

            byte[] result = DecimalByteArrayCalculator.add(z2Pow, z1Pow);
            result = DecimalByteArrayCalculator.add(result, z0);

            logForDebug(String.format("Step %d: halfN(n/2) = %d, fullN(n) = %d", recursiveDepth, halfN, fullN));
            logForDebug(String.format("Step %d: a%s", recursiveDepth, DecimalByteArrayUtils.toString(aNumber, startIdx, endIdx)));
            logForDebug(String.format("Step %d: b%s", recursiveDepth, DecimalByteArrayUtils.toString(bNumber, startIdx, endIdx)));
            logForDebug(String.format("Step %d: z2 = a1 * b1 = %s", recursiveDepth, DecimalByteArrayUtils.toString(z2)));
            logForDebug(String.format("Step %d: z1 = (a1 + a0) * (b1 + b0) = %s", recursiveDepth, DecimalByteArrayUtils.toString(z1)));
            logForDebug(String.format("Step %d: z0 = a0 * b0 = %s", recursiveDepth, DecimalByteArrayUtils.toString(z0)));

            logForDebug(String.format("Step %d: z2Pow = z2 * (10^%d) = %s", recursiveDepth, fullN, DecimalByteArrayUtils.toString(z2Pow)));
            logForDebug(String.format("Step %d: z1Pow = z1 * (10^%d) = %s", recursiveDepth, halfN, DecimalByteArrayUtils.toString(z1Pow)));

            logForDebug(
                    String.format("Step %d 결과: a%s * b%s = z2Pow%s + z1Pow%s + z0%s = %s",
                            recursiveDepth,
                            DecimalByteArrayUtils.toString(aNumber, startIdx, endIdx),
                            DecimalByteArrayUtils.toString(bNumber, startIdx, endIdx),
                            DecimalByteArrayUtils.toString(z2Pow),
                            DecimalByteArrayUtils.toString(z1Pow),
                            DecimalByteArrayUtils.toString(z0),
                            DecimalByteArrayUtils.toString(result))
            );
            logForDebug(String.format("---------------------- Step %d 종료 ----------------------\n", recursiveDepth));
            --recursiveDepth;
            return result;
        }

        private void logForDebug(String message) {
            if (ENABLED_DEBUG && this.isOriginalObject) {
                System.out.println(message);
            }
        }
    }

    public static class DecimalByteArrayCalculator {

        /**
         * 두 양수인 a, b를 더한다.
         *
         * @param a
         * @param b
         * @return a + b
         * @throws IllegalArgumentException a 또는 b가 음수일 때 발생한다.
         */
        public static byte[] add(byte[] a, byte[] b) {
            checkPositive(a, b);

            int n = Math.max(a.length, b.length);
            byte[] result = new byte[n + 1];
            int carry = 0;
            for (int i = 0; i < n; i++) {
                byte aVal = i < a.length ? a[i] : 0;
                byte bVal = i < b.length ? b[i] : 0;
                int sum = aVal + bVal + carry;
                result[i] = (byte) (sum % 10);
                carry = sum / 10;
            }
            result[n] = (byte) carry;

            return adjustArrayLength(result);
        }

        /**
         * 두 양수인 a, b를 뺀다. 이때 a >= b 이어야 한다.
         *
         * @param a
         * @param b
         * @return a - b (>= 0)
         * @throws IllegalArgumentException a < b 일 때 발생한다.
         */
        public static byte[] sub(byte[] a, byte[] b) {
            checkPositive(a, b);

            int n = Math.max(a.length, b.length);
            byte[] result = new byte[n];
            int borrow = 0;
            for (int i = 0; i < n; i++) {
                byte aVal = i < a.length ? a[i] : 0;
                byte bVal = i < b.length ? b[i] : 0;
                int diff = aVal - bVal - borrow;
                if (diff < 0) {
                    diff += 10;
                    borrow = 1;
                } else {
                    borrow = 0;
                }
                result[i] = (byte) diff;
            }

            return adjustArrayLength(result);
        }

        /**
         * 두 양수인 a, b를 곱한다.
         *
         * @param a
         * @param b
         * @return a * b
         */
        public static byte[] multiply(byte[] a, byte[] b) {
            checkPositive(a, b);

            int maxLength = a.length + b.length + 1;
            byte[] result = new byte[maxLength];

            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b.length; j++) {
                    int mul = a[i] * b[j];
                    int sum = result[i + j] + mul;
                    result[i + j] = (byte) (sum % 10);
                    result[i + j + 1] += sum / 10;
                }
            }

            return adjustArrayLength(result);
        }

        /**
         * 양수인 a * 10^n을 계산한다.
         *
         * @param a
         * @param n
         * @return a * 10^n
         */
        public static byte[] pow10(byte[] a, int n) {
            checkPositive(a);

            byte[] result = new byte[a.length + n];
            // src = 소스
            // srcPos = 소스의 시작 인덱스
            // dest = 목적지
            // destPos = 목적지의 시작 인덱스
            // length = 복사할 길이
            System.arraycopy(a, 0, result, n, a.length);

            return adjustArrayLength(result);
        }

        public static byte[] adjustArrayLength(byte[] result) {
            checkPositive(result);

            for (int i = result.length - 1; i >= 0; i--) {
                if (result[i] != 0) {
                    if (i == result.length - 1) {
                        return result;
                    } else {
                        byte[] newResult = new byte[i + 1];
                        System.arraycopy(result, 0, newResult, 0, i + 1);
                        return newResult;
                    }
                }
            }
            // 모든 자리가 0인 경우
            return new byte[]{0};
        }

        public static void checkPositive(byte[]... arrays) {
            for (byte[] array : arrays) {
                if (isNegative(array)) {
                    throw new IllegalArgumentException("음수는 지원하지 않습니다.");
                }
            }
        }

        public static boolean isNegative(byte[] a) {
            return a[a.length - 1] < 0;
        }
    }

    public static class DecimalByteArrayUtils {

        /**
         * 숫자 문자열을 n 사이즈인 byte 배열로 변환한다. 사이즈가 부족할 경우 0으로 채운다.
         *
         * @param numberString  변환할 숫자 문자열
         * @param n             byte 배열의 사이즈
         * @return 변환된 byte 배열
         */
        public static byte[] numberStringToByteArrayWithZeroPadding(String numberString, int n) {
            // 문자열의 길이만큼의 byte 배열 생성
            byte[] byteArray = new byte[n];

            // 각 문자를 byte로 변환하여 배열에 저장
            int numberLength = numberString.length();
            for (int i = 0; i < numberLength; i++) {
                byteArray[i] = (byte) (numberString.charAt(numberLength - i - 1) - '0');
            }
            // 나머지는 0으로 채운다.
            for (int i = numberLength; i < n; i++) {
                byteArray[i] = 0;
            }

            return byteArray;
        }

        /**
         * byte 배열을 숫자 문자열로 변환한다.
         *
         * @param byteArray 10 진수 양수
         * @return 변환된 숫자 문자열
         */
        public static String toNumberString(byte[] byteArray) {
            StringBuilder sb = new StringBuilder();
            for (int i = byteArray.length - 1; i >= 0; i--) {
                sb.append(byteArray[i]);
            }
            return sb.toString();
        }

        /**
         * Byte 배열을 복사한다.
         *
         * @param source    복사할 배열
         * @param startIdx  시작 인덱스(포함)
         * @param endIdx    끝 인덱스(포함)
         * @return 복사된 배열
         */
        public static byte[] copy(byte[] source, int startIdx, int endIdx) {
            int length = endIdx - startIdx + 1;

            byte[] result = new byte[length];
            System.arraycopy(source, startIdx, result, 0, length);
            return result;
        }

        /**
         * 배열을 문자열로 변환한다.
         *
         * @param byteArray
         * @return
         */
        public static String toString(byte[] byteArray) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < byteArray.length; i++) {
                sb.append(byteArray[i]);
                if (i != byteArray.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }

        /**
         * 배열을 문자열로 변환한다.
         *
         * @param byteArray
         * @param startIdx
         * @param endIdx
         * @return
         */
        public static String toString(byte[] byteArray, int startIdx, int endIdx) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = startIdx; i <= endIdx; i++) {
                sb.append(byteArray[i]);
                if (i != endIdx) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }
}
