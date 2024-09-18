package prm2_09_plateaux;

import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire une fonction qui calcule la longueur du dernier plus long plateau de hauteur H.
Retourne la longueur dernier plus long plateau de haut = H. Zéro si s est vide.

e = élement courant
prec = élément précédent
max = maximum courant
posCour = position courante
lgPlatCourant = longueur du plateau courant
posPlatCourant = position du plateau courant

prec             2 2 1 1 7 7 2 2 2 7 3 7 7   =>   prec = e;
-------------------------------------
e              2 2 1 1 7 7 2 2 2 7 3 7 7 1   =>   e = it.next();
-------------------------------------
lgPlatCourant  1 2 1 2 1 2 1 2 3 1 1 1 2 1   =>   if (e==prec) ++lgPlatCourant; else lgPlatCourant=1;
res (h=7)      0 0 0 0 1 2 2 2 2 2 2 2 2 2  =>   if (e==h && res < lgPlatCourant) res=lgPlatCourant;
res (h=2)      1 2 2 2 2 2 2 2 3 3 3 3 3 3
res (h=1)      0 0 1 2 2 2 2 2 2 2 2 2 2 2
 */
public class Plateaux_ex19 {

    public static int lgPlusLongPlatHauteurH(SeqInt s, int h) {
        SeqIntIterator it = s.iterator();
        int res = 0;
        if (it.hasNext()) {
            int e = it.next();
            int lgPlatCourant = 1;
            if (e == h)
                res = 1;
            while (it.hasNext()) {
                int prec = e;
                e = it.next();
                if (e == prec)
                    ++lgPlatCourant;
                else {
                    lgPlatCourant = 1;
                }
                if (e == h) {
                    if (res < lgPlatCourant) {
                        res = lgPlatCourant;
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("lgPlusLongPlatHauteurH : 0 = " + lgPlusLongPlatHauteurH(sVide, 42));
        System.out.println();

        SeqInt s1 = new SeqInt(2, 2, 2, 1, 1, 7, 7, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s1);
        System.out.println("lgPlusLongPlatHauteurH (h=7) : 2 = " + lgPlusLongPlatHauteurH(s1, 7));
        System.out.println();

        SeqInt s2 = new SeqInt(2, 2, 2, 1, 1, 7, 7, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s2);
        System.out.println("lgPlusLongPlatHauteurH (h=2) : 3 = " + lgPlusLongPlatHauteurH(s2, 2));
        System.out.println();

        SeqInt s3 = new SeqInt(2, 2, 2, 1, 1, 7, 7, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s3);
        System.out.println("lgPlusLongPlatHauteurH (h=1) : 2 = " + lgPlusLongPlatHauteurH(s3, 1));
        System.out.println();

        SeqInt s4 = new SeqInt(2, 2, 2, 1, 1, 7, 7, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s4);
        System.out.println("lgPlusLongPlatHauteurH (h=9) : 0 = " + lgPlusLongPlatHauteurH(s4, 9));
        System.out.println();
    }
}
