import eu.epfc.prm2.Array;

public class App {

    public static int strCompare(String str1, String str2) {
        int j = Math.min(str1.length(), str2.length());
        int s1, s2;
        for (int i = 0; i < j; i++) {
            s1 = Character.toUpperCase(str1.charAt(i));
            s2 = Character.toUpperCase(str2.charAt(i));
            if (s1 != s2) {
                return s2 - s1;
            }
        }
        return str2.length() - str1.length();
    }

    public static Person createPerson(Date ddn, String fname, String lname) {
        Person p = new Person();
        p.ddn = ddn;
        p.lname = lname;
        p.fname = fname;
        return p;
    }

    public static Date createDate(int day, int month, int year) {
        Date d = new Date();
        d.day = day;
        d.month = month;
        d.year = year;
        return d;
    }

    public static int compareDate(Date d1, Date d2) {
        int diff = 0;
        if (d1.year != d2.year) {
            diff = d2.year - d1.year;
        } else if (d1.month != d2.month) {
            diff = d2.month - d1.month;
        } else if (d1.day != d2.day) {
            diff = d2.day - d1.day;
        }
        return diff;
    }

    public static int compareAge(Person p1, Person p2) {
        return compareDate(p1.ddn, p2.ddn);
    }

    public static int compareNames(Person p1, Person p2) {
        if (p1.lname.equals(p2.lname)) {
            return strCompare(p1.fname, p2.fname);
        }
        return strCompare(p1.lname, p2.lname);
    }

    public static void main(String[] args) throws Exception {
        // Bob
        Person p1 = createPerson(createDate(15, 5, 1999), "Bob", "Martin");
        // Henry
        Person p2 = createPerson(createDate(14, 5, 1997), "Henry", "Dupont");
        int compAge = compareAge(p1, p2);
        if (compAge > 0) {
            System.out.println(p1 + " plus grand que " + p2);
        } else if (compAge < 0) {
            System.out.println(p1 + " plus petit que " + p2);
        } else {
            System.out.println(p1 + " a le meme age que " + p2);
        }
        Array<Person> arr = new Array<Person>();

    }
}
