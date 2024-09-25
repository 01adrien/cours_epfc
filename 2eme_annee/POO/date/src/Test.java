import javax.management.RuntimeErrorException;

public class Test {

    private  void ASSERT(boolean exp) {
        if (!exp) {
            throw new RuntimeErrorException(null, "ERROR");
        }
    }

    private  void testIncrementDay(){
        // normal day
        Date d = new Date(1, 5, 1995);
        d.increment();
        ASSERT(d.getDay() == 2 && d.getMonth() == 5 && d.getYear() == 1995); 
        // end of month
        Date d1 = new Date(31, 1, 1995);
        d1.increment();
        ASSERT(d1.getDay() == 1 && d1.getMonth() == 2 && d1.getYear() == 1995);
        // bissextile year
        Date d2 = new Date(28, 2, 2024);
        d2.increment();
        ASSERT(d2.getDay() == 1 && d2.getMonth() == 3 && d2.getYear() == 2024);
        // end of year
        Date d3 = new Date(30, 12, 1978);
        d3.increment();
        ASSERT(d3.getDay() == 1 && d3.getMonth() == 1 && d3.getYear() == 1979);

    }

    private  void testDayOfYear() {
        Date d = new Date(1, 1, 2022);
        ASSERT(d.dayOfYear() == 1);
        Date d1 = new Date(3, 3, 2022);
        ASSERT(d1.dayOfYear() == 62);
        // bisextile year
        Date d2 = new Date(15, 7, 2024);
        ASSERT(d2.dayOfYear() == 197);

    }

    private  void testDayOfWeek() {
        Date d1 = new Date(20, 9, 2020);
    }

    private  void testPersonn(){
        Personn p = new Personn("Bob", "Blue",  24, 9, 2020);
        ASSERT(p.getAge() == 3);
        Personn p1 = new Personn("Bob", "Blue",  22, 9, 2020);
        ASSERT(p1.getAge() == 4);
    }

    private void testCreateIllegalDate() {
        Date d = new Date(31, 7, 2024);
    }

    public void runAll() {
        //testIncrementDay();
        //testDayOfYear();
        //testDayOfWeek();
        //testPersonn();
        testCreateIllegalDate();
        System.out.println("All test passed.");
    }
}
