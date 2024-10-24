import java.util.ArrayList;
import java.util.List;

public class PrimeFlixStreaming extends PrimeFlixAccount {

    protected StreamingQuality quality;

    private int streamWatchedTime = 0;

    private List<Movie> streamedMovies;

    private final int streamLimit = 200;

    public PrimeFlixStreaming(String userName, StreamingQuality quality) {
        super(userName);
        this.quality = quality;
        this.streamedMovies = new ArrayList<Movie>();
    }

    public void streamMovie(Movie movie) throws Exception {
        if (streamWatchedTime == streamLimit || !movie.isStreamable()) {
            throw new Exception("You reached stream limit or this film is not streamable");
        }
        streamedMovies.add(movie);
        streamWatchedTime++;
    }

    @Override
    public double getInvoice() {
        invoice += (quality == StreamingQuality.HIGH) ? 15.99 : 10.99;
        return invoice;
    }

    @Override
    public void printHistory() {
        super.printHistory();
        System.out.println("Streamed movies:");
        for (Movie movie : streamedMovies) {
            System.out.println(movie);
        }
    }

}