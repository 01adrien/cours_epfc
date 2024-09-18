import seqint.*;

class Q2 {

    public void run() {
        SeqInt seq = new SeqInt(8, 8, 8, 5, 5, 5, 6);
        SeqIntIterator it = seq.iterator();
        int res, curr, prev;
        boolean isHtP, isLgI, firstIter;
        res = 0;
        if (it.hasNext()) {
            curr = it.next();
            prev = curr;
            isHtP = curr % 2 == 0;
            firstIter = isLgI = true;
            res = isHtP && isLgI ? 1 : 0;
            while (it.hasNext()) {
                prev = curr;
                System.out.println(curr);
                curr = it.next();
                if (firstIter && (prev == curr)) {
                    res--;
                    firstIter = false;
                }
                if (curr != prev) {
                    if (isHtP && isLgI) {
                        res++;
                    }
                    isLgI = true;
                } else {
                    isLgI = !isLgI;
                }
                isHtP = curr % 2 == 0;
            }
        }
        System.out.println("res " + res);
    }


}
