package sunset.leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class P0207_CourseSchedule {

    public static void main(String[] args) {
        Solution solution = new P0207_CourseSchedule().new Solution();
        int numCourses = NUM_COURSES_5;
        int[][] prerequisites = PREREQUISITES_5;

        boolean result = solution.canFinish(numCourses, prerequisites);
        System.out.println(result);
    }

    /**
     * 모든 정점에서 각각 해당 정점으로 돌아오는 경로가 있는지를 dfs 로 탐색한다.
     * 이 알고리즘은 비효율적인데 이유는 예를들면 1번 정점에서 사이클확인하는 것이 2번 정점에서 사이클확인하는 것을 포함하더라도
     * 따로따로 계산하고 있다. 그렇기 때문에 캐시를 사용할 수도 없어서 성능개선을 하려면 이부분을 모두 수정해야한다.
     *
     * 시간복잡도: O(|V||E|)
     * 결과: 1539ms / 49.53MB
     */
    class Solution {
        private int numCourses;
        // startVertex: [endVertex, ...]
        private Map<Integer, List<Integer>> graph;
        private Map<List<Integer>, Boolean> visitedEdge;

        public boolean canFinish(int numCourses, int[][] prerequisites) {
            this.numCourses = numCourses;
            graph = makeGraph(prerequisites);
            visitedEdge = makeVisitedEdge(prerequisites);

            for (Integer initVertex: graph.keySet()) {
                if (existsCycle(initVertex)) {
                    return false;
                }
            }

            return true;
        }

        private Map<Integer, List<Integer>> makeGraph(int[][] prerequisites) {
            if (prerequisites == null) {
                return Collections.emptyMap();
            }
            return Arrays.stream(prerequisites)
                    .collect(Collectors.groupingBy(
                            schedule -> schedule[0],
                            Collectors.mapping(
                                    schedule -> schedule[1],
                                    Collectors.toList()
                            )
                    ));
        }

        private Map<List<Integer>, Boolean> makeVisitedEdge(int[][] prerequisites) {
            if (prerequisites == null) {
                return Collections.emptyMap();
            }
            return Arrays.stream(prerequisites)
                    .map(schedule -> List.of(schedule[0], schedule[1]))
                    .collect(Collectors.toMap(
                            edge -> edge,
                            edge -> false
                    ));
        }

        private boolean existsCycle(int initVertex) {
            List<Integer> nextVertexList = graph.get(initVertex);
            if (nextVertexList == null) {
                return false;
            }

            for (Map.Entry<List<Integer>, Boolean> entry: visitedEdge.entrySet()) {
                entry.setValue(false);
            }

            for (Integer nextVertex: nextVertexList) {
                if (dfsAndExistsCycle(initVertex, nextVertex)) {
                    return true;
                }
            }
            return false;
        }

        private boolean dfsAndExistsCycle(int initVertex, int startVertex) {
            if (initVertex == startVertex) {
                return true;
            }

            List<Integer> nextVertexList = graph.get(startVertex);
            if (nextVertexList == null) {
                return false;
            }
            for (Integer nextVertex: nextVertexList) {
                List<Integer> edge = List.of(startVertex, nextVertex);
                if (!visitedEdge.get(edge)) {
                    visitedEdge.put(edge, true);
                    if (dfsAndExistsCycle(initVertex, nextVertex)) {
                        return true;
                    }
                }
            }
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
