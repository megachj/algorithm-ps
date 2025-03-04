package sunset.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @see <a href="https://leetcode.com/problems/permutations/">링크</a>
 */
public class P0046_Permutations {

    public static void main(String[] args) {
        Solution solution = new P0046_Permutations().new Solution();
        List<List<Integer>> results = solution.permute(new int[]{1, 2, 3});
        System.out.println(results);
    }

    class Solution {

        private static final int NOT = -20;

        private int[] nums;

        private boolean[] visited;
        private int[] permutation;

        private List<List<Integer>> results;

        /**
         *
         * @param nums nums.length [1, 6]
         * @return
         */
        public List<List<Integer>> permute(int[] nums) {
            this.nums = nums;
            this.visited = new boolean[nums.length];
            this.permutation = new int[nums.length];
            this.results = new ArrayList<>(720); // 6!

            for (int i = 0; i < nums.length; ++i) {
                visited[i] = false;
                permutation[i] = NOT;
            }

            find(0);

            return results;
        }

        private void find(int targetIdx) {
            // 순열을 다 찾았다면
            if (targetIdx >= permutation.length) {
                addResult();
                return;
            }

            for (int i = 0; i < nums.length; ++i) {
                if (!visited[i]) {
                    visited[i] = true;
                    permutation[targetIdx] = nums[i];
                    find(targetIdx + 1);
                    permutation[targetIdx] = NOT;
                    visited[i] = false;
                }
            }
        }

        private void addResult() {
            results.add(
                    Arrays.stream(permutation)
                            .boxed()
                            .collect(Collectors.toList())
            );
        }
    }
}
