package sunset.problem;

import java.util.HashMap;
import java.util.Map;

/**
 * 문제 유형: 해시<br>
 * 난이도: lv1<br>
 *
 * @see <a href = "https://school.programmers.co.kr/learn/courses/30/lessons/42576?language=java">링크</a>
 */
public class P42576 {

    class Solution {
        public String solution(String[] participant, String[] completion) {
            Map<String, Integer> map = new HashMap<String, Integer>(participant.length);

            for(int i = 0; i < participant.length; ++i) {
                if (map.containsKey(participant[i])) {
                    map.replace(participant[i], map.get(participant[i]) + 1);
                } else {
                    map.put(participant[i], 1);
                }
            }

            for (int i = 0; i < completion.length; ++i) {
                if (!map.containsKey(completion[i])) {
                    continue;
                }

                int count = map.get(completion[i]);
                if (count == 1) {
                    map.remove(completion[i]);
                } else {
                    map.replace(completion[i], count - 1);
                }
            }

            return map.keySet().stream().findAny().get();
        }
    }
}
