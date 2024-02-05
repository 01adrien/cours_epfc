package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire un sous-programme qui calcule le nombre de plateaux d’une séquence.
Retourne le nombre de plateaux de la séquence; zéro si s est vide.

e = élement courant
prec = élément précédent
nbPlateaux = nombre de plateaux rencontrés jusqu'ici

prec              5 2 1 1 1 2 2 7 7 7 7 1   =>   prec = e;
-----------------------------------------
e               5 2 1 1 1 2 2 7 7 7 7 1 2   =>   e = it.next();
-----------------------------------------
nbPlateaux      1 2 3 3 3 4 4 5 5 5 5 6 7   =>   if (e != prec) ++nbPlateaux;
*/

public class Plateaux_ex01 {

    public static int nbPlateaux(SeqInt s) {
        int nbPlateaux = 0;
        SeqIntIterator it = s.iterator();
        if (it.hasNext()) {
            int e = it.next();
            nbPlateaux = 1;
            while (it.hasNext()) {
                int prec = e;
                e = it.next();
                if (e != prec) {
                    ++nbPlateaux;
                }
            }
        }
        return nbPlateaux;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("nbPlateaux : 0 = " + nbPlateaux(sVide));
        System.out.println();
        
        SeqInt s = new SeqInt(5, 2, 1, 1, 1, 2, 2, 7, 7, 7, 7, 1, 2);
        System.out.println(s);
        System.out.println("nbPlateaux : 7 = " + nbPlateaux(s));
        System.out.println();
    }
}
