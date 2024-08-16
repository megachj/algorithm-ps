package sunset.problem;

import java.util.HashMap;
import java.util.Map;

/**
 * @see <a href="https://school.programmers.co.kr/learn/courses/30/lessons/42578?language=java">문제</a>
 */
public class P42578 {

    class Solution {

        /**
         *
         * @param clothes 옷 리스트<br>
         * - 각 행은 [의상의 이름, 의상의 종류]<br>
         * - 같은 이름을 가진 의상은 존재하지 않음<br>
         * - 모든 문자열의 길이는 1이상, 20이하<br>
         *
         * @return 옷 입는 조합의 수
         */
        public int solution(String[][] clothes) {
            Map<String, Integer> map = new HashMap<>(clothes.length);
            for (int i = 0; i < clothes.length; i++) {
                int alreadyCount = map.getOrDefault(clothes[i][1], 0);
                map.put(clothes[i][1], alreadyCount + 1);
            }

            int result = 1;
            for (int value: map.values()) {
                result *= (value + 1);
            }
            return result - 1;
        }
    }
}
