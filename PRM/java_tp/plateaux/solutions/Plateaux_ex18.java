package prm2_09_plateaux;

import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire une fonction qui calcule la position et la longueur du dernier plateau de hauteur H (donnée).

e = élement courant
prec = élément précédent
lgPlatCourant = longueur du plateau courant
res = longueur du dernier plateau de hauteur == h

prec               4 4 5 5 5 5 4 5 4 5 5 5 4   =>   prec = e;
-------------------------------------------
e                4 4 5 5 5 5 4 5 4 5 5 5 4 4   =>   e = it.next();
-------------------------------------------
lgPlatCourant    1 2 1 2 3 4 1 1 1 1 2 3 1 2   =>   if (e==prec) ++lgPlatCourant; else lgPlatCourant=1;
posCour          1 2 3 4 5 6 7 8 9 1 1 1 1 1
                                   0 1 2 3 4
resH (h=4)       1 2 2 2 2 2 1 1 1 1 1 1 1 2   =>   if (e==h) res=lgPlatCourant;
resH (h=5)       0 0 1 2 3 4 4 1 1 1 2 3 3 3
resH (h=7)       0 0 0 0 0 0 0 0 0 0 0 0 0 0

pos  (h=4)       1 1 1 1 1 1 7 7 9 9 9 9 1 1   =>   if (e==h && e!=prec) pos=posCour;
                                         3 3
pos  (h=5)       0 0 3 3 3 3 3 8 8 1 1 1 1 1
                                   0 0 0 0 0
pos  (h=7)       0 0 0 0 0 0 0 0 0 0 0 0 0 0
*/
public class Plateaux_ex18 {
    public static SeqInt posEtLgDernierPlatHauteurH(SeqInt s, int h) {
        SeqIntIterator it = s.iterator();
        int lg = 0;
        int pos = 0;
        if (it.hasNext()) {
            int e = it.next();
            int lgPlatCourant = 1;
            int posCour = 1;
            if (e == h) {
                pos = 1;
                lg = lgPlatCourant;
            }
            while (it.hasNext()) {
                int prec = e;
                e = it.next();
                ++posCour;

                if (e == prec) {
                    ++lgPlatCourant;
                } else {
                    lgPlatCourant = 1;
                }

                if (e == h) {
                    lg = lgPlatCourant;
                    if (e != prec) {
                        pos = posCour;
                    }
                }
            }
        }
        return new SeqInt(pos, lg);
    }

    public static void main(String[] args) {

        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("posEtLgDernierPlatHauteurH : [0, 0] = " + posEtLgDernierPlatHauteurH(sVide, 2));
        System.out.println();

        SeqInt s1 = new SeqInt(2, 2, 1, 2, 7, 7, 2, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s1);
        System.out.println("posEtLgDernierPlatHauteurH (h=2) : [7, 3] = " + posEtLgDernierPlatHauteurH(s1, 2));
        System.out.println();

        SeqInt s2 = new SeqInt(5, 2, 1, 1, 2, 2, 5, 3, 7, 7, 7, 4, 6, 6, 5);
        System.out.println(s2);
        System.out.println("posEtLgDernierPlatHauteurH (h=7) : [9, 3] = " + posEtLgDernierPlatHauteurH(s2, 7));
        System.out.println();

        SeqInt s3 = new SeqInt(4, 4, 5, 5, 5, 5, 4, 5, 4, 5, 5, 5, 4, 4);
        System.out.println(s3);
        System.out.println("posEtLgDernierPlatHauteurH (h=4) : [13, 2] = " + posEtLgDernierPlatHauteurH(s3, 4));
        System.out.println();

        SeqInt s4 = new SeqInt(4, 4, 5, 5, 5, 5, 4, 5, 4, 5, 5, 5, 4, 4);
        System.out.println(s4);
        System.out.println("posEtLgDernierPlatHauteurH (h=7) : [0, 0] = " + posEtLgDernierPlatHauteurH(s4, 7));
        System.out.println();
    }
}
