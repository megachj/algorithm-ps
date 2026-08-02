package sunset.leetcode.medium;

import sunset.leetcode.support.datastructure.ListNode;
import sunset.leetcode.support.datastructure.ListNodeUtils;

public class P0148_SortList {

    public static void main(String[] args) {
        // {4, 2, 1, 3}
        // {-1, 5, 3, 4, 0}
        // {}
        int[] inputs = new int[]{};
        ListNode head = ListNodeUtils.convertToListNode(inputs);

        Solution solution = new P0148_SortList().new Solution();
        ListNode output = solution.sortList(head);

        while (output != null) {
            System.out.printf("%d, ", output.val);
            output = output.next;
        }
        System.out.println();
    }

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode() {}
     * ListNode(int val) { this.val = val; }
     * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    class Solution {
        public ListNode sortList(ListNode head) {
            if (head == null) {
                return null;
            }

            int n = 0;
            ListNode current = head;
            while (current != null) {
                n++;
                current = current.next;
            }

            return mergeSort(head, n);
        }

        private ListNode mergeSort(ListNode firstHead, int size) {
            if (size == 1) {
                firstHead.next = null;
                return firstHead;
            }

            ListNode secondHead = firstHead;
            for (int i = 0; i < size / 2; ++i) {
                secondHead = secondHead.next;
            }

            ListNode first = mergeSort(firstHead, size / 2);
            ListNode second = mergeSort(secondHead, size - (size/2));

            ListNode mergedHead;
            if (first.val <= second.val) {
                mergedHead = first;
                first = first.next;
            } else {
                mergedHead = second;
                second = second.next;
            }

            ListNode mergeNext;
            ListNode mergedPointer = mergedHead;
            while (first != null || second != null) {
                if (first != null && second == null) {
                    mergeNext = first;
                    first = first.next;
                    mergedPointer.next = mergeNext;
                    mergedPointer = mergedPointer.next;
                } else if (first == null && second != null) {
                    mergeNext = second;
                    second = second.next;
                    mergedPointer.next = mergeNext;
                    mergedPointer = mergedPointer.next;
                } else {
                    if (first.val <= second.val) {
                        mergeNext = first;
                        first = first.next;
                        mergedPointer.next = mergeNext;
                        mergedPointer = mergedPointer.next;
                    } else {
                        mergeNext = second;
                        second = second.next;
                        mergedPointer.next = mergeNext;
                        mergedPointer = mergedPointer.next;
                    }
                }
            }
            return mergedHead;
        }
    }
}
