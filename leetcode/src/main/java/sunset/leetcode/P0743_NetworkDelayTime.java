package sunset.leetcode;

import java.util.*;

public class P0743_NetworkDelayTime {

    public static void main(String[] args) {
        P0743_NetworkDelayTime.Solution solution = new P0743_NetworkDelayTime().new Solution();
        int[][] times = TIMES_6;
        int n = N_6;
        int k = K_6;

        int result = solution.networkDelayTime(times, n, k);
        System.out.println(result);
    }

    /**
     * 다익스트라 알고리즘을 구현해 해결한다.
     * - 시간복잡도: O(ElogE) / 이유는 모든 간선을 탐색하는데, 우선순위큐(힙)을 이용하므로 큐에 삽입할때 logE가 걸리게 된다.<br>
     * - 공간복잡도: O(V + E)<br>
     * - 결과: 38ms / 51.39MB<br>
     */
    class Solution {
        public int networkDelayTime(int[][] times, int n, int k) {
            // 그래프 생성
            Map<Integer, List<int[]>> graph = makeGraph(times);

            // minDistances[u-1] = k 에서 정점 u까지의 최소거리
            int[] minDistances = new int[n]; // 인덱스: [0, n)
            Arrays.fill(minDistances, Integer.MAX_VALUE);
            minDistances[k-1] = 0;

            // k 에서 각 정점까지 최소 거리 구하기: 다익스트라 알고리즘(bfs)
            // pq: [(정점, 누적거리), (정점, 누적거리), ...]
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
            for (int[] edge: graph.getOrDefault(k, Collections.emptyList())) {
                pq.offer(new int[]{edge[1], minDistances[k-1] + edge[2]});
            }

            while (!pq.isEmpty()) {
                int[] element = pq.poll();
                int u = element[0];
                int sumDistance = element[1];

                if (minDistances[u-1] < Integer.MAX_VALUE) {
                    continue;
                }

                minDistances[u-1] = sumDistance;
                for (int[] edge: graph.getOrDefault(u, Collections.emptyList())) {
                    pq.offer(new int[]{edge[1], sumDistance + edge[2]});
                }
            }

            int result = 0;
            for (int minDistance: minDistances) {
                result = Math.max(minDistance, result);
            }

            if (result == Integer.MAX_VALUE) {
                return -1;
            } else {
                return result;
            }
        }

        // key: u, value: [(u, v, w), ...]
        // 값에 u는 필요하지 않지만 그러면 int[] 를 다시 생성해야해서 기존 객체를 쓰기 위해 그대로 포함해서 사용한다.
        private Map<Integer, List<int[]>> makeGraph(int[][] times) {
            Map<Integer, List<int[]>> graph = new HashMap<>();
            for (int[] time: times) {
                graph.putIfAbsent(time[0], new ArrayList<>());
                graph.get(time[0]).add(time);
            }
            return graph;
        }
    }

    // 답: 2
    private static final int[][] TIMES_1 = new int[][]{
            {2, 1, 1}, {2, 3, 1}, {3, 4, 1}
    };
    private static final int N_1 = 4;
    private static final int K_1 = 2;

    // 답: 1
    private static final int[][] TIMES_2 = new int[][]{
            {1, 2, 1}
    };
    private static final int N_2 = 2;
    private static final int K_2 = 1;

    // 끊어져 있는 정점이 있는 경우
    // 답: -1
    private static final int[][] TIMES_3 = new int[][]{
            {1, 2, 1}
    };
    private static final int N_3 = 2;
    private static final int K_3 = 2;

    // 돌아가면 더 짧은 길이 있는 경우
    // 답: 2
    private static final int[][] TIMES_4 = new int[][]{
            {1, 2, 1}, {1, 3, 3}, {2, 3, 1}
    };
    private static final int N_4 = 3;
    private static final int K_4 = 1;

    // 사이클이 있는 경우
    // 답: 2
    private static final int[][] TIMES_5 = new int[][]{
            {1, 2, 1}, {2, 1, 1}, {2, 3, 1}
    };
    private static final int N_5 = 3;
    private static final int K_5 = 1;

    // 우선순위 큐를 사용하지 않고 큐를 사용하면 못 푸는 경우
    // 답: 4
    private static final int[][] TIMES_6 = new int[][]{
            {1, 2, 5}, {1, 3, 2}, {2, 4, 1}, {3, 2, 1}
    };
    private static final int N_6 = 4;
    private static final int K_6 = 1;
}
