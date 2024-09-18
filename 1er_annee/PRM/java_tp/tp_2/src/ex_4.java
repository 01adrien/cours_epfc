import java.util.Scanner;

public class ex_4 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        int b = s.nextInt();
        int res = a > b ? a - b : b - a;
        System.out.println(res);
    }
}
