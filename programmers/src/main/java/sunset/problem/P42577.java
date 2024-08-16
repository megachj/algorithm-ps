package sunset.problem;

import java.util.Arrays;

/**
 * @see <a href="https://school.programmers.co.kr/learn/courses/30/lessons/42577?language=java">문제</a>
 */
public class P42577 {

    class Solution {

        /**
         *
         * @param phone_book 전화번호부<br>
         * - 길이: 1 이상 1,000,000 이하<br>
         * - 같은 전화번호가 중복해서 들어있지 않음<br>
         * @return 접두어 전화번호 존재 여부(존재: false, 비존재: true)
         */
        public boolean solution(String[] phone_book) {
            Arrays.sort(phone_book);
            for (int i = 0; i < phone_book.length - 1; i++) {
                if (phone_book[i+1].startsWith(phone_book[i])) {
                    return false;
                }
            }
            return true;
        }
    }
}