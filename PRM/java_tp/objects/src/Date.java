public class Date {
    public int day, month, year;

    @Override
    public String toString() {
        return this.day + "/" + this.month + "/" + this.year;
    }
}