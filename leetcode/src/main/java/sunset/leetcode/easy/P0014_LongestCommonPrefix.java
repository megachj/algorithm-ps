package sunset.leetcode.easy;

public class P0014_LongestCommonPrefix {

    public static void main(String[] args) {
        // {"flower", "flow", "flight"}, "fl"
        // {"dog", "racecar", "car"}, ""
        String[] input = new String[]{"flower", "flow", "flight"};
        Solution solution = new P0014_LongestCommonPrefix().new Solution();
        String output = solution.longestCommonPrefix(input);
        System.out.println(output);
    }

    class Solution {
        public String longestCommonPrefix(String[] strs) {
            StringBuilder longestCommonPrefixBuilder = new StringBuilder();
            for (int curIndex = 0; curIndex < 200; ++curIndex) {
                char curCharacter;
                try {
                    curCharacter = strs[0].charAt(curIndex);
                } catch (Exception e) {
                    break;
                }

                boolean allMatch = true;
                for (int i = 1; i < strs.length; ++i) {
                    try {
                        char character = strs[i].charAt(curIndex);
                        if (curCharacter != character) {
                            allMatch = false;
                            break;
                        }
                    } catch (Exception e) {
                        allMatch = false;
                        break;
                    }
                }
                if (allMatch) {
                    longestCommonPrefixBuilder.append(curCharacter);
                } else {
                    break;
                }
            }
            return longestCommonPrefixBuilder.toString();
        }
    }
}
