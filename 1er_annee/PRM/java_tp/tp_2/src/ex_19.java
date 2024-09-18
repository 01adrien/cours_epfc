import java.util.Scanner;

public class ex_19 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("days : ");
        int day = s.nextInt();
        System.out.print("kms : ");
        int km = s.nextInt();
        int priceDays = 250 * day;
        double amount = 0;

        if (km >= 50 && km <= 500) {
            amount += km * 1.25;
        } else if (km > 500) {
            amount += 450 * 1.25;
            amount += (km - 500) * (1.25 * 0.90);
        }
        System.out.println("price : " + (amount + priceDays) + " euros");
    }
}
