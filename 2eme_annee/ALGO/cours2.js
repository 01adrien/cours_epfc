/*
Ecrivez une fonction qui regroupe les éléments d’un tableau composé
exclusivement des entiers -1, 0 et 1 de façon à ce que tous les -1 soient
au début, suivis des 0 et, finalement des 1. 
Par exemple, le tableau: 
    [0, -1, 0, 0, 1, 1, -1, 0, -1, -1, -1, -1, 1]
donnerait: 
    [-1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 1, 1, 1]
*/

function swap(array, i, j) {
    temp = array[i];
    array[i] = array[j];
    array[j] = temp;
}


/*
[0, -1, 0, 0, -1, 1, 1] | i = 0| iMinusOne = 0| iOne = 6 
[-1, 0, 0, 0, -1, 1, 1] | i = 1| iMinusOne = 1| iOne = 
[-1, -1, 0, 0, 0, 1, 1] | i = 2 | iMinusOne = | iOne = 
[0, -1, 0, 0, -1, 1, 1] | i = | iMinusOne = | iOne = 
[0, -1, 0, 0, -1, 1, 1] | i = | iMinusOne = | iOne = 

*/

function ex4(array) {
    iMinusOne = 0;
    iOne = array.length;
    let i = 0;
    while (i < iOne) {
        if (array[i] == -1) {
            swap(array, i, iMinusOne++);
        } else if (array[i] == 1) {
            swap(array, i, --iOne);
        } else {
            i++;
        }
    }
}

/*
Ecrivez une fonction qui sépare les éléments d’un tableau d’entiers
quelconques de façon à regrouper tous les négatifs d’abord (dans un
ordre quelconque) suivis de tous les autres (positifs ou nuls, eux-aussi
dans un ordre quelconque). Votre fonction doit renvoyer le nombre de
négatifs. Par exemple le tableau :
    [0, -7, 4, 0, 5, 9, -4, 0, -2, -9, -4, -8, 2]
pourrait donner par exemple :
    [-7, -4, -2, -9, -4, -8, 0, 0, 4, 0, 5, 9, 2]
*/

function ex5(array) {
    neg = 0;
    pos = 0;
    for (let i = 0; i < array.length; i++) {
        if (array[i] < 0) {
            swap(array, i, neg++);
        } else {
            pos++;
        }
    }
    return neg;
}


/*
(Facultatif) 
Ecrivez une fonction qui regroupe les éléments d’un tableau
d’entiers de façon à ce que tous les négatifs soient au début (dans un
ordre quelconque), suivis des 0 et, finalement des positifs. 
Le tableau :
    [0, -7, 0, 0, 5, 9, -4, 0, -2, -9, -4, -8, 2]
pourrait donner par exemple :
    [-7, -8, -4, -4, -2, -9, 0, 0, 0, 0, 9, 2, 5]

Hypothèse inductive suggérée :
    < : négatifs
    = : zéros
    ? : inconnus
    > : positifs
    [<<<<<<<<<<<<<<<|=======|??????????????????????|>>>>>>>>>]
    Le premier inconnu est en tab[nbNegatifs + nbnuls]
*/

function ex6(array) {
    neg = 0;
    pos = array.length;
    let i = 0;
    while (i < pos) {
        if (array[i] < 0) {
            swap(array, i, neg++);
        } else if (array[i] > 0) {
            swap(array, i, --pos);
        } else {
            i++;
        }
    }
}

function main() {
    array = [0, -7, 0, 0, 5, 9, -4, 0, -2, -9, -4, -8, 2, 8, 6, 2, -8, -4, 0, 5, 9];
    console.log(array);
    ex6(array);
    console.log(array);
}

main();