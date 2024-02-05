package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire  un  sous-programme  qui  calcule  le  nombre  de  plateaux  qui  suivent  la dernière occurrence du maximum.
Retourne le nombre de plateaux de la séquence après max; zéro si s est vide ou si max = dernier élément.

e = élement courant
prec = élément précédent
max = maximum courant
res = nbre de plateaux depuis dernière occurrence du max

prec      5 2 1 1 1 2 2 5 3 3 7 1 1   =>   prec = e;
-----------------------------------
e       5 2 1 1 1 2 2 5 3 3 7 1 1 2   =>   e = it.next();
-----------------------------------
max     5 5 5 5 5 5 5 5 5 5 7 7 7 7   =>   if (e > max) max = e;
res     0 1 2 2 2 3 3 0 1 1 0 1 1 2   =>   if (e >= max) res=0; else if (e != prec) ++res;
*/

public class Plateaux_ex03 {

    public static int nbPlateauxApresDernierMax(SeqInt s) {
        int res = 0;
        SeqIntIterator it = s.iterator();
        if (it.hasNext()) {
            int e = it.next();
            int max = e;
            while (it.hasNext()) {
                int prec = e;
                e = it.next();
                if (e >= max) {
                    max = e;
                    res = 0;
                } else if (e != prec) {
                    ++res;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("nbPlateauxApresDernierMax : 0 = " + nbPlateauxApresDernierMax(sVide));
        System.out.println();
        
        SeqInt s1 = new SeqInt(5, 2, 1, 1, 1, 2, 2, 5, 3, 3, 7, 1, 1, 2);
        System.out.println(s1);
        System.out.println("nbPlateauxApresDernierMax : 2 = " + nbPlateauxApresDernierMax(s1));
        System.out.println();
        
        SeqInt s2 = new SeqInt(5, 2, 1, 1, 1, 2, 2, 5, 3, 3, 7, 1, 1, 7);
        System.out.println(s2);
        System.out.println("nbPlateauxApresDernierMax : 0 = " + nbPlateauxApresDernierMax(s2));
        System.out.println();
    }
}
