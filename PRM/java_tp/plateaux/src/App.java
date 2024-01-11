import seqint.*;

public class App {

    // Écrire une fonction qui calcule le nombre de plateaux d’une séquence.
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
        // Écrire une fonction qui calcule le nombre de plateaux qui précèdent la
        // première occurrence du maximum.
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
        // Écrire une fonction qui calcule le nombre de plateaux qui suivent la dernière
        // occurrence du maximum.
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

        // Écrire une fonction qui calcule le nombre de plateaux de longueur supérieure
        // ou égale à 3.

        // curr - 1 1 2 2 2 2 4 4 5 5 5 5 5 8 9 9 4 4 4 4 4 4 --- curr = it.next()
        // prev --- 1 1 2 2 2 2 4 4 5 5 5 5 5 8 9 9 4 4 4 4 4 --- prev = curr
        // res -- 0 0 0 0 1 1 1 1 1 1 2 2 2 2 2 2 2 2 3 3 3 3 --- if (lgPlat==3)res++
        // lgPlat 1 2 1 2 3 4 1 2 1 2 3 4 5 1 1 2 1 2 3 4 5 6 --- if(curr==prev)lgPlat++
        // else lgPlat = 0

        // INIT
        // res = 0;
        // curr = it.next;
        // prev = curr
        // lgPlat = 1

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

    public static int ex5(SeqInt seq) {

        return 0;
    }

    public static void main(String[] args) throws Exception {
        SeqInt seq = new SeqInt(1, 1, 2, 2, 2, 2, 4, 4, 5, 5, 5, 5, 5, 8, 9, 9, 4, 4, 4, 4, 4, 4);
        System.out.println(ex4(seq));
    }
}