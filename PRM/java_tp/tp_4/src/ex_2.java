import java.util.Scanner;

public class ex_2 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        for (int i = 1; i <= 10; i++){
            System.out.println(i + " x " + n + " = " + (i * n));
        }
    }
}