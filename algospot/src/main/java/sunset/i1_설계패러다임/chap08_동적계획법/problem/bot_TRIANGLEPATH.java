package sunset.i1_설계패러다임.chap08_동적계획법.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/TRIANGLEPATH
 *
 * 상태: 성공
 */
public class bot_TRIANGLEPATH {

    public static int n;

    public static int[][] cache = new int[100][100];

    public static int[][] board;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int C = Integer.parseInt(br.readLine().trim());
        List<Integer> results = new ArrayList<>(C);
        for (int c = 0; c < C; ++c) {
            n = Integer.parseInt(br.readLine().trim());
            init();
            board = new int[n][n];

            for (int i = 0; i < n; ++i) {
                String[] input = br.readLine().trim().split("\\s+");
                for (int j = 0; j <= i; ++j) {
                    board[i][j] = Integer.parseInt(input[j]);
                }
            }

            results.add(maxValue(0, 0));
        }

        results.forEach(result -> {
            System.out.println(result);
        });
    }

    public static int maxValue(int y, int x) {
        if (y >= n || x > y)
            return -1;

        if (cache[y][x] >= 0) {
            return cache[y][x];
        }

        if (y == n-1) {
            cache[y][x] = board[y][x];
            return board[y][x];
        }

        int result = board[y][x] + Math.max(maxValue(y+1, x), maxValue(y+1, x+1));
        cache[y][x] = result;

        return result;
    }

    public static void init() {
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                cache[i][j] = -1;
            }
        }
    }
}
