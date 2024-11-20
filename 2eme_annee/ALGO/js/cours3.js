/*
Exercices
8. Ecrivez, dans le langage de programmation de votre choix, une fonction
fusion qui reçoit deux listes (tableaux) déjà triées et qui renvoie une
nouvelle liste avec tous les éléments des deux listes dans l’ordre. Par
exemple si la fonction reçoit les deux listes [3, 5, 6, 9] et
[1, 2, 4, 4, 8], votre fonction renverra la liste
[1, 2, 3, 4, 4, 5, 6, 8, 9]
Pour cet exercice vous devez produire un nouveau tableau (ou une
nouvelle liste). L’algorithme ne fonctionne pas en place !
*/

function fusion(a, b) {
    result = [];
    ia = 0;
    ib = 0;

    while (ia < a.length && ib < b.length) {
        if (a[ia] < b[ib]) {
            result.push(a[ia]);
            ia++;
        } else {
            result.push(b[ib]);
            ib++;
        }
    }
    return result.concat(a.slice(ia), b.slice(ib));
};

function main() {
    a = [3, 5, 6, 9];
    b = [1, 2, 4, 4, 8];
    console.log(fusion(a, b));
}

main()