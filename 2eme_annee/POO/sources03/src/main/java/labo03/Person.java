package labo03;

@SuppressWarnings("rawtypes")
public class Person implements Comparable {

    private final String firstName;
    private final String lastName;
    private final Date dateOfBirth;

    public Person(String firstName, String lastName,
            int day, int month, int year) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = new Date(day, month, year);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " né le " + dateOfBirth
                + ", il a " + getAge() + " ans.";
    }

    public int getAge() {
        Date today = new Date();
        int age = today.getYear() - dateOfBirth.getYear();
        if (today.getMonth() < dateOfBirth.getMonth()
                || (today.getMonth() == dateOfBirth.getMonth()
                        && today.getDay() < dateOfBirth.getDay())) {
            age--;
        }
        return age;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Person) {
            Date now = new Date();
            Person p = (Person) o;
            Date d1 = this.dateOfBirth;
            Date d2 = p.dateOfBirth;
            int age1 = ((now.getYear() - d1.getYear() - 1) * 365) + d1.dayOfYear();
            int age2 = ((now.getYear() - d2.getYear() - 1) * 365) + d2.dayOfYear();
            return age2 - age1;
        }
        throw new RuntimeException("Cannot compare Person and " + o.getClass());
    }
}
