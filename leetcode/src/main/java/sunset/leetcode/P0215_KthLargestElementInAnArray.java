package sunset.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class P0215_KthLargestElementInAnArray {

    /**
     * 결과: 121ms / 73.22MB
     */
    class Solution {
        public int findKthLargest(int[] nums, int k) {
            PriorityQueue<Integer> pq = new PriorityQueue<>(
                    nums.length,
                    Comparator.comparing((Integer a) -> a).reversed()
            );

            for (int num: nums) {
                pq.offer(num);
            }
            for (int i = 0; i < k-1; ++i) {
                pq.poll();
            }
            return pq.poll();
        }
    }
}
