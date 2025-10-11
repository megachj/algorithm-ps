package sunset.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 1 <= k <= points.length <= 10^4
 * -10^4 <= xi, yi <= 10^4
 *
 * N = points.length
 */
public class P0973_KClosestPointsToOrigin {

    public static void main(String[] args) {
        int[][] points = new int[][]{
                {1, 3},
                {-2, 2}
        };
        int k = 1;

        int[][] result = new P0973_KClosestPointsToOrigin().new Solution().kClosest(points, k);
        System.out.printf("[%d, %d]\n", result[0][0], result[0][1]);
    }

    /**
     * 직접 생각한 풀이법. -> 책 풀이도 동일하다.
     * 우선순위 큐에 (인덱스, 거리정보)를 모두 넣어서, k개 뽑아서 결과를 얻는다.
     *
     * - 시간복잡도: O(N logN) + O(k logN) = O(N logN)
     * - 공간복잡도: O(N)
     * - 결과: 24ms / 52.01MB
     */
    class Solution {
        public int[][] kClosest(int[][] points, int k) {
            // [0]: index, [1]: 원점으로부터 거리^2
            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(
                    Comparator.comparingInt(o -> o[1])
            );

            for (int i = 0; i < points.length; ++i) {
                int distanceV2 = (points[i][0] * points[i][0]) + (points[i][1] * points[i][1]);
                priorityQueue.add(new int[]{i, distanceV2});
            }

            int[][] result = new int[k][];
            for (int i = 0; i < k; ++i) {
                result[i] = points[priorityQueue.poll()[0]];
            }

            return result;
        }
    }
}
