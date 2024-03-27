public class Person {
    public String lname, fname;
    public Date ddn;

    public Person(String lname, String fname, Date ddn) {
        this.lname = lname;
        this.fname = fname;
        this.ddn = ddn;
    }

    public int compareTo(Person p) {
        if (!this.lname.equals(p.lname)) {
            return this.lname.toLowerCase().compareTo(p.lname.toLowerCase());
        }
        return this.fname.toLowerCase().compareTo(p.fname.toLowerCase());
    }

    @Override
    public String toString() {
        return this.fname + " " + this.lname + " " + this.ddn.toString();
    }
}