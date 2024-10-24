public class PrimeFlixPremium extends PrimeFlixStreaming {

    private int freeCount;

    public PrimeFlixPremium(String userName) {
        super(userName, StreamingQuality.HIGH);
        freeCount = 2;
    }

    @Override
    public double getInvoice() {
        invoice += 19.99;
        return invoice;
    }

    @Override
    public void rentMovie(Movie movie) {
        if (movie.isPremiere() && freeCount != 0) {
            movie.setFree();
        }
        super.rentMovie(movie);
    }

}
