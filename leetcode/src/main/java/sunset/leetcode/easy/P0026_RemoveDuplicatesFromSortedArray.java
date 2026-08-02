package sunset.leetcode.easy;

import java.util.ArrayList;
import java.util.List;

public class P0026_RemoveDuplicatesFromSortedArray {

    public static void main(String[] args) {
        // {1, 1, 2}: {1, 2, _}
        // {0, 0, 1, 1, 1, 2, 2, 3, 3, 4}: {0, 1, 2, 3, 4, _, ...}
        int[] inputs = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        Solution solution = new P0026_RemoveDuplicatesFromSortedArray().new Solution();
        int output = solution.removeDuplicates(inputs);
        System.out.println(output);
    }

    class Solution {
        public int removeDuplicates(int[] nums) {
            List<Integer> uniqueNums = new ArrayList<>();
            uniqueNums.add(nums[0]);
            for (int i = 1; i < nums.length; ++i) {
                if (nums[i] == uniqueNums.get(uniqueNums.size() - 1)) {
                    continue;
                }
                uniqueNums.add(nums[i]);
            }

            for (int i = 0; i < uniqueNums.size(); ++i) {
                nums[i] = uniqueNums.get(i);
            }
            return uniqueNums.size();
        }
    }
}
