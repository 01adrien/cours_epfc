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
de  hauteur maximale.
Retourne le nbre de plateau de haut = max.
Zéro si s est vide.

e = élement courant
prec = élément précédent
max = maximum courant
res = nbre de plateaux de hauteur == max

prec      2 2 1 2 7 7 2 2 2 7 3 7 7   =>   prec = e;
-----------------------------------
e       2 2 1 2 7 7 2 2 2 7 3 7 7 1   =>   e = it.next();
-----------------------------------
max     2 2 2 2 7 7 7 7 7 7 7 7 7 7   =>   if (e>max) max=e;
res     1 1 1 2 1 1 1 1 1 2 2 3 3 3   =>   if (e>max) res=1
                                           else if (e==max && e!=prec) ++res;
 */

public class Plateaux_ex10 {

    public static int nbPlateauxHauteurMax(SeqInt s) {
        int res = 0;
        SeqIntIterator it = s.iterator();

        if (it.hasNext()) {
            int e = it.next();
            int max = e;
            res = 1;

            while (it.hasNext()) {
                int prec = e;
                e = it.next();

                if (e > max) {
                    max = e;
                    res = 1;
                } else if (e == max && e != prec) {
                    ++res;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("nbPlateauxHauteurMax : 0 = " + nbPlateauxHauteurMax(sVide));
        System.out.println();

        SeqInt s1 = new SeqInt(2, 2, 1, 2, 7, 7, 2, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s1);
        System.out.println("nbPlateauxHauteurMax : 3 = " + nbPlateauxHauteurMax(s1));
        System.out.println();

        SeqInt s2 = new SeqInt(5, 2, 1, 1, 2, 2, 5, 3, 3, 7, 1, 1, 9);
        System.out.println(s2);
        System.out.println("nbPlateauxHauteurMax : 1 = " + nbPlateauxHauteurMax(s2));
        System.out.println();
    }
}
