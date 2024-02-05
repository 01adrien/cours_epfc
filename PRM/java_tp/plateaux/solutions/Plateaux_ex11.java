package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire  un  sous-programme  qui  calcule  le  nombre  de  plateaux
de  hauteur maximale et de longueur strictement supérieure à 1.
Retourne le nbre de plateau de haut = max et lg > 1. Zéro si s est vide.

e = élement courant
prec = élément précédent
max = maximum courant
lgPlatCourant = longueur du plateau courant
res = nbre de plateaux de hauteur == max et de longueur > 1

prec                 2 2 1 1 7 7 2 2 2 7 3 7 7   =>   prec = e;
----------------------------------------------
e                  2 2 1 1 7 7 2 2 2 7 3 7 7 1   =>   e = it.next();
----------------------------------------------
max                2 2 2 2 7 7 7 7 7 7 7 7 7 7   =>   if (e>max) max=e;
lgPlatCourant      1 2 1 2 1 2 1 2 3 1 1 1 2 1   =>   if (e==prec) ++lgPlatCourant; else lgPlatCourant=1;
res                0 1 1 1 0 1 1 1 1 1 1 1 2 2   =>   if (e==max && lgPlatCourant==2) ++res;
                                                      if (e>max) res=0;
*/

public class Plateaux_ex11 {

    public static int nbPlateauxHautMaxLongPlusGrand1(SeqInt s) {
        int res = 0;
        SeqIntIterator it = s.iterator();
        if (it.hasNext()) {
            int e = it.next();
            int max = e;
            int lgPlatCourant = 1;
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
                } else if (e == max && lgPlatCourant == 2) {
                    ++res;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("nbPlateauxHautMaxLongPlusGrand1 : 0 = " + nbPlateauxHautMaxLongPlusGrand1(sVide));
        System.out.println();

        SeqInt s1 = new SeqInt(2, 2, 1, 2, 7, 7, 2, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s1);
        System.out.println("nbPlateauxHautMaxLongPlusGrand1 : 2 = " + nbPlateauxHautMaxLongPlusGrand1(s1));
        System.out.println();

        SeqInt s2 = new SeqInt(5, 2, 1, 1, 2, 2, 5, 3, 3, 7, 1, 1, 9);
        System.out.println(s2);
        System.out.println("nbPlateauxHautMaxLongPlusGrand1 : 0 = " + nbPlateauxHautMaxLongPlusGrand1(s2));
        System.out.println();
    }
}
