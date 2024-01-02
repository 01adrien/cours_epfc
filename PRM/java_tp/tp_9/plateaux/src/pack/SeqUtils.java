package pack;

import seqint.*;

public class SeqUtils {

    public boolean isEmpty(SeqInt seq) {
        return !seq.iterator().hasNext();
    }

    public int length(SeqInt seq) {
        SeqIntIterator iter = seq.iterator();
        int count = 0;
        while (iter.hasNext()) {
            iter.next();
            count++;
        }
        return count;
    }

    public int sum(SeqInt seq) {
        SeqIntIterator iter = seq.iterator();
        int sum = 0;
        while (iter.hasNext()) {
            sum += iter.next();
        }
        return sum;
    }

    public int max(SeqInt seq) {
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

}