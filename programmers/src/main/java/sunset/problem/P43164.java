package sunset.problem;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 문제 유형: DFS<br>
 * 난이도: lv3<br>
 *
 * @see <a href= "https://school.programmers.co.kr/learn/courses/30/lessons/43164">링크</a>
 */
public class P43164 {

    public static void main(String[] args) {
        Solution solution = new P43164().new Solution();
        String[][] tickets = TICKETS_3;

        String[] result = solution.solution(tickets);

        System.out.println(Arrays.asList(result));
    }

    class Solution {

        // 출발지, 도착지 리스트(도착지명, 방문 여부(true, false))
        private Map<String, List<List<String>>> ticketsMap;
        private List<String> reversedResult = new ArrayList<>();

        public String[] solution(String[][] tickets) {
            ticketsMap = convertMap(tickets);
            dfs("ICN");

            // TODO: 우아한 코드로 바꾸기
            String[] answer = new String[reversedResult.size()];
            for (int i = 0; i < reversedResult.size(); ++i) {
                answer[i] = reversedResult.get(reversedResult.size() - 1 - i);
            }
            return answer;
        }

        private Map<String, List<List<String>>> convertMap(String[][] tickets) {
            // List<String> 은 정렬되도록 작업한다.
            // 만약 문제에서 동일한 티켓의 중복을 허용하지 않는다면 값을 TreeSet 타입으로 선택할 수도 있다.
            return Arrays.stream(tickets)
                    .collect(Collectors.groupingBy(
                            ticket -> ticket[0],
                            Collectors.collectingAndThen(
                                    Collectors.mapping(ticket -> Arrays.asList(ticket[1], "false"), Collectors.toList()),
                                    list -> {
                                        Collections.sort(list, Comparator.comparing(ticket -> ticket.get(0)));
                                        return list;
                                    }
                            )
                    ));
        }

        private void dfs(String start) {
            List<List<String>> candidates = ticketsMap.get(start);
            if (candidates == null) {
                reversedResult.add(start);
                return;
            }

            for (int i = 0; i < candidates.size(); ++i) {
                List<String> candidate = candidates.get(i);
                if (candidate.get(1).equals("false")) {
                    candidate.set(1, "true");
                    dfs(candidate.get(0));
                }
            }
            reversedResult.add(start);
        }
    }

    private static final String[][] TICKETS_1 = new String[][]{
            {"ICN", "JFK"},
            {"HND", "IAD"},
            {"JFK", "HND"}
    };
    private static final String[][] TICKETS_2 = new String[][]{
            {"ICN", "SFO"},
            {"ICN", "ATL"},
            {"SFO", "ATL"},
            {"ATL", "ICN"},
            {"ATL", "SFO"}
    };
    private static final String[][] TICKETS_3 = new String[][]{
            {"ICN", "AAA"},
            {"ICN", "BBB"},
            {"BBB", "ICN"}
    };
}
