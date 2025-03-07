package sunset.leetcode;

import java.util.Arrays;

public class P0042_TrappingRainWater {

    public static void main(String[] args) {
        Solution solution = new P0042_TrappingRainWater().new Solution();
        int[] input = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        int result = solution.trap(input);
        System.out.println(result);
    }

    class Solution {

        public int trap(int[] height) {
            // 물이 갇힌 값 리스트. 최초 초기값은 모두 -1
            int[] waterTrapped = new int[height.length];
            Arrays.fill(waterTrapped, -1);

            // height 가 내림차순으로 정렬된 인덱스 리스트
            Integer[] sortedIndices = getDescendingSortedIndices(height);

            // 높은 기둥 순서대로 두개씩 고르면, 해당 기둥 안에 있는 워터 트랩은 두 기둥중 낮은 기둥 - 해당 인덱스의 기둥높이들의 합이 된다.
            for (int i = 0; i < sortedIndices.length - 1; ++i) {
                int startIndex = Math.min(sortedIndices[i], sortedIndices[i + 1]);
                int endIndex = Math.max(sortedIndices[i], sortedIndices[i + 1]);
                if (waterTrapped[startIndex] > 0 && waterTrapped[endIndex] > 0) {
                    continue;
                }

                for (int j = startIndex; j <= endIndex; ++j) {
                    if (waterTrapped[j] == -1) {
                        int waterTrapValue = height[sortedIndices[i + 1]] - height[j];
                        if (waterTrapValue < 0) {
                            waterTrapValue = 0;
                        }
                        waterTrapped[j] = waterTrapValue;
                    }
                }
            }

            int result = 0;
            for (int trap : waterTrapped) {
                result += trap;
            }
            return Math.max(result, 0);
        }

        private Integer[] getDescendingSortedIndices(int[] arr) {
            Integer[] indices = new Integer[arr.length];

            for (int i = 0; i < arr.length; ++i) {
                indices[i] = i;
            }

            Arrays.sort(indices, (i, j) -> Integer.compare(arr[j], arr[i]));

            return indices;
        }
    }
}
