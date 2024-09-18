package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire  un  sous-programme  qui  calcule  le  nombre  de  plateaux  de  longueur  LG (LG > 0).
Retourne le nombre de plateaux de longueur = LG; zéro si s est vide.

Généralisation. Tenir compte du cas particulier de lg==1 pour l'initialisation de res à 1 (0 dans les autres cas)

lg = paramètre fourni à la fonction - la fct retournera le nombre de plateaux de longueur égale à ce paramètre
e = élement courant
prec = élément précédent
lgPlatCourant = longueur du plateau courant
res = nbre de plateaux de longueur == 1

lg                 2 2 2 2 2 2 2 2 2 2 2 2 2
prec                 5 2 1 1 1 2 2 7 7 7 7 1   =>   prec = e;
--------------------------------------------
e                  5 2 1 1 1 2 2 7 7 7 7 1 2   =>   e = it.next();
--------------------------------------------
lgPlatCourant      1 1 1 2 3 1 2 1 2 3 4 1 1   =>   if (e == prec) ++lgPlatCourant; else lgPlatCourant=1;
res (lg==2)        0 0 0 1 0 0 1 1 2 1 1 1 1   =>   if (lgPlatCourant == lg) ++res; else if (lgPlatCourant == lg + 1) --res;
res (lg==1)        1 2 3 2 2 3 2 3 2 2 2 3 4   
 */

public class Plateaux_ex08 {

    public static int nbPlateauxLongLg(SeqInt s, int lg) {
        int res = 0;
        SeqIntIterator it = s.iterator();
        if (it.hasNext()) {
            int e = it.next();
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
                if (lgPlatCourant == lg) {
                    ++res;
                } else if (lgPlatCourant == lg + 1) {
                    --res;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("nbPlateauxLongLg (lg = 42) : 0 = " + nbPlateauxLongLg(sVide, 42));
        System.out.println();

        SeqInt s1 = new SeqInt(5, 2, 1, 1, 1, 2, 2, 7, 7, 7, 7, 1, 2);
        System.out.println(s1);
        System.out.println("nbPlateauxLongLg (lg = 2) : 1 = " + nbPlateauxLongLg(s1, 2));
        System.out.println();

        SeqInt s2 = new SeqInt(5, 2, 1, 1, 2, 2, 5, 3, 3, 7, 1, 1, 7);
        System.out.println(s2);
        System.out.println("nbPlateauxLongLg (lg = 1) : 5 = " + nbPlateauxLongLg(s2, 1));
        System.out.println();
    }
}
