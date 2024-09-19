public class Date {
    int day, month, year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
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

    public int dayOfWeek() {
        int k = year % 100;
        int j = year/100;
        return (day + (((month + 1) * 13)/5) + k + ((k) / 4) +((j)/4) + (5 * j)) % 7;
    } 

    public void prettyPrint(){
        System.out.println(this);
    }

    public boolean isBisextile() {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0) ;
    }

    public boolean isEndOfMonth() {
        return (day == 31 && month % 2 == 1) 
                || (day == 30 && month % 2 == 0) 
                || (isBisextile() && day == 28 && month == 2);
    }

    @Override
    public String toString() {
        String[] days = {"lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche"};
        String[] months = {"janvier", "fevrier", "mars", "avril", "mai", "juin", "juillet", 
                            "aout", "septembre", "octobre", "novembre", "decembre"};
        return "Date [day=" + days[dayOfWeek()] + ", month=" + months[month] + ", year=" + year + "]";
    }

    
}   