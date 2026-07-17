package sunset.problem.datastructure;

import java.util.ArrayList;
import java.util.List;

public class IntervalHeap<T extends Comparable<T>> {

    private final List<Interval<T>> elements;

    public IntervalHeap() {
        elements = new ArrayList<>();
    }

    public void add(T value) {
        if (elements.isEmpty() || elements.get(elements.size() - 1).isInterval()) {
            elements.add(new Interval<>(value));
        } else {
            elements.get(elements.size() - 1).add(value);
        }
    }

    public void removeMin() {
        if (elements.isEmpty()) {
            return;
        }

        if (elements.size() == 1) {
            elements.get(0).removeMin();
            if (elements.get(0).isEmpty()) {
                elements.remove(0);
            }
            return;
        }

        // 루트 노드 min 값 제거 & 마지막 값 루트로 가져오기
        elements.get(0).removeMin();
        T lastLeafMin = elements.get(elements.size() - 1).removeMin();
        if (elements.get(elements.size() - 1).isEmpty()) {
            elements.remove(elements.size() - 1);
        }
        elements.get(0).add(lastLeafMin);

        // 자손들과 스왑
        int index = 0;
        while (true) {
            if (index >= elements.size()) {
                break;
            }

            Interval<T> parent = elements.get(index);

            int leftChildIndex = index * 2 + 1;
            int rightChildIndex = index * 2 + 2;

            Interval<T> leftChild = leftChildIndex < elements.size() ? elements.get(leftChildIndex) : null;
            Interval<T> rightChild = rightChildIndex < elements.size() ? elements.get(rightChildIndex) : null;

            SwapChildrenResult result = parent.swapChildren(leftChild, rightChild);
            if (result == SwapChildrenResult.NONE) {
                break;
            } else if (result == SwapChildrenResult.LEFT) {
                index = leftChildIndex;
            } else {
                index = rightChildIndex;
            }
        }
    }

    public void removeMax() {
        if (elements.isEmpty()) {
            return;
        }

        if (elements.size() == 1) {
            elements.get(0).removeMax();
            if (elements.get(0).isEmpty()) {
                elements.remove(0);
            }
            return;
        }

        // 루트 노드 max 값 제거 & 마지막 값 루트로 가져오기
        elements.get(0).removeMax();
        T lastLeafMax = elements.get(elements.size() - 1).removeMax();
        if (elements.get(elements.size() - 1).isEmpty()) {
            elements.remove(elements.size() - 1);
        }
        elements.get(0).add(lastLeafMax);

        // 자손들과 스왑
        int index = 0;
        while (true) {
            if (index >= elements.size()) {
                break;
            }

            Interval<T> parent = elements.get(index);

            int leftChildIndex = index * 2 + 1;
            int rightChildIndex = index * 2 + 2;

            Interval<T> leftChild = leftChildIndex < elements.size() ? elements.get(leftChildIndex) : null;
            Interval<T> rightChild = rightChildIndex < elements.size() ? elements.get(rightChildIndex) : null;

            SwapChildrenResult result = parent.swapChildren(leftChild, rightChild);
            if (result == SwapChildrenResult.NONE) {
                break;
            } else if (result == SwapChildrenResult.LEFT) {
                index = leftChildIndex;
            } else {
                index = rightChildIndex;
            }
        }
    }

    private static class Interval<T extends Comparable<T>> {
        private T min;
        private T max;

        public Interval(T value) {
            min = value;
        }

        T getMin() {
            return min != null ? min : max;
        }

        T getMax() {
            return max != null ? max : min;
        }

        T getPoint() {
            if (!isPoint()) {
                throw new IllegalStateException("점이 아닌데, 점 값을 불러오려고 합니다.");
            }
            return min != null ? min : max;
        }

        void setPoint(T value) {
            if (!isPoint()) {
                throw new IllegalStateException("점이 아닌데, 점 값을 갱신하려고 합니다.");
            }
            if (min != null) {
                min = value;
            } else {
                max = value;
            }
        }

        public boolean isEmpty() {
            return min == null && max == null;
        }

        public boolean isPoint() {
            return (min != null && max == null) || (min == null && max != null);
        }

        public boolean isInterval() {
            return min != null && max != null;
        }

        /**
         * 값을 구간에 추가한다.
         *
         * @param value 값
         */
        public void add(T value) {
            if (min != null && max != null) {
                throw new IllegalStateException("최솟값, 최댓값이 모두 존재하고 있어서 추가할 수 없습니다.");
            }

            if (min == null) {
                min = value;
            } else {
                max = value;
            }

            // min, max 값 조정
            if (isInterval() && min.compareTo(max) > 0) {
                T temp = min;
                min = max;
                max = temp;
            }
        }

