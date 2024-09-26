import javax.management.RuntimeErrorException;
import java.time.LocalDate;

public class Date {
    private int day, month, year;

    final static int[] DAYS_IN_MONTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    public Date(int day, int month, int year) {
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    public Date() {
        this(LocalDate.now().getDayOfMonth(), 
             LocalDate.now().getMonthValue(),
             LocalDate.now().getYear());
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        if (day == 0 || day > daysInMonth()) {
            throw new RuntimeErrorException(null, "Invalid day number");
        }
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        if (month > 0 && month < 13) {
            this.month = month;
        } else {
            throw new RuntimeErrorException(null, "Invalid month number");
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int daysInMonth() {
        return Date.daysInMonth(month);
    }

    public void increment() {
        if (isEndOfMonth()) {
            day = 1;
            month = month == 12 ? 1 : ++month;
            year = month == 1 ? ++year : year;
        } else {
            day++;
        }
    }

    public int dayOfYear() {
        int res = day;
        for (int i = 0; i < month - 1; i++) {
            res += DAYS_IN_MONTH[i];
        }
        if (isBisextile() && month >= 3) {
            ++res;
        }
        return res;
    }

    public int numberDayInMonth() {

        return 0;
    }

    public int dayOfWeek() {
        /*
         * int k = year % 100;
         * int j = year/100;
         * int h = (day + (((month + 1) * 13)/5) + k + ((k) / 4) +((j)/4) + (5 * j)) %
         * 7;
         * return h ;
         * 
         */
        int q = this.day;
        int m = this.month;
        int y = this.year;
        if (m <= 2) {
            m = m + 12;
            --y;
        }
        int j = y / 100;
        int k = y % 100;
        int h = (q + (m + 1) * 13 / 5 + k + k / 4 + j / 4 + 5 * j) % 7;
        return (h + 5) % 7;
    }

    public void prettyPrint() {
        System.out.println(this);
    }

    public static boolean isBisextile(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static int daysInMonth(int month) {
        return DAYS_IN_MONTH[month - 1];

    }

    public boolean isBisextile() {
        return Date.isBisextile(this.year);
    }

    public boolean isEndOfMonth() {
        return (day == daysInMonth() || (isBisextile() && month == 2 && day == 29));
    }

    @Override
    public String toString() {
        String[] days = { "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche" };
        String[] months = { "janvier", "fevrier", "mars", "avril", "mai", "juin", "juillet",
                "aout", "septembre", "octobre", "novembre", "decembre" };
        return "Date [ " + days[dayOfWeek()] + " " + day + " " + months[month - 1] + " " + year + "]";
    }

}