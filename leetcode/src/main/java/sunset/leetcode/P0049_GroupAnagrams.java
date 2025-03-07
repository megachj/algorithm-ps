package sunset.leetcode;

import java.util.*;

public class P0049_GroupAnagrams {

    public static void main(String[] args) {
        Solution solution = new P0049_GroupAnagrams().new Solution();
        String[] input = new String[]{"eat","tea","tan","ate","nat","bat"};
        List<List<String>> output = solution.groupAnagrams(input);
        System.out.println(output);
    }

    class Solution {
        public List<List<String>> groupAnagrams(String[] strs) {
            Map<String, List<String>> anagrams = new HashMap<>(strs.length);

            for (String word: strs) {
                char[] charArray = word.toCharArray();
                Arrays.sort(charArray);

                String key = new String(charArray);
                if (!anagrams.containsKey(key)) {
                    anagrams.put(key, new ArrayList<>());
                }

                anagrams.get(key).add(word);
            }

            return new ArrayList<>(anagrams.values());
        }
    }
}
