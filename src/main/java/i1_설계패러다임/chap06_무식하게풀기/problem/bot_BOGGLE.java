package i1_설계패러다임.chap06_무식하게풀기.problem;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/BOGGLE
 *
 * 상태: 성공
 */
public class bot_BOGGLE {

    // Pair: dy, dx
    public static List<Pair<Integer, Integer>> dpList = Arrays.asList(
            new Pair<>(0, -1), new Pair<>(-1, -1), new Pair<>(-1, 0), new Pair<>(-1, 1),
            new Pair<>(0, 1), new Pair<>(1, 1), new Pair<>(1, 0), new Pair<>(1, -1)
    );

    public static char[][] board = new char[5][5];

    public static int[][][] cache = new int[10][5][5];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int C = Integer.parseInt(br.readLine());
        for (int c = 0; c < C; ++c) {
            for (int i = 0; i < 5; ++i) {
                String line = br.readLine();
                board[i] = line.toCharArray();
            }

            List<String> results = new ArrayList<>(10);

            int N = Integer.parseInt(br.readLine());
            for (int n = 0; n < N; ++n) {
                initialize();
                String word = br.readLine();
                boolean isExist = false;
                for (int y = 0; y < 5; ++y) {
                    for (int x = 0; x < 5; ++x) {
                        if (solve(word, 0, y, x)) {
                            isExist = true;
                            break;
                        }
                    }
                    if (isExist)
                        break;
                }
                results.add(isExist ? String.format("%s YES", word) : String.format("%s NO", word));
            }

            for (int n = 0; n < N; ++n) {
                System.out.println(results.get(n));
            }
        }
    }

    public static boolean solve(String word, int idx, int y, int x){
        if (y >= 5 || y < 0 || x >= 5 || x < 0)
            return false;

        if (word.length() <= idx)
            return true;

        if (cache[idx][y][x] == 0)
            return false;

        if (word.charAt(idx) == board[y][x]) {
            int dy, dx;
            for (int i = 0; i < dpList.size(); ++i) {
                dy = dpList.get(i).getKey();
                dx = dpList.get(i).getValue();
                if (solve(word, idx+1, y+dy, x+dx))
                    return true;
            }
        }

        cache[idx][y][x] = 0;
        return false;
    }

    public static void initialize() {
        for (int i = 0; i < 10; ++i)
            for (int j = 0; j < 5; ++j)
                for (int k = 0; k < 5; ++k)
                    cache[i][j][k] = -1;
    }
}
