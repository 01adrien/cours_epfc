package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire un sous-programme qui calcule la position
et la longueur du dernier plateau de hauteur maximale.
Retourne une séquence contenant deux éléments : le premier est la position, 
le seconde est la longueur du dernier plateau de hauteur max. 
Retourne une séquence vide si la séqence reçue est vide.

e = élement courant
prec = élément précédent
posCour = position courante
max = maximum courant
lgPlatCourant = longueur du plateau courant
lg = longueur du dernier plateau de hauteur == max
pos = position du dernier plateau de hauteur == max

prec            2 2 1 1 7 7 2 2 2 7 3 7 7   =>   prec = e;
-----------------------------------------
e             2 2 1 1 7 7 2 2 2 7 3 7 7 1   =>   e = it.next();
-----------------------------------------
posCour       1 2 3 4 5 6 7 8 9 1 1 1 1 1   =>   ++pos;
                                0 1 2 3 4
max           2 2 2 2 7 7 7 7 7 7 7 7 7 7   =>   if (e>max) max=e;
lgPlatCour    1 2 1 2 1 2 1 2 3 1 1 1 2 1   =>   if (e==prec) ++lgPlatCourant; else lgPlatCourant=1;
lg            1 2 2 2 1 2 2 2 2 1 1 1 2 2   =>   if (e>=max) lg=lgPlatCourant;
pos           1 1 1 1 5 5 5 5 5 1 1 1 1 1   =>   if (e>=max && e!=prec) pos=posCour;
                                0 0 2 2 2
*/

public class Plateaux_ex15 {

    public static SeqInt posLgDernierPlatHauteurMax(SeqInt s) {
        SeqIntIterator it = s.iterator();
        SeqInt res = new SeqInt();
        if (it.hasNext()) {
            int pos = 0, lg = 0;
            int e = it.next();
            int max = e;
            int lgPlatCourant = 1;
            int posCour = 1;
            pos = 1;
            lg = 1;
            while (it.hasNext()) {
                int prec = e;
                e = it.next();
                ++posCour;
                if (e == prec) {
                    ++lgPlatCourant;
                } else {
                    lgPlatCourant = 1;
                }

                if (e >= max) {
                    max = e;
                    lg = lgPlatCourant;
                    if (e != prec) {
                        pos = posCour;
                    }
                }
            }
            res = new SeqInt(pos, lg);
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("posLgDernierPlatHauteurMax : 0 = " + posLgDernierPlatHauteurMax(sVide));
        System.out.println();

        SeqInt s1 = new SeqInt(2, 2, 1, 2, 7, 7, 2, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s1);
        System.out.println("posLgDernierPlatHauteurMax : 2 = " + posLgDernierPlatHauteurMax(s1));
        System.out.println();

        SeqInt s2 = new SeqInt(5, 2, 1, 1, 2, 2, 5, 3, 7, 7, 7, 4, 6, 6, 5);
        System.out.println(s2);
        System.out.println("posLgDernierPlatHauteurMax : 3 = " + posLgDernierPlatHauteurMax(s2));
        System.out.println();
    }
}
