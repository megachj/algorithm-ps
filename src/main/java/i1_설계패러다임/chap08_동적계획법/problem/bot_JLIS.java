package i1_설계패러다임.chap08_동적계획법.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/JLIS
 *
 * 상태: 성공
 */
public class bot_JLIS {

    public static int n, m;

    public static long[] A = new long[101];
    public static long[] B = new long[101];

    public static int[][] cache = new int[101][101];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input;

        int C = Integer.parseInt(br.readLine().trim());
        List<Integer> results = new ArrayList<>(C);
        for (int c = 0; c < C; ++c) {
            initCache();

            input = br.readLine().trim().split(" ");
            n = Integer.parseInt(input[0]);
            m = Integer.parseInt(input[1]);

            input = br.readLine().trim().split(" ");
            A[0] = Long.MIN_VALUE;
            for (int i = 1; i <= n; ++i)
                A[i] = Long.parseLong(input[i-1]);

            input = br.readLine().trim().split(" ");
            B[0] = Long.MIN_VALUE;
            for (int i = 1; i <= m; ++i)
                B[i] = Long.parseLong(input[i-1]);

            int max = 1;
            for (int i = 0; i <= n; ++i) {
                for (int j = 0; j <= m; ++j) {
                    max = Math.max(max, jLis(i, j));
                }
            }

            results.add(max);
        }

        for (int result: results) {
            System.out.println(result);
        }
    }

    // Sorted(A[a], B[b]), ... 로 시작하는 JLIS 크기
    public static int jLis(int idxA, int idxB) {
        if (cache[idxA][idxB] >= 0)
            return cache[idxA][idxB];

        // 만약 크기가 같으면 JLIS 가 될 수 없으므로 0을 리턴
        if (A[idxA] == B[idxB]) {
            cache[idxA][idxB] = 0;
            return 0;
        }

        int addA = idxA == 0 ? 0 : 1; // 1 + jLis(nextA, idxB), 이땐 A[idxA] 가 남겨지므로 존재하지 않는 0이 아니라면 1을 더해준다.
        int addB = idxB == 0 ? 0 : 1; // 1 + jLis(idxA, nextB), 이땐 B[idxB] 가 남겨지므로 존재하지 않는 0이 아니라면 1을 더해준다.

        int jLisMax = addA + addB;
        long maxValue = Math.max(A[idxA], B[idxB]);
        for (int nextA = idxA+1; nextA <= n; ++nextA) {
            if (maxValue < A[nextA]) {
                jLisMax = Math.max(jLisMax, addA + jLis(nextA, idxB)); // 남아있는 값 A[idxA]
            }
        }

        for (int nextB = idxB+1; nextB <= m; ++nextB) {
            if (maxValue < B[nextB]) {
                jLisMax = Math.max(jLisMax, addB + jLis(idxA, nextB)); // 남아있는 값 B[idxB]
            }
        }

        cache[idxA][idxB] = jLisMax;
        return jLisMax;
    }

    public static void initCache() {
        for (int i = 0; i <= 100; ++i) {
            for (int j = 0; j <= 100; ++j) {
                cache[i][j] = -1;
            }
        }
    }
}
