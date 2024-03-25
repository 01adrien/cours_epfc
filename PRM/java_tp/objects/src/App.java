import java.util.Scanner;

import eu.epfc.prm2.Array;

public class App {

    public static int strCompare(String str1, String str2) {
        /*
         * 
         * str1 == str2 : The method returns 0.
         * str1 > str2 : The method returns a positive value.
         * str1 < str2 : The method returns a negative value.
         * 
         */
        int j = Math.min(str1.length(), str2.length());
        int s1, s2;
        for (int i = 0; i < j; i++) {
            s1 = Character.toUpperCase(str1.charAt(i));
            s2 = Character.toUpperCase(str2.charAt(i));
            if (s1 != s2) {
                return s1 - s2;
            }
        }
        return str1.length() - str2.length();
    }

    public static Person createPerson(Date ddn, String fname, String lname) {
        Person p = new Person();
        p.ddn = ddn;
        p.lname = lname;
        p.fname = fname;
        return p;
    }

    public static Date createDate(int day, int month, int year) {
        return new Date(day, month, year);
    }

    public static int compareAge(Person p1, Person p2) {
        return p1.ddn.compare(p2.ddn);
    }

    public static int compareNames(Person p1, Person p2) {
        if (p1.lname.equals(p2.lname)) {
            return strCompare(p1.fname, p2.fname);
        }
        return strCompare(p1.lname, p2.lname);
    }

    public static void printPersonArray(Array<Person> arr) {
        for (Person p : arr) {
            System.out.println(p);
        }
    }

    public static void printIntegerArray(Array<Integer> arr) {
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
    }

    public static void swapPerson(Array<Person> arr, int i, int j) {
        Person tmp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, tmp);
    }

    // TRIS
    // tri par selection EX 1
    public static void alphaSort(Array<Person> arr) {
        for (int i = 0; i < arr.size(); i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.size(); j++) {
                Person p1 = arr.get(minIndex);
                Person p2 = arr.get(j);
                if (compareNames(p1, p2) > 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swapPerson(arr, i, minIndex);
            }
        }
    }

    // tri par insertion EX 1
    public static void ageSort(Array<Person> arr) {
        for (int i = 1; i < arr.size(); i++) {
            int j = i;
            Person p1 = arr.get(i);
            while (j > 0 && (p1.ddn.compare(arr.get(j - 1).ddn)) < 0) {
                arr.set(j, arr.get(j - 1));
                j--;
            }
            arr.set(j, p1);
        }
    }

    public static int inputNum(int i) {
        Scanner s = new Scanner(System.in);
        System.out.println("entrez num " + i + " :");
        return s.nextInt();
    }

    public static void insertSort(Array<Integer> arr, int n) {
        arr.add(n);
        if (arr.size() > 0) {
            int i = arr.size() - 1;
            int val = arr.get(i);
            int j = i;
            while (j > 0 && arr.get(j - 1) > val) {
                arr.set(j, arr.get(j - 1));
                j--;
            }
            arr.set(j, val);
        }
    }

    public static void main(String[] args) throws Exception {
        Person p1 = createPerson(createDate(15, 5, 1999), "Bob", "Z");
        Person p2 = createPerson(createDate(14, 5, 1997), "Henry", "D");
        Person p3 = createPerson(createDate(15, 5, 1995), "Bob", "E");
        Person p4 = createPerson(createDate(14, 5, 1924), "Henry", "C");
        Person p6 = createPerson(createDate(14, 5, 2020), "James", "G");
        Person p5 = createPerson(createDate(15, 5, 1935), "Bob", "C");
        Array<Person> arrP = new Array<Person>(p1, p2, p3, p4, p5, p6);
        // printPersonArray(arrP);
        // ageSort(arrP);
        // System.out.println();
        // printPersonArray(arrP);
        Array<Integer> arrI = new Array<Integer>();
        for (int i = 0; i < 5; i++) {
            insertSort(arrI, inputNum(i + 1));
        }
        // arrI.add(78);
        System.out.println(arrI);
        // printIntegerArray(arrI);
    }
}
