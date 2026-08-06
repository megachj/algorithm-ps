package sunset.leetcode.easy;

public class P0704_BinarySearch {

    public static void main(String[] args) {
        // {-1, 0, 3, 5, 9, 12}, 9 -> 4
        // {-1, 0, 3, 5, 9, 12}, 2 -> -1
        int[] nums = new int[]{-1, 0, 3, 5, 9, 12};
        int target = 2;

        Solution solution = new P0704_BinarySearch().new Solution();
        int output = solution.search(nums, target);

        System.out.println(output);
    }

    class Solution {
        public int search(int[] nums, int target) {
            return binarySearch(nums, target, 0, nums.length - 1);
        }

        private int binarySearch(int[] nums, int target, int startIndex, int endIndex) {
            if (startIndex > endIndex) {
                return -1;
            }

            int middleIndex = (endIndex - startIndex) / 2 + startIndex;
            int middleValue = nums[middleIndex];
            if (target == middleValue) {
                return middleIndex;
            } else if (target < middleValue) {
                return binarySearch(nums, target, startIndex, middleIndex - 1);
            } else {
                return binarySearch(nums, target, middleIndex + 1, endIndex);
            }
        }
    }
}