        /**
         * 최솟값을 제거한다. 없으면 최댓값을 제거한다.
         *
         * @return
         */
        public T removeMin() {
            T result;
            if (min != null) {
                result = min;
                min = null;
            } else {
                result = max;
                max = null;
            }
            return result;
        }

        /**
         * 최댓값을 제거한다. 없으면 최솟값을 제거한다.
         *
         * @return
         */
        public T removeMax() {
            T result;
            if (max != null) {
                result = max;
                max = null;
            } else {
                result = min;
                min = null;
            }
            return result;
        }

        /**
         * 부모와 자식들의 구간을 비교하고 필요하면 조정한다.
         *
         * @param leftChild 왼쪽 자식
         * @param rightChild 오른쪽 자식
         * @return 변경결과
         */
        public SwapChildrenResult swapChildren(Interval<T> leftChild, Interval<T> rightChild) {
            // 자식이 없을때
            if (leftChild == null && rightChild == null) {
                return SwapChildrenResult.NONE;
            }

            // 왼쪽 자식만 있을때(자식이 하나일 때)
            if (leftChild != null && rightChild == null) {
                ContainResult containResult = contains(leftChild);
                switch (containResult) {
                    case CONTAINED:
                        return SwapChildrenResult.NONE;
                    case MIN_EXCESS:
                    case MAX_EXCESS:
                        swap(leftChild);
                        return SwapChildrenResult.LEFT;
                    default:
                        throw new RuntimeException();
                }
            }

            // 자식이 둘 다 있을 때
            ContainResult leftResult = contains(leftChild);
            ContainResult rightResult = contains(rightChild);

            if (leftResult == ContainResult.CONTAINED && rightResult == ContainResult.CONTAINED) {
                return SwapChildrenResult.NONE;
            }
            else if (leftResult != ContainResult.CONTAINED && rightResult == ContainResult.CONTAINED) {
                swap(leftChild);
                return SwapChildrenResult.LEFT;
            }
            else if (leftResult == ContainResult.CONTAINED && rightResult != ContainResult.CONTAINED) {
                swap(rightChild);
                return SwapChildrenResult.RIGHT;
            }
            else {
                if (leftResult == ContainResult.MIN_EXCESS) {
                    T leftMin = leftChild.getMin();
                    T rightMin = rightChild.getMin();
                    if (leftMin.compareTo(rightMin) <= 0) {
                        swap(leftChild);
                        return SwapChildrenResult.LEFT;
                    } else {
                        swap(rightChild);
                        return SwapChildrenResult.RIGHT;
                    }
                } else {
                    T leftMax = leftChild.getMax();
                    T rightMax = rightChild.getMax();
                    if (leftMax.compareTo(rightMax) >= 0) {
                        swap(leftChild);
                        return SwapChildrenResult.LEFT;
                    } else {
                        swap(rightChild);
                        return SwapChildrenResult.RIGHT;
                    }
                }
            }
        }

        private ContainResult contains(Interval<T> child) {
            if (min.compareTo(child.getMin()) > 0) {
                return ContainResult.MIN_EXCESS;
            }

            if (max.compareTo(child.getMax()) < 0) {
                return ContainResult.MAX_EXCESS;
            }

            return ContainResult.CONTAINED;
        }

        private void swap(Interval<T> child) {
            if (child.isPoint()) {
                T childPoint = child.getPoint();
                if (childPoint.compareTo(min) < 0) {
                    T temp = min;
                    min = childPoint;
                    child.setPoint(temp);
                    return;
                }
                if (childPoint.compareTo(max) > 0) {
                    T temp = max;
                    max = childPoint;
                    child.setPoint(temp);
                    return;
                }
            }

            if (child.min.compareTo(min) < 0) {
                T temp = min;
                min = child.min;
                child.min = temp;
                return;
            }
            if (child.max.compareTo(max) > 0) {
                T temp = max;
                max = child.max;
                child.max = temp;
            }
        }
    }

    enum ContainResult {
        // 최솟값 초과
        MIN_EXCESS,
        // 최댓값 초과
        MAX_EXCESS,
        // 포함
        CONTAINED,
        ;
    }

    enum SwapChildrenResult {
        LEFT,
        RIGHT,
        NONE,
        ;
    }
}
