package sunset.leetcode.medium;

public class P0240_Search2DMatrix2 {

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {1,4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        int target = 20;

        Solution solution = new P0240_Search2DMatrix2().new Solution();

        boolean output = solution.searchMatrix(matrix, target);

        System.out.println(output);
    }

    class Solution {
        public boolean searchMatrix(int[][] matrix, int target) {
            for (int[] row: matrix) {
                if (binarySearch(row, target, 0, row.length - 1)) {
                    return true;
                }
            }
            return false;
        }

        private boolean binarySearch(int[] row, int target, int lowIndex, int highIndex) {
            if (lowIndex > highIndex) {
                return false;
            }
            if (lowIndex == highIndex) {
                return row[lowIndex] == target;
            }
            // TODO: 효율적이게 되는지 확인이 필요. 있든없든 큰 차이가 있을까 싶긴하다.
            if (isNotInterval(row, target, lowIndex, highIndex)) {
                return false;
            }

            int middleIndex = lowIndex + (highIndex - lowIndex) / 2;
            int middleValue = row[middleIndex];
            if (target == middleValue) {
                return true;
            } else if (target < middleValue) {
                return binarySearch(row, target, lowIndex, middleIndex - 1);
            } else {
                return binarySearch(row, target, middleIndex + 1, highIndex);
            }
        }

        private boolean isNotInterval(int[] row, int target, int lowIndex, int highIndex) {
            int lowValue = row[lowIndex];
            int highValue = row[highIndex];
            return lowValue > target || target > highValue;
        }
    }
}
