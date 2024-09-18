import seqint.SeqInt;
import seqint.SeqIntIterator;

public class Seq {

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

    public static boolean has_step(SeqInt seq) {
        SeqIntIterator iter = seq.iterator();
        boolean res = false;
        if (!isEmpty(seq)) {
            int current = iter.next();
            while (iter.hasNext()) {
                int next = iter.next();
                if (next == current) {
                    res = true;
                }
                current = next;
            }
        }
        return res;
    }

    public static int last_pos_max(SeqInt seq) {
        SeqIntIterator iter = seq.iterator();
        int pos = -1;
        int current;
        if (!isEmpty(seq)) {
            int m = max(seq);
            for (int i = 0; iter.hasNext(); i++) {
                current = iter.next();
                if (current == m) {
                    pos = i;
                }
            }

        }
        return pos;
    }

    public static int first_pos_max(SeqInt seq) {
        SeqIntIterator iter = seq.iterator();
        int pos = -1;
        int current;
        boolean found = false;
        if (!isEmpty(seq)) {
            int m = max(seq);
            for (int i = 0; iter.hasNext() && !found; i++) {
                current = iter.next();
                if (current == m) {
                    pos = i;
                    found = true;
                }
            }

        }
        return pos;
    }

    public static int count_max(SeqInt seq) {
        SeqIntIterator iter = seq.iterator();
        int count = 0;
        int current;
        if (!isEmpty(seq)) {
            int m = max(seq);
            while (iter.hasNext()) {
                current = iter.next();
                if (current == m) {
                    count++;
                }
            }

        }
        return count;
    }

    public static int max_neg(SeqInt seq) {
        SeqIntIterator iter = seq.iterator();
        int m = 0;
        if (!isEmpty(seq)) {
            while (iter.hasNext()) {
                int current = iter.next();
                if (current < 0) {
                    if (m == 0) {
                        m = current;
                    } else if (current > m) {
                        m = current;
                    }
                }
            }
        }
        return m;

    }

    public static SeqInt min_max(SeqInt seq) {
        int min = 0;
        int max = 0;
        int current;
        SeqIntIterator iter = seq.iterator();
        if (!isEmpty(seq)) {
            max = min = iter.next();
            while (iter.hasNext()) {
                current = iter.next();
                if (current > max) {
                    max = current;
                }
                if (current < min) {
                    min = current;
                }
            }
        }

        return new SeqInt(max, min);
    }

    public static void main(String[] args) throws Exception {
        SeqInt seq = new SeqInt(1, 0);
        System.out.println();
        try {
            SeqInt mn = min_max(seq);
            SeqIntIterator iter = mn.iterator();
            System.out.println("MAX => " + iter.next());
            System.out.println("MIN => " + iter.next());
            System.out.println();
        } catch (Exception e) {
            System.out.println("Catch something");
            System.out.println(e.toString());
        }
        System.out.println();

    }
}
