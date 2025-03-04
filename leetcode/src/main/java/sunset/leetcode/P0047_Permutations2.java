package sunset.leetcode;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @see <a href="https://leetcode.com/problems/permutations-ii/description/">링크</a>
 */
public class P0047_Permutations2 {

    public static void main(String[] args) {
        Solution solution = new P0047_Permutations2().new Solution();
        List<List<Integer>> results = solution.permuteUnique(new int[]{1, 1, 2});
        System.out.println(results);
    }

    class Solution {

        private static final int NOT = -20;

        private int[] nums;

        private boolean[] visited;
        private int[] permutation;

        private Set<List<Integer>> results;

        public List<List<Integer>> permuteUnique(int[] nums) {
            this.nums = nums;
            this.visited = new boolean[nums.length];
            for (int i = 0; i < nums.length; ++i) {
                this.visited[i] = false;
            }

            this.permutation = new int[nums.length];
            for (int i = 0; i < nums.length; ++i) {
                this.permutation[i] = NOT;
            }

            this.results = new HashSet<>();
            find(0);

            return new ArrayList<>(results);
        }

        private void find(int targetIdx) {
            if (targetIdx >= nums.length) {
                addResult();
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
