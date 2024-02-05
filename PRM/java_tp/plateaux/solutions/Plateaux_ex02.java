package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire un sous-programme qui calcule le nombre de plateaux
qui précèdent la première occurrence du maximum.
Retourne le nombre de plateaux de la séquence avant max;
zéro si s est vide ou si max = premier élément.

e = élement courant
prec = élément précédent
nbPlateaux = nombre de plateaux rencontrés jusqu'ici indépendamment du max
res = nbre de plateaux avant 1ère occurrence du max

prec            5 2 1 1 1 2 2 7 7 7 7 1   =>   prec = e;
---------------------------------------
e             5 2 1 1 1 2 2 7 7 7 7 1 2   =>   e = it.next();
---------------------------------------
max           5 5 5 5 5 5 5 7 7 7 7 7 7   =>   if (e > max) max = e;
res           0 0 0 0 0 0 0 4 4 4 4 4 4   =>   if (e > max) res = nbPlateaux;
nbPlateaux    1 2 3 3 3 4 4 5 5 5 5 6 7   =>   if (e != prec) ++nbPlateaux;
*/

public class Plateaux_ex02 {

    public static int nbPlateauxAvantMax(SeqInt s) {
        int res = 0;
        SeqIntIterator it = s.iterator();
        if (it.hasNext()) {
            int e = it.next();
            int max = e;
            int nbPlateaux = 1;
            while (it.hasNext()) {
                int prec = e;
                e = it.next();
                if (e != prec) {
                    if (e > max) {
                        max = e;
                        res = nbPlateaux;
                    }
                    ++nbPlateaux;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("nbPlateauxAvantMax : 0 = " + nbPlateauxAvantMax(sVide));
        System.out.println();
        
        SeqInt s = new SeqInt(5, 2, 1, 1, 1, 2, 2, 7, 7, 7, 7, 1, 2);
        System.out.println(s);
        System.out.println("nbPlateauxAvantMax : 4 = " + nbPlateauxAvantMax(s));
        System.out.println();
    }
}
