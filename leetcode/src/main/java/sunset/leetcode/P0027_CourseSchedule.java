package sunset.leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class P0027_CourseSchedule {

    public static void main(String[] args) {
        Solution solution = new P0027_CourseSchedule().new Solution();
        int numCourses = NUM_COURSES_5;
        int[][] prerequisites = PREREQUISITES_5;

        boolean result = solution.canFinish(numCourses, prerequisites);
        System.out.println(result);
    }

    /**
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
                if (existsCircle(initVertex)) {
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

        private boolean existsCircle(int initVertex) {
            List<Integer> nextVertexList = graph.get(initVertex);
            if (nextVertexList == null) {
                return false;
            }

            for (Map.Entry<List<Integer>, Boolean> entry: visitedEdge.entrySet()) {
                entry.setValue(false);
            }

            for (Integer nextVertex: nextVertexList) {
                if (dfsAndExistsCircle(initVertex, nextVertex)) {
                    return true;
                }
            }
            return false;
        }

        private boolean dfsAndExistsCircle(int initVertex, int startVertex) {
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
                    if (dfsAndExistsCircle(initVertex, nextVertex)) {
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
