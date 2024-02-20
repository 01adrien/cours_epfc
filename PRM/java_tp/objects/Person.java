public class Person {
    public String lname, fname;
    public Date ddn;

    @Override
    public String toString() {
        return this.fname + " " + this.lname + " " + this.ddn.toString();
    }
}