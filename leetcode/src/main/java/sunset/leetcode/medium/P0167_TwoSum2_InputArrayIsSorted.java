package sunset.leetcode.medium;

public class P0167_TwoSum2_InputArrayIsSorted {

    public static void main(String[] args) {
        // {2, 7, 11, 15}, 9 -> [1, 2]
        // {2, 3, 4}, 6 -> [1, 3]
        // {-1, 0}, -1 -> [1, 2]
        int[] numbers = new int[]{-1, 0};
        int target = -1;

        Solution solution = new P0167_TwoSum2_InputArrayIsSorted().new Solution();
        int[] output = solution.twoSum(numbers, target);

        System.out.printf("[%d, %d]\n", output[0], output[1]);
    }

    class Solution {
        public int[] twoSum(int[] numbers, int target) {
            for (int i = 0; i < numbers.length; ++i) {
                int binarySearchTargetIndex = binarySearch(numbers, target - numbers[i], i + 1, numbers.length - 1);
                if (binarySearchTargetIndex != -1) {
                    return i < binarySearchTargetIndex
                            ? new int[]{i + 1, binarySearchTargetIndex + 1}
                            : new int[]{binarySearchTargetIndex + 1, i + 1};
                }
            }
            throw new IllegalArgumentException("해답이 존재하지 않는 케이스입니다.");
        }

        /**
         *
         * @return 찾은 값의 인덱스, 존재하지 않으면 -1
         */
        private int binarySearch(int[] numbers, int target, int lowIndex, int highIndex) {
            if (lowIndex == highIndex) {
                return numbers[lowIndex] == target ? lowIndex : -1;
            }

            int middleIndex = (highIndex - lowIndex) / 2 + lowIndex;
            int middleValue = numbers[middleIndex];

            if (target <= middleValue) {
                return binarySearch(numbers, target, lowIndex, middleIndex);
            } else {
                return binarySearch(numbers, target, middleIndex + 1, highIndex);
            }
        }
    }
}
