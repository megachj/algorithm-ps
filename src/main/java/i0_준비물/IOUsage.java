package i0_준비물;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class IOUsage {
    public static void main(String[] args) throws Exception {
        // input_BufferedReader_usage();
        // input_Scanner_usage();
        // input_StringTokenizer_usage();
        // output_SystemOut_usage();
        // output_StringBuilder_usage();
    }

    /**
     * NOTE: 가장 추천하는 입력 방식
     *
     * BufferedReader 는 문자열로만 입력 가능. 하지만 속도가 빠르므로 입력이 많은 경우에 사용하자.
     * 문자열로만 입력 받으므로 적절히 파싱이 필요하다.
     */
    private static void input_BufferedReader_usage() throws Exception {
        System.out.println("----- input_BufferedReader_usage -----");

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String line;
        String[] words;

        // 문자열을 읽는다. '\n' 는 포함하지 않음.
        System.out.print("문자열을 입력해주세요: ");
        line = bf.readLine();
        System.out.printf("[%s]\n", line);

        // 문자열 양 옆 공백 제거.
        // 문자열을 읽는다. '\n' 는 포함하지 않음.
        System.out.print("문자열을 입력해주세요: ");
        line = bf.readLine().trim();
        System.out.printf("[%s]\n", line);

        // 입력: 숫자 1개
        System.out.print("N 입력(정수): ");
        int N = Integer.parseInt(bf.readLine().trim());

        // 입력: 숫자 N개
        int[] numbers = new int[N];
        System.out.println("정수 N개 입력: ");
        // 아래처럼 \\s+ 로 해야 모든 공백에 대해서 정확히 구분된다. (공백 여러개, 탭 등 포함)
        words = bf.readLine().trim().split("\\s+"); // split(" ") 이렇게 하면 공백이 1개인 것만 구분되고, 2개 이상인 경우 구분이 오류남.
        for (int i = 0; i < N; ++i) {
            numbers[i] = Integer.parseInt(words[i]);
        }
    }

    /**
     * Scanner 는 서식문자로 입력 가능. 하지만 속도가 느리므로 입력이 적은 경우에만 사용하자.
     *  - 정수: %d = 10진수, %o = 8진수, %x = 16진수
     *  - 실수: %f = 소수형, %e = e 표기법, %g = 출력 대상에 따라 %e 또는 %f로 출력
     *  - 문자(열): %s = 문자열, %c = 문자
     */
    private static void input_Scanner_usage() {
        System.out.println("----- input_Scanner_usage -----");
        Scanner scanner = new Scanner(System.in);

        // 문자열이 아닌 입력: 항상 '\n'도 읽자.
        System.out.print("정수를 입력해주세요: ");
        int n = scanner.nextInt(); scanner.nextLine();

        // 문자열 입력
        System.out.print("문자열을 입력해주세요: ");
        String s = scanner.nextLine();

        System.out.printf("[정수: %d, 문자열: %s]\n", n, s);

        // 입력에서 정수가 주어지는 동안 계속 입력 받음, 숫자가 아니면 중단
        System.out.print("정수 배열을 입력해주세요: ");
        int sum = 0;
        while(scanner.hasNextInt()) {
            sum += scanner.nextInt();
        }
        System.out.printf("[합계: %d]\n", sum);
    }

    /**
     * 입력 문자열을 토큰으로 잘라야할 때 사용한다.
     */
    private static void input_StringTokenizer_usage() throws Exception {
        System.out.println("----- input_StringTokenizer_usage -----");
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("정수 배열을 1줄에 입력해주세요: ");
        String line = bf.readLine();
        StringTokenizer st = new StringTokenizer(line, " ");
        int sum = 0;
        while(st.hasMoreTokens())
            sum += Integer.parseInt(st.nextToken());
        System.out.printf("[합계: %d]", sum);
    }

    private static void output_SystemOut_usage() {
        System.out.println("----- output_SystemOut_usage -----");

        // 문자열 출력
        System.out.println("Hello World!");

        // format 출력. 문자열, 정수, 실수
        System.out.printf("[%s], [%03d], [%010.2f]\n", "age", 1, 3.1415962);
    }

    /**
     * 출력할 것이 많을 때, 여러 번 출력하지 않고 하나의 문자열로 만들어 한 번에 출력하는 것이 빠르다.
     *  - 단일 스레드: StringBuilder, 빠름
     *  - 멀티 스레드: StringBuffer, 느림
     */
    private static void output_StringBuilder_usage() {
        System.out.println("----- output_StringBuilder_usage -----");

        int N = 100_000;
        long start1, end1, start2, end2;

        start1 = System.currentTimeMillis();
        for (int i = 0; i < N; ++i)
            System.out.println(i);
        end1 = System.currentTimeMillis();

        start2 = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; ++i)
            sb.append(i).append("\n");
        System.out.println(sb);
        end2 = System.currentTimeMillis();

        System.out.printf("[N번 출력: %.3f(s), StringBuilder: %.3f(s)]\n", (end1-start1)/1000.0, (end2-start2)/1000.0);
    }
}
