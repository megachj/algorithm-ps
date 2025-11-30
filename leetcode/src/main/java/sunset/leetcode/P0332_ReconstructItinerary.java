package sunset.leetcode;

import java.util.*;

public class P0332_ReconstructItinerary {

    public static void main(String[] args) {
//        String[][] input = new String[][]{
//                {"MUC","LHR"},{"JFK", "MUC"},{"SFO","SJC"},{"LHR", "SFO"}
//        };
//        String[][] input = new String[][]{
//                {"JFK","SFO"},{"JFK","ATL"},{"SFO","ATL"},{"ATL","JFK"},{"ATL","SFO"}
//        };
        String[][] input = new String[][]{
                {"JFK","AAA"},{"JFK","BBB"},{"BBB","JFK"}
        };
//        String[][] input = new String[][]{
//                {"JFK","SFO"},{"JFK","ATL"},{"SFO","JFK"},{"ATL","AAA"},{"AAA","ATL"},
//                {"ATL","BBB"},{"BBB","ATL"},{"ATL","CCC"},{"CCC","ATL"},{"ATL","DDD"},
//                {"DDD","ATL"},{"ATL","EEE"},{"EEE","ATL"},{"ATL","FFF"},{"FFF","ATL"},
//                {"ATL","GGG"},{"GGG","ATL"},{"ATL","HHH"},{"HHH","ATL"},{"ATL","III"},
//                {"III","ATL"},{"ATL","JJJ"},{"JJJ","ATL"},{"ATL","KKK"},{"KKK","ATL"},
//                {"ATL","LLL"},{"LLL","ATL"},{"ATL","MMM"},{"MMM","ATL"},{"ATL","NNN"},
//                {"NNN","ATL"}
//        };

        List<List<String>> tickets = new ArrayList<>(input.length);
        for (String[] strings : input) {
            List<String> ticketI = new ArrayList<>();
            ticketI.add(strings[0]);
            ticketI.add(strings[1]);
            tickets.add(ticketI);
        }

        List<String> result = new P0332_ReconstructItinerary().new Solution().findItinerary(tickets);
        System.out.println(result);
    }

    class Solution {

        List<List<String>> tickets;
        boolean[] traversedArray;
        List<Integer> traversedPath;
        // from:[(to, index), ...]
        Map<String, List<Element>> ticketMap;

        List<String> result;

        public List<String> findItinerary(List<List<String>> tickets) {
            init(tickets);
            traverse(null, null, -1);
            return result;
        }

        private void init(List<List<String>> tickets) {
            this.tickets = tickets;
            this.traversedArray = new boolean[tickets.size()];
            this.traversedPath = new ArrayList<>(tickets.size());
            this.ticketMap = new HashMap<>();
            for (int i = 0; i < tickets.size(); ++i) {
                List<String> element = tickets.get(i);
                String key = element.get(0);
                String value = element.get(1);
                if (!ticketMap.containsKey(key)) {
                    ticketMap.put(key, new ArrayList<>());
                }

                ticketMap.get(key).add(new Element(value, i));
            }

            for (Map.Entry<String, List<Element>> entry: ticketMap.entrySet()) {
                entry.getValue().sort(Comparator.comparing(o -> o.to));
            }

            this.result = new ArrayList<>(tickets.size() + 1);
        }

        private void traverse(String from, String to, int index) {
            // 처음일 때
            if (from == null) {
                List<Element> starts = ticketMap.get("JFK");
                for (Element element: starts) {
                    traverse("JFK", element.to, element.index);
                }
            }
            else {
                // 이 여행지 여행 완료
                traversedArray[index] = true;
                traversedPath.add(index);

                // 다음 여행지 여행한다.
                List<Element> candidates = ticketMap.get(to);
                if (candidates != null) {
                    for (Element element : candidates) {
                        if (!traversedArray[element.index]) {
                            traverse(to, element.to, element.index);
                        }
                    }
                }
                // 모든 경로를 여행했다면 정답이다.
                if (traversedPath.size() == tickets.size()) {
                    if (result.isEmpty()) {
                        for (int traverseIndex : traversedPath) {
                            // from 만 추가하기
                            result.add(tickets.get(traverseIndex).get(0));
                        }
                        // 마지막 to 추가하기
                        int lastIndex = traversedPath.get(traversedPath.size() - 1);
                        result.add(tickets.get(lastIndex).get(1));
                    }
                }
                // 모든 경로를 여행하지 못했다면 해당 여행루트는 실패이다.
                else {
                    traversedArray[index] = false;
                    traversedPath.remove(traversedPath.size() - 1);
                }
            }
        }

        class Element {
            public String to;
            public int index;

            public Element(String to, int index) {
                this.to = to;
                this.index = index;
            }
        }
    }

    class SolutionBook {
        public List<String> findItinerary(List<List<String>> tickets) {
            List<String> results = new LinkedList<>();
            Map<String, PriorityQueue<String>> fromToMap = new HashMap<>();

            for (List<String> ticket : tickets) {
                fromToMap.putIfAbsent(ticket.get(0), new PriorityQueue<>());
                fromToMap.get(ticket.get(0)).add(ticket.get(1));
            }
            dfs(results, fromToMap, "JFK");
            return results;
        }

        public void dfs(List<String> results, Map<String, PriorityQueue< String>> fromToMap, String from) {
            while (fromToMap.containsKey(from) && !fromToMap.get(from).isEmpty()) {
                dfs(results, fromToMap, fromToMap.get(from).poll());
            }
            results.add(0, from);
        }
    }
}
