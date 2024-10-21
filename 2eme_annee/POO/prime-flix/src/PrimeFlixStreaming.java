import java.time.LocalDate;

public class PrimeFlixStreaming extends PrimeFlixAccount {

    private StreamingQuality quality;

    private int streamWatchedTime = 0;

    private final int streamLimit = 200;

    public PrimeFlixStreaming(String userName, StreamingQuality quality) {
        super(userName);
        this.quality = quality;
        invoice = (quality == StreamingQuality.HIGH) ? 15.99 : 10.99;
    }

    public void streamMovie(Movie movie) throws Exception {
        if (streamWatchedTime == streamLimit) {
            throw new Exception("You reached strem limit for " + LocalDate.now().getMonth());
        }
        streamWatchedTime++;
    }

}
