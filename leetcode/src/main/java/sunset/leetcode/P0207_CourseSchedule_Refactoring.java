package sunset.leetcode;

import java.util.*;

public class P0207_CourseSchedule_Refactoring {

    public static void main(String[] args) {
        P0207_CourseSchedule_Refactoring.Solution solution = new P0207_CourseSchedule_Refactoring().new Solution();
        int numCourses = NUM_COURSES_4;
        int[][] prerequisites = PREREQUISITES_4;

        boolean result = solution.canFinish(numCourses, prerequisites);
        System.out.println(result);
    }

    /**
     * 모든 정점에서 각각 해당 정점으로 돌아오는 경로가 있는지를 dfs 로 탐색한다.
     * 이 알고리즘은 비효율적인데 이유는 예를들면 1번 정점에서 사이클확인하는 것이 2번 정점에서 사이클확인하는 것을 포함하더라도
     * 따로따로 계산하고 있다. 그렇기 때문에 캐시를 사용할 수도 없어서 성능개선을 하려면 이부분을 모두 수정해야한다.
     *
     * 시간복잡도: O(|E|)
     * 결과: 28ms / 47.6MB
     */
    class Solution {
        private int numCourses;
        // -1 초기화, 0 사이클 없음, 1 사이클 있음
        private int[] resultCache;
        // startVertex: [endVertex, ...]
        private Map<Integer, List<Integer>> graph;

        public boolean canFinish(int numCourses, int[][] prerequisites) {
            this.numCourses = numCourses;
            this.resultCache = new int[numCourses];
            Arrays.fill(resultCache, -1);
            this.graph = makeGraph(prerequisites);

            for (Integer startVertex: graph.keySet()) {
                if (dfs(startVertex, new ArrayList<>())) {
                    return false;
                }
            }

            return true;
        }

        private Map<Integer, List<Integer>> makeGraph(int[][] prerequisites) {
            Map<Integer, List<Integer>> graph = new HashMap<>();
            for (int[] prerequisite: prerequisites) {
                graph.putIfAbsent(prerequisite[0], new ArrayList<>());
                graph.get(prerequisite[0]).add(prerequisite[1]);
            }
            return graph;
        }

        /**
         * startVertex 에서 시작해서 그래프에 사이클이 있는지를 dfs 로 조회한다.
         *
         * @param startVertex 시작점
         * @param visited 깊이있게 방문한 정점들
         * @return 사이클이 있으면 true, 없으면 false
         */
        private boolean dfs(int startVertex, List<Integer> visited) {
            // 이미 탐색된 결과가 있다면 리턴
            if (resultCache[startVertex] != -1) {
                return resultCache[startVertex] == 1;
            }
            // 시작정점이 이미 방문한 정점이면 사이클이 있다고 판단된다.
            if (visited.contains(startVertex)) {
                return true;
            }

            List<Integer> nextVertexList = graph.get(startVertex);
            if (nextVertexList == null) {
                resultCache[startVertex] = 0;
                return false;
            }

            visited.add(startVertex);
            for (int nextVertex: nextVertexList) {
                if (dfs(nextVertex, visited)) {
                    return true;
                }
            }
            visited.remove(visited.size() - 1);
            resultCache[startVertex] = 0;
            return false;
        }
    }

    // true
    private static final int NUM_COURSES_1 = 2;
    private static final int[][] PREREQUISITES_1 = new int[][]{{1, 0}};

    // false
    private static final int NUM_COURSES_2 = 2;
    private static final int[][] PREREQUISITES_2 = new int[][]{
            {1, 0}, {0, 1}
    };

    // true
    private static final int NUM_COURSES_3 = 2;
    private static final int[][] PREREQUISITES_3 = new int[][]{};

    // false
    private static final int NUM_COURSES_4 = 5;
    private static final int[][] PREREQUISITES_4 = new int[][]{
            {0, 1}, {1, 2}, {2, 3}, {2, 4}, {3, 1}
    };

    // true
    private static final int NUM_COURSES_5 = 5;
    private static final int[][] PREREQUISITES_5 = new int[][]{
            {1, 4}, {2, 4}, {3, 1}, {3, 2}
    };
}
