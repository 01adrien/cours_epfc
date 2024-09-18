package prm2_09_plateaux;

import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire une fonction qui calcule la position du dernier plus long plateau de hauteur maximale.
Retourne la position dernier plus long plateau de haut = max. Zéro si s est vide.

e = élement courant
prec = élément précédent
max = maximum courant
posCour = position courante
lgPlatCourant = longueur du plateau courant
posPlatCourant = position du plateau courant
lgMax = longueur du plus long plateau de hauteur maximale

prec             2 2 1 1 7 7 2 2 2 7 3 7 7   =>   prec = e;
-------------------------------------
e              2 2 1 1 7 7 2 2 2 7 3 7 7 1   =>   e = it.next();
-------------------------------------
lgPlatCourant  1 2 1 2 1 2 1 2 3 1 1 1 2 1   =>   if (e==prec) ++lgPlatCourant; else lgPlatCourant=1;
posCour        1 2 3 4 5 6 7 8 9 1 1 1 1 1   =>   ++posCour
                                 0 1 2 3 4
posPlatCourant 1 1 3 3 5 5 7 7 7 1 1 1 1 1   =>   if (e!=prec) posPlatCourant=posCour;
                                 0 1 2 2 4
max            2 2 2 2 7 7 7 7 7 7 7 7 7 7   =>   if (e>max) max=e;
lgMax          1 2 2 2 1 2 2 2 2 2 2 2 2 2   =>   if (e==max && lgMax < lgPlatCourant || e>max) lgMax=lgPlatCourant;
res            1 1 1 1 5 5 5 5 5 5 5 5 1 1   =>   if (e>=max && lgMax == lgPlatCourant) res=posPlatCourant;
                                       2 2
 */
public class Plateaux_ex22 {

    public static int posDernierPlusLongPlatHauteurMax(SeqInt s) {
        SeqIntIterator it = s.iterator();
        int res = 0;
        if (it.hasNext()) {
            int e = it.next();
            int max = e;
            int posCour = 1;
            int lgPlatCourant = 1;
            int lgMax = 1;
            int posPlatCourant = 1;
            res = 1;
            while (it.hasNext()) {
                int prec = e;
                e = it.next();
                ++posCour;
                if (e==prec)
                    ++lgPlatCourant;
                else {
                    lgPlatCourant = 1;
                    posPlatCourant = posCour;
                }
                if (e >= max) {
                    if (e > max || lgMax <= lgPlatCourant) {
                        lgMax = lgPlatCourant;
                        res = posPlatCourant;
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
        System.out.println("posDernierPlusLongPlatHauteurMax : 0 = " + posDernierPlusLongPlatHauteurMax(sVide));
        System.out.println();

        SeqInt s1 = new SeqInt(2, 2, 1, 2, 7, 7, 2, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s1);
        System.out.println("posDernierPlusLongPlatHauteurMax : 12 = " + posDernierPlusLongPlatHauteurMax(s1));
        System.out.println();

        SeqInt s2 = new SeqInt(2, 2, 1, 2, 7, 7, 2, 2, 2, 7, 3, 3, 7, 1);
        System.out.println(s2);
        System.out.println("posDernierPlusLongPlatHauteurMax : 5 = " + posDernierPlusLongPlatHauteurMax(s2));
        System.out.println();
    }
}
