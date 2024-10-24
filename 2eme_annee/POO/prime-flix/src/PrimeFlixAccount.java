import java.util.ArrayList;
import java.util.List;

public class PrimeFlixAccount {

    protected final String userName;
    protected double invoice = 0;
    protected List<Movie> rentedMovies;

    public PrimeFlixAccount(String userName) {
        this.userName = userName;
        this.rentedMovies = new ArrayList<Movie>();
    }

    public void rentMovie(Movie movie) {
        if (!movie.isFree()) {
            invoice += movie.isOld() ? 2.99 : 4.99;
        }
        rentedMovies.add(movie);
    }

    public double getInvoice() {
        return invoice;
    }

    public void printHistory() {
        System.out.println(userName);
        System.out.println("Rented movies:");
        for (Movie movie : rentedMovies) {
            System.out.println(movie);
        }
    }

}
