package sunset.leetcode;

public class P0328_OddEvenLinkedList {

    public static void main(String[] args) {
        int[] inputList1 = new int[] {1, 2, 3, 4, 5, 6};
        ListNode list1 = makeInput(inputList1);

        ListNode result = new P0328_OddEvenLinkedList().new Solution().oddEvenList(list1);
        printListNode(result);
    }

    /**
     * 설명
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(1)<br>
     * - 결과: 0ms / 45.10MB<br>
     */
    class Solution {
        public ListNode oddEvenList(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode evenHead = head.next;

            ListNode pointer = head;
            int pointerNo = 1;
            while (pointer != null) {
                if (isLastOddOrEven(pointer)) {
                    if (pointerNo % 2 == 1) {
                        ListNode next = pointer.next;
                        pointer.next = evenHead;
                        pointer = next;
                        pointerNo++;
                    } else {
                        ListNode next = pointer.next;
                        pointer.next = null;
                        pointer = next;
                        pointerNo++;
                    }
                } else {
                    ListNode next = pointer.next;
                    pointer.next = pointer.next.next;
                    pointer = next;
                    pointerNo++;
                }
            }

            return head;
        }

        private boolean isLastOddOrEven(ListNode pointer) {
            return pointer.next == null || pointer.next.next == null;
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
