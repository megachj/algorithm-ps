package sunset.leetcode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class P0003_LongestSubstringWithoutRepeatingCharacters {

    public static void main(String[] args) {
        String s = "pwwkew";
        int result = new P0003_LongestSubstringWithoutRepeatingCharacters().new Solution().lengthOfLongestSubstring(s);

        System.out.println(result);
    }

    /**
     * 직접 생각한 풀이법.
     * 일반적으로 부분문자열을 비교하려면 O(N^2) 인데, 한번의 반복문으로 set 을 이용해 풀었다.
     *
     * - 시간복잡도: O(N) -> 한 문자당 최대 2번까지 조회
     * - 공간복잡도: O(N)
     * - 결과: 7ms / 45.28MB
     */
    class Solution {
        public int lengthOfLongestSubstring(String s) {
            // 같은 것이 있는지 판별할 set
            Set<Character> set = new HashSet<>();
            // 앞에부터 쌓이는 인덱스 리스트
            LinkedList<Integer> indexes = new LinkedList<>();

            int result = 0;
            for (int i = 0; i < s.length();) {
                char ch = s.charAt(i);
                // 같은 것이 포함되어 있으면 결과가 발생한다.
                if (set.contains(ch)) {
                    result = Math.max(result, set.size());

                    // 맨 앞에 인덱스는 지났으니 지워주고, set 에서도 제거해준다.
                    int firstIndex = indexes.removeFirst();
                    char firstCh = s.charAt(firstIndex);
                    set.remove(firstCh);
                }
                // 같은 것이 포함되지 않았으면 계속 부분문자열이 늘어날 수 있다.
                else {
                    set.add(ch);
                    indexes.add(i);
                    i++;
                }
            }
            // 모두 끝난 경우에도 결과를 발생시킨다.
            result = Math.max(result, set.size());

            return result;
        }
    }
}
