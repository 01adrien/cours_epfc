
public class App {
    public static void main(String[] args) throws Exception {
        PrimeFlixPremium premiumAccount = new PrimeFlixPremium("Bob");
        premiumAccount.rentMovie(
                new Movie("Spiderman 22", 2022, true, true));
        premiumAccount.streamMovie(
                new Movie("Dune", 2023, false, true));
        premiumAccount.rentMovie(
                new Movie("Dune 2", 2023, false, false));
        premiumAccount.printHistory();
        System.out.println(premiumAccount.getInvoice());
    }
}
