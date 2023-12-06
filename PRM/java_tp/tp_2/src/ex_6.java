import java.util.Scanner;

public class ex_6 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        boolean res = a >= 1 && a <= 10;
        System.out.println(res);
    }
}
