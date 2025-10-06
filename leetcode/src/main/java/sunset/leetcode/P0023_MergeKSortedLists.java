package sunset.leetcode;

import sunset.leetcode.listnode.ListNode;
import sunset.leetcode.listnode.ListNodeUtils;

import java.util.PriorityQueue;

import static sunset.leetcode.listnode.ListNodeUtils.printListNode;

public class P0023_MergeKSortedLists {

    public static void main(String[] args) {
        int[][] input = new int[][]{
                {1, 4, 5},
                {1, 3, 4},
                {2, 6},
        };

        ListNode[] list = ListNodeUtils.convertToListNodeArray(input);
        ListNode result = new P0023_MergeKSortedLists().new Solution().mergeKLists(list);
        printListNode(result);
    }

    class Solution {
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists == null) {
                return null;
            }

            // 이진 힙에 넣기
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
            for (int i = 0; i < lists.length; ++i) {
                ListNode pointer = lists[i];
                while (pointer != null) {
                    priorityQueue.add(pointer.val);
                    pointer = pointer.next;
                }
            }

            // 이집 힙에서 삭제하면서 결과 채우기
            if (priorityQueue.isEmpty()) {
                return null;
            }
            ListNode head = new ListNode(priorityQueue.poll());
            ListNode pointer = head;
            while (!priorityQueue.isEmpty()) {
                pointer.next = new ListNode(priorityQueue.poll());
                pointer = pointer.next;
            }

            return head;
        }
    }
}
