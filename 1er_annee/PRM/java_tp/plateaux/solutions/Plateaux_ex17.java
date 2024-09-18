package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire une fonction qui calcule la longueur du dernier plateau de hauteur H (donnée).

e = élement courant
prec = élément précédent
lgPlatCourant = longueur du plateau courant
res = longueur du dernier plateau de hauteur == h

prec              4 4 5 5 5 5 4 5 4 5 5 5 4   =>   prec = e;
-------------------------------------------
e               4 4 5 5 5 5 4 5 4 5 5 5 4 4   =>   e = it.next();
-------------------------------------------
lgPlatCourant   1 2 1 2 3 4 1 1 1 1 2 3 1 2   =>   if (e==prec) ++lgPlatCourant; else lgPlatCourant=1;
res (h=4)       1 2 2 2 2 2 1 1 1 1 1 1 1 2   =>   if (e==h) res=lgPlatCourant;
res (h=5)       0 0 1 2 3 4 4 1 1 1 2 3 3 3
res (h=7)       0 0 0 0 0 0 0 0 0 0 0 0 0 0
*/

public class Plateaux_ex17 {

    public static int lgDernierPlateauHauteurH(SeqInt s, int h) {
        SeqIntIterator it = s.iterator();
        int res = 0;
        if (it.hasNext()) {
            int e = it.next();
            int lgPlatCourant = 1;
            if (e == h) {
                res = lgPlatCourant;
            }
            while (it.hasNext()) {
                int prec = e;
                e = it.next();

                if (e == prec) {
                    ++lgPlatCourant;
                } else {
                    lgPlatCourant = 1;
                }

                if (e == h) {
                    res = lgPlatCourant;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("lgDernierPlateauHauteurH : 0 = " + lgDernierPlateauHauteurH(sVide, 42));
        System.out.println();

        SeqInt s1 = new SeqInt(4, 4, 5, 5, 5, 5, 4, 5, 4, 5, 5, 5, 4, 4);
        System.out.println(s1);
        System.out.println("lgDernierPlateauHauteurH (lg = 4) : 2 = " + lgDernierPlateauHauteurH(s1, 4));
        System.out.println();

        SeqInt s2 = new SeqInt(4, 4, 5, 5, 5, 5, 4, 5, 4, 5, 5, 5, 4, 4);
        System.out.println(s2);
        System.out.println("lgDernierPlateauHauteurH (lg = 7) : 0 = " + lgDernierPlateauHauteurH(s2, 7));
        System.out.println();
    }
}
