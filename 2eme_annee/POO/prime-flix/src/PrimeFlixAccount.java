import java.util.ArrayList;
import java.util.List;

public class PrimeFlixAccount {

    protected final String userName;
    protected double invoice = 0;
    protected List<Movie> movies;

    public PrimeFlixAccount(String userName) {
        this.userName = userName;
        movies = new ArrayList<Movie>();
    }

    public void rentMovie(Movie movie) {
        invoice += movie.isOld() ? 2.99 : 4.99;
        movies.add(movie);
    }

    public double getInvoice() {
        return invoice;
    }

    public void printHistory() {
        System.out.println(userName);
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

}
