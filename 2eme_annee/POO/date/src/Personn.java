
public class Personn {
    String firstName, lastName;
    Date dateOfBirth;

    public Personn(String firstName, String lastName, int day, int month, int year) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = new Date(day, month, year);
    }

    public int getAge() {
        Date today = new Date();
        int day = today.getDay();
        int month = today.getMonth();
        int year = today.getYear();
        int age = year - this.dateOfBirth.getYear();
        if (month < this.dateOfBirth.getMonth()
                || month == this.dateOfBirth.getMonth() && this.dateOfBirth.getDay() < day) {
            age--;
        }
        return age;
    }

    @Override
    public String toString() {
        return "first name: " + this.firstName + " | last name: " + this.lastName + " | age: " + this.getAge()
                + " year(s)";
    }

}
