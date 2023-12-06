import seqint.SeqInt;
import seqint.SeqIntIterator;

public class Main {

    public static boolean isEmpty(SeqInt seq) {
        return !seq.iterator().hasNext();
    }

    public static int length(SeqInt seq) {
        SeqIntIterator iter = seq.iterator();
        int count = 0;
        while (iter.hasNext()) {
            iter.next();
            count++;
        }
        return count;
    }

    public static int sum(SeqInt seq) {
        SeqIntIterator iter = seq.iterator();
        int sum = 0;
        while (iter.hasNext()) {
            sum += iter.next();
        }
        return sum;
    }

    public static int count_n(SeqInt seq, int n) {
        int count = 0;
        SeqIntIterator iter = seq.iterator();
        while (iter.hasNext()) {
            int current = iter.next();
            if (current == n) {
                count++;
            }
        }
        return count;
    }

    public static double average(SeqInt seq) {
        return !isEmpty(seq) ? (double) sum(seq) / length(seq) : 0;
    }

    public static int max(SeqInt seq) {
        SeqIntIterator iter = seq.iterator();
        int max = iter.next();
        while (iter.hasNext()) {
            int next = iter.next();
            if (next > max) {
                max = next;
            }
        }
        return max;
    }

    public static int last_pos_n(SeqInt seq, int n) {
        int pos = 0;
        SeqIntIterator iter = seq.iterator();
        for (int i = 0; iter.hasNext(); i++) {
            if (iter.next() == n) {
                pos = i;
            }
        }
        return pos > 0 ? pos : -1;
    }

    public static int first_pos_n(SeqInt seq, int n) {
        int pos = 0;
        SeqIntIterator iter = seq.iterator();
        boolean found = false;
        for (int i = 0; iter.hasNext() && !found; i++) {
            if (iter.next() == n) {
                pos = i;
                found = true;
            }
        }
        return found ? pos : -1;
    }

    public static boolean is_asc(SeqInt seq) {
        SeqIntIterator iter = seq.iterator();
        boolean err = false;
        int current = iter.next();
        while (iter.hasNext() && !err) {
            int next = iter.next();
            if (next < current) {
                err = true;
            }
            current = next;
        }
        return err ? false : true;
    }

    public static void main(String[] args) throws Exception {
        SeqInt seq = new SeqInt(1, 2, 3, 4, 8, 9, 5);
        System.out.println();
        try {
            System.out.println(is_asc(seq) ? "OK" : "PAS OK");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println();

    }
}
