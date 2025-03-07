package sunset.leetcode;

import java.util.Arrays;

public class P0561_ArrayPartition {

    public static void main(String[] args) {
        Solution solution = new P0561_ArrayPartition().new Solution();
        int[] input = new int[]{6,2,6,5,1,2};
        int output = solution.arrayPairSum(input);
        System.out.println(output);
    }

    class Solution {
        public int arrayPairSum(int[] nums) {
            Arrays.sort(nums);

            int result = 0;
            for (int i = 0; i < nums.length; i += 2) {
                result += nums[i];
            }

            return result;
        }
    }
}
