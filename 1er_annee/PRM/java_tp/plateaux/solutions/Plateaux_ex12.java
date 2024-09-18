package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire  un  sous-programme  qui  calcule  le  nombre  de
plateaux  de  hauteur maximale et de longueur LG (LG > 0).
Retourne le nbre de plateau de haut = max et longueur = lg.
Zéro si s est vide ou si aucun plateau max de longueur lg.

e = élement courant
prec = élément précédent
max = maximum courant
lgPlatCourant = longueur du plateau courant
res = nbre de plateaux de hauteur == max et de longueur == LG

prec                   2 2 1 1 7 7 2 2 2 7 3 7 7   =>   prec = e;
------------------------------------------------
e                    2 2 1 1 7 7 2 2 2 7 3 7 7 1   =>   e = it.next();
------------------------------------------------
max                  2 2 2 2 7 7 7 7 7 7 7 7 7 7   =>   if (e>max) max=e;
lgPlatCourant        1 2 1 2 1 2 1 2 3 1 1 1 2 1   =>   if (e==prec) ++lgPlatCourant; else lgPlatCourant=1;
res(lg=1)            1 0 0 0 1 0 0 0 0 1 1 2 1 1   =>   if (e==max && lgPlatCourant==lg) ++res;
res(lg=2)            0 1 1 1 0 1 1 1 1 1 1 1 2 2        if (e==max && lgPlatCourant==lg+1) --res;
res(lg=3)            0 0 0 0 0 0 0 0 0 0 0 0 0 0        if (e>max && lg==1) res=1;
                                                        if (e>max && lg>1) res=0;
*/

public class Plateaux_ex12 {

    public static int nbPlateauxHautMaxLongLG(SeqInt s, int lg) {
        int res = 0;
        SeqIntIterator it = s.iterator();
        if (it.hasNext()) {
            int e = it.next();
            int max = e;
            int lgPlatCourant = 1;
            if (lg == 1) {
                res = 1;
            }
            while (it.hasNext()) {
                int prec = e;
                e = it.next();

                if (e == prec) {
                    ++lgPlatCourant;
                } else {
                    lgPlatCourant = 1;
                }

                if (e > max) {
                    max = e;
                    res = 0;
                    if (lg == 1) {
                        ++res;
                    }
                } else if (e == max) {
                    if (lgPlatCourant == lg) {
                        ++res;
                    } else if (lgPlatCourant == lg + 1) {
                        --res;
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("nbPlateauxHautMaxLongLG (lg = 42) : 0 = " + nbPlateauxHautMaxLongLG(sVide, 42));
        System.out.println();

        SeqInt s1 = new SeqInt(2, 2, 1, 1, 7, 7, 2, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s1);
        System.out.println("nbPlateauxHautMaxLongLG (lg = 2) : 2 = " + nbPlateauxHautMaxLongLG(s1, 2));
        System.out.println();

        SeqInt s2 = new SeqInt(2, 2, 1, 1, 7, 7, 2, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s2);
        System.out.println("nbPlateauxHautMaxLongLG (lg = 3) : 0 = " + nbPlateauxHautMaxLongLG(s2, 3));
        System.out.println();
    }
}
