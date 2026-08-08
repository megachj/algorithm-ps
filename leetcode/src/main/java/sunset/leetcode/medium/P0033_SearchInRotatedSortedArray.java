package sunset.leetcode.medium;

public class P0033_SearchInRotatedSortedArray {

    public static void main(String[] args) {
        // {4, 5, 6, 7, 0, 1, 2}, 0 -> 4
        // {4, 5, 6, 7, 0, 1, 2}, 3 -> -1
        // {1}, 0 -> -1
        int[] nums = new int[]{4, 5, 6, 7, 0, 1, 2};
        int target = 3;

        Solution solution = new P0033_SearchInRotatedSortedArray().new Solution();
        int output = solution.search(nums, target);

        System.out.println(output);
    }

    class Solution {
        public int search(int[] nums, int target) {
            return binarySearch(nums, target, 0, nums.length - 1);
        }

        private int binarySearch(int[] nums, int target, int lowIndex, int highIndex) {
            if (lowIndex > highIndex) {
                return -1;
            }
            if (lowIndex == highIndex) {
                return nums[lowIndex] == target ? lowIndex : -1;
            }

            int low = nums[lowIndex];
            int high = nums[highIndex];

            int middleIndex = (lowIndex - highIndex) / 2 + highIndex;
            int middle = nums[middleIndex];
            if (target == middle) {
                return middleIndex;
            }

            // 오름차순 정렬인 경우
            if (low < high) {
                if (target < middle) {
                    return binarySearch(nums, target, lowIndex, middleIndex - 1);
                } else {
                    return binarySearch(nums, target, middleIndex + 1, highIndex);
                }
            } else if (low > high) { // 중간([lowIndex+1, highIndex] 구간)에 변화되는 점이 있는 경우
                if (low < middle) {
                    return (low <= target) && (target < middle)
                            ? binarySearch(nums, target, lowIndex, middleIndex - 1)
                            : binarySearch(nums, target, middleIndex + 1, highIndex);
                } else {
                    return (middle < target) && (target <= high)
                            ? binarySearch(nums, target, middleIndex + 1, highIndex)
                            : binarySearch(nums, target, lowIndex, middleIndex - 1);
                }
            } else {
                throw new IllegalArgumentException("nums 는 같은 요소가 포함되지 않아야 합니다.");
            }
        }
    }
}
