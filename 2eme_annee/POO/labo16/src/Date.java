
import java.time.LocalDate;
import java.util.Scanner;

public class Date implements Comparable<Date> {

    private static final int[] DAYS_IN_MONTHS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private static final String[] MONTH_IN_FRENCH = {
            "Janvier", "Février", "Mars",
            "Avril", "Mai", "Juin", "Juillet", "Aout",
            "Septembre", "Octobre", "Novembre", "Décembre"
    };
    private static final String[] DAY_IN_FRENCH = {
            "Lundi", "Mardi", "Mercredi",
            "Jeudi", "Vendredi", "Samedi",
            "Dimanche"
    };

    private int day;
    private int month;
    private int year;

    static private boolean validDate(int day, int month, int year) {
        return day >= 1 && day <= daysInMonth(month, year)
                && month >= 1 && month <= 12;
    }

    public Date(int day, int month, int year) {
        if (validDate(day, month, year)) {
            this.year = year;
            this.month = month;
            this.day = day;
        } else {
            throw new RuntimeException("bad date");
        }
    }

    public Date() {
        this(LocalDate.now().getDayOfMonth(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear());

    }

    public Date(Date d) {
        this(d.getDay(), d.getMonth(), d.getYear());
    }

    public static Date input() {
        Scanner scanner = new Scanner(System.in);

        Date date = null;
        boolean ok = false;

        do {
            System.out.print("Entrez 3 entiers séparés par des espaces (JJ MM YYYY) : ");

            // on suppose, sans le vérifier que l'utilisateur a entré 3 entiers.
            int day = scanner.nextInt();
            int month = scanner.nextInt();
            int year = scanner.nextInt();

            try {
                date = new Date(day, month, year);
                ok = true;
            } catch (RuntimeException exception) {
                System.out.println("Date invalide. Rééssayez.");
            }

        } while (!ok);

        return date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        if (validDate(day, this.month, this.year)) {
            this.day = day;
        } else {
            throw new RuntimeException("bad day");
        }
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        if (validDate(this.day, month, this.year)) {
            this.month = month;
        } else {
            throw new RuntimeException("bad month");
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (validDate(this.day, this.month, year)) {
            this.year = year;
        } else {
            throw new RuntimeException("bad year");
        }
    }

    public void increment() {
        if (lastDayOfMonth()) {
            setDay(1);
            if (getMonth() == 12) {
                setMonth(1);
                setYear(getYear() + 1);
            } else {
                setMonth(getMonth() + 1);
            }
        } else {
            setDay(getDay() + 1);
        }
    }

    private static int daysInMonth(int aMonth, int aYear) {
        return DAYS_IN_MONTHS[aMonth - 1] + (isLeapYear(aYear) && aMonth == 2 ? 1 : 0);
    }

    private int daysInMonth() {
        return daysInMonth(getMonth(), getYear());
    }

    private boolean lastDayOfMonth() {
        return getDay() == daysInMonth();
    }

    public static boolean isLeapYear(int aYear) {
        return aYear % 400 == 0 || (aYear % 100 != 0 && aYear % 4 == 0);
    }

    private boolean isLeapYear() {
        return isLeapYear(getYear());
    }

    public int dayOfYear() {
        int dayOfYear = this.getDay();
        for (int i = 1; i < getMonth(); i++) {
            dayOfYear += daysInMonth(i, getYear());
        }
        return dayOfYear;
    }

    public int dayOfWeek() {
        int m = this.getMonth(); // local copies because
        int y = this.getYear(); // month and year can be modified
        if (m == 1 || m == 2) {
            m += 12;
            y--;
        }

        int century = y / 100;
        int yearOfCentury = y % 100;
        int dayOfWeek = (getDay()
                + (((m + 1) * 26) / 10)
                + yearOfCentury
                + (yearOfCentury / 4)
                + (century / 4)
                + 5 * century) % 7;

        return (dayOfWeek + 5) % 7;
    }

    @Override
    public String toString() {
        return DAY_IN_FRENCH[dayOfWeek()] + " " + getDay() + " "
                + MONTH_IN_FRENCH[getMonth() - 1] + " " + getYear();
        // + " le " + dayOfYear() + "-ième jour de l'année";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Date) {
            Date d = (Date) o;
            return day == d.day && month == d.month && year == d.year;
        }
        return false;
    }

    private int toUSInteger() {
        return 10000 * year + 100 * month + day;
    }

    @Override
    public int compareTo(Date that) {
        return toUSInteger() - that.toUSInteger();
    }

    // /**
    // * alternative: pretending each month has 31 days and
    // * each year has 31 * 12 = 372 days
    // *
    // */
    // public int compareTo(Date that) {
    // return 372 * year + 31 * month + day
    // - 372 * that.year - 31 * that.month - that.day;
    // }
}
