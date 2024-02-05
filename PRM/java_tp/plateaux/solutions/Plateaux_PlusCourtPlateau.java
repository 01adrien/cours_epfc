package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;

public class Plateaux_PlusCourtPlateau {

    public static int plusCourtPlateau(SeqInt s) {
        SeqIntIterator it = s.iterator();
        int res = 0;
        if (it.hasNext()) {
            int e = it.next();
            res = 1;
            boolean nouvPlat = false;
            while (it.hasNext() && !nouvPlat) {     // Parcourir le premier plateau
                int prec = e;
                e = it.next();
                if (e == prec) {
                    ++res;
                }
                nouvPlat = e != prec;
            }
            if (nouvPlat) {                         // Nouveau plateau trouvé
                int plusCourtFini = res;
                res = 1;
                int lgPlatCour = 1;
                while (it.hasNext()) {
                    int prec = e;
                    e = it.next();
                    if (e == prec) {
                        ++lgPlatCour;
                        res = Math.min(lgPlatCour, plusCourtFini);
                    } else {
                        if (lgPlatCour < plusCourtFini)
                            plusCourtFini = lgPlatCour;
                        lgPlatCour = 1;
                        res = 1;
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt s1 = new SeqInt(5, 5, 1, 1, 1, 2, 2, 2, 3, 3, 3, 1, 1, 1, 1, 2);
        System.out.println(s1);
        System.out.println("Longueur du plus court plateau : " + plusCourtPlateau(s1));
        SeqInt s2 = new SeqInt(5, 5, 5, 5, 7, 7, 7, 7, 7, 1, 1, 1, 3, 3, 3, 3, 3, 3);
        System.out.println(s2);
        System.out.println("Longueur du plus court plateau : " + plusCourtPlateau(s2));
        SeqInt s3 = new SeqInt(5, 5, 5, 5, 7, 7, 7, 7, 7, 3, 3, 3, 3, 3, 3);
        System.out.println(s3);
        System.out.println("Longueur du plus court plateau : " + plusCourtPlateau(s3));
        SeqInt s4 = new SeqInt(5, 5, 5, 5, 7, 7, 7, 7, 7, 3, 3, 3);
        System.out.println(s4);
        System.out.println("Longueur du plus court plateau : " + plusCourtPlateau(s4));
        SeqInt s5 = new SeqInt(5, 5, 3);
        System.out.println(s5);
        System.out.println("Longueur du plus court plateau : " + plusCourtPlateau(s5));
    }
}
