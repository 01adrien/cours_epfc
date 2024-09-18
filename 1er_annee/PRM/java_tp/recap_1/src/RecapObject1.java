import java.util.Random;
import eu.epfc.prm2.*;

public class RecapObject1 {

    public class MedalCountry {
        public int medal;
        public String country;

        public MedalCountry(int medal, String country) {
            this.medal = medal;
            this.country = country;
        }

        public int compareTo(MedalCountry m) {
            return this.medal - m.medal;
        }

        @Override
        public String toString() {
            return "Country " + this.country + "\n" + "Medal(s): " + this.medal;
        }

    }

    public int findMedalCountry(Array<MedalCountry> medals, String country) {
        boolean found = false;
        int i = 0;
        while (i < medals.size() && !found) {
            if (medals.get(i).country == country) {
                found = true;
            } else {
                i++;
            }
        }
        return found ? i : -1;
    }

    public void swap(Array<MedalCountry> medals, int a, int b) {
        MedalCountry temp = medals.get(a);
        medals.set(a, medals.get(b));
        medals.set(b, temp);
    }

    public void addMedal(Array<MedalCountry> medals, String country) {
        int pos = findMedalCountry(medals, country);
        if (pos != -1) {
            medals.get(pos).medal++;
            if (pos != 0) {
                while (pos > 0 && medals.get(pos - 1).compareTo(medals.get(pos)) < 0) {
                    swap(medals, pos, pos - 1);
                    pos--;
                }
            }
        } else {
            medals.add(new MedalCountry(1, country));
        }
    }

    public void run() {
        Array<String> countries = new Array<String>(
                "France", "Italie", "Bresil", "Danemark",
                "Belgique", "Espagne", "Maroc");
        Array<MedalCountry> medals = new Array<MedalCountry>();
        Random r = new Random();
        while (medals.size() != countries.size()) {
            addMedal(medals, countries.get(r.nextInt(countries.size())));
        }
        for (MedalCountry m : medals) {
            System.out.println(m + "\n");
        }
    }

}
