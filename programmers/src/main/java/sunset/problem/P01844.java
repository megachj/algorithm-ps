package sunset.problem;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class P01844 {

    // 답: 11
    private static final int[][] INPUT_1 = new int[][] {
            {1,0,1,1,1}, {1,0,1,0,1}, {1,0,1,1,1}, {1,1,1,0,1}, {0,0,0,0,1}
    };

    // 답: -1
    private static final int[][] INPUT_2 = new int[][] {
            {1,0,1,1,1}, {1,0,1,0,1}, {1,0,1,1,1}, {1,1,1,0,0}, {0,0,0,0,1}
    };

    public static void main(String[] args) {
        int[][] maps = INPUT_2;
        int result = new P01844().new Solution().solution(maps);
        System.out.println(result);
    }

    class Solution {
        private static final int NO_VISITED = 100_000;
        private static final int WALL = Integer.MAX_VALUE;

        public int solution(int[][] maps) {
            int n = maps.length;
            int m = maps[0].length;

            // 방문정보 자료구조
            // 벽 아니고 아직 방문안한 점: NO_VISITED
            // 벽인 점: WALL
            int[][] visited = new int[n][m];
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    if (maps[i][j] == 1) {
                        visited[i][j] = NO_VISITED;
                    } else {
                        visited[i][j] = WALL;
                    }
                }
            }

            // pq 생성
            // dst n, dst m, distance
            Queue<int[]> pq = new PriorityQueue<>(
                    Comparator.comparing(a -> a[2])
            );

            // bfs 탐색
            pq.offer(new int[]{0, 0, 1});
            while (!pq.isEmpty()) {
                int[] dst = pq.poll();
                int dstN = dst[0];
                int dstM = dst[1];
                int distance = dst[2];

                // 인덱스 범위를 벗어났다면 스킵
                if (dstN < 0 || dstN >= n || dstM < 0 || dstM >= m) {
                    continue;
                }

                // 벽이거나 또는 이미 방문한 점이면 스킵
                int dstVisited = visited[dstN][dstM];
                if (dstVisited == WALL || dstVisited < NO_VISITED) {
                    continue;
                }

                // 방문
                visited[dstN][dstM] = distance;

                // 다음 정점 추가
                pq.offer(new int[]{dstN, dstM + 1, distance+1});
                pq.offer(new int[]{dstN + 1, dstM, distance+1});
                pq.offer(new int[]{dstN, dstM - 1, distance+1});
                pq.offer(new int[]{dstN - 1, dstM, distance+1});
            }

            return visited[n-1][m-1] == NO_VISITED ? -1 : visited[n-1][m-1];
        }
    }
}
