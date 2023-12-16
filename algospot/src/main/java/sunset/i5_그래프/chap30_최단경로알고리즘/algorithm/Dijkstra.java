package sunset.i5_그래프.chap30_최단경로알고리즘.algorithm;

import sunset.library.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dijkstra {

    private final int V; // 정점의 개수
    private final List<List<Pair<Integer, Integer>>> adj; // 그래프 인접 리스트 (연결된 정점 번호, 간선 가중치) 쌍

    public Dijkstra(int v, List<List<Pair<Integer, Integer>>> adj) {
        V = v;
        this.adj = adj;
    }

    /**
     * 다익스트라 알고리즘.
     * 시작점 src 에서 각 정점의 최단 거리를 구한다.
     * 음수간선이 존재하면 안된다.(이 알고리즘으로 구할 수는 있지만, 시간 복잡도가 늘어남.)
     *
     * 시간 복잡도: O(|E|lg|V|)
     *  - 각 정점마다 인접한 간선 모두 검사: O(|E|)
     *  - 우선순위 큐에 원소 추가, 삭제 작업: 최대 모두 간선이 추가 삭제될 수 있으므로 O(|E|lg|E|), |V|-1 <= |E| <= |V|^2 이므로 O(|E|lg|V|)
     *    최대 길이가 |E| 인 힙에 원소 1개 추가, 삭제는 O(lg|E|) 따라서 원소 |E| 개 추가, 삭제는 O(|E|lg|E|) 가 된다.
     *
     * @param src: 시작점
     * @return List<Integer>: src 에서의 각 정점의 최단거리 리스트
     */
    public List<Integer> dijkstra(int src) {
        // 각 정점들의 최단거리 리스트 생성 처음은 INF 로 초기화.
        List<Integer> dist = Stream.generate(() -> Integer.MAX_VALUE).limit(V).collect(Collectors.toList());
        dist.set(src, 0); // 시작점은 최단 거리 0

        // 우선순위 큐 생성, 간선 가중치가 작을수록 우선순위
        // <정점 번호, 가중치>
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Pair::getValue));
        pq.offer(new Pair<>(src, 0)); // 시작점은 간선 가중치가 0

        while (!pq.isEmpty()) {
            Pair<Integer, Integer> top = pq.poll();
            int here = top.getKey();
            int cost = top.getValue();

            // 만약 지금 꺼낸 것보다 더 짧은 경로를 알고 있다면 지금 꺼낸 것을 무시한다.
            if (dist.get(here) < cost) continue;

            // 인접한 정점들 모두 검사
            for (int i = 0; i < adj.get(here).size(); ++i) {
                int there = adj.get(here).get(i).getKey();
                int nextDist = cost + adj.get(here).get(i).getValue();
                // here 를 찾음으로써, there 까지의 거리가 더 짧아지면 dist 를 갱신하고 우선순위 큐에 넣는다.
                if (dist.get(there) > nextDist) {
                    dist.set(there, nextDist);
                    pq.offer(new Pair<>(there, nextDist));
                }
            }
        }

        return dist;
    }

    /**
     * 우선순위 큐를 사용하지 않는 다익스트라 알고리즘.
     * 정점의 수가 적거나 간선의 수가 매우 많은 경우에는 이 구현 방식이 더욱 빠른 경우가 있다.
     *
     * 시간 복잡도: O(|V|^2 + |E|)
     *
     * @param src: 시작점
     * @return List<Integer>: src 에서의 각 정점의 최단거리 리스트
     */
    public List<Integer> dijkstra2(int src) {
        // 각 정점들의 최단거리 리스트 생성 처음은 INF 로 초기화.
        List<Integer> dist = Stream.generate(() -> Integer.MAX_VALUE).limit(V).collect(Collectors.toList());
        // 각 정점을 방문했는지 여부 저장.
        List<Boolean> visited = Stream.generate(() -> false).limit(V).collect(Collectors.toList());
        dist.set(src, 0); visited.set(src, true);

        while (true) {
            int closest = Integer.MAX_VALUE;
            int here = -1;
            for (int i = 0; i < V; ++i) {
                if (dist.get(i) < closest && !visited.get(i)) {
                    here = i;
                    closest = dist.get(i);
                }
            }
            if (closest == Integer.MAX_VALUE) break;

            // 가장 가까운 정점을 방문
            visited.set(here, true);
            for (int i = 0; i < adj.get(here).size(); ++i) {
                int there = adj.get(here).get(i).getKey();
                if (visited.get(there)) continue;
                int nextDist = dist.get(here) + adj.get(here).get(i).getValue();
                dist.set(there, Math.min(dist.get(there), nextDist));
            }
        }

        return dist;
    }
}
