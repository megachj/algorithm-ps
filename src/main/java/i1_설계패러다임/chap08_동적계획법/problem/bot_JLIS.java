package i1_설계패러다임.chap08_동적계획법.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/JLIS
 *
 * 상태:
 */
public class bot_JLIS {

    public static int n, m;


    public static int[] A = new int[100];
    public static int[] B = new int[100];

    public static int[][] cache = new int[100][100];

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
            for (int i = 0; i < n; ++i)
                A[i] = Integer.parseInt(input[i]);

            input = br.readLine().trim().split(" ");
            for (int i = 0; i < m; ++i)
                B[i] = Integer.parseInt(input[i]);

            int max = 0;
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    max = Math.max(max, jLis(i, j));
                }
            }

            results.add(max);
        }

        for (int result: results) {
            System.out.println(result);
        }
    }

    // A[a], B[b] 는 반드시 포함하는 JLIS 크기
    public static int jLis(int idxA, int idxB) {
        if (cache[idxA][idxB] >= 0)
            return cache[idxA][idxB];

        int max = A[idxA] == B[idxB] ? 1 : 2;
        int addValue = A[idxA] == B[idxB] ? 0 : 1;
        for (int nextB = idxB+1; nextB < m; ++nextB) {
            if (B[idxB] < B[nextB]) {
                max = Math.max(max, addValue + jLis(idxA, nextB));
            }
        }

        for (int nextA = idxA+1; nextA < n; ++nextA) {
            if (A[idxA] < A[nextA]) {
                max = Math.max(max, addValue + jLis(nextA, idxB));
            }
        }

        cache[idxA][idxB] = max;
        return max;
    }

    public static void initCache() {
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                cache[i][j] = -1;
            }
        }
    }
}
