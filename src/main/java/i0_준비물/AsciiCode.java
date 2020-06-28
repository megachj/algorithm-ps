package i0_준비물;

public class AsciiCode {
    public static void main(String[] args) throws Exception {
        String str = "abAB";
        char a = str.charAt(0);
        char b = str.charAt(1);
        char A = str.charAt(2);
        char B = str.charAt(3);

        System.out.printf("char type: %c, %c, %c, %c\n", a, b, A, B);
        System.out.printf("int ascii code: %d, %d, %d, %d\n", (int)a, (int)b, (int)A, (int)B);

        String intLine = "12345";
        int[] array = new int[intLine.length()];
        for (int i = 0; i < intLine.length(); ++i) {
            array[i] = intLine.charAt(i) - '0';
            System.out.printf("%d ", array[i]);
        }
        System.out.println();

        System.out.println('2' - '3');
    }
}
