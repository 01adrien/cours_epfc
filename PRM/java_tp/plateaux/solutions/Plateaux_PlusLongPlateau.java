package prm2_09_plateaux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import seqint.SeqInt;
import seqint.SeqIntIterator;

public class Plateaux_PlusLongPlateau {

    public static int plusLongPlateau(SeqInt s) {
        SeqIntIterator it = s.iterator();
        int res = 0;
        if (it.hasNext()) {
            int e = it.next();
            int lgPlatCour = 1;
            res = lgPlatCour;
            while (it.hasNext()) {
                int prec = e;
                e = it.next();
                if (e == prec) {
                    ++lgPlatCour;
                } else {
                    lgPlatCour = 1;
                }
                if (lgPlatCour > res) {
                    res = lgPlatCour;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SeqInt s1 = new SeqInt(5, 2, 1, 1, 1, 2, 2, 5, 3, 3, 7, 1, 1, 1, 1, 2);
        System.out.println(s1);
        System.out.println("Longueur du plus long plateau : " + plusLongPlateau(s1));
    }
}
