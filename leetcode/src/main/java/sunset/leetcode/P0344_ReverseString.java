package sunset.leetcode;

import java.util.Arrays;

public class P0344_ReverseString {

    public static void main(String[] args) {
        Solution solution = new P0344_ReverseString().new Solution();
        char[] input = new char[]{'h', 'e', 'l', 'l', 'o'};
        solution.reverseString(input);
        System.out.println(Arrays.toString(input));
    }

    class Solution {
        public void reverseString(char[] s) {
            char temp;
            for (int i = 0; i < s.length / 2; ++i) {
                temp = s[i];
                s[i] = s[s.length - 1 - i];
                s[s.length - 1 - i] = temp;
            }
        }
    }
}
