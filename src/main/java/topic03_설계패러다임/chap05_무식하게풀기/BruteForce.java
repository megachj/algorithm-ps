package topic03_설계패러다임.chap05_무식하게풀기;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteForce {

    private static List<String> valueList;

    public static void main(String[] args) {
        valueList = Arrays.asList("a", "b", "c", "d");
        combination(4, new ArrayList<>(), 2);
    }

    // TODO
    /**
     *
     */
    public static void permutation() {

    }

    /**
     * n 개의 원소 중 m개를 고르는 모든 조합을 찾는 알고리즘
     *
     * @param n: 전체 원소의 수
     * @param picked: 지금까지 고른 원소들의 번호
     * @param toPick: 더 고를 원소의 수
     */
    public static void combination(int n, List<Integer> picked, int toPick) {
        // 기저 사례: 더 고를 원소가 없을 때 고른 원소들을 출력한다.
        if (toPick == 0) {
            picked.forEach(idx -> {
                System.out.printf("%s ", valueList.get(idx));
            });
            System.out.println();
            return;
        }

        // 고를 수 있는 가장 작은 index
        int smallestIdx = picked.isEmpty() ? 0 : picked.get(picked.size() - 1) + 1;

        // 이 단계에서 원소 하나를 고른다.
        for (int next = smallestIdx; next < n; ++next) {
            picked.add(next);
            combination(n, picked, toPick - 1);
            picked.remove(picked.size()-1);
        }
    }

    // TODO
    /**
     *
     */
    public static void searchAllCase() {

    }
}
