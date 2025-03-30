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

    /**
     * 설명: 왼쪽부터 자신을 제외한 왼쪽모든값을 곱한 값과, 오른쪽부터 자신을 제외한 오른쪽 모든 값을 곱한 결과 리스트를 만들어 활용한다.
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(1)<br>
     * - 결과: 2ms / 55.44MB<br>
     */
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
