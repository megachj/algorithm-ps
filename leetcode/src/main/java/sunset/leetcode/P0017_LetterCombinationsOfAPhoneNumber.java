package sunset.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P0017_LetterCombinationsOfAPhoneNumber {

    /*
    Example 1:
    Input: digits = "23"
    Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]

    Example 2:
    Input: digits = "2"
    Output: ["a","b","c"]
     */
    public static void main(String[] args) {
        Solution solution = new P0017_LetterCombinationsOfAPhoneNumber().new Solution();
        List<String> result = solution.letterCombinations("2");
        System.out.println(result);
    }

    /**
     * 재귀를 통해 깊이우선탐색(DFS) 을 진행한다.<br>
     * - 시간복잡도: digits 길이가 4일때 최대 144번<br>
     * - 공간복잡도: digits 길이가 4일때 스택깊이는 최대 4<br>
     * - 결과: 3ms / 48.74MB<br>
     */
    class Solution {
        private final Map<Character, String> dialMap;

        private String digits;

        private final List<String> result;

        public Solution() {
            dialMap = new HashMap<>(8);
            dialMap.put('2', "abc");
            dialMap.put('3', "def");
            dialMap.put('4', "ghi");
            dialMap.put('5', "jkl");
            dialMap.put('6', "mno");
            dialMap.put('7', "pqrs");
            dialMap.put('8', "tuv");
            dialMap.put('9', "wxyz");

            result = new ArrayList<>();
        }

        public List<String> letterCombinations(String digits) {
            this.digits = digits;
            dfs("");
            return result;
        }

        private void dfs(String letter) {
            if (letter.length() == digits.length()) {
                result.add(letter);
                return;
            }

            char dialCharacter = digits.charAt(letter.length());
            String dialLetters = dialMap.get(dialCharacter);
            for (int i = 0; i < dialLetters.length(); ++i) {
                dfs(letter + dialLetters.charAt(i));
            }
        }
    }
}
