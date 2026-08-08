package sunset.leetcode.easy;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class P0349_IntersectionOfTwoArrays {

    public static void main(String[] args) {
        int[] nums1, nums2;
        nums1 = new int[]{1, 2, 2, 1};
        nums2 = new int[]{2, 2};

        Solution solution = new P0349_IntersectionOfTwoArrays().new Solution();
        int[] output = solution.intersection(nums1, nums2);

        for (int e: output) {
            System.out.printf("%d, ", e);
        }
        System.out.println();
    }

    class Solution {
        public int[] intersection(int[] nums1, int[] nums2) {
            Set<Integer> set = Arrays.stream(nums1)
                    .boxed()
                    .collect(Collectors.toSet());

            return Arrays.stream(nums2)
                    .boxed()
                    .filter(set::contains)
                    .distinct()
                    .mapToInt(Integer::intValue)
                    .toArray();
        }
    }
}
