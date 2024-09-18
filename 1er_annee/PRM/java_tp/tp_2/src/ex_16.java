import java.util.Scanner;

public class ex_16 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int year = s.nextInt();
        if ((year % 4 == 0 || year % 400 == 0) && year % 100 != 0) {
            System.out.println(year + " est bissextile");
        } else {
            System.out.println(year + " n'est pas bissextile");
        }
    }
}
