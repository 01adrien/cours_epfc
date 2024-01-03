import seqint.*;

import pack.SeqUtils;

public class App {

    public static int ex1(SeqInt seq) {
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

    public static int ex2(SeqInt seq) {
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

    public static int ex3(SeqInt seq) {
        int n = 0;
        int current;
        SeqIntIterator it = seq.iterator();
        SeqUtils utils = new SeqUtils();
        if (it.hasNext()) {
            current = it.next();
            int posMax = utils.lastPosMax(seq);
            for (int i = 0; i < posMax; i++) {
                current = it.next();
            }
            while (it.hasNext()) {
                int next = it.next();
                if (next != current) {
                    n++;
                }
                current = next;
            }
        }
        return n;
    }

    public static void main(String[] args) throws Exception {
        SeqInt seq = new SeqInt(12, 5);
        System.out.println(ex3(seq));
    }
}
