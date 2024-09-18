package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire  un  sous-programme  qui  calcule  la  longueur  du  dernier  plateau  de
hauteur maximale. Retourne longueur dernier plateau de haut = max. Zéro si s est vide.

e = élement courant
prec = élément précédent
max = maximum courant
lgPlatCourant = longueur du plateau courant
lg = longueur du dernier plateau de hauteur == max

prec               2 2 1 1 7 7 2 2 2 7 3 7 7   =>   prec = e;
--------------------------------------------
e                2 2 1 1 7 7 2 2 2 7 3 7 7 1   =>   e = it.next();
--------------------------------------------
max              2 2 2 2 7 7 7 7 7 7 7 7 7 7   =>   if (e>max) max=e;
lgPlatCourant    1 2 1 2 1 2 1 2 3 1 1 1 2 1   =>   if (e==prec) ++lgPlatCourant; else lgPlatCourant=1;
res              1 2 2 2 1 2 2 2 2 1 1 1 2 2   =>   if (e>=max) res=lgPlatCourant;
*/

public class Plateaux_ex14 {

    public static int lgDernierPlatHauteurMax(SeqInt s) {
        int res = 0;
        SeqIntIterator it = s.iterator();
        if (it.hasNext()) {
            int e = it.next();
            int max = e;
            int lgPlatCourant = 1;
            res = 1;
            while (it.hasNext()) {
                int prec = e;
                e = it.next();

                if (e == prec) {
                    ++lgPlatCourant;
                } else {
                    lgPlatCourant = 1;
                }

                if (e >= max) {
                    max = e;
                    res = lgPlatCourant;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("lgDernierPlatHauteurMax : 0 = " + lgDernierPlatHauteurMax(sVide));
        System.out.println();

        SeqInt s1 = new SeqInt(2, 2, 1, 2, 7, 7, 2, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s1);
        System.out.println("lgDernierPlatHauteurMax : 2 = " + lgDernierPlatHauteurMax(s1));
        System.out.println();

        SeqInt s2 = new SeqInt(5, 2, 1, 1, 2, 2, 5, 3, 7, 7, 7, 4, 6, 6, 5);
        System.out.println(s2);
        System.out.println("lgDernierPlatHauteurMax : 3 = " + lgDernierPlatHauteurMax(s2));
        System.out.println();
    }
}
