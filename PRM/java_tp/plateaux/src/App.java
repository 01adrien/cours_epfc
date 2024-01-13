import seqint.*;

public class App {

    public static int ex1(SeqInt seq) {
        SeqIntIterator it = seq.iterator();
        int res = 0;
        if (it.hasNext()) {
            int curr = it.next();
            res++;
            while (it.hasNext()) {
                int prec = curr;
                curr = it.next();
                if (prec != curr) {
                    res++;
                }
            }
        }
        return res;
    }

    public static int ex2(SeqInt seq) {
        SeqIntIterator it = seq.iterator();
        int res, nbrPlateaux, max, prec, curr;
        res = 0;
        nbrPlateaux = 0;
        if (it.hasNext()) {
            curr = it.next();
            nbrPlateaux = 1;
            max = curr;
            while (it.hasNext()) {
                prec = curr;
                curr = it.next();
                if (curr != prec) {
                    nbrPlateaux++;
                }
                if (curr > max) {
                    max = curr;
                    res = nbrPlateaux - 1;
                }
            }
        }
        System.out.println("plateaux => " + nbrPlateaux);
        return res;
    }

    public static int ex3(SeqInt seq) {
        SeqIntIterator it = seq.iterator();
        int res, max, prec, curr;
        res = 0;
        if (it.hasNext()) {
            curr = it.next();
            max = curr;
            while (it.hasNext()) {
                prec = curr;
                curr = it.next();
                if (curr > max) {
                    max = curr;
                }
                if (curr == max) {
                    res = 0;
                } else if (curr != prec) {
                    res++;
                }
            }
        }
        return res;
    }

    public static int ex4(SeqInt seq) {
        SeqIntIterator it = seq.iterator();
        int res, curr, prev, lgPlat;
        res = 0;
        if (it.hasNext()) {
            curr = it.next();
            lgPlat = 1;
            while (it.hasNext()) {
                prev = curr;
                curr = it.next();
                if (curr == prev) {
                    lgPlat++;
                } else {
                    lgPlat = 1;
                }
                if (lgPlat == 3) {
                    res++;
                }
            }
        }
        return res;
    }

    public static int ex5(SeqInt seq, int lg) {
        SeqIntIterator it = seq.iterator();
        int res, curr, prev, lgPlat;
        res = 0;
        if (it.hasNext()) {
            curr = it.next();
            lgPlat = 1;
            res = lg == lgPlat ? 1 : 0;
            while (it.hasNext()) {
                prev = curr;
                curr = it.next();
                if (curr == prev) {
                    lgPlat++;
                } else {
                    lgPlat = 1;
                }
                if (lgPlat == lg) {
                    res++;
                }
            }
        }
        return res;
    }

    public static int ex6(SeqInt seq) {
        SeqIntIterator it = seq.iterator();
        int res, curr, prev, lgPlat;
        res = 0;
        if (it.hasNext()) {
            curr = it.next();
            lgPlat = 1;
            while (it.hasNext()) {
                prev = curr;
                curr = it.next();
                if (curr == prev) {
                    lgPlat++;
                } else {
                    lgPlat = 1;
                }
                if (lgPlat == 3) {
                    res++;
                } else if (lgPlat == 4) {
                    res -= res != 0 ? 1 : 0;
                }
            }
        }
        return res;
    }

    public static int ex7(SeqInt seq) {
        SeqIntIterator it = seq.iterator();
        int res, curr, prev, lgPlat;
        res = 0;
        if (it.hasNext()) {
            curr = it.next();
            lgPlat = res = 1;
            while (it.hasNext()) {
                prev = curr;
                curr = it.next();
                if (curr == prev) {
                    lgPlat++;
                } else {
                    lgPlat = 1;
                }
                if (lgPlat == 1) {
                    res++;
                } else if (lgPlat == 2) {
                    res -= res != 0 ? 1 : 0;
                }
            }
        }
        return res;
    }

    public static int ex8(SeqInt seq) {
        // pareil que ex 6
        return 0;
    }

    public static int ex9(SeqInt seq, int lg1, int lg2) {
        SeqIntIterator it = seq.iterator();
        int res, curr, prev, lgPlat;
        res = 0;
        if (it.hasNext()) {
            curr = it.next();
            lgPlat = 1;
            boolean inc = false;
            if ((lg1 == lg2) && lg1 == 1) {
                res = 1;
            }
            while (it.hasNext()) {
                prev = curr;
                curr = it.next();
                if (curr == prev) {
                    lgPlat++;
                } else {
                    lgPlat = 1;
                    inc = false;
                }
                if ((lgPlat >= lg1 && lgPlat <= lg2) && !inc) {
                    res++;
                    inc = true;
                } else if (lgPlat > lg2 && inc) {
                    res -= res != 0 ? 1 : 0;
                    inc = false;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        SeqInt seq = new SeqInt(1, 1, 2, 3, 2, 2, 4, 1, 5, 5, 5, 5, 8, 9, 9, 4, 4, 4, 4, 4, 4);
        System.out.println(ex9(seq, 2, 4));

    }
}