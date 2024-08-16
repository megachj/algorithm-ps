package sunset.library;

public class DividableRange {

    public final int start;
    public final int endExclusive;
    public final int middle;

    private DividableRange(int start, int endExclusive) {
        this.start = start;
        this.endExclusive = endExclusive;
        this.middle = (start + endExclusive) / 2;
    }

    public static DividableRange of(int start, int endExclusive) {
        return new DividableRange(start, endExclusive);
    }

    public boolean isDividable() {
        return start != middle;
    }

    /**
     * start <= x < middle 로 나눈다.
     */
    public DividableRange getDividedLeft() {
        if (!isDividable()) {
            throw new IllegalArgumentException("더 이상 쪼갤 수 없습니다.");
        }
        return new DividableRange(start, middle);
    }

    /**
     * middle <= x < endExclusive 로 나눈다.
     */
    public DividableRange getDividedRight() {
        if (!isDividable()) {
            throw new IllegalArgumentException("더 이상 쪼갤 수 없습니다.");
        }
        return new DividableRange(middle, endExclusive);
    }
}
