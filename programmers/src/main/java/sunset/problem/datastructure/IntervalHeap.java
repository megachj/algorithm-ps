package sunset.problem.datastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntervalHeap<T extends Comparable<T>> {

    private final List<Interval<T>> elements;

    public IntervalHeap() {
        elements = new ArrayList<>();
    }

    /**
     * 빈 값인지 확인한다.
     *
     * @return 빈 값 여부
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * 구간을 조회한다.
     *
     * @return [] or [값] or [최솟값, 최댓값]
     */
    public List<T> getInterval() {
        if (isEmpty()) {
            return Collections.emptyList();
        }

        if (getInterval(0).isPoint()) {
            return Collections.singletonList(getInterval(0).getPoint());
        }

        return List.of(getInterval(0).getMin(), getInterval(0).getMax());
    }

    /**
     * 값을 추가한다.
     *
     * @param value 값
     */
    public void add(T value) {
        // 마지막 노드에 값 추가
        if (isEmpty() || getInterval(lastIndex()).isInterval()) {
            elements.add(new Interval<>(value));
        } else {
            getInterval(lastIndex()).add(value);
        }

        // 선조들과 구간 포함여부 확인하며 교환 반복
        int childIndex = lastIndex();
        while (true) {
            if (childIndex <= 0) {
                break;
            }

            int parentIndex = calcParentIndex(childIndex);

            Interval<T> child = getInterval(childIndex);
            Interval<T> parent = getInterval(parentIndex);

            if (!child.swapParent(parent)) {
                break;
            }
            childIndex = parentIndex;
        }
    }

    /**
     * 최솟값을 제거한다.
     */
    public void removeMin() {
        if (isEmpty()) {
            return;
        }

        if (size() == 1) {
            getInterval(0).removeMin();
            if (getInterval(0).isEmpty()) {
                elements.remove(0);
            }
            return;
        }

        // 루트 노드 최솟값 제거 & 마지막 값 루트로 가져오기
        getInterval(0).removeMin();
        T lastLeafMin = getInterval(lastIndex()).removeMin();
        if (getInterval(lastIndex()).isEmpty()) {
            elements.remove(lastIndex());
        }
        getInterval(0).add(lastLeafMin);

        // 자손들과 구간 포함여부 확인하며 교환 반복
        int parentIndex = 0;
        while (true) {
            if (parentIndex >= size()) {
                break;
            }

            Interval<T> parent = getInterval(parentIndex);

            int[] childrenIndex = calcChildrenIndex(parentIndex);
            int leftChildIndex = childrenIndex[0];
            int rightChildIndex = childrenIndex[1];

            Interval<T> leftChild = leftChildIndex < size() ? getInterval(leftChildIndex) : null;
            Interval<T> rightChild = rightChildIndex < size() ? getInterval(rightChildIndex) : null;

            SwapChildrenResult result = parent.swapChildren(leftChild, rightChild);
            if (result == SwapChildrenResult.NONE) {
                break;
            } else if (result == SwapChildrenResult.LEFT) {
                parentIndex = leftChildIndex;
            } else {
                parentIndex = rightChildIndex;
            }
        }
    }

    /**
     * 최댓값을 제거한다.
     */
    public void removeMax() {
        if (isEmpty()) {
            return;
        }

        if (size() == 1) {
            getInterval(0).removeMax();
            if (getInterval(0).isEmpty()) {
                elements.remove(0);
            }
            return;
        }

        // 루트 노드 최댓값 제거 & 마지막 값 루트로 가져오기
        getInterval(0).removeMax();
        T lastLeafMax = getInterval(lastIndex()).removeMax();
        if (getInterval(lastIndex()).isEmpty()) {
            elements.remove(lastIndex());
        }
        getInterval(0).add(lastLeafMax);

        // 자손들과 구간 포함여부 확인하며 교환 반복
        int parentIndex = 0;
        while (true) {
            if (parentIndex >= size()) {
                break;
            }

            Interval<T> parent = getInterval(parentIndex);
            int[] childrenIndex = calcChildrenIndex(parentIndex);
            int leftChildIndex = childrenIndex[0];
            int rightChildIndex = childrenIndex[1];

            Interval<T> leftChild = leftChildIndex < size() ? getInterval(leftChildIndex) : null;
            Interval<T> rightChild = rightChildIndex < size() ? getInterval(rightChildIndex) : null;

            SwapChildrenResult result = parent.swapChildren(leftChild, rightChild);
            if (result == SwapChildrenResult.NONE) {
                break;
            } else if (result == SwapChildrenResult.LEFT) {
                parentIndex = leftChildIndex;
            } else {
                parentIndex = rightChildIndex;
            }
        }
    }

    private Interval<T> getInterval(int index) {
        return elements.get(index);
    }

    private int size() {
        return elements.size();
    }

    private int lastIndex() {
        return size() - 1;
    }

    private static int[] calcChildrenIndex(int parentIndex) {
        return new int[]{ parentIndex * 2 + 1, parentIndex * 2 + 2 };
    }

    private static int calcParentIndex(int childIndex) {
        return childIndex % 2 == 1 ? childIndex / 2 : childIndex / 2 - 1;
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
         *
         * @param parent
         * @return
         */
        public boolean swapParent(Interval<T> parent) {
            ContainResult containResult = parent.contains(this);
            if (containResult == ContainResult.CONTAINED) {
                return false;
            }

            parent.swap(this);
            return true;
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
