package sunset.leetcode;

import java.util.*;

public class P0819_MostCommonWord {

    public static void main(String[] args) {
        Solution solution = new P0819_MostCommonWord().new Solution();
        String inputParagraph = "Bob hit a ball, the hit BALL flew far after it was hit.";
        String[] inputBanned = new String[]{"hit"};
        String output = solution.mostCommonWord(inputParagraph, inputBanned);
        System.out.println(output);
    }

    class Solution {
        public String mostCommonWord(String paragraph, String[] banned) {
            String cleanedParagraph = paragraph.replaceAll("[^a-zA-z ]", " ").toLowerCase();
            String[] split = cleanedParagraph.split("\\s+");

            Map<String, Integer> dict = new HashMap<>(split.length);

            for (String word: split) {
                dict.put(word, dict.getOrDefault(word, 0) + 1);
            }

            Set<String> bannedSet = new HashSet<>(Arrays.asList(banned));

            int max = 0;
            String result = null;
            for (Map.Entry<String, Integer> entry: dict.entrySet()) {
                if (!bannedSet.contains(entry.getKey()) && max < entry.getValue()) {
                    max = entry.getValue();
                    result = entry.getKey();
                }
            }

            return result;
        }
    }
}
