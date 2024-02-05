package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire  un  sous-programme  qui  calcule  le  nombre  de  plateaux  de  longueur comprise  entre  LG1  et  LG2,  LG1  et  LG2  inclus  (LG1  >  0  et  LG1  <=  LG2).
Retourne le nombre de plateaux de longueur comprise entre LG1 et LG2; zéro si s est vide ousi LG1 > LG2.

Généralisation

lg1 = premier paramètre fourni à la fonction 
lg2 = second paramètre fourni à la fonction 
La fct retournera le nombre de plateaux de longueur comprise entre lg1 et lg2

e = élement courant
prec = élément précédent
lgPlatCourant = longueur du plateau courant
res = nbre de plateaux de longueur == 1

prec                 5 2 1 1 1 2 2 7 7 7 7 1   =>   prec = e;
--------------------------------------------
e                  5 2 1 1 1 2 2 7 7 7 7 1 2   =>   e = it.next();
--------------------------------------------
lgPlatCourant      1 1 1 2 3 1 2 1 2 3 4 1 1   =>   if (e == prec) ++lgPlatCourant; else lgPlatCourant=1;
res (lg1=2,lg2=3)  0 0 0 1 1 1 2 2 3 3 2 2 2   =>   if (lgPlatCourant == lg1) ++res; else if (lgPlatCourant == lg2 + 1) --res;
res (lg1=2,lg2=2)  0 0 0 1 0 0 1 1 2 1 1 1 1 
res (lg1=1,lg2=2)  1 2 3 3 2 3 3 4 4 3 3 4 5

Tenir compte du cas particulier de lg1==1 pour l'initialisation de res à 1 (0 dans les autres cas)

 */
public class Plateaux_ex09 {

    public static int nbPlateauxLongEntreLg1EtLg2(SeqInt s, int lg1, int lg2) {
        int res = 0;
        SeqIntIterator it = s.iterator();
        if (it.hasNext() && ! (lg1 > lg2)) {
            int e = it.next();
            int lgPlatCourant = 1;
            if (lg1 == 1) {
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
                if (lgPlatCourant == lg1) {
                    ++res;
                } else if (lgPlatCourant == lg2 + 1) {
                    --res;
                }
            }
        }
        return res;
    }
    
    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("nbPlateauxLongEntreLg1EtLg2 (lg1 = 4, lg2 = 42) : 0 = " + nbPlateauxLongEntreLg1EtLg2(sVide, 4, 42));
        System.out.println();

        SeqInt s1 = new SeqInt(5, 2, 1, 1, 1, 2, 2, 7, 7, 7, 7, 1, 2);
        System.out.println(s1);
        System.out.println("nbPlateauxLongEntreLg1EtLg2 (lg1 = 2, lg2 = 3) : 2 = " + nbPlateauxLongEntreLg1EtLg2(s1, 2, 3));
        System.out.println();

        SeqInt s2 = new SeqInt(5, 2, 1, 1, 2, 2, 5, 3, 3, 3, 7, 1, 1, 1, 1, 7);
        System.out.println(s2);
        System.out.println("nbPlateauxLongEntreLg1EtLg2 (lg1 = 2, lg2 = 2) : 2 = " + nbPlateauxLongEntreLg1EtLg2(s2, 2, 2));
        System.out.println();
        
        SeqInt s3 = new SeqInt(5, 2, 1, 1, 2, 2, 5, 3, 3, 3, 7, 1, 1, 1, 1, 7);
        System.out.println(s2);
        System.out.println("nbPlateauxLongEntreLg1EtLg2 (lg1 = 3, lg2 = 2) : 0 = " + nbPlateauxLongEntreLg1EtLg2(s3, 3, 2));
        System.out.println();
    }
}
