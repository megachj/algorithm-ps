package sunset.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class P0232_ImplementQueueUsingStacks {

    /**
     * 스택을 이용해서 큐를 구현하라.
     */
    class MyQueue {

        private Deque<Integer> stack;
        private Deque<Integer> temp;

        public MyQueue() {
            this.stack = new ArrayDeque<>();
            this.temp = new ArrayDeque<>();
        }

        public void push(int x) {
            while(!stack.isEmpty()) {
                temp.push(stack.pop());
            }
            temp.push(x);

            while(!temp.isEmpty()) {
                stack.push(temp.pop());
            }
        }

        public int pop() {
            return stack.pop();
        }

        public int peek() {
            return stack.peek();
        }

        public boolean empty() {
            return stack.isEmpty();
        }
    }
}
