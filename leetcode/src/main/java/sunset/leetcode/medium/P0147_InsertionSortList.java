package sunset.leetcode.medium;

import sunset.leetcode.support.datastructure.ListNode;
import sunset.leetcode.support.datastructure.ListNodeUtils;

public class P0147_InsertionSortList {

    public static void main(String[] args) {
        // {4, 2, 1, 3} -> {1, 2, 3, 4}
        // {-1, 5, 3, 4, 0} -> {-1, 0, 3, 4, 5}
        // {3, 2, 4} -> {2, 3, 4}
        int[] inputs = new int[]{3, 2, 4};
        ListNode inputNode = ListNodeUtils.convertToListNode(inputs);

        Solution solution = new P0147_InsertionSortList().new Solution();
        ListNode output = solution.insertionSortList(inputNode);
        ListNodeUtils.printListNode(output);
    }

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    class Solution {
        public ListNode insertionSortList(ListNode head) {
            ListNode prevStep, step;

            prevStep = head;
            step = prevStep.next;

            while (step != null) {
                ListNode before = null;
                ListNode current = head;

                boolean isSwaped = false;
                while (current != step) {
                    if (step.val < current.val) {
                        ListNode temp = step;
                        step = step.next;

                        if (before == null) {
                            head = temp;
                        } else {
                            before.next = temp;
                        }

                        temp.next = current;
                        prevStep.next = step;
                        isSwaped = true;
                        break;
                    }

                    before = current;
                    current = current.next;
                }
                if (!isSwaped) {
                    prevStep = step;
                    step = step.next;
                }
            }

            return head;
        }
    }
}
