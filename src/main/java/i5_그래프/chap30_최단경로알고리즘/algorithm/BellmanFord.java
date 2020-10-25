package i5_그래프.chap30_최단경로알고리즘.algorithm;

import javafx.util.Pair;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class BellmanFord {

    private final int V; // 정점의 개수
    private final List<List<Pair<Integer, Integer>>> adj; // 그래프 인접 리스트 (연결된 정점 번호, 간선 가중치) 쌍

    /**
     * 벨만-포드 알고리즘.
     * 시작점 src 에서 각 정점의 최단 거리를 구한다.
     * 음수 간선이 존재해도 최단 거리를 구할 수 있으며, 음수 사이클이 존재하는 것도 판별이 가능하다.
     *
     * 시간 복잡도: O(|V||E|)
     *
     * @param src
     * @return
     */
    public List<Integer> bellmanFord(int src) {
        // 시작점을 제외한 모든 정점까지의 최단 거리 상한을 INF 로 둔다.
        List<Integer> upper = Stream.generate(() -> Integer.MAX_VALUE).limit(V).collect(Collectors.toList());
        upper.set(src, 0); // 시작점은 최단 거리 0

        boolean updated = false;
        // V 번 순회한다.
        for (int iter = 0; iter < V; ++iter) {
            updated = false;
            for (int here = 0; here < V; ++here) {
                for (int i = 0; i < adj.get(here).size(); ++i) {
                    int there = adj.get(here).get(i).getKey();
                    int cost = adj.get(here).get(i).getValue();
                    // (here, there) 간선을 따라 완화를 시도한다.
                    if (upper.get(there) > upper.get(here) + cost) {
                        // 성공
                        upper.set(there, upper.get(here) + cost);
                        updated = true;
                    }
                }
            }
            // 모든 간선에 대해 완화가 실패했을 경우 V-1 번도 돌 필요 없이 곧장 종료한다.
            if (!updated) break;
        }
        // V번째 순회에서도 완화가 성공했다면 음수 사이클이 존재한다.
        if (updated) upper.clear();

        return upper;
    }
}
