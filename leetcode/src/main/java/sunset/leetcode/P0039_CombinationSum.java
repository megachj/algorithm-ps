package sunset.leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class P0039_CombinationSum {

    /*
    Example 1:
    Input: candidates = [2,3,6,7], target = 7
    Output: [[2,2,3],[7]]
    Explanation:
    2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
    7 is a candidate, and 7 = 7.
    These are the only two combinations.

    Example 2:
    Input: candidates = [2,3,5], target = 8
    Output: [[2,2,2,2],[2,3,3],[3,5]]

    Example 3:
    Input: candidates = [2], target = 1
    Output: []
     */
    public static void main(String[] args) {
        int[] candidates = new int[]{
                2
        };
        int target = 1;

        Solution solution = new P0039_CombinationSum().new Solution();
        List<List<Integer>> result = solution.combinationSum(candidates, target);
        System.out.println(result);
    }

    /*
    1 <= candidates.length <= 30
    2 <= candidates[i] <= 40
    All elements of candidates are distinct.
    1 <= target <= 40
     */
    // 시간: 2ms
    class Solution {

        private int[] candidates;
        private int target;
        private List<List<Integer>> results;

        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            init(candidates, target);
            dfs(0, new ArrayList<>(), 0);
            return results;
        }

        private void init(int[] candidates, int target) {
            this.candidates = candidates;
            this.target = target;
            this.results = new ArrayList<>();
        }

        private void dfs(int index, List<Integer> picked, int pickedSum) {
            if (pickedSum == target) {
                results.add(new ArrayList<>(picked));
                return;
            }
            if (pickedSum > target) {
                return;
            }

            // 인덱스 0부터 시작하면 중복건이 생기므로, index 부터 한다.
            for (int i = index; i < candidates.length; ++i) {
                int value = candidates[i];
                picked.add(value);
                dfs(i, picked, pickedSum + value);
                picked.remove(picked.size() - 1);
            }
        }
    }

    // 시간: 140ms
    class Solution2 {

        private int[] candidates;
        private int target;
        private Set<List<Integer>> results;

        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            init(candidates, target);
            dfs(new ArrayList<>(), 0);
            return results.stream().map(ArrayList::new).collect(Collectors.toList());
        }

        private void init(int[] candidates, int target) {
            this.candidates = candidates;
            this.target = target;
            this.results = new HashSet<>();
        }

        private void dfs(List<Integer> picked, int pickedSum) {
            if (pickedSum == target) {
                List<Integer> sortedPicked = picked.stream().sorted().collect(Collectors.toList());
                results.add(sortedPicked);
                return;
            }
            if (pickedSum > target) {
                return;
            }

            // 인덱스 0부터 시작해서 중복건이 생겼다.
            for (int value : candidates) {
                picked.add(value);
                dfs(picked, pickedSum + value);
                picked.remove(picked.size() - 1);
            }
        }
    }
}
