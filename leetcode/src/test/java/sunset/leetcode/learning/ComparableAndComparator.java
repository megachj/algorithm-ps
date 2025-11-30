package sunset.leetcode.learning;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

@DisplayName("Comparable, Comparator 테스트")
public class ComparableAndComparator {

    @DisplayName("Comparator 를 인자로 받는 테스트")
    @Test
    public void comparator_arguments_test() {
        // 1) Comparator 구현체 클래스
        PriorityQueue<Item> pq = new PriorityQueue<>(ITEM_COMPARATOR);

        // 2) 익명 객체
        PriorityQueue<Item> pq2 = new PriorityQueue<>((o1, o2) -> {
            int val = Integer.compare(o1.priority, o2.priority);
            if (val != 0)
                return val;

            return o1.name.compareTo(o2.name);
        });

        // 3) Comparable 이 구현된 클래스는 Comparator.naturalOrder() 로 Comparator 를 생성할 수 있음.
        PriorityQueue<Item> pq3 = new PriorityQueue<>(Comparator.naturalOrder());
        PriorityQueue<Integer> pqInteger = new PriorityQueue<>(Comparator.naturalOrder());
        // Object 는 Comparable 이 구현돼있지 않아서 사용할 수 없다.
        // PriorityQueue<Object> pqObject = new PriorityQueue<>(Comparator.naturalOrder());

        // 4) Comparable 의 compareTo 메소드로 쓸 수 있음.
        PriorityQueue<Item> pq4 = new PriorityQueue<>(Item::compareTo);
    }

    // Comparator 의 static 메소드로 Comparator 구현체 클래스를 생성할 수 있다.
    private static final Comparator<Item> ITEM_COMPARATOR =
            Comparator.comparingInt((Item item) -> item.priority)
                    .thenComparing((Item item) -> item.name);

    // Comparator 인터페이스를 구현하는 클래스를 직접 생성할 수 있다.
    private static class ItemComparator implements Comparator<Item> {
        @Override
        public int compare(Item o1, Item o2) {
            return ITEM_COMPARATOR.compare(o1, o2);
        }
    }

    private static class Item implements Comparable<Item> {
        String name; // 사전순
        int priority; // 낮을수록 우선순위 높음

        public Item(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        // 분기문 이용
        @Override
        public int compareTo(Item o) {
            if (priority < o.priority) {
                return -1;
            } else if (priority > o.priority) {
                return 1;
            } else {
                return name.compareTo(o.name);
            }
        }

        // 뺄셈 이용
//        @Override
//        public int compareTo(Item o) {
//            // 오버플로우 가능성 있음
//            int val = priority - o.priority;
//            if (val != 0)
//                return val;
//
//            return name.compareTo(o.name);
//        }

        // 비교 유틸 사용
//        @Override
//        public int compareTo(Item o) {
//            int val = Integer.compare(priority, o.priority);
//            if (val != 0)
//                return val;
//
//            return name.compareTo(o.name);
//        }

        // Comparator 사용
//        @Override
//        public int compareTo(Item o) {
//            return ITEM_COMPARATOR.compare(this, o);
//        }
    }

    private static class ItemComparableCustomInterface {

        Item item;

        public int compareTo(Item o) {
            if (item.priority < o.priority) {
                return -1;
            } else if (item.priority > o.priority) {
                return 1;
            } else {
                return item.name.compareTo(o.name);
            }
        }

        public static int compareToStatic(Item o) {
            return -1;
        }
    }
}
