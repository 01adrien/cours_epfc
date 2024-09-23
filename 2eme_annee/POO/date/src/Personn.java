import java.time.LocalDate;

public class Personn {
    String firstName, lastName;
    Date dateOfBirth;

    public Personn(String firstName, String lastName, int day, int month , int year) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = new Date(day, month, year);
    }

    public int getAge(){
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        int month = today.getMonthValue();
        int year = today.getYear();
        int age = year - this.dateOfBirth.getYear();
        if (month < this.dateOfBirth.getMonth() || month == this.dateOfBirth.getMonth() && day < this.dateOfBirth.getDay()) {
            age--;
        } 
        return age;        
    }

    @Override
    public String toString() {
        return "first name: " + this.firstName + " | last name: " + this.lastName + " | age: " + this.getAge() + " year(s)" ;
    }

    
    
}
