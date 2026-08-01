package sunset.leetcode.easy;

import java.util.HashMap;
import java.util.Map;

public class P0013_RomanToInteger {

    public static void main(String[] args) {
        // III, 3
        // LVIII, 3
        // MCMXCIV, 1994
        // IM, 999
        String input = "III";
        Solution solution = new P0013_RomanToInteger().new Solution();
        int output = solution.romanToInt(input);
        System.out.println(output);
    }

    class Solution {

        private Map<Character, Integer> romanToIntMap;

        public Solution() {
            romanToIntMap = new HashMap<>();
            romanToIntMap.put('I', 1);
            romanToIntMap.put('V', 5);
            romanToIntMap.put('X', 10);
            romanToIntMap.put('L', 50);
            romanToIntMap.put('C', 100);
            romanToIntMap.put('D', 500);
            romanToIntMap.put('M', 1000);
        }

        public int romanToInt(String s) {
            int output = 0;
            for (int i = 0; i < s.length(); ++i) {
                int currentValue = romanToIntMap.get(s.charAt(i));
                if (i+1 < s.length()) {
                    int nextValue = romanToIntMap.get(s.charAt(i+1));
                    if (nextValue > currentValue) {
                        output += (nextValue - currentValue);
                        ++i;
                        continue;
                    }
                }
                output += currentValue;
            }
            return output;
        }
    }
}
