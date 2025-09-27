package sunset.leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class P0225_ImplementStackUsingQueues {

    /**
     * 큐를 이용해서 스택을 구현하라.
     */
    class MyStack {

        private Queue<Integer> queue;

        public MyStack() {
            this.queue = new LinkedList<>();
        }

        public void push(int x) {
            this.queue.offer(x);
            for (int i = 0; i < this.queue.size() - 1; ++i)
                this.queue.offer(this.queue.poll());
        }

        public int pop() {
            return this.queue.poll();
        }

        public int top() {
            return this.queue.peek();
        }

        public boolean empty() {
            return this.queue.isEmpty();
        }
    }
}
