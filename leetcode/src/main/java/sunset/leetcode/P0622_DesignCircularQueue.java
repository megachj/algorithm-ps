package sunset.leetcode;

public class P0622_DesignCircularQueue {

    /**
     * 배열을 이용해 원형 큐를 구현하라.
     */
    class MyCircularQueue {

        private final int[] arr;
        private int front;
        private int rear;
        private int count;

        public MyCircularQueue(int k) {
            if (k <= 0) {
                throw new IllegalArgumentException();
            }
            this.arr = new int[k];
            this.front = -1;
            this.rear = -1;
            this.count = 0;
        }

        public boolean enQueue(int value) {
            if (isFull()) {
                return false;
            }

            if (isEmpty()) {
                front = rear = 0;
                arr[0] = value;
            } else {
                rear = nextIndex(rear);
                arr[rear] = value;
            }
            count++;
            return true;
        }

        public boolean deQueue() {
            if (isEmpty()) {
                return false;
            }

            front = nextIndex(front);
            count--;
            return true;
        }

        public int Front() {
            if (isEmpty()) {
                return -1;
            }
            return arr[front];
        }

        public int Rear() {
            if (isEmpty()) {
                return -1;
            }
            return arr[rear];
        }

        public boolean isEmpty() {
            return count == 0;
        }

        public boolean isFull() {
            return count == arr.length;
        }

        private int nextIndex(int index) {
            return (index+1) % arr.length;
        }
    }
}
