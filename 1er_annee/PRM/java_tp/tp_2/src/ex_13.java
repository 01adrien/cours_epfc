import java.util.Scanner;

public class ex_13 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int hour = s.nextInt();
        int min = s.nextInt();
        if (min == 59) {
            if (hour == 23) {
                System.out.println("00:00");
            } else {
                System.out.println((hour + 1) + ":00");
            }
        } else {
            System.out.println(hour + ":" + (min + 1));
        }
    }
}
