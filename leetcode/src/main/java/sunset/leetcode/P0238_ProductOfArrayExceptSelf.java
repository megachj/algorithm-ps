package sunset.leetcode;

import java.util.Arrays;

public class P0238_ProductOfArrayExceptSelf {

    public static void main(String[] args) {
        Solution solution = new P0238_ProductOfArrayExceptSelf().new Solution();
        int[] input = new int[]{-1,1,0,-3,3, 0};
        int[] output = solution.productExceptSelf(input);
        System.out.println(Arrays.toString(output));
    }

    class Solution {
        public int[] productExceptSelf(int[] nums) {
            int zeroCount = 0;
            int totalProduct = 1;
            int totalProductExceptZero = 1;
            for (int i = 0; i < nums.length; ++i) {
                totalProduct *= nums[i];
                if (nums[i] == 0) {
                    zeroCount++;
                } else {
                    totalProductExceptZero *= nums[i];
                }
            }

            int[] result = new int[nums.length];
            for (int i = 0; i < nums.length; ++i) {
                if (zeroCount >= 2) {
                    result[i] = 0;
                } else if (zeroCount == 1) {
                    if (nums[i] == 0) {
                        result[i] = totalProductExceptZero;
                    } else {
                        result[i] = 0;
                    }
                } else {
                    result[i] = totalProduct / nums[i];
                }
            }
            return result;
        }
    }
}
