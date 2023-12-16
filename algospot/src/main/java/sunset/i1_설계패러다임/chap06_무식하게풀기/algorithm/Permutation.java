package sunset.i1_설계패러다임.chap06_무식하게풀기.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutation {

    private static List<String> valueList;

    public static void main(String[] args) throws Exception {
        valueList = Arrays.asList("a", "b", "c", "d");
        permutation(4, new ArrayList<>(), 2);
    }

    /**
     * P(n, toPick) 순열
     *
     * @param n: 전체 원소의 수
     * @param picked: 지금까지 고른 원소들의 번호
     * @param toPick: 더 고를 원소의 수
     */
    public static void permutation(int n, List<Integer> picked, int toPick) {
        // TODO
    }
}
