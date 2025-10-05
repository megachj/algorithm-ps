package sunset.leetcode;

public class P0641_DesignCircularDeque {

    class MyCircularDeque {

        private final int[] arr;
        private int front;
        private int rear;
        private int count;

        public MyCircularDeque(int k) {
            arr = new int[k];
            front = rear = -1;
            count = 0;
        }

        public boolean insertFront(int value) {
            if (isFull()) {
                return false;
            }

            if (isEmpty()) {
                front = rear = 0;
            } else {
                front = prevIndex(front);
            }
            arr[front] = value;
            count++;
            return true;
        }

        public boolean insertLast(int value) {
            if (isFull()) {
                return false;
            }

            if (isEmpty()) {
                front = rear = 0;
            } else {
                rear = nextIndex(rear);
            }
            arr[rear] = value;
            count++;
            return true;
        }

        public boolean deleteFront() {
            if (isEmpty()) {
                return false;
            }
            front = nextIndex(front);
            count--;
            return true;
        }

        public boolean deleteLast() {
            if (isEmpty()) {
                return false;
            }
            rear = prevIndex(rear);
            count--;
            return true;
        }

        public int getFront() {
            return isEmpty() ? -1 : arr[front];
        }

        public int getRear() {
            return isEmpty() ? -1 : arr[rear];
        }

        public boolean isEmpty() {
            return count == 0;
        }

        public boolean isFull() {
            return count == arr.length;
        }

        private int prevIndex(int index) {
            int prev = index - 1;
            if (prev < 0) {
                return prev + arr.length;
            } else {
                return prev;
            }
        }

        private int nextIndex(int index) {
            return (index + 1) % arr.length;
        }
    }
}
