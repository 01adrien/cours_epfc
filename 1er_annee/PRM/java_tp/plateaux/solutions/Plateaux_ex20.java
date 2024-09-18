package prm2_09_plateaux;

import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire une fonction qui calcule la position du dernier plus long plateau de hauteur H.
Retourne la position dernier plus long plateau de haut = H. Zéro si s est vide.

e = élement courant
prec = élément précédent
max = maximum courant
posCour = position courante
lgPlatCourant = longueur du plateau courant
posPlatCourant = position du plateau courant
lgMax = longueur du plus long plateau de hauteur h

prec             2 2 1 1 7 7 2 2 2 7 3 7 7   =>   prec = e;
-------------------------------------
e              2 2 1 1 7 7 2 2 2 7 3 7 7 1   =>   e = it.next();
-------------------------------------
lgPlatCourant  1 2 1 2 1 2 1 2 3 1 1 1 2 1   =>   if (e==prec) ++lgPlatCourant; else lgPlatCourant=1;
posCour        1 2 3 4 5 6 7 8 9 1 1 1 1 1   =>   ++posCour
                                 0 1 2 3 4
posPlatCourant 1 1 3 3 5 5 7 7 7 1 1 1 1 1   =>   if (e!=prec) posPlatCourant=posCour;
                                 0 1 2 2 4
lgMax (h=7)    0 0 0 0 1 2 2 2 2 2 2 2 2 2  =>   if (e==h && lgMax < lgPlatCourant) lgMax=lgPlatCourant;
res (h=7)      0 0 0 0 5 5 5 5 5 5 5 5 1 1  =>   if (e==h && lgMax == lgPlatCourant) res=posPlatCourant;
                                       2 2
lgMax (h=2)    1 2 2 2 2 2 2 2 3 3 3 3 3 3
res (h=2)      1 1 1 1 1 1 1 7 7 7 7 7 7 7
lgMax (h=1)    0 0 1 2 2 2 2 2 2 2 2 2 2 2
res (h=1)      0 0 3 3 3 3 3 3 3 3 3 3 3 3
 */
public class Plateaux_ex20 {

    public static int posDernierPlusLongPlatHauteurH(SeqInt s, int h) {
        SeqIntIterator it = s.iterator();
        int res = 0;
        if (it.hasNext()) {
            int e = it.next();
            int posCour = 1;
            int lgPlatCourant = 1;
            int posPlatCourant = 1;
            int lgMax = 0;
            if (e == h) {
                lgMax = 1;
                res = 1;
            }
            while (it.hasNext()) {
                int prec = e;
                e = it.next();
                ++posCour;
                if (e == prec)
                    ++lgPlatCourant;
                else {
                    lgPlatCourant = 1;
                    posPlatCourant = posCour;
                }
                if (e == h) {
                    if (lgMax <= lgPlatCourant) {
                        lgMax = lgPlatCourant;
                        res = posPlatCourant;
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("posDernierPlusLongPlatHauteurH : 0 = " + posDernierPlusLongPlatHauteurH(sVide, 42));
        System.out.println();

        SeqInt s1 = new SeqInt(2, 2, 2, 1, 1, 7, 7, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s1);
        System.out.println("posDernierPlusLongPlatHauteurH (h=7) : 12 = " + posDernierPlusLongPlatHauteurH(s1, 7));
        System.out.println();

        SeqInt s2 = new SeqInt(2, 2, 2, 1, 1, 7, 7, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s2);
        System.out.println("posDernierPlusLongPlatHauteurH (h=2) : 1 = " + posDernierPlusLongPlatHauteurH(s2, 2));
        System.out.println();

        SeqInt s3 = new SeqInt(2, 2, 2, 1, 1, 7, 7, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s3);
        System.out.println("posDernierPlusLongPlatHauteurH (h=1) : 4 = " + posDernierPlusLongPlatHauteurH(s3, 1));
        System.out.println();

        SeqInt s4 = new SeqInt(2, 2, 2, 1, 1, 7, 7, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s4);
        System.out.println("posDernierPlusLongPlatHauteurH (h=9) : 0 = " + posDernierPlusLongPlatHauteurH(s4, 9));
        System.out.println();
    }
}
