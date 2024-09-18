package prm2_09_plateaux;

import seqint.SeqInt;
import seqint.SeqIntIterator;

/*
Ecrire un sous-programme qui calcule la position
du dernier plateau de hauteur H (donnée).

e = élement courant
prec = élément précédent
posCour = position courante
pos = position du dernier plateau de hauteur == H

prec            2 2 1 1 7 7 2 2 2 7 3 7 7   =>   prec = e;
-----------------------------------------
e             2 2 1 1 7 7 2 2 2 7 3 7 7 1   =>   e = it.next();
-----------------------------------------
posCour       1 2 3 4 5 6 7 8 9 1 1 1 1 1   =>   ++pos;
                                0 1 2 3 4
pos (H=2)     1 1 1 1 1 1 7 7 7 7 7 7 7 7   =>   if (e==H && e!=prec) pos=posCour;
pos (H=1)     0 0 3 3 3 3 3 3 3 3 3 3 3 1 
                                        4
										
=> initialisation : if (e==H) pos=1; else pos=0;
*/

public class Plateaux_ex16 {

    public static int posDernierPlatHauteurH(SeqInt s, int h) {
        SeqIntIterator it = s.iterator();
        int pos = 0;
        if (it.hasNext()) {
            int e = it.next();
            int posCour = 1;
            if (e == h)
                pos = 1;
            while (it.hasNext()) {
                int prec = e;
                e = it.next();
                ++posCour;
                if (e != prec && e == h) {
                    pos = posCour;
                }
            }
        }
        return pos;
    }

    public static void main(String[] args) {
        SeqInt sVide = new SeqInt();
        System.out.println(sVide);
        System.out.println("posDernierPlatHauteurH : 0 = " + posDernierPlatHauteurH(sVide, 2));
        System.out.println();

        SeqInt s1 = new SeqInt(2, 2, 1, 2, 7, 7, 2, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s1);
        System.out.println("posDernierPlatHauteurH : 7 = " + posDernierPlatHauteurH(s1, 2));
        System.out.println();

        SeqInt s2 = new SeqInt(5, 2, 1, 1, 2, 2, 5, 3, 7, 7, 7, 4, 6, 6, 5);
        System.out.println(s2);
        System.out.println("posDernierPlatHauteurH : 9 = " + posDernierPlatHauteurH(s2, 7));
        System.out.println();

        SeqInt s3 = new SeqInt(2, 2, 1, 1, 7, 7, 2, 2, 2, 7, 3, 7, 7, 1);
        System.out.println(s3);
        System.out.println("posDernierPlatHauteurH : 7 = " + posDernierPlatHauteurH(s3, 2));
        System.out.println();

        SeqInt s4 = new SeqInt(2);
        System.out.println(s4);
        System.out.println("posDernierPlatHauteurH : 1 = " + posDernierPlatHauteurH(s4, 2));
        System.out.println();

        SeqInt s5 = new SeqInt(1);
        System.out.println(s5);
        System.out.println("posDernierPlatHauteurH : 0 = " + posDernierPlatHauteurH(s5, 2));
        System.out.println();
    }
}
