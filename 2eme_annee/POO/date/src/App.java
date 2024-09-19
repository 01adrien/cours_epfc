import javax.management.RuntimeErrorException;

public class App {

    private static void ASSERT(boolean exp) {
        if (!exp) {
            throw new RuntimeErrorException(null, "ERROR");
        }
    }

    public static void testIncrementDay(){
        // normal day
        Date d = new Date(1, 5, 1995);
        d.increment();
        ASSERT(d.day == 2 && d.month == 5 && d.year == 1995); 
        // end of month
        Date d1 = new Date(31, 1, 1995);
        d1.increment();
        ASSERT(d1.day == 1 && d1.month == 2 && d1.year == 1995);
        // bissextile year
        Date d2 = new Date(28, 2, 2024);
        d2.increment();
        ASSERT(d2.day == 1 && d2.month == 3 && d2.year == 2024);
        // end of year
        Date d3 = new Date(30, 12, 1978);
        d3.increment();
        ASSERT(d3.day == 1 && d3.month == 1 && d3.year == 1979);

    }

    public static void testDayOfYear() {
        Date d = new Date(1, 1, 2022);
        ASSERT(d.dayOfYear() == 1);
        Date d1 = new Date(3, 3, 2022);
        ASSERT(d1.dayOfYear() == 62);
        // bisextile year
        Date d2 = new Date(15, 7, 2024);
        ASSERT(d2.dayOfYear() == 197);

    }

    public static void testDayOfWeek() {
        
    }
    
    public static void main(String[] args) throws Exception {
        App.testIncrementDay();
        App.testDayOfYear();

    }
}
