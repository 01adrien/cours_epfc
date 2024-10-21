import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class PrimeFlixTest {
    private double precision = 0.00001;

    @Test
    public void testPrimeFlixAccount() {
        // test un film ancien et un film rÃ©cent

        PrimeFlixAccount primeFlixAccount = new PrimeFlixAccount("Bob");

        int thisYear = LocalDate.now().getYear();
        Movie newMovie = new Movie("Barbie", thisYear, true, true);
        primeFlixAccount.rentMovie(newMovie);

        Movie oldMovie = new Movie("The Matrix", 1999, false, true);
        primeFlixAccount.rentMovie(oldMovie);

        primeFlixAccount.printHistory();
        double bill = primeFlixAccount.getInvoice();
        // 2.99â‚¬ + 4.99â‚¬
        assertEquals(7.98, bill, precision);

    }

    @Test
    public void testStreamingPrimeFlixAccount() {
        // test 1 film en streaming et un film louÃ©

        // StreamingPrimeFlixAccount streamingPrimeFlixAccount = new
        // StreamingPrimeFlixAccount("Bob", StreamingQuality.HIGH);
        //
        // Movie movie1 = new Movie("Dune", 2023, false, true);
        // streamingPrimeFlixAccount.streamMovie(movie1);
        //
        // int thisYear = LocalDate.now().getYear();
        // Movie movie2 = new Movie("The Creator", thisYear, false, false);
        // streamingPrimeFlixAccount.rentMovie(movie2);
        //
        // double bill = streamingPrimeFlixAccount.getInvoice();
        // // 15.99â‚¬ + 4.99â‚¬
        // assertEquals(20.98, bill, precision);
    }

    @Test
    public void testPremiumPrimeFlixAccount() {
        // test 3 premiere movies, 1 rented movie, 1 streamed movie

        // PremiumPrimeFlixAccount premiumPrimeFlixAccount = new
        // PremiumPrimeFlixAccount("Bob");
        //
        // int thisYear = LocalDate.now().getYear();
        // Movie premiereMovie1 = new Movie("Spiderman 22", thisYear, true, true);
        // premiumPrimeFlixAccount.rentMovie(premiereMovie1);
        // Movie premiereMovie2 = new Movie("StarWars 65", thisYear, true, true);
        // premiumPrimeFlixAccount.rentMovie(premiereMovie2);
        // Movie premiereMovie3 = new Movie("Avengers 5", thisYear, true, true);
        // premiumPrimeFlixAccount.rentMovie(premiereMovie3);
        //
        // Movie rentMovie = new Movie("The Creator", thisYear, false, false);
        // premiumPrimeFlixAccount.rentMovie(rentMovie);
        //
        // Movie streamMovie = new Movie("Dune", 2023, false, true);
        // premiumPrimeFlixAccount.streamMovie(streamMovie);
        //
        // double bill = premiumPrimeFlixAccount.getInvoice();
        // // 0â‚¬ + 4.99â‚¬ + 4.99â‚¬ + 19.99 â‚¬
        // assertEquals(29.97, bill, precision);

    }
}
