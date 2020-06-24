package i5_그래프.chap28_DFS.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/DICTIONARY
 *
 * 상태: 
 */
public class bot_DICTIONARY {

    public static int C, N;

    public static boolean[][] adjMatrix = new boolean[26][26];

    public static boolean[] visited = new boolean[26];

    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        C = Integer.parseInt(bf.readLine());
        for (int c = 0; c < C; ++c) {
            // 2차원 배열 초기화
            for (boolean[] row : adjMatrix)
                Arrays.fill(row, false);
            // 1차원 배열 초기화
            Arrays.fill(visited, false);

            N = Integer.parseInt(bf.readLine());
            List<String> wordList = new ArrayList<>(N);
            for (int n = 0; n < N; ++n) {
                wordList.add(bf.readLine());
            }

            mappingAdjMatrix(wordList);
            System.out.println(topologicalSort());
        }
    }

    public static void mappingAdjMatrix(List<String> wordList) {
        for (int i = 0; i < wordList.size() - 1; ++i) {
            for (int j = i; j < wordList.size(); ++j) {
                // TODO
            }
        }
    }

    // TODO
    public static String topologicalSort() {
        return null;
    }

    // TODO
    public static void dfs() {

    }
}
