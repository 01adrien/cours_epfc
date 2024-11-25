package ex02;

import java.util.Objects;

public class Person implements Comparable<Person> {

    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public Person(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Person(Person p) {
        this(p.firstName, p.lastName,
                new Date(
                        p.getDateOfBirth().getDay(),
                        p.getDateOfBirth().getMonth(),
                        p.getDateOfBirth().getYear()));
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " né le " + dateOfBirth + ", il a " + getAge() + " ans.";
    }

    public int getAge() {
        return getAgeAt(new Date());
    }

    public int getAgeAt(Date d) {
        int age = d.getYear() - dateOfBirth.getYear();
        if (d.getMonth() < dateOfBirth.getMonth()
                || (d.getMonth() == dateOfBirth.getMonth() && d.getDay() < dateOfBirth.getDay())) {
            age--;
        }
        return age;
    }

    @Override
    public int compareTo(Person p) {
        return -this.dateOfBirth.compareTo(p.dateOfBirth);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Person) {
            Person p = (Person) o;
            return this.firstName.equals(p.firstName) && this.lastName.equals(p.lastName)
                    && this.dateOfBirth.equals(p.dateOfBirth);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.firstName);
        hash = 47 * hash + Objects.hashCode(this.lastName);
        hash = 47 * hash + Objects.hashCode(this.dateOfBirth);
        return hash;
    }
}
