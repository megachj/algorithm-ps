package sunset.leetcode;

import java.util.*;

public class P0310_MinimumHeightTrees {

    private static final Input INPUT_1 = new Input(4, new int[][]{
            {1, 0}, {1, 2}, {1, 3}
    });

    private static final Input INPUT_2 = new Input(6, new int [][]{
            {3, 0}, {3, 1}, {3, 2}, {3, 4}, {5, 4}
    });

    public static void main(String[] args) {
       Input input = INPUT_2;
       var output = new P0310_MinimumHeightTrees().new Solution1().findMinHeightTrees(input.n, input.edges);

        System.out.println(output);
    }

    /**
     * 시간 초과
     */
    class Solution {
        public List<Integer> findMinHeightTrees(int n, int[][] edges) {
            if (n == 1) {
                return Collections.singletonList(0);
            }

            Map<Integer, Set<Integer>> edgeMap = createEdgeMap(n, edges);

            while (true) {
                List<Integer> middleNodes = new ArrayList<>();
                List<Integer> leafNodes = new ArrayList<>();
                for (Map.Entry<Integer, Set<Integer>> entry: edgeMap.entrySet()) {
                    var edgeSize = entry.getValue().size();
                    if (edgeSize == 0) {
                        continue;
                    } else if (edgeSize == 1) {
                        leafNodes.add(entry.getKey());
                    } else {
                        middleNodes.add(entry.getKey());
                    }
                }

                // 2개만 남게되면서 종료되는 경우
                if (middleNodes.isEmpty() && leafNodes.size() == 2) {
                    return leafNodes;
                }
                // 1개만 남게되면서 종료되는 경우
                if (middleNodes.size() == 1 && !leafNodes.isEmpty()) {
                    return middleNodes;
                }

                for (Integer key: leafNodes) {
                    int value = (int) edgeMap.get(key).toArray()[0];

                    edgeMap.get(key).clear();
                    edgeMap.get(value).remove(key);
                }
            }
        }

        private Map<Integer, Set<Integer>> createEdgeMap(int n, int[][] edges) {
            Map<Integer, Set<Integer>> edgeMap = new HashMap<>(n);
            for (int i = 0; i < n; ++i) {
                edgeMap.put(i, new HashSet<>());
            }

            for (int[] edge: edges) {
                edgeMap.get(edge[0]).add(edge[1]);
                edgeMap.get(edge[1]).add(edge[0]);
            }
            return edgeMap;
        }
    }

    /**
     * 결과: 97ms / 83.82MB
     */
    class Solution1 {
        public List<Integer> findMinHeightTrees(int n, int[][] edges) {
            if (n == 1) {
                return Collections.singletonList(0);
            }

            // key: startNode
            // value: endNodes
            Map<Integer, Set<Integer>> edgeMap = createEdgeMap(n, edges);

            // key: edgeSize
            // value: nodeNumber
            Map<Integer, Set<Integer>> edgeSizeToNodeNumber = new HashMap<>();
            for (Map.Entry<Integer, Set<Integer>> entry: edgeMap.entrySet()) {
                var nodeNumber = entry.getKey();
                var edgeSize = entry.getValue().size();
                edgeSizeToNodeNumber.putIfAbsent(edgeSize, new HashSet<>());
                edgeSizeToNodeNumber.get(edgeSize).add(nodeNumber);
            }


            while (true) {
                var edgeSizeSet = edgeSizeToNodeNumber.keySet();

                // 2개만 남게되는 경우
                // 엣지사이즈가 한개만 남았을때는, 오직 리프노드 2개가 남았을때이다.
                if (edgeSizeSet.size() == 1) {
                    var leafNodes = edgeSizeToNodeNumber.get(1).toArray();
                    return List.of((int)leafNodes[0], (int)leafNodes[1]);
                }

                // 1개만 남게되는 경우
                // middleNode 는 1개, 리프노드는 2개 이상 남았을때 결정된다. 이 숫자가 맞는지 확인한다.
                if (edgeSizeSet.size() == 2 && edgeSizeToNodeNumber.get(1).size() >= 2) {
                    int middleNodeSize = -1;
                    for (Integer edgeSize: edgeSizeSet) {
                        if (edgeSize != 1) {
                            middleNodeSize = edgeSize;
                        }
                    }
                    if (edgeSizeToNodeNumber.get(middleNodeSize).size() == 1) {
                        return Collections.singletonList((int) edgeSizeToNodeNumber.get(middleNodeSize).toArray()[0]);
                    }
                }

                // 리프노드를 제거한다.
                var leafNodes = new ArrayList<>(edgeSizeToNodeNumber.get(1));
                for (int leafNode: leafNodes) {
                    // edgeMap: leafNode -> v 인 것 제거
                    int v = (int) edgeMap.get(leafNode).toArray()[0];
                    edgeMap.get(leafNode).clear();

                    // edgeMap: v -> leafNode 인 것 제거
                    int vEdgeSize = edgeMap.get(v).size();
                    edgeMap.get(v).remove(leafNode);

                    // edgeSizeToNodeNumber: vEdgeSize 에서 v 제거
                    edgeSizeToNodeNumber.get(vEdgeSize).remove(v);
                    if (edgeSizeToNodeNumber.get(vEdgeSize).isEmpty()) {
                        edgeSizeToNodeNumber.remove(vEdgeSize);
                    }

                    // edgeSizeToNodeNumber: vEdgeSize - 1 에 v 추가
                    edgeSizeToNodeNumber.putIfAbsent(vEdgeSize - 1, new HashSet<>());
                    edgeSizeToNodeNumber.get(vEdgeSize - 1).add(v);

                    // edgeSizeToNodeNumber: leafNode 제거
                    edgeSizeToNodeNumber.get(1).remove(leafNode);
                    if (edgeSizeToNodeNumber.get(1).isEmpty()) {
                        edgeSizeToNodeNumber.remove(1);
                    }
                }
            }
        }

        private Map<Integer, Set<Integer>> createEdgeMap(int n, int[][] edges) {
            Map<Integer, Set<Integer>> edgeMap = new HashMap<>(n);
            for (int i = 0; i < n; ++i) {
                edgeMap.put(i, new HashSet<>());
            }

            for (int[] edge: edges) {
                edgeMap.get(edge[0]).add(edge[1]);
                edgeMap.get(edge[1]).add(edge[0]);
            }
            return edgeMap;
        }
    }

    private static class Input {
        public int n;
        public int[][] edges;

        public Input(int n, int[][] edges) {
            this.n = n;
            this.edges = edges;
        }
    }
}
