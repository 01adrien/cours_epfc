public class App {
    public static void main(String[] args) throws Exception {
        PrimeFlixAccount primeFlixAccount = new PrimeFlixAccount("Bob");
        Movie newMovie = new Movie("Barbie", 2023, false, false);
        primeFlixAccount.rentMovie(newMovie);
        double bill = primeFlixAccount.getInvoice();
        System.out.println(bill);
        primeFlixAccount.printHistory();
    }
}
