package sunset.leetcode;

public class P0005_LongestPalindromicSubstring {

    public static void main(String[] args) {
        Solution solution = new P0005_LongestPalindromicSubstring().new Solution();
        String input = "aacabdkacaa";
        String output = solution.longestPalindrome(input);
        System.out.println(output);
    }

    class Solution {
        public String longestPalindrome(String s) {
            String maxPalindrome = "";
            for (int i = 0; i < s.length(); ++i) {
                for (int j = s.length() - 1; j > i; --j) {
                    if (maxPalindrome.length() >= j - i + 1) {
                        break;
                    }
                    if (s.charAt(i) == s.charAt(j)) {
                        boolean isPalindrome = isValidPalindrome(s, i + 1, j - 1);
                        if (isPalindrome) {
                            maxPalindrome = s.substring(i, j + 1);
                        }
                    }
                }
            }

            if (maxPalindrome.isEmpty()) {
                maxPalindrome = s.substring(0, 1);
            }

            return maxPalindrome;
        }

        private boolean isValidPalindrome(String s, int start, int end) {
            if (start >= end) {
                return true;
            }

            boolean palindrome = true;
            int middle = (start + end) / 2;
            for (int i = 0; i <= middle - start; ++i) {
                if (s.charAt(start + i) != s.charAt(end - i)) {
                    palindrome = false;
                    break;
                }
            }

            return palindrome;
        }
    }
}
