package sunset.leetcode;

import java.util.Arrays;

public class P0042_TrappingRainWater {

    public static void main(String[] args) {
//        int[] input = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        int[] input = new int[]{4, 3, 2, 1};

        Solution solution = new P0042_TrappingRainWater().new Solution();
        int result = solution.trap(input);
        System.out.println(result);

        Solution2 solution2 = new P0042_TrappingRainWater().new Solution2();
        int result2 = solution2.trap(input);
        System.out.println(result2);
    }

    /**
     * 설명: 내림차순으로 정렬한 뒤, 앞에서부터 슬라이딩으로 두개씩 잡아 그 구간의 물 트래핑을 더해간다.<br>
     * - 시간복잡도: O(n^2)<br>
     * - 공간복잡도: O(n)<br>
     * - 결과: 11ms / 45.5MB<br>
     */
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

    /**
     * 설명: 투포인터를 이용한다.<br>
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(1)<br>
     * - 결과: 0ms / 46.74MB<br>
     */
    class Solution2 {

        public int trap(int[] height) {
            int leftPointer = 0; // 좌측 포인터
            int maxLeftHeight = 0; // 좌측 포인터가 현재까지 찾은 가장 큰 높이
            int rightPointer = height.length - 1; // 우측 포인터
            int maxRightHeight = 0; // 우측 포인터가 현재까지 찾은 가장 큰 높이
            int answer = 0; // 결과. 지금까지 가둔 물 높이

            while (leftPointer < rightPointer) {
                int leftHeight = height[leftPointer];
                int rightHeight = height[rightPointer];

                if (leftHeight <= rightHeight) {
                    if (leftHeight < maxLeftHeight) {
                        int trapped = maxLeftHeight - leftHeight;
                        if (trapped > 0) answer += trapped;
                    }
                    maxLeftHeight = Math.max(maxLeftHeight, leftHeight);
                    leftPointer++;
                } else {
                    if (rightHeight < maxRightHeight) {
                        int trapped = maxRightHeight - rightHeight;
                        if (trapped > 0) answer += trapped;
                    }
                    maxRightHeight = Math.max(maxRightHeight, rightHeight);
                    rightPointer--;
                }
            }

            return answer;
        }
    }

    /**
     * 설명: 스택을 이용한다.<br>
     * - 시간복잡도: <br>
     * - 공간복잡도: <br>
     * - 결과: <br>
     */
    class Solution3 {

//        public int trap(int[] height) {
//        }
    }
}
