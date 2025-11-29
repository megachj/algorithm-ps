package sunset.leetcode.datastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 데크(Double-Ended Queue) ADT<br>
 * - 인터페이스: java.util.Deque<br>
 * - 구현체: java.util.ArrayDeque<br>
 */
public class DequeTest {

    @DisplayName("LIFO 로 사용하기")
    @Test
    public void test_LIFO() {
        Deque<Integer> deque = new ArrayDeque<>();
        // 추가는 기본이 끝에 추가
        deque.add(1);
        deque.addLast(2);
        deque.offer(3);
        deque.offerLast(4);

        // 조회는 처음 조회
        Assertions.assertEquals(4, deque.peekLast());

        // 제거는 처음 제거
        Assertions.assertEquals(4, deque.pollLast());
        Assertions.assertEquals(3, deque.pollLast());
        Assertions.assertEquals(2, deque.pollLast());
        Assertions.assertEquals(1, deque.pollLast());
        Assertions.assertTrue(deque.isEmpty());
    }

    @DisplayName("LIFO 로 사용하기2")
    @Test
    public void test_LIFO2() {
        Deque<Integer> deque = new ArrayDeque<>();
        // push 는 처음에 추가
        deque.push(1);
        deque.push(2);
        deque.offerFirst(3);
        deque.addFirst(4);

        // 조회는 처음 조회
        Assertions.assertEquals(4, deque.peek());

        // 제거는 처음 제거
        Assertions.assertEquals(4, deque.pop());
        Assertions.assertEquals(3, deque.pop());
        Assertions.assertEquals(2, deque.poll());
        Assertions.assertEquals(1, deque.poll());
        Assertions.assertTrue(deque.isEmpty());
    }

    @DisplayName("FIFO 로 사용하기")
    @Test
    public void test_FIFO() {
        Deque<Integer> deque = new ArrayDeque<>();
        // 추가는 끝에 추가
        deque.offer(1);
        deque.add(2);

        // 조회는 처음 조회
        Assertions.assertEquals(1, deque.peek());

        // 제거는 처음 제거
        Assertions.assertEquals(1, deque.pop());
        Assertions.assertEquals(2, deque.pop());
        Assertions.assertTrue(deque.isEmpty());
    }
}
