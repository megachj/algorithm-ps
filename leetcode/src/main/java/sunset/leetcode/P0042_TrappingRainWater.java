package sunset.leetcode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class P0042_TrappingRainWater {

    public static void main(String[] args) {
        int[] input1 = new int[]{0,1,0,2,1,0,1,3,2,1,2,1}; // 6
        int[] input2 = new int[]{4, 2, 0, 3, 2, 5}; // 9
        int[] input3 = new int[]{4, 3, 2, 1}; // 0

        int[] input = input1;
        Solution solution = new P0042_TrappingRainWater().new Solution();
        int result = solution.trap(input);
        System.out.println(result);

        Solution2 solution2 = new P0042_TrappingRainWater().new Solution2();
        int result2 = solution2.trap(input);
        System.out.println(result2);

        Solution3 solution3 = new P0042_TrappingRainWater().new Solution3();
        int result3 = solution3.trap(input);
        System.out.println(result3);
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
     * - 결과: 0ms / 47.00MB<br>
     */
    class Solution2 {

        public int trap(int[] height) {
            int leftPointer = 0; // 좌측 포인터
            int maxLeftHeight = 0; // 좌측 포인터가 현재까지 찾은 가장 큰 높이
            int rightPointer = height.length - 1; // 우측 포인터
            int maxRightHeight = 0; // 우측 포인터가 현재까지 찾은 가장 큰 높이
            int answer = 0; // 결과. 지금까지 가둔 물 높이

            while (leftPointer < rightPointer) {
                maxLeftHeight = Math.max(maxLeftHeight, height[leftPointer]);
                maxRightHeight = Math.max(maxRightHeight, height[rightPointer]);

                if (maxLeftHeight <= maxRightHeight) {
                    answer += maxLeftHeight - height[leftPointer];
                    leftPointer++;
                } else {
                    answer += maxRightHeight - height[rightPointer];
                    rightPointer--;
                }
            }

            return answer;
        }
    }

    /**
     * 설명: 스택을 이용한다.<br>
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(n)<br>
     * - 결과: 5ms / 45.68MB<br>
     */
    class Solution3 {

        private int[] height;

        private int answer;
        private int alreadyMaxHeight; // 이제까지 가장 높았던 높이
        private int alreadyMaxIndex; // 이제까지 가장 높았던 인덱스
        private int pointer;
        private Deque<Integer> indexStack;

        public int trap(int[] height) {
            init(height);

            while (pointer < height.length) {
                // 스택이 비었으면 추가하고 포인터 이동한다.
                if (indexStack.isEmpty()) {
                    nextPointerAndMaybeSetMaxValue();
                    continue;
                }

                // 스택에 있다면
                int curHeight = height[pointer];
                int targetHeight = Math.min(alreadyMaxHeight, curHeight);

                while (!indexStack.isEmpty()) {
                    int stackIndex = indexStack.peek();
                    int stackHeight = height[stackIndex];
                    // 스택에 있는게 현재 높이보다 크거나 같다면 끝낸다.
                    if (stackHeight >= curHeight) {
                        break;
                    }

                    // 스택에 있는게 현재 높이보다 작은 경우는 물이 가둬질 수 있는 가능성이 있다.
                    indexStack.pop(); // 아까 안뺀거 빼둔다.

                    // 스택에서 하나 더 전 값을 조회한다.
                    Integer beforeIndex = indexStack.peek();

                    // 하나 더 전 값이 없다면 물이 가둬질 수 없으므로 끝낸다.
                    if (beforeIndex == null) {
                        break;
                    }

                    // 물 가둬진 것을 계산한다.
                    answer += ((targetHeight - stackHeight) * (stackIndex - beforeIndex));
                }

                nextPointerAndMaybeSetMaxValue();
            }

            return answer;
        }

        private void init(int[] height) {
            this.height = height;
            this.answer = 0;
            this.alreadyMaxHeight = 0; // 이제까지 가장 높았던 높이
            this.alreadyMaxIndex = -1; // 이제까지 가장 높았던 인덱스
            this.pointer = 0;
            this.indexStack = new ArrayDeque<>();
        }

        private void nextPointerAndMaybeSetMaxValue() {
            if (alreadyMaxHeight <= height[pointer]) {
                alreadyMaxHeight = height[pointer];
                alreadyMaxIndex = pointer;
            }
            indexStack.push(pointer++);
        }
    }
}
