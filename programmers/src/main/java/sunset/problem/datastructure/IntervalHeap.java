package sunset.problem.datastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

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

        if (rootInterval().isPoint()) {
            return Collections.singletonList(rootInterval().getPoint());
        }

        return List.of(rootInterval().getMin(), rootInterval().getMax());
    }

    /**
     * 값을 추가한다.
     *
     * @param value 값
     */
    public void add(T value) {
        // 마지막 노드에 값 추가
        if (isEmpty() || lastInterval().isInterval()) {
            elements.add(new Interval<>(value));
        } else {
            lastInterval().add(value);
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

            if (!child.compareAndSubstituteWithParent(parent)) {
                break;
            }
            childIndex = parentIndex;
        }
    }

    /**
     * 최솟값을 제거한다.
     */
    public void removeMin() {
        remove(Interval::removeMin);
    }

    /**
     * 최댓값을 제거한다.
     */
    public void removeMax() {
        remove(Interval::removeMax);
    }

    private void remove(Function<Interval<T>, T> removeFunction) {
        if (isEmpty()) {
            return;
        }

        if (size() == 1) {
            removeFunction.apply(rootInterval());
            if (rootInterval().isEmpty()) {
                elements.remove(0);
            }
            return;
        }

        // 루트 노드 값 제거
        removeFunction.apply(rootInterval());

        // 마지막 노드 값 루트로 이동
        T lastValue = removeFunction.apply(lastInterval());
        if (lastInterval().isEmpty()) {
            elements.remove(lastIndex());
        }
        rootInterval().add(lastValue);

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

            ChildrenSubstitutionResult result = parent.compareAndSubstituteWithChildren(leftChild, rightChild);
            if (result == ChildrenSubstitutionResult.NONE) {
                break;
            } else if (result == ChildrenSubstitutionResult.LEFT) {
                parentIndex = leftChildIndex;
            } else {
                parentIndex = rightChildIndex;
            }
        }
    }

    private Interval<T> rootInterval() {
        return getInterval(0);
    }

    private Interval<T> lastInterval() {
        return getInterval(lastIndex());
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
         * @return 제거된 값
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
         * @return 제거된 값
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
         * 부모 노드와 구간 포함관계를 비교하고 필요하면 교체한다.
         *
         * @param parent 부모
         * @return 교체됐는지 여부
         */
        public boolean compareAndSubstituteWithParent(Interval<T> parent) {
            ContainRelation containResult = parent.contains(this);
            if (containResult == ContainRelation.CONTAINED) {
                return false;
            }

            parent.swap(this);
            return true;
        }

        /**
         * 자식 노드들과 구간 포함관계를 비교하고 필요하면 교체한다.
         *
         * @param leftChild 왼쪽 자식
         * @param rightChild 오른쪽 자식
         * @return 교체된 결과
         */
        public ChildrenSubstitutionResult compareAndSubstituteWithChildren(Interval<T> leftChild, Interval<T> rightChild) {
            // 자식이 없을때
            if (leftChild == null && rightChild == null) {
                return ChildrenSubstitutionResult.NONE;
            }

            // 왼쪽 자식만 있을때(자식이 하나일 때)
            if (leftChild != null && rightChild == null) {
                ContainRelation leftContains = contains(leftChild);
                switch (leftContains) {
                    case CONTAINED:
                        return ChildrenSubstitutionResult.NONE;
                    case MIN_EXCESS:
                    case MAX_EXCESS:
                        swap(leftChild);
                        return ChildrenSubstitutionResult.LEFT;
                    default:
                        throw new RuntimeException();
                }
            }

            // 자식이 둘 다 있을 때
            ContainRelation leftContains = contains(leftChild);
            ContainRelation rightContains = contains(rightChild);

            if (leftContains == ContainRelation.CONTAINED && rightContains == ContainRelation.CONTAINED) {
                return ChildrenSubstitutionResult.NONE;
            }
            else if (leftContains != ContainRelation.CONTAINED && rightContains == ContainRelation.CONTAINED) {
                swap(leftChild);
                return ChildrenSubstitutionResult.LEFT;
            }
            else if (leftContains == ContainRelation.CONTAINED && rightContains != ContainRelation.CONTAINED) {
                swap(rightChild);
                return ChildrenSubstitutionResult.RIGHT;
            }
            else {
                if (leftContains == ContainRelation.MIN_EXCESS) {
                    T leftMin = leftChild.getMin();
                    T rightMin = rightChild.getMin();
                    if (leftMin.compareTo(rightMin) <= 0) {
                        swap(leftChild);
                        return ChildrenSubstitutionResult.LEFT;
                    } else {
                        swap(rightChild);
                        return ChildrenSubstitutionResult.RIGHT;
                    }
                } else {
                    T leftMax = leftChild.getMax();
                    T rightMax = rightChild.getMax();
                    if (leftMax.compareTo(rightMax) >= 0) {
                        swap(leftChild);
                        return ChildrenSubstitutionResult.LEFT;
                    } else {
                        swap(rightChild);
                        return ChildrenSubstitutionResult.RIGHT;
                    }
                }
            }
        }

        private ContainRelation contains(Interval<T> child) {
            if (min.compareTo(child.getMin()) > 0) {
                return ContainRelation.MIN_EXCESS;
            }

            if (max.compareTo(child.getMax()) < 0) {
                return ContainRelation.MAX_EXCESS;
            }

            return ContainRelation.CONTAINED;
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

    enum ContainRelation {
        // 포함
        CONTAINED,
        // 최솟값 초과
        MIN_EXCESS,
        // 최댓값 초과
        MAX_EXCESS,
        ;
    }

    enum ChildrenSubstitutionResult {
        LEFT,
        RIGHT,
        NONE,
        ;
    }
}
