package sunset.i5_그래프.chap28_DFS.algorithm;

import java.util.ArrayList;

public class DFS {

    public static ArrayList<ArrayList<Integer>> adj; // 그래프 인접 리스트
    public static ArrayList<Boolean> visited; // 각 정점을 방문했는지 여부

    public static void dfsAll() {
        // visited 모두 false 로 초기화
        visited = new ArrayList<>(adj.size());
        for (int i = 0; i < adj.size(); ++i)
            visited.add(false);

        for (int i = 0; i < adj.size(); ++i)
            if (!visited.get(i))
                dfs(i);
    }

    public static void dfs(int here) {
        System.out.printf("DFS visit: %d\n", here);
        visited.set(here, true);

        for (int i = 0; i < adj.get(here).size(); ++i) {
            int there = adj.get(here).get(i);
            if (!visited.get(there))
                dfs(there);
        }
    }
}
