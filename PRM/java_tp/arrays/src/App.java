import eu.epfc.prm2.*;

public class App {

    /*
     * Ecrivez une fonction qui renvoie la somme
     * des éléments d’un tableau d’entiers (Integer)
     */
    public static int ex1(Array<Integer> arr) {
        int sum = 0;
        for (int i = 0; i < arr.size(); i++) {
            sum += arr.get(i);
        }
        return sum;
    }

    public static int ex1Rec(Array<Integer> arr, int size) {
        return size == 1 ? arr.get(size - 1) : arr.get(size - 1) + ex1Rec(arr, size - 1);
    }

    /*
     * Ecrivez une fonction qui renvoie la moyenne des
     * éléments d’un tableau d’entiers (Integer).
     */
    public static double ex2(Array<Integer> arr) {
        return (double) ex1(arr) / arr.size();
    }

    /*
     * Ecrivez une fonction qui détermine si un entier donné apparaît, oui ou non,
     * dans un tableau
     * d’entiers. Refaites le même exercice pour déterminer si une valeur apparaît
     * dans le tableau en
     * commençant à chercher à partir d'une position donnée (commencez à compter les
     * positions à 0).
     * Qu’envisagez-vous si la position de recherche n’est pas valide (est en dehors
     * du tableau) ?
     */
    public static boolean ex3(Array<Integer> arr, int search) {
        boolean found = false;
        if (arr.size() > 0) {
            int i = 0;
            while (!found && i < arr.size()) {
                if (arr.get(i) == search) {
                    found = true;
                }
                i++;
            }
        }
        return found;
    }

    public static boolean ex3Bis(Array<Integer> arr, int search, int pos) {
        boolean found = false;
        if (arr.size() > 0 && pos < arr.size()) {
            int i = pos;
            while (!found && i < arr.size()) {
                if (arr.get(i) == search) {
                    found = true;
                }
                i++;
            }
        }
        return found;
    }

    /*
     * Ecrivez une fonction qui renvoie la position de la 1ère apparition d’une
     * valeur dans un tableau. On
     * considère que le premier se trouve en position 0. Et si la valeur cherchée
     * n'apparaît pas ? Que
     * renvoyez-vous
     */

    public static int ex4(Array<Integer> arr, int search) {
        int pos = -1;
        if (arr.size() > 0) {
            int i = 0;
            while (i < arr.size() && pos == -1) {
                if (arr.get(i) == search) {
                    pos = i;
                }
                i++;
            }
        }
        return pos;
    }

    /*
     * Refaites le même exercice mais en renvoyant la position de la dernière
     * apparition.
     */
    public static int ex5(Array<Integer> arr, int search) {
        int pos = -1;
        if (arr.size() > 0) {
            int i = 0;
            while (i < arr.size()) {
                if (arr.get(i) == search) {
                    pos = i;
                }
                i++;
            }
        }
        return pos;
    }

    /**
     * Ecrivez une fonction qui échange les deux valeurs d’un tableau aux positions
     * données. Que se
     * passe-t-il si les positions sont invalides ?
     */
    public static boolean ex6(Array<Integer> arr, int p1, int p2) {
        if (p1 < arr.size() && p2 < arr.size()) {
            int temp = arr.get(p1);
            arr.set(p1, arr.get(p2));
            arr.set(p2, temp);
            return true;
        }
        return false;
    }

    /*
     * Ecrivez une fonction qui renverse l’ordre des éléments d’un tableau. Si le
     * tableau contient
     * initialement les entiers [4, 7, 5, 2, 4, 3, 2, 3] ; il contiendra après
     * renversement : [3, 2, 3, 4, 2, 5, 7, 4].
     */

    public static void ex7(Array<Integer> arr) {
        int start = 0;
        int end = arr.size() - 1;
        for (int i = 0; i < arr.size() / 2; i++) {
            ex6(arr, end--, start++);
        }
    }

