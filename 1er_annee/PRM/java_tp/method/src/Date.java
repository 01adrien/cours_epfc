public class Date implements Comparable<Date> {
    public int day, month, year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return this.day + "/" + this.month + "/" + this.year;
    }

    @Override
    public int compareTo(Date d2) {
        int diff = 0;
        if (this.year != d2.year) {
            diff = this.year - d2.year;
        } else if (this.month != d2.month) {
            diff = this.month - d2.month;
        } else if (this.day != d2.day) {
            diff = this.day - d2.day;
        }
        return diff;
    }

}