package topic3_설계패러다임.chap05_무식하게풀기.problem;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.algospot.com/judge/problem/read/BOGGLE
 *
 * 난이도: 하
 */
public class BOGGLE {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] board = new char[5][5];
        int C = scanner.nextInt(); scanner.nextLine();
        for (int c = 0; c < C; ++c) {
            for (int i = 0; i < 5; ++i) {
                String row = scanner.nextLine();
                board[i] = row.toCharArray();
            }

            List<Result> results = new ArrayList<>();
            int N = scanner.nextInt(); scanner.nextLine();
            for (int i = 0; i < N; ++i) {
                results.add(new Result(scanner.nextLine()));
            }

            // TODO
        }
    }

    @RequiredArgsConstructor
    @Data
    private static class Result {
        private final String word;
        private boolean result = false;
    }
}
