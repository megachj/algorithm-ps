package sunset.problem;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class P42628 {

    private static final String[] INPUT_1 = new String[]{
            "I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"
    };

    private static final String[] INPUT_2 = new String[]{
            "I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"
    };

    public static void main(String[] args) {
        String[] input = INPUT_2;

        int[] result = new P42628().new Solution().solution(input);

        System.out.println("[" + result[0] + ", " + result[1] + "]");
    }

    /**
     * TreeMap(BST 일종인 Red-Black Tree) 을 사용하여 풀이(직접 생각)
     *
     * - 시간복잡도: O(nlogn)
     * - 공간복잡도: O(n)
     * - 결과: 73.51ms / 132MB
     */
    class Solution {

        /**
         * @param operations "I 숫자": 큐에 숫자 삽입, "D 1": 큐에서 최댓값 삭제, "D -1": 큐에서 최솟값 삭제
         */
        public int[] solution(String[] operations) {
            TreeMap<Integer, Integer> map = new TreeMap<>(
                    Comparator.naturalOrder()
            );
            for(String operation: operations) {
                String[] split = operation.split(" ");
                String op = split[0];
                int n = Integer.parseInt(split[1]);

                if ("I".equals(op)) {
                    if (map.containsKey(n)) {
                        map.put(n, map.get(n) + 1);
                    } else {
                        map.put(n, 1);
                    }
                } else {
                    try {
                        Map.Entry<Integer, Integer> entry = n == 1 ? map.lastEntry() : map.firstEntry();
                        if (entry.getValue() == 1) {
                            map.remove(entry.getKey());
                        } else {
                            map.put(entry.getKey(), entry.getValue() - 1);
                        }
                    } catch (Exception ignored) {}
                }
            }

            if (map.isEmpty()) {
                return new int[]{0, 0};
            } else {
                return new int[]{map.lastKey(), map.firstKey()};
            }
        }
    }

    /**
     * MinHeap, MaxHeap 두 개를 이용하여 풀이(책 해답 풀이)
     * - 시간복잡도: ?
     * - 공간복잡도: ?
     * - 결과: ?ms / ?MB
     */
    class Solution1 {
        public int[] solution(String[] operations) {
            // TODO
            return null;
        }
    }

    /**
     * 구간힙을 직접 구현하여 풀이(책 해답 풀이)
     * - 시간복잡도: ?
     * - 공간복잡도: ?
     * - 결과: ?ms / ?MB
     */
    class Solution2 {
        public int[] solution(String[] operations) {
            // TODO
            return null;
        }
    }
}
