import seqint.*;

import pack.SeqUtils;

public class App {

    public static int plateaux_nbr(SeqInt seq) {
        int n = 0;
        SeqIntIterator it = seq.iterator();
        if (it.hasNext()) {
            n++;
            int current = it.next();
            while (it.hasNext()) {
                int next = it.next();
                if (current != next) {
                    n++;
                }
                current = next;
            }
        }
        return n;
    }

    public static int plateaux_nbr_before_first_max(SeqInt seq) {
        int n = 0;
        SeqUtils utils = new SeqUtils();
        SeqIntIterator it = seq.iterator();
        if (it.hasNext()) {
            int max = utils.max(seq);
            int current = it.next();
            while (it.hasNext() && current != max) {
                int next = it.next();
                if (current != next) {
                    n++;
                }
                current = next;
            }

        }
        return n;
    }

    public static void main(String[] args) throws Exception {
        SeqInt seq = new SeqInt(5, 5, 4, 12);
        System.out.println(plateaux_nbr_before_first_max(seq));
    }
}
