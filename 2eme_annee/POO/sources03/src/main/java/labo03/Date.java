package labo03;

import java.time.LocalDate;

@SuppressWarnings("rawtypes")
public class Date implements Comparable {

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

    public Date(int day, int month, int year) {
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    /*
     * Construit la date du jour.
     * 
     * Remarque : comme on appelle 3x LocalDate.now(), un bug potentiel pourrait
     * apparaitre par exemple un 31/12 à 23h59:59,9999.
     * Dans ce cas, le dernier appel pourrait avoir lieu l'année suivante.
     * 
     * Le but de cet exercice est d'insister sur l'obligation d'utiliser this(...)
     * uniquement à la première ligne d'un constructeur.
     * 
     * Comme nous pouvons avoir confiance en la librairie Java, ce constructeur
     * pourrait s'écrire sans appeler notre autre constructeur.
     */
    public Date() {
        this(LocalDate.now().getDayOfMonth(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear());

    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        if (day >= 1 && day <= this.daysInMonth()) {
            this.day = day;
        } else {
            throw new RuntimeException("incorrect day");
        }
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        if (month >= 1 && month <= 12 && !(day > daysInMonth(month, year))) {
            this.month = month;
        } else {
            throw new RuntimeException("incorrect month");
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {

        if (isLeapYear() && lastDayOfMonth() && !isLeapYear(year)) {
            throw new RuntimeException("incorrect year");
        }

        this.year = year;
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
        if (day != 0 && month != 0) { // !!! ATTENTION !!!
            return getDay() == daysInMonth();
        }
        return false;
    }

    public static boolean isLeapYear(int aYear) {
        return aYear % 400 == 0 || (aYear % 100 != 0 && aYear % 4 == 0);
    }

    public static boolean isValidDate(int day, int month, int year) {
        return day > 0 && month > 0 && month < 13 && day <= daysInMonth(month, year);

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
                + MONTH_IN_FRENCH[getMonth() - 1] + " " + getYear()
                + " le " + dayOfYear() + "-ième jour de l'année";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + day;
        result = prime * result + month;
        result = prime * result + year;
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Date) {
            Date o = (Date)other;
            return o.day == day && o.month == month && o.year == year;
        }
        return false;
    }


    @Override
    public int compareTo(Object o) {
        if (o instanceof Date) {
            Date d = (Date)o;
            return ((day * 10) + (month * 100) + (year * 1000)) 
                    - ((d.day * 10)  + (d.month * 100) + (d.year * 1000));
        }
        throw new RuntimeException("Cannot compare Date and " + o.getClass());
    }

}
