package sunset.leetcode;

public class P0125_ValidPalindrome {

    public static void main(String[] args) {
        Solution solution = new P0125_ValidPalindrome().new Solution();
        boolean result = solution.isPalindrome(" ");
        System.out.println(result);
    }

    class Solution {

        public boolean isPalindrome(String s) {
            String cleansingString = s.toLowerCase().replaceAll("[^a-z0-9]", "");
            int length = cleansingString.length();
            for (int i = 0; i < length / 2; ++i) {
                if (cleansingString.charAt(i) != cleansingString.charAt(length - 1 - i)) {
                    return false;
                }
            }
            return true;
        }
    }
}
