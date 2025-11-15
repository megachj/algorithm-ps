package sunset.leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class P0078_Subsets {

    /*
    Example 1:
    Input: nums = [1,2,3]
    Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]

    Example 2:
    Input: nums = [0]
    Output: [[],[0]]
     */
    public static void main(String[] args) {
        int[] nums = new int[]{
                1, 2, 3
        };

        List<List<Integer>> result = new P0078_Subsets().new Solution2().subsets(nums);
        System.out.println(result);
    }

    /*
    Constraints:
    1 <= nums.length <= 10
    -10 <= nums[i] <= 10
    All the numbers of nums are unique.
     */
    // DFS: 시간 4ms
    class Solution {

        private int[] nums;
        private List<List<Integer>> result;

        public List<List<Integer>> subsets(int[] nums) {
            init(nums);
            dfs(new ArrayList<>(nums.length));
            return result;
        }

        private void init(int[] nums) {
            this.nums = nums;
            this.result = new ArrayList<>();
        }

        private void dfs(List<Integer> indices) {
            result.add(convertValues(indices));

            if (indices.isEmpty()) {
                for (int i = 0; i < nums.length; ++i) {
                    indices.add(i);
                    dfs(indices);
                    indices.remove(indices.size() - 1);
                }
            } else {
                int lastIndex = indices.get(indices.size() - 1);
                for (int i = lastIndex + 1; i < nums.length; ++i) {
                    indices.add(i);
                    dfs(indices);
                    indices.remove(indices.size() - 1);
                }
            }
        }

        private List<Integer> convertValues(List<Integer> indices) {
            return indices.stream()
                    .map(index -> nums[index])
                    .collect(Collectors.toList());
        }
    }

    // BFS: 시간 6ms
    class Solution2 {

        private int[] nums;

        private Queue<List<Integer>> queue;
        private List<List<Integer>> result;

        public List<List<Integer>> subsets(int[] nums) {
            init(nums);

            List<Integer> emptyList = new ArrayList<>();
            queue.offer(emptyList);
            result.add(emptyList);
            while (!queue.isEmpty()) {
                bfs(queue.poll());
            }
            return result;
        }

        private void init(int[] nums) {
            this.nums = nums;
            this.queue = new LinkedList<>();
            this.result = new ArrayList<>();
        }

        private void bfs(List<Integer> indices) {
            if (indices.isEmpty()) {
                for (int i = 0; i < nums.length; ++i) {
                    List<Integer> newList = new ArrayList<>();
                    newList.add(i);
                    queue.offer(newList);
                    result.add(convertValues(newList));
                }
            } else {
                int lastIndex = indices.get(indices.size() - 1);
                for (int i = lastIndex + 1; i < nums.length; ++i) {
                    List<Integer> newList = new ArrayList<>(indices);
                    newList.add(i);
                    queue.offer(newList);
                    result.add(convertValues(newList));
                }
            }
        }

        private List<Integer> convertValues(List<Integer> indices) {
            return indices.stream()
                    .map(index -> nums[index])
                    .collect(Collectors.toList());
        }
    }
}
