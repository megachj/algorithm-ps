package sunset.leetcode.medium;

public class P0075_SortColors {

    public static void main(String[] args) {
        // {2, 0, 2, 1, 1, 0} -> {0, 0, 1, 1, 2, 2}
        // {2, 0, 1} -> {0, 1, 2}
        int[] inputs = new int[]{2, 0, 2, 1, 1, 0};

        Solution solution = new P0075_SortColors().new Solution();
        solution.sortColors(inputs);

        for (int input : inputs) {
            System.out.printf("%d, ", input);
        }
        System.out.println();
    }

    class Solution {
        public void sortColors(int[] nums) {
            int low = 0;
            int high = nums.length - 1;
            int cur = 0;

            while (cur <= high) {
                int color = nums[cur];
                switch (color) {
                    case 0:
                        swap(nums, low, cur);
                        cur++;
                        low++;
                        break;
                    case 1:
                        cur++;
                        break;
                    case 2:
                        swap(nums, high, cur);
                        high--;
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }

        private void swap(int[] nums, int aIndex, int bIndex) {
            int temp = nums[aIndex];
            nums[aIndex] = nums[bIndex];
            nums[bIndex] = temp;
        }
    }
}
