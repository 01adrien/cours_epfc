package prm2_09_plateaux;

import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire une fonction qui calcule la longueur du plus long plateau de hauteur maximale.
Retourne la longueur dernier plus long plateau de haut = max. Zéro si s est vide.

e = élement courant
prec = élément précédent
lgPlatCourant = longueur du plateau courant
posCour = position courante
max = maximum courant
res = longueur du plus long plateau de hauteur maximale

prec             2 2 1 1 7 7 2 2 2 7 3 7 7   =>   prec = e;
-------------------------------------
e              2 2 1 1 7 7 2 2 2 7 3 7 7 1   =>   e = it.next();
-------------------------------------
lgPlatCourant  1 2 1 2 1 2 1 2 3 1 1 1 2 1   =>   if (e==prec) ++lgPlatCourant; else lgPlatCourant=1;
posCour        1 2 3 4 5 6 7 8 9 1 1 1 1 1   =>   ++posCour
                                 0 1 2 3 4
max            2 2 2 2 7 7 7 7 7 7 7 7 7 7   =>   if (e>max) max=e;
res            1 2 2 2 1 2 2 2 2 2 2 2 2 2   =>   if (e==max && res < lgPlatCourant || e>max) res=lgPlatCourant;

 */

public class Plateaux_ex21 {

    public static int lgPlusLongPlatHauteurMax(SeqInt s) {
        SeqIntIterator it = s.iterator();
        int res = 0;
        if (it.hasNext()) {
            int e = it.next();
            int max = e;
            int posCour = 1;
            int lgPlatCourant = 1;
            res = 1;
            while (it.hasNext()) {
                int prec = e;
                e = it.next();
                ++posCour;
                if (e == prec)
                    ++lgPlatCourant;
                else {
                    lgPlatCourant = 1;
                }
                if (e >= max) {
                    if (e > max || res <= lgPlatCourant) {
                        res = lgPlatCourant;
                    }
                    max = e;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("lgPlusLongPlatHauteurMax : 0 = " + lgPlusLongPlatHauteurMax(sVide));
        System.out.println();

        SeqInt s1 = new SeqInt(2, 2, 1, 2, 7, 7, 2, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s1);
        System.out.println("lgPlusLongPlatHauteurMax : 2 = " + lgPlusLongPlatHauteurMax(s1));
        System.out.println();

        SeqInt s2 = new SeqInt(2, 2, 2, 2, 1, 2, 7, 7, 7, 2, 2, 2, 7, 3, 3, 7, 7, 1);
        System.out.println(s2);
        System.out.println("lgPlusLongPlatHauteurMax : 3 = " + lgPlusLongPlatHauteurMax(s2));
        System.out.println();
    }
}
