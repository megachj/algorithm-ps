package sunset.i1_설계패러다임.chap08_동적계획법.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/PI
 *
 * 상태: 성공
 */
public class bot_PI {

    public static final int INF = 200_000;

    public static int[] cache = new int[10000];

    public static String A;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int C = Integer.parseInt(br.readLine().trim());
        List<Integer> results = new ArrayList<>(C);
        for (int c = 0; c < C; ++c) {
            A = br.readLine().trim();
            initCache();

            int result = solve(0);
            results.add(result);
        }

        for (Integer result: results)
            System.out.println(result);
    }

    // A[i] A[i+1] ... 의 최소 난이도 리턴
    public static int solve(int i) {
        // 정확히 마지막까지 다 난이도 계산이 끝난 경우
        if (i == A.length())
            return 0;

        // 마지막 1, 2 개가 남으면 난이도 계산이 미완성 되므로 아주 큰 값 리턴
        int limit = Math.min(A.length() - i, 5);
        if (limit < 3)
            return INF;

        if (cache[i] >= 0)
            return cache[i];

        int minLevel = INF;
        for (int length = 3; length <= limit; ++length) {
            minLevel = Math.min(minLevel, countLevel(i, length) + solve(i + length));
        }

        cache[i] = minLevel;
        return minLevel;
    }

    // A[idx] A[idx+1] ... A[idx+length-1] 의 난이도 계산
    public static int countLevel(int idx, int length) {
        // 계차 수열을 이용해 난이도를 확인한다.
        int d1 = A.charAt(idx + 1) - A.charAt(idx);
        boolean isOneDistinct = true;
        boolean isSwitched = true;
        for (int i = 2; i < length; ++i) {
            int dif = A.charAt(idx + i) - A.charAt(idx + i - 1);
            if (d1 != dif) {
                isOneDistinct = false;
            }

            if (i % 2 == 0 && dif != d1 * -1) {
                isSwitched = false;
            } else if (i % 2 == 1 && dif != d1) {
                isSwitched = false;
            }
        }

        // 모든 숫자가 같은 경우 == 계차 수열의 원소가 모두 0
        if (isOneDistinct && d1 == 0) {
            return 1;
        }

        // 1씩 단조 증가 or 1씩 단조 감소 == 계차 수열의 원소가 모두 1 or -1
        if (isOneDistinct && (d1 == 1 || d1 == -1)) {
            return 2;
        }

        // 숫자가 등차 수열을 이룰 때 == 계차 수열의 원소가 0, 1, -1이 아니면서 모두 같을 때
        if (isOneDistinct) {
            return 5;
        }

        // 숫자 2개가 번갈아 출현 == 계차 수열이 n, -n, ... 처럼 번갈아 출현
        if (isSwitched) {
            return 4;
        }

        return 10;
    }

    public static void initCache() {
        for (int i = 0; i < A.length(); ++i)
            cache[i] = -1;
    }
}
