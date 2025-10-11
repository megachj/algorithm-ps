package sunset.leetcode;

import java.util.HashSet;
import java.util.Set;

public class P0771_JewelsAndStones {

    public static void main(String[] args) {
        String jewels = "aA";
        String stones = "aAAbbbb";

        int result = new P0771_JewelsAndStones().new Solution().numJewelsInStones(jewels, stones);
        System.out.println(result);
    }

    /**
     * 결과: 1ms / 42.11MB
     */
    class Solution {
        public int numJewelsInStones(String jewels, String stones) {
            Set<Character> jewelSet = new HashSet<>(jewels.length());
            for (char j: jewels.toCharArray()) {
                jewelSet.add(j);
            }

            int result = 0;
            for (char stone: stones.toCharArray()) {
                if (jewelSet.contains(stone)) {
                    result++;
                }
            }
            return result;
        }
    }
}
