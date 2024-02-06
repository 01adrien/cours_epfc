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

    public static int ex10(SeqInt seq) {
        SeqIntIterator it = seq.iterator();
        int curr, prev, res, max;
        res = 0;
        if (it.hasNext()) {
            curr = it.next();
            max = curr;
            res = 1;
            while (it.hasNext()) {
                prev = curr;
                curr = it.next();
                if (curr != prev && curr == max) {
                    res++;
                } else if (curr > max) {
                    max = curr;
                    res = 1;
                }
            }
        }
        return res;
    }

    public static int ex11(SeqInt seq) {
        SeqIntIterator it = seq.iterator();
        int curr, prev, res, max, lgPlat;
        res = 0;
        if (it.hasNext()) {
            curr = it.next();
            max = curr;
            lgPlat = 1;
            while (it.hasNext()) {
                prev = curr;
                curr = it.next();
                if (curr == prev) {
                    lgPlat++;
                } else {
                    lgPlat = 1;
                }
                if (curr == max && lgPlat == 2) {
                    res++;
                } else if (curr > max) {
                    max = curr;
                    res = 0;
                }
            }
        }
        return res;
    }

    public static int ex12(SeqInt seq, int lg) {
        SeqIntIterator it = seq.iterator();
        int curr, prev, res, max, lgPlat;
        res = 0;
        if (it.hasNext()) {
            curr = it.next();
            max = curr;
            lgPlat = 1;
            res = (lg == 1) ? 1 : res;
            while (it.hasNext()) {
                prev = curr;
                curr = it.next();
                if (curr == prev) {
                    lgPlat++;
                } else {
                    lgPlat = 1;
                }
                if (curr == max) {
                    if (lgPlat == lg) {
                        res++;
                    } else if (lgPlat == lg + 1) {
                        res--;
                    }
                } else if (curr > max) {
                    max = curr;
                    res = (lg > 1) ? 0 : 1;
                }
            }
        }
        return res;
    }

    public static int ex13(SeqInt seq) {
        SeqIntIterator it = seq.iterator();
        int curr, prev, res, max, i;
        res = -1;
        if (it.hasNext()) {
            curr = it.next();
            res = i = 0;
            max = curr;
            while (it.hasNext()) {
                prev = curr;
                curr = it.next();
                i++;
                if (curr == max && prev != curr) {
                    res = i;
                } else if (curr > max) {
                    max = curr;
                }
            }
        }
        return res;
    }

    public static int ex14(SeqInt seq) {

        SeqIntIterator it = seq.iterator();
        int curr, prev, res, max, length;
        res = 0;
        if (it.hasNext()) {
            curr = it.next();
            length = res = 1;
            max = curr;
            while (it.hasNext()) {
                prev = curr;
                curr = it.next();
                if (curr == prev) {
                    length++;
                } else {
                    length = 1;
                }
                if (curr == max) {
                    res = length;
                } else if (curr > max) {
                    max = curr;
                    res = 1;
                }
            }
        }
        return res;
    }

    public static SeqInt ex15(SeqInt seq) {
        SeqIntIterator it = seq.iterator();
        int curr, prev, pos, len, max, currLen, i;
        len = 0;
        pos = -1;
        if (it.hasNext()) {
            curr = it.next();
            currLen = len = 1;
            pos = i = 0;
            max = curr;
            while (it.hasNext()) {
                prev = curr;
                curr = it.next();
                i++;
                if (curr == prev) {
                    currLen++;
                } else {
                    currLen = 1;
                }
                if (curr == max) {
                    if (prev != curr) {
                        pos = i;
                    }
                    len = currLen;
                } else if (curr > max) {
                    max = curr;
                    len = 1;
                }
            }
        }
        System.out.println(pos + " " + len);
        return new SeqInt(pos, len);
    }

    public static int ex16(SeqInt seq, int h) {
        SeqIntIterator it = seq.iterator();
        int curr, prev, pos, len, i;
        pos = -1;
        if (it.hasNext()) {
            curr = it.next();
            i = 0;
            if (h == curr) {
                pos = i;
            }
            while (it.hasNext()) {

            }
        }

        return pos;
    }

    public static void main(String[] args) throws Exception {
        SeqInt seq = new SeqInt(2, 2, 5, 2, 4, 5, 5, 1, 7, 1, 7, 7, 2, 3, 3, 7, 7, 7, 4);
        SeqInt res = ex15(seq);

        // System.out.println(ex13(seq));

    }
}