package sunset.leetcode;

public class P0002_AddTwoNumbers {

    public static void main(String[] args) {
        int[] inputList1 = new int[] {9,9,9,9,9,9,9};
        int[] inputList2 = new int[] {9,9,9,9};
        ListNode list1 = makeInput(inputList1);
        ListNode list2 = makeInput(inputList2);

        ListNode result = new P0002_AddTwoNumbers().new Solution().addTwoNumbers(list1, list2);
        printListNode(result);
    }

    /**
     * 설명: 두 리스트를 더한다.
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(n)<br>
     * - 결과: 1ms / 44.72MB<br>
     */
    class Solution {

        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode pointer1 = l1;
            ListNode pointer2 = l2;

            ListNode newHeader = null;
            ListNode newPointer = null;
            int carry = 0;
            while (pointer1 != null || pointer2 != null) {
                int val1 = pointer1 != null ? pointer1.val : 0;
                int val2 = pointer2 != null ? pointer2.val : 0;

                int sum = carry + val1 + val2;
                carry = 0;
                if (sum >= 10) {
                    sum -= 10;
                    carry = 1;
                }

                ListNode newNode = new ListNode(sum);
                if (newPointer == null) {
                    newPointer = newNode;
                    newHeader = newNode;
                } else {
                    newPointer.next = newNode;
                    newPointer = newNode;
                }

                if (pointer1 != null) {
                    pointer1 = pointer1.next;
                }
                if (pointer2 != null) {
                    pointer2 = pointer2.next;
                }
            }

            if (carry > 0) {
                newPointer.next = new ListNode(carry);
            }

            return newHeader;
        }
    }

    private static ListNode makeInput(int[] inputs) {
        ListNode head = new ListNode(inputs[0]);
        ListNode pointer = head;
        for (int i = 1; i < inputs.length; ++i) {
            pointer.next = new ListNode(inputs[i]);
            pointer = pointer.next;
        }
        return head;
    }

    private static void printListNode(ListNode listNode) {
        while (listNode != null) {
            System.out.printf("%d, ", listNode.val);
            listNode = listNode.next;
        }
    }


    private static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
