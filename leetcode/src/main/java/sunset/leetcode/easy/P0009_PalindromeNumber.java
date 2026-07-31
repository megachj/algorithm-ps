package sunset.leetcode.easy;

public class P0009_PalindromeNumber {

    public static void main(String[] args) {
        // 121, true
        // -121, false
        // 10, false
        // 0, true
        // 5, true
        // 23, false
        // 22, true
        // Interger.MAX_VALUE, false
        int input = Integer.MAX_VALUE;

        Solution solution = new P0009_PalindromeNumber().new Solution();
        boolean output = solution.isPalindrome(input);
        System.out.println(output);
    }

    class Solution {
        public boolean isPalindrome(int x) {
            if (x < 0) {
                return false;
            }

            int[] array = new int[11];

            int index = 0;
            int d = x;
            do {
                int r = d % 10;
                array[index++] = r;
                d = d / 10;
            } while (d > 0);

            for (int i = 0; i < index / 2; i++) {
                if (array[i] != array[index - 1 - i]) {
                    return false;
                }
            }

            return true;
        }
    }
}
