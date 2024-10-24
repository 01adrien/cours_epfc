import java.time.LocalDate;

class Movie {
    private final String title;
    private final int releaseYear;
    private final boolean isPremiere;
    private final boolean streamable;
    private boolean free;

    public Movie(String title, int releaseYear, boolean isPremiere, boolean streamable) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.isPremiere = isPremiere;
        this.streamable = streamable;
        this.free = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isOld() {
        return (LocalDate.now().getYear() - releaseYear) > 5;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree() {
        this.free = true;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public boolean isPremiere() {
        return isPremiere;
    }

    public boolean isStreamable() {
        return streamable;
    }

    @Override
    public String toString() {
        return "Movie [title=" + title + ", releaseYear=" + releaseYear + ", free=" + free + "]";
    }

}
