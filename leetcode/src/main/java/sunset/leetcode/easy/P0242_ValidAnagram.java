package sunset.leetcode.easy;

import java.util.Map;
import java.util.stream.Collectors;

public class P0242_ValidAnagram {

    public static void main(String[] args) {
        // {"anagram", "nagaram"} -> true
        // {"rat", "car"} -> false
        String[] inputs = new String[]{"rat", "car"};

        Solution solution = new P0242_ValidAnagram().new Solution();
        boolean output = solution.isAnagram(inputs[0], inputs[1]);
        System.out.println(output);
    }

    class Solution {
        public boolean isAnagram(String s, String t) {
            Map<Integer, Integer> sMap = s.codePoints()
                    .boxed()
                    .collect(Collectors.toMap(
                            x -> x,
                            x -> 1,
                            Integer::sum
                    ));

            Map<Integer, Integer> tMap = t.codePoints()
                    .boxed()
                    .collect(Collectors.toMap(
                            x -> x,
                            x -> 1,
                            Integer::sum
                    ));

            return sMap.equals(tMap);
        }
    }
}
