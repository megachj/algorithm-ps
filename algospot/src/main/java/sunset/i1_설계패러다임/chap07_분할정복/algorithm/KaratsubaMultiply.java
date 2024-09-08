package sunset.i1_설계패러다임.chap07_분할정복.algorithm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @see <a href="https://www.acmicpc.net/problem/13277">문제</a>
 */
public class KaratsubaMultiply {

    // 테스트 케이스
    // 1 1 = 1
    // 11 11 = 121
    // 123 1 = 123
    // 1 123 = 123
    // 123 12 = 1476
    // 12 123 = 1476

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        String aNumberString = input[0];
        String bNumberString = input[1];
        int maxLength = Math.max(aNumberString.length(), bNumberString.length());

        byte[] aNumber = numberStringToByteArrayWithZeroPadding(aNumberString, maxLength);
        byte[] bNumber = numberStringToByteArrayWithZeroPadding(bNumberString, maxLength);

        Solution solution = new Solution(aNumber, bNumber);
        printByteArray(solution.solve());
    }

    /**
     * 숫자 문자열을 n 사이즈인 byte 배열로 변환한다. 사이즈가 부족할 경우 0으로 채운다.
     *
     * @param numberString  변환할 숫자 문자열
     * @param n             byte 배열의 사이즈
     * @return 변환된 byte 배열
     */
    private static byte[] numberStringToByteArrayWithZeroPadding(String numberString, int n) {
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
     * byte 배열을 출력한다.
     *
     * @param byteArray
     */
    private static void printByteArray(byte[] byteArray) {
        for (int i = byteArray.length - 1; i >= 0; i--) {
            System.out.print(byteArray[i]);
        }
        System.out.println();
    }

    static class Solution {

        private final byte[] aNumber;
        private final byte[] bNumber;

        private String toString(byte[] byteArray) {
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

        private String toString(byte[] byteArray, int startIdx, int endIdx) {
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

        public Solution(byte[] aNumber, byte[] bNumber) {
            if (aNumber == null || bNumber == null || aNumber.length == 0) {
                throw new IllegalArgumentException("파라미터가 이상합니다.");
            }
            if (aNumber.length != bNumber.length) {
                throw new IllegalArgumentException("두 수의 길이가 다릅니다.");
            }
            this.aNumber = aNumber;
            this.bNumber = bNumber;
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
            System.out.printf("카라츠바 시작: a%s * b%s\n", toString(aNumber, startIdx, endIdx), toString(bNumber, startIdx, endIdx));
            int middleIdx;
            try {
                middleIdx = findMiddleIndex(startIdx, endIdx);
            } catch (RuntimeException e) {
                // 자를 수 없을 때
                byte[] result = new byte[]{(byte) (aNumber[startIdx] * bNumber[startIdx])};
                System.out.printf("카라츠바 끝: a%s * b%s = %s\n\n", toString(aNumber, startIdx, endIdx), toString(bNumber, startIdx, endIdx), toString(result));
                return result;
            }

            // z0 = a0 * b0
            byte[] z0 = multiplyKaratsuba(startIdx, middleIdx);
            System.out.printf("z0 = a0%s * b0%s = %s\n", toString(aNumber, startIdx, middleIdx), toString(bNumber, startIdx, middleIdx), toString(z0));
            // z2 = a1 * b1
            byte[] z2 = multiplyKaratsuba(middleIdx + 1, endIdx);
            System.out.printf("z2 = a1%s * b1%s = %s\n", toString(aNumber, middleIdx + 1, endIdx), toString(bNumber, middleIdx + 1, endIdx), toString(z2));
            // z1 = (a0 + a1) * (b0 + b1) - z0 - z2 = a1*b0 + a0*b1
            byte[] z1 = calculateZ1(startIdx, middleIdx, endIdx, z0, z2);
            System.out.printf("z1 = (a0%s + a1%s) * (b0%s + b1%s) - z0 - z2 = %s\n", toString(aNumber, startIdx, middleIdx), toString(aNumber, middleIdx + 1, endIdx), toString(bNumber, startIdx, middleIdx), toString(bNumber, middleIdx + 1, endIdx), toString(z1));

            // result
            return mergeResult(z0, z1, z2, startIdx, endIdx);
        }

        private int findMiddleIndex(int startIdx, int endIdx) {
            if (startIdx == endIdx) {
                throw new RuntimeException("자를 수 없습니다.");
            }
            return (startIdx + endIdx) / 2;
        }

        // z1 = (a0 + a1) * (b0 + b1) - z0 - z2
        //    = a1*b0 + a0*b1
        private byte[] calculateZ1(int startIdx, int middleIdx, int endIdx, byte[] z0, byte[] z2) {
            byte[] a1 = copy(aNumber, middleIdx + 1, endIdx);
            byte[] a0 = copy(aNumber, startIdx, middleIdx);
            byte[] b1 = copy(bNumber, middleIdx + 1, endIdx);
            byte[] b0 = copy(bNumber, startIdx, middleIdx);
            System.out.printf("a1 = %s, a0 = %s, b1 = %s, b0 = %s\n", toString(a1), toString(a0), toString(b1), toString(b0));

            byte[] a1AddA0 = add(a1, a0);
            System.out.printf("a1 + a0 = %s\n", toString(a1AddA0));
            byte[] b1AddB0 = add(b1, b0);
            System.out.printf("b1 + b0 = %s\n", toString(b1AddB0));

            Solution solution = new Solution(a1AddA0, b1AddB0); // 길이 같게 해줘야함.
            byte[] a1a0b1b0 = solution.solve();
            byte[] midResult = sub(a1a0b1b0, z0);
            byte[] z1 = sub(midResult, z2);
            System.out.printf("z1 = %s\n", toString(z1));

            return z1;
        }

        private byte[] mergeResult(byte[] z0, byte[] z1, byte[] z2, int startIdx, int endIdx) {
            int n = endIdx - startIdx + 1;

            byte[] z2Pow = pow10(z2, n);
            System.out.printf("z2(10^%d) = %s\n", n, toString(z2Pow));
            byte[] z1Pow = pow10(z1, n / 2);
            System.out.printf("z1(10^%d) = %s\n", n/2, toString(z1Pow));
            byte[] result = add(z2Pow, z1Pow);
            result = add(result, z0);
            System.out.printf("카라츠바 끝: a%s * b%s = z2Pow%s + z1Pow%s + z0%s = %s\n\n", toString(aNumber, startIdx, endIdx), toString(bNumber, startIdx, endIdx), toString(z2Pow), toString(z1Pow), toString(z0), toString(result));

            return result;
        }

        private byte[] copy(byte[] source, int startIdx, int endIdx) {
            byte[] result = new byte[endIdx - startIdx + 1];
            for (int i = startIdx; i <= endIdx; i++) {
                result[i - startIdx] = source[i];
            }
            return result;
        }

        private byte[] pow10(byte[] a, int n) {
            byte[] result = new byte[a.length + n];
            // src = 소스
            // srcPos = 소스의 시작 인덱스
            // dest = 목적지
            // destPos = 목적지의 시작 인덱스
            // length = 복사할 길이
            System.arraycopy(a, 0, result, n, a.length);
            return result;
        }

        private byte[] add(byte[] a, byte[] b) {
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

            for (int i = n; i >= 0; i--) {
                if (result[i] != 0) {
                    if (i == n) {
                        return result;
                    } else {
                        byte[] newResult = new byte[i + 1];
                        System.arraycopy(result, 0, newResult, 0, i + 1);
                        return newResult;
                    }
                }
            }
            return result;
        }

        private byte[] sub(byte[] a, byte[] b) {
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
            for (int i = n - 1; i >= 0; i--) {
                if (result[i] != 0) {
                    if (i == n - 1) {
                        return result;
                    } else {
                        byte[] newResult = new byte[i + 1];
                        System.arraycopy(result, 0, newResult, 0, i + 1);
                        return newResult;
                    }
                }
            }
            return result;
        }
    }


}
