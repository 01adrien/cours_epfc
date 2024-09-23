import javax.management.RuntimeErrorException;

public class Date {
    private int day, month, year;

    public Date(int day, int month, int year) {
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        // System.out.println(day);
        // System.out.println(month);
        // System.out.println(year);
        if (day == 0 
            || (day > 31 && month % 2 == 1 ) 
            || (day > 30 && month % 2 == 0 ) 
            || (day > 28 && month == 2 && !Date.isBisextile(year)) 
            || (day > 29 && Date.isBisextile(year) && month == 2)) {
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

    public void increment() {
        if (isEndOfMonth()) { 
            day = 1;
            month = month == 12 ? 1 : ++month;
            year = month == 1? ++year: year ;            
        } 
        else {
            day++;
        }
    }

    public int dayOfYear() {
        int[] months = {31, 28, 31, 30, 31, 30, 31, 30, 31, 30, 31, 30};
        int res = day;
        for (int i = 0; i < month - 1; i++) {
            res += months[i];
        }
        if (isBisextile() && month >= 3) {
            ++res;
        }
        return  res ;
    } 

    public int numberDayInMonth(){

        return 0;
    }

    public int dayOfWeek() {
        /*
        int k = year % 100;
        int j = year/100;
        int h = (day + (((month + 1) * 13)/5) + k + ((k) / 4) +((j)/4) + (5 * j)) % 7;
        return h ;
         * 
         */
        int q = this.day;
        int m = this.month;
        int y = this.year;
        if(m <= 2) {
            m = m + 12;
            --y;
        }
        int j = y / 100;
        int k = y % 100;
        int h = (q + (m+1)*13/5 + k + k/4 + j/4 + 5*j) % 7;
        return (h + 5) % 7;
    } 

    public void prettyPrint(){
        System.out.println(this);
    }

    public static boolean isBisextile(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public boolean isBisextile() {
        return Date.isBisextile(this.year) ;
    }

    public boolean isEndOfMonth() {
        return (day == 31 && month % 2 == 1) 
                || (day == 30 && month % 2 == 0) 
                || (isBisextile() && day == 28 && month == 2);
    }

    @Override
    public String toString() {
        String[] days = { "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche"};
        String[] months = {"janvier", "fevrier", "mars", "avril", "mai", "juin", "juillet", 
                            "aout", "septembre", "octobre", "novembre", "decembre"};
        System.out.println(dayOfWeek());
        return "Date [ " + days[dayOfWeek()] + " " + day + " " + months[month - 1] + " " + year + "]";
    }

    
}   