    /*
     * Ecrivez une fonction qui effectue une permutation cyclique vers la gauche des
     * éléments d’un tableau.
     * Si le tableau contient initialement les 8 entiers [4, 7, 5, 2, 4, 3, 2, 3] ;
     * il contiendra après permutation : [7, 5, 2, 4, 3, 2, 3, 4]. Les éléments «
     * glissent » vers
     * la gauche. Le premier vient en dernier. Attention si le tableau est vide !
     */
    public static void ex8(Array<Integer> arr) {
        for (int i = 0; i < arr.size() - 1; i++) {
            ex6(arr, i, i + 1);
        }
    }

    /*
     * Ecrivez une fonction qui effectue une permutation cyclique vers la droite des
     * éléments d’un tableau. Si le tableau contient
     * initialement les 5 entiers [4, 7, 5, 2, 4, 3, 2, 3]
     * il contiendra après permutation : [3, 4, 7, 5, 2, 4, 3, 2].
     */
    public static void ex9(Array<Integer> arr) {
        for (int i = 0; i < arr.size(); i++) {
            ex6(arr, i, arr.size() - 1);
        }
    }

    /*
     * Ecrivez une fonction qui ajoute (insère) une valeur à une position donnée
     * dans un tableau. On
     * commence à compter les positions à 0. Par exemple, ajouter la valeur 9 en
     * position 3 dans le tableau [4, 7, 5, 2, 4, 3, 2, 3]
     * donnerait le tableau [4, 7, 5, 9, 2, 4, 3, 2, 3].
     * La valeur 9 est venue prendre la place du 2 qui occupait la position 3. En
     * conséquence, les valeurs
     * suivantes ont été décalées vers la droite. Remarquez que le tableau s’est
     * agrandi : son nombre
     * d’éléments a augmenté de 1. Pour ce faire, vous devez utiliser add(). Que
     * faites-vous si la
     * position donnée n’est pas dans le tableau ?
     */
    public static void ex10(Array<Integer> arr, int val, int pos) {
        if (pos < arr.size()) {
            arr.add(val);
            for (int i = pos; i < arr.size(); i++) {
                ex6(arr, i, arr.size() - 1);
            }
        }
    }

    /*
     * Ecrivez une fonction qui supprime l’élément d’un tableau se trouvant en une
     * position donnée (en
     * commençant à compter à 0). La suppression se fait par décalage vers la gauche
     * de tous les
     * éléments se trouvant plus loin que celui à supprimer dans le tableau.
     * Utilisez reduceTo() pour
     * prendre en compte le changement du nombre d’éléments dans le tableau ? Que
     * faites-vous si la
     * position donnée n’est pas dans le tableau ?
     */

    public static void ex11(Array<Integer> arr, int pos) {
        if (pos < arr.size()) {
            for (int i = pos; i < arr.size(); i++) {
                ex6(arr, i, i + 1);
            }
            arr.reduceTo(arr.size() - 1);
        }
    }

    /*
     * Ecrivez une fonction qui supprime, dans un tableau, toutes les apparitions
     * d’une valeur donnée.
     * Par exemple, supprimer toutes les apparitions de 4 dans le tableau [4, 3, 7,
     * 2, 5, 4, 4, 4,
     * 4, 3, 5, 4] donnerait [3, 7, 2, 5, 3, 5]. Refaites le même exercice mais en
     * commençant
     * à partir d’une position donnée et plus à partir du début du tableau
     * 
     * !! A REFAIRE AVEC UNE SEULE BOUCLE
     * 
     * 2 pointeurs (lecture / ecriture) tout pousser au debut
     * 
     */
    public static void ex12(Array<Integer> arr, int val) {
        int s = arr.size();
        for (int i = 0; i < s - 1; i++) {
            while (arr.get(i) == val) {
                ex11(arr, i);
            }
            s = arr.size();
        }
    }

    public static void ex12Bis(Array<Integer> arr, int val) {
        int s = arr.size();
    }

    public static void main(String[] args) throws Exception {
        Array<Integer> arr = new Array<>(4, 3, 7, 2, 5, 4, 4, 4, 4, 3, 5, 4);
        System.out.println(arr);
        ex12(arr, 4);
        System.out.println(arr);
    }

}
