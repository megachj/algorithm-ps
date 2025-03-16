package sunset.leetcode;

public class P0005_LongestPalindromicSubstring {

    public static void main(String[] args) {
        Solution solution = new P0005_LongestPalindromicSubstring().new Solution();
        String input = "aacabdkacaa";
        String output = solution.longestPalindrome(input);
        System.out.println(output);
    }

    /**
     * 설명: 조금 최적화한 브루트포스 방법. 최대 3중 루프로 예상된다.<br>
     * - 시간복잡도: O(n^3)<br>
     * - 공간복잡도: O(1)<br>
     * - 결과: 166ms / 42.1MB<br>
     */
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

    /**
     * 설명: 슬라이딩 + 투포인터를 활용해 최대 팰린드롬을 구한다.<br>
     * - 시간복잡도: O(n^2)<br>
     * - 공간복잡도: O(1)<br>
     * - 결과: 18ms / 42.5MB<br>
     */
    class Solution1 {
        public String longestPalindrome(String s) {
            int maxLength = 1;
            int startIdx = -1;
            int endIdx = -1;

            for (int i = 0; i < s.length(); ++i) {
                // 짝수 투포인터
                int left = i;
                int right = i+1;
                while (left >= 0 && right < s.length()) {
                    if (s.charAt(left) != s.charAt(right)) {
                        break;
                    }
                    left--;
                    right++;
                }
                int palindromeLength = right - left - 1;
                if (maxLength < palindromeLength) {
                    maxLength = palindromeLength;
                    startIdx = left + 1;
                    endIdx = right - 1;
                }

                // 홀수 투포인터(1 제외)
                left = i;
                right = i+2;
                while (left >= 0 && right < s.length()) {
                    if (s.charAt(left) != s.charAt(right)) {
                        break;
                    }
                    left--;
                    right++;
                }
                palindromeLength = right - left - 1;
                if (maxLength < palindromeLength) {
                    maxLength = palindromeLength;
                    startIdx = left + 1;
                    endIdx = right - 1;
                }
            }
            if (maxLength > 1) {
                return s.substring(startIdx, endIdx + 1);
            } else {
                return s.substring(0, 1);
            }
        }
    }
}
