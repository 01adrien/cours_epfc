import java.util.Comparator;

public class ComparatorPerson3 implements Comparator<Person> {

    public int compareFname(String s1, String f2) {
        return s1.compareToIgnoreCase(f2);
    }

    public int compareLname(String s1, String f2) {
        return s1.compareToIgnoreCase(f2);
    }

    public int compareAge(Integer i1, Integer i2) {
        return i1.compareTo(i2);
    }

    @Override
    public int compare(Person o1, Person o2) {
        if (compareLname(o1.lastName, o2.lastName) != 0) {
            return compareLname(o1.lastName, o2.lastName);
        }
        if (compareFname(o1.firstName, o2.firstName) != 0) {
            return compareFname(o1.firstName, o2.firstName);
        }
        return compareAge(o1.age, o2.age);
    }

}
