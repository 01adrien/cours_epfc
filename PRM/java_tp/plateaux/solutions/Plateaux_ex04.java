package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire  un  sous-programme  qui  calcule  le  nombre  de  plateaux  de  longueur supérieure ou égale à 3.
Retourne le nombre de plateaux de longueur >= 3; zéro si s est vide.

e = élement courant
prec = élément précédent
lgPlatCourant = longueur du plateau courant
res = nbre de plateaux de longueur >= 3

prec                 5 2 1 1 1 2 2 7 7 7 7 1   =>   prec = e;
--------------------------------------------
e                  5 2 1 1 1 2 2 7 7 7 7 1 2   =>   e = it.next();
--------------------------------------------
lgPlatCourant      1 1 1 2 3 1 2 1 2 3 4 1 1   =>   if (e == prec) ++lgPlatCourant; else lgPlatCourant=1;
res                0 0 0 0 1 1 1 1 1 2 2 2 2   =>   if (lgPlatCourant == 3) ++res;
*/

public class Plateaux_ex04 {

    public static int nbPlateauxLongueurPlusGrandEgal3(SeqInt s) {
        int res = 0;
        SeqIntIterator it = s.iterator();
        if (it.hasNext()) {
            int e = it.next();
            int lgPlatCourant = 1;
            while (it.hasNext()) {
                int prec = e;
                e = it.next();
                if (e != prec) {
                    lgPlatCourant = 1;
                } else {
                    ++lgPlatCourant;
                }
                if (lgPlatCourant == 3) {
                    ++res;
                }
            }
        }
        return res;
    }
    
    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("nbPlateauxLongueurPlusGrandEgal3 : 0 = " + nbPlateauxLongueurPlusGrandEgal3(sVide));
        System.out.println();
        
        SeqInt s1 = new SeqInt(5, 2, 1, 1, 1, 2, 2, 7, 7, 7, 7, 1, 2);
        System.out.println(s1);
        System.out.println("nbPlateauxLongueurPlusGrandEgal3 : 2 = " + nbPlateauxLongueurPlusGrandEgal3(s1));
        System.out.println();
        
        SeqInt s2 = new SeqInt(5, 2, 1, 1, 2, 2, 5, 3, 3, 7, 1, 1, 7);
        System.out.println(s2);
        System.out.println("nbPlateauxLongueurPlusGrandEgal3 : 0 = " + nbPlateauxLongueurPlusGrandEgal3(s2));
        System.out.println();
    }
}
