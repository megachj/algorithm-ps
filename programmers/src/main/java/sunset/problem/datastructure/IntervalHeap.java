package sunset.problem.datastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// TODO, FIXME: 인터벌 힙
public class IntervalHeap<T extends Comparable<T>> {

    private final List<Interval<T>> list;
    private int currentIndex;

    public IntervalHeap() {
        this.list = new ArrayList<>();
        this.list.add(new Interval<>(null, null));
        this.currentIndex = 0;
    }

    /**
     * TODO: O(logn) 으로 되는가?
     * 값을 삽입한다. 중복되도 된다.
     *
     * @param value 값
     */
    public void offer(T value) {
        // 삽입
        if (list.get(currentIndex).min == null) {
            list.get(currentIndex).min = value;
        } else if (list.get(currentIndex).max == null) {
            list.get(currentIndex).max = value;
        } else {
            list.add(new Interval<>(value, null));
            currentIndex++;
        }

        // 선조들 비교 및 스왑
        for (int nodeIndex = currentIndex, parentIndex = getParentIndex(nodeIndex); parentIndex >= 0; nodeIndex = parentIndex, parentIndex = getParentIndex(nodeIndex)) {
            var node = list.get(nodeIndex);
            var parent = list.get(parentIndex);
            if (node.isStrictlyWithinParent(parent)) {
                break;
            }
            node.swapParent(parent);
        }
    }

    /**
     * TODO: O(logn) 으로 되는가?
     * 값을 제거한다. 중복된 값이면 한 개만 제거된다.
     *
     * @param value 값
     */
    public void remove(T value) {
        boolean find = false;
        int nodeIndex = 0;
        while (nodeIndex <= currentIndex) {
            var node = list.get(nodeIndex);
            if (node.isEqualsValue(value)) {
                find = true;
                break;
            }

            int leftChildIndex = getLeftChildIndex(nodeIndex);
            Optional<Interval<T>> leftChildNode = maybeInterval(leftChildIndex);
            if (leftChildNode.isPresent() && leftChildNode.get().isWithinValue(value)) {
                nodeIndex = leftChildIndex;
                continue;
            }

            int rightChildIndex = getRightChildIndex(nodeIndex);
            Optional<Interval<T>> rightChildNode = maybeInterval(rightChildIndex);
            if (rightChildNode.isPresent() && rightChildNode.get().isWithinValue(value)) {
                nodeIndex = rightChildIndex;
                continue;
            }

            break;
        }

        if (!find) {
            return;
        }

        // 노드에서 값 제거
        // min, max 가 같더라도 하나만 지운다. min 부터 지우도록 한다.
        String removeType;
        Interval<T> node = maybeInterval(nodeIndex).orElseThrow();
        if (value.compareTo(node.min) == 0) {
            node.min = null;
            removeType = "MIN";
        } else if (value.compareTo(node.max) == 0) {
            node.max = null;
            removeType = "MAX";
        } else {
            throw new IllegalStateException();
        }

        // 후손들 비교 및 스왑
        while (true) {
            int leftChildIndex = getLeftChildIndex(nodeIndex);
        }
    }

    public List<T> peekInterval() {
        if (list.get(0).min == null) {
            return Collections.emptyList();
        }
        return List.of(list.get(0).min, list.get(0).max);
    }

    private Optional<Interval<T>> maybeInterval(int index) {
        if (index < 0 || index > currentIndex) {
            return Optional.empty();
        }
        return Optional.of(list.get(index));
    }

    private int getParentIndex(int nodeIndex) {
        if (nodeIndex % 2 == 1) {
            return nodeIndex / 2;
        } else {
            return nodeIndex / 2 - 1;
        }
    }

    private int getLeftChildIndex(int nodeIndex) {
        return nodeIndex * 2 + 1;
    }

    private int getRightChildIndex(int nodeIndex) {
        return nodeIndex * 2 + 2;
    }

    private static class Interval<T extends Comparable<T>> {
        private T min;
        private T max;

        public Interval(T min, T max) {
            this.min = min;
            this.max = max;
        }

        /**
         * 객체가 부모에 포함되는지 여부를 확인한다. (parent.min < min && max < parent.max)
         *
         * @param parent 부모
         * @return 부모 범위에 포함되는지 여부
         */
        public boolean isStrictlyWithinParent(Interval<T> parent) {
            return parent.min.compareTo(min) < 0 && max.compareTo(parent.max) < 0;
        }

        /**
         * 부모에 포함되지 않는다면, 포함관계가 성립하도록 부모와 값을 스왑한다.
         *
         * @param parent 부모
         */
        public void swapParent(Interval<T> parent) {
            if (min.compareTo(parent.min) < 0) {
                T temp = min;
                min = parent.min;
                parent.min = temp;
            }
            if (max.compareTo(parent.max) > 0) {
                T temp = max;
                max = parent.max;
                parent.max = temp;
            }
        }

        public boolean isWithinValue(T value) {
            return value.compareTo(min) >= 0 || value.compareTo(max) <= 0;
        }

        /**
         * min, max 중 같은 값이 있는지 확인한다.
         *
         * @param value 값
         * @return 같은 값이 있는지 여부
         */
        public boolean isEqualsValue(T value) {
            return value.compareTo(min) == 0 || value.compareTo(max) == 0;
        }
    }
}
