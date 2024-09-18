package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire  un  sous-programme  qui  calcule  la  position  du  dernier  plateau  de
hauteur maximale. Retourne la position dernier plateau de haut = max. Zéro si s est vide.

e = élement courant
prec = élément précédent
max = maximum courant
posCour = position courante
pos = position du dernier plateau de hauteur == max

prec        2 2 1 1 7 7 2 2 2 7 3 7 7   =>   prec = e;
-------------------------------------
e         2 2 1 1 7 7 2 2 2 7 3 7 7 1   =>   e = it.next();
-------------------------------------
max       2 2 2 2 7 7 7 7 7 7 7 7 7 7   =>   if (e>max) max=e;
posCour   1 2 3 4 5 6 7 8 9 1 1 1 1 1   =>   ++posCour
                            0 1 2 3 4
res       1 1 1 1 5 5 5 5 5 1 1 1 1 1   =>   if (e>=max && e!=prec) res=posCour;
                            0 0 2 2 2
*/

public class Plateaux_ex13 {

    public static int posDernierPlatHauteurMax(SeqInt s) {
        SeqIntIterator it = s.iterator();
        int res = 0;
        if (it.hasNext()) {
            int e = it.next();
            int max = e;
            int posCour = 1;
            res = 1;
            while (it.hasNext()) {
                int prec = e;
                e = it.next();
                ++posCour;
                if (e >= max) {
                    max = e;
                    if (e != prec) {
                        res = posCour;
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("posDernierPlatHauteurMax : 0 = " + posDernierPlatHauteurMax(sVide));
        System.out.println();

        SeqInt s1 = new SeqInt(2, 2, 1, 2, 7, 7, 2, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s1);
        System.out.println("posDernierPlatHauteurMax : 12 = " + posDernierPlatHauteurMax(s1));
        System.out.println();

        SeqInt s2 = new SeqInt(5, 2, 1, 1, 2, 2, 5, 3, 3, 7, 9, 4, 1, 1);
        System.out.println(s2);
        System.out.println("posDernierPlatHauteurMax : 11 = " + posDernierPlatHauteurMax(s2));
        System.out.println();
    }
}
