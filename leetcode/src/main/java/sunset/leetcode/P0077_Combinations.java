package sunset.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @see <a href="https://leetcode.com/problems/combinations/description/">링크</a>
 */
public class P0077_Combinations {

    public static void main(String[] args) {
        Solution solution = new P0077_Combinations().new Solution();
        List<List<Integer>> answer = solution.combine(1, 1);
        System.out.println(answer);
    }

    class Solution {

        private int n;
        private int k;

        private boolean[] visited;
        private List<List<Integer>> result;

        /**
         *
         * @param n [1, 20]
         * @param k [1, n]
         * @return
         */
        public List<List<Integer>> combine(int n, int k) {
            this.n = n;
            this.k = k;

            this.visited = new boolean[n];
            for (int i = 0; i < n; ++i) {
                visited[i] = false;
            }

            this.result = new ArrayList<>(184756); // 20C10 = 184756

            find(1, k);

            return result;
        }

        /**
         *
         * @param a         정수 a
         * @param pickCount 뽑아야할 갯수
         */
        private void find(int a, int pickCount) {
            // 뽑을게 없다면 현재 방문한 수로 조합을 추가한다.
            if (pickCount == 0) {
                addCombination();
                return;
            }
            // 뽑을게 있는데 마지막이라면 그냥 끝낸다.
            if (a > n) {
                return;
            }

            // 1번 케이스: a 를 포함하는 경우
            visited[a - 1] = true;
            find(a + 1, pickCount - 1);

            // 2번 케이스: a 를 포함하지 않는 경우
            visited[a - 1] = false;
            find(a + 1, pickCount);
        }

        private void addCombination() {
            List<Integer> combination = new ArrayList<>(k);
            for (int i = 0; i < n; ++i) {
                if (visited[i]) {
                    combination.add(i + 1);
                }
            }
            result.add(combination);
        }
    }
}
