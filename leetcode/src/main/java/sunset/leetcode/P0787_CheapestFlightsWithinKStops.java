package sunset.leetcode;

import java.util.*;

public class P0787_CheapestFlightsWithinKStops {

    // 답: 700
    private static final Input INPUT_1 = new Input(
            4, 0, 3, 1,
            new int[][]{ {0, 1, 100}, {1, 2, 100}, {2, 0, 100}, {1, 3, 600}, {2, 3, 200} }
    );

    // 답: 200
    private static final Input INPUT_2 = new Input(
            3, 0, 2, 1,
            new int[][]{ {0, 1, 100}, {1, 2, 100}, {0, 2, 500} }
    );

    // 답: 500
    private static final Input INPUT_3 = new Input(
            3, 0 , 2, 0,
            new int[][]{ {0, 1, 100}, {1, 2, 100}, {0, 2, 500} }
    );

    // 답: 7
    private static final Input INPUT_4 = new Input(
            8, 0, 6, 2,
            new int[][]{
                    {0, 1, 1}, {0, 2, 2},
                    {1, 3, 1}, {1, 4, 5},
                    {2, 5, 2},
                    {3, 4, 1}, {3, 6, 7},
                    {4, 6, 1},
                    {5, 6, 4}, {5, 7, 1},
                    {7, 6, 1}
            }
    );

    // 답: 7
    private static final Input INPUT_5 = new Input(
            5, 0, 2, 2,
            new int[][]{
                    {0, 1, 5}, {0, 3, 2},
                    {1, 2, 5}, {1, 4, 1},
                    {3, 1, 2},
                    {4, 2, 1}
            }
    );

    public static void main(String[] args) {
        Input input = INPUT_5;

        int result = new P0787_CheapestFlightsWithinKStops().new Solution().findCheapestPrice(
                input.n, input.flights, input.src, input.dst, input.k
        );
        System.out.println(result);
    }

    /**
     * 다익스트라 알고리즘을 구현해 해결한다.
     * (거리가 짧은순, 환승가능횟수가 많은순) 으로 그리디하게 선택해서 처리한다.
     * TODO: 중간 정점에서 환승가능횟수가 같을때 거리가 짧은 경로가 무조건 먼저 선택되는게 직관적으로 잘 이해가 가지 않는다.
     *
     * - 시간복잡도: O(ElogE) / 이유는 모든 간선을 탐색하는데, 우선순위큐(힙)을 이용하므로 큐에 삽입할때 logE가 걸리게 된다.<br>
     * - 공간복잡도: O(V^2)? / 이유는 distanceMap 의 상한이 각 정점마다 생성되고, 정점마다는 최대 k개까지 생성될 수 있는데 문제에서 k<n 이라고 했으므로 k 상한은 V라고 생각할 수 있을 것 같다.<br>
     * - 결과: 44ms / 48.21MB<br>
     */
    class Solution {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
            // 그래프 생성
            // key: src
            // value: [[src, dst, w], ...]
            Map<Integer, List<int[]>> graph = makeGraph(flights);

            // distanceMap.get(m).get(a) = 환승이 a번 가능한 시작 정점으로 부터 m 까지의 최소 거리
            Map<Integer, Map<Integer, Integer>> distanceMap = new HashMap<>();
            Map<Integer, Integer> srcMap = new HashMap<>();
            srcMap.put(k, 0);
            distanceMap.put(src, srcMap);

            // pq: [[src, dst, distance, 환승가능횟수], ..]
            Queue<int[]> pq = new PriorityQueue<>((a, b) -> {
                // distance 가 작은 순서
                if (a[2] != b[2]) {
                    return Integer.compare(a[2], b[2]);
                }

                // 환승가능횟수 가 큰 순서
                return Integer.compare(b[3], a[3]);
            });

            // 큐를 초기화한다.
            for (int[] edge: graph.getOrDefault(src, Collections.emptyList())) {
                int transit = edge[1] == dst ? 0 : 1;
                pq.offer(new int[] {edge[0], edge[1], edge[2], k - transit});
            }

            while(!pq.isEmpty()) {
                // 큐에서 하나 뽑는다.
                int[] picked = pq.poll();
                int u = picked[0];
                int v = picked[1];
                int dist = picked[2];
                int transitableCount = picked[3];

                // 환승가능횟수가 음수로 모두 차감되었다면 해당 경로는 처리할 수 없다.
                if (transitableCount < 0) {
                    continue;
                }

                // 해당 환승가능횟수에 데이터가 있다면 이미 방문한것이므로 처리하지 않는다.
                // TODO: 이 정보도 그리디가 적용되는걸 어떻게 보장할 수 있는가?
                Map<Integer, Integer> vDistanceMap = distanceMap.get(v);
                if (vDistanceMap != null) {
                    if (vDistanceMap.get(transitableCount) != null) {
                        continue;
                    }
                }

                distanceMap.putIfAbsent(v, new HashMap<>());
                distanceMap.get(v).put(transitableCount, dist);
                if (v == dst) {
                    break;
                }

                for (int[] edge: graph.getOrDefault(v, Collections.emptyList())) {
                    int transit = edge[1] == dst ? 0 : 1;
                    pq.offer(new int[] {edge[0], edge[1], dist + edge[2], transitableCount - transit});
                }
            }

            Map<Integer, Integer> dstDistanceMap = distanceMap.get(dst);
            if (dstDistanceMap == null || dstDistanceMap.isEmpty()) {
                return -1;
            }
            int result = Integer.MAX_VALUE;
            for (int val: dstDistanceMap.values()) {
                result = Integer.min(result, val);
            }
            return result;
        }

        private Map<Integer, List<int[]>> makeGraph(int[][] flights) {
            Map<Integer, List<int[]>> graph = new HashMap<>();
            for (int[] flight: flights) {
                graph.putIfAbsent(flight[0], new ArrayList<>());
                graph.get(flight[0]).add(flight);
            }
            return graph;
        }
    }

    static class Input {
        int n;
        int src;
        int dst;
        int k;
        int[][] flights;

        public Input(int n, int src, int dst, int k, int[][] flights) {
            this.n = n;
            this.src = src;
            this.dst = dst;
            this.k = k;
            this.flights = flights;
        }
    }
}
