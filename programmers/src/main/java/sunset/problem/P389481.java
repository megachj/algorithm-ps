package sunset.problem;

import java.util.Arrays;

/**
 * 문제 유형: 알파벳과 숫자 대응<br>
 * 난이도: lv3<br>
 *
 * @see <a href = "https://school.programmers.co.kr/learn/courses/30/lessons/389481">링크</a>
 */
public class P389481 {

    public static void main(String[] args) {
        Solution solution = new P389481().new Solution();
        String answer = solution.solution(7388, new String[] { "gqk", "kdn", "jxj", "jxi", "fug", "jxg", "ewq", "len", "bhc" }); // 답: jxk
//        String answer = solution.solution(52, new String[] { }); // 답: az
        System.out.println(answer);
    }

    class Solution {

        public String solution(long n, String[] bans) {
            // 1. bans 정렬
            Arrays.sort(bans, (o1, o2) -> {
                if (o1.length() < o2.length()) {
                    return -1;
                } else if (o1.length() > o2.length()) {
                    return 1;
                }
                return o1.compareTo(o2);
            });

            for (int i = 0; i < bans.length; ++i) {
                long banValue = wordToValue(bans[i]);
                if (n >= banValue) {
                    n++;
                }
            }

            return valueToWord(n);
        }

        private long wordToValue(String word) {
            long result = 0;
            for (int i = word.length() - 1; i >= 0; --i) {
                result += ((word.charAt(i) - 'a' + 1) * (long) Math.pow(26, word.length() - 1 - i));
            }
            return result;
        }

        // 몫: quotient, 나머지: remainder, 분모: denominator, 분자: numerator
        private String valueToWord(long value) {
            // 1. 역순으로 저장
            byte[] reverseArray = new byte[12];
            for (int i = 0; i < 12; ++i) {
                reverseArray[i] = -2;
            }

            long quotient = value;
            for (int i = 0; i < 12; ++i) {
                reverseArray[i] = (byte) (quotient % 26);
                quotient /= 26;
                if (quotient == 0) {
                    break;
                }
            }

            // 2. 0 값 조정(내림 조정)
            for (int i = 0; i < 11; ++i) {
                if (reverseArray[i] <= -2) {
                    break;
                }

                if (reverseArray[i] <= 0) {
                    reverseArray[i] += 26;
                    reverseArray[i + 1] -= 1;
                }
            }

            // 3. 문자열 변환
            StringBuilder sb = new StringBuilder();
            for (int i = 11; i >= 0; --i) {
                if (reverseArray[i] >= 1) {
                    sb.append((char) ('a' + reverseArray[i] - 1));
                }
            }
            return sb.toString();
        }
    }
}
