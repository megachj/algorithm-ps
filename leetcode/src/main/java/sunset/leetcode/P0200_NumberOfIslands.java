package sunset.leetcode;

public class P0200_NumberOfIslands {

    public static void main(String[] args) {
        char[][] input = new char[][] {
                {'1','1','1','1','0'},
                {'1','1','0','1','0'},
                {'1','1','0','0','0'},
                {'0','0','0','0','0'}
        };

        Solution solution = new P0200_NumberOfIslands().new Solution();
        int result = solution.numIslands(input);
        System.out.println(result);
    }

    /**
     * 재귀를 통해 깊이우선탐색(DFS) 을 진행한다.<br>
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(n)<br>
     * - 결과: 4ms / 52.29MB<br>
     */
    class Solution {
        private char[][] grid;
        private boolean[][] visited;

        private int rowLimit; // 0 <= row < rowLimit
        private int colLimit; // i <= col < colLimit

        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0 || grid[0].length == 0) {
                throw new IllegalArgumentException();
            }

            // rowLimit, colLimit 설정
            this.grid = grid;
            rowLimit = grid.length;
            colLimit = grid[0].length;

            // visited 초기화
            visited = new boolean[grid.length][grid[0].length];
            for (int i = 0; i < grid.length; ++i) {
                for (int j= 0; j < grid[i].length; ++j) {
                    visited[i][j] = false;
                }
            }

            // dfs
            int result = 0;
            for (int i = 0; i < rowLimit; ++i) {
                for (int j = 0; j < colLimit; ++j) {
                    if (dfs(i, j)) {
                        result++;
                    }

                }
            }

            return result;
        }

        private boolean dfs(int i, int j) {
            // 범위를 벗어나면 방문하지 않는다.
            if (i < 0 || i >= rowLimit || j < 0 || j >= colLimit) {
                return false;
            }
            // 이미 방문했거나, 물이라면 방문하지 않는다.
            if (visited[i][j] || grid[i][j] == '0') {
                return false;
            }

            // 현재 정점 방문 기록
            visited[i][j] = true;

            // 우
            dfs(i+1, j);

            // 하
            dfs(i, j+1);

            // 좌
            dfs(i-1, j);

            // 상
            dfs(i, j-1);

            return true;
        }
    }
}
