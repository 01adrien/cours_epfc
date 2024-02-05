package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;


/*
Ecrire  un  sous-programme  qui  calcule  le  nombre  de  plateaux  de  longueur supérieure ou égale à LG (LG > 0).
Retourne le nombre de plateaux de longueur >= LG; zéro si s est vide.

Idem précédent, avec le cas particulier pour l'initialisation de lg == 1 : dans ce cas, on initialise res à 1, sinon à 0.

lg = paramètre fourni à la fonction - la fct retournera le nombre de plateaux de longueur supérieure ou égale à ce paramètre
e = élement courant
prec = élément précédent
lgPlatCourant = longueur du plateau courant
res = nbre de plateaux de longueur >= 3

lg                 2 2 2 2 2 2 2 2 2 2 2 2 2
prec                 5 2 1 1 1 2 2 7 7 7 7 1   =>   prec = e;
--------------------------------------------
e                  5 2 1 1 1 2 2 7 7 7 7 1 2   =>   e = it.next();
--------------------------------------------
lgPlatCourant      1 1 1 2 3 1 2 1 2 3 4 1 1   =>   if (e == prec) ++lgPlatCourant; else lgPlatCourant=1;
res                0 0 0 1 1 1 2 2 3 3 3 3 3   =>   if (lgPlatCourant == lg) ++res;
*/

public class Plateaux_ex05 {

    public static int nbPlateauxLongueurPlusGrandEgalLG(SeqInt s, int lg) {
        int res = 0;
        SeqIntIterator it = s.iterator();
        if (it.hasNext()) {
            int e = it.next();
            int lgPlatCourant = 1;
            if (lgPlatCourant == lg) {
                res = 1;
            }
            while (it.hasNext()) {
                int prec = e;
                e = it.next();
                if (e != prec) {
                    lgPlatCourant = 1;
                } else {
                    ++lgPlatCourant;
                }
                if (lgPlatCourant == lg) {
                    ++res;
                }
            }
        }
        return res;
    }
    
    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("nbPlateauxLongueurPlusGrandEgalLG (lg = 42) : 0 = " + nbPlateauxLongueurPlusGrandEgalLG(sVide, 42));
        System.out.println();
        
        SeqInt s1 = new SeqInt(5, 2, 1, 1, 1, 2, 2, 7, 7, 7, 7, 1, 2);
        System.out.println(s1);
        System.out.println("nbPlateauxLongueurPlusGrandEgalLG (lg = 2) : 3 = " + nbPlateauxLongueurPlusGrandEgalLG(s1, 2));
        System.out.println();
        
        SeqInt s2 = new SeqInt(5, 2, 1, 1, 2, 2, 5, 3, 3, 7, 1, 1, 7);
        System.out.println(s2);
        System.out.println("nbPlateauxLongueurPlusGrandEgalLG (lg = 1) : 9 = " + nbPlateauxLongueurPlusGrandEgalLG(s2, 1));
        System.out.println();
    }

}
