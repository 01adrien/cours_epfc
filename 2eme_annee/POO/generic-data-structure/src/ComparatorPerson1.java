import java.util.Comparator;

public class ComparatorPerson1 implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return o1.firstName.compareToIgnoreCase(o2.firstName);
    }

}
