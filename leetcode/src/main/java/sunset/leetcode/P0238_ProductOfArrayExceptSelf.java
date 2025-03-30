package sunset.leetcode;

import java.util.Arrays;

public class P0238_ProductOfArrayExceptSelf {

    public static void main(String[] args) {
        int[] input1 = new int[]{-1, 1, 0, -3, 3, 0};
        int[] input2 = new int[]{1, 2, 3, 4};
        int[] input3 = new int[]{-1, 1, 0, -3, 3};

        int[] input = input3;
        Solution solution = new P0238_ProductOfArrayExceptSelf().new Solution();
        int[] output = solution.productExceptSelf(input);
        System.out.println(Arrays.toString(output));
    }

    class Solution {
        public int[] productExceptSelf(int[] nums) {
            int[] result = new int[nums.length];

            int product = 1;
            result[0] = product;
            for (int i = 1; i < nums.length; ++i) {
                product = nums[i - 1] * product;
                result[i] = product;
            }

            product = 1;
            for (int i = nums.length - 2; i >= 0; --i) {
                product = nums[i + 1] * product;
                result[i] *= product;
            }

            return result;
        }
    }
}
