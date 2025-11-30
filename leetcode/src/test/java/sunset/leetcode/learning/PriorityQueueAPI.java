package sunset.leetcode.learning;

import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

/**
 * 우선순위 큐 ADT<br>
 * - 구현체(인터페이스X): java.util.PriorityQueue(내부적으로 Min Heap 사용, 완전이진트리)<br>
 */
public class PriorityQueueAPI {

    @Test
    public void test() {
        //PriorityQueue<Item> priorityQueue = new PriorityQueue<>((Item o1, Item o2) -> {
        //    return Integer.compare(o1.priority, o2.priority);
        //});
        //PriorityQueue<Item> priorityQueue = new PriorityQueue<>(Comparator.comparingInt((Item o) -> o.priority));
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            return o1.compareTo(o2);
        });
        // PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.naturalOrder());
        // PriorityQueue<Item> priorityQueue = new PriorityQueue<>(Item::compareTo);
    }

    public static class Item implements Comparable<Item> {
        String val;
        int priority;

        @Override
        public int compareTo(Item o) {
            return Integer.compare(priority, o.priority);
        }
    }
}
