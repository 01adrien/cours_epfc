const diviseur = (n, m) => n % m == 0;

const estPremier = (n) => {
    for (let i = 2; i < n; i++) {
        if (diviseur(n, i)) {
            return false;
        }
    }
    return true;
}

const premiersInferieursA = (n) => {
    const array = [];
    for (let i = 2; i < n; i++) {
        if (estPremier(i)) {
            array.push(i);
        }
    }
    return array;
}

const premiersNombresPremiers = (n) => {
    const array = [];
    let i = 2;
    while (array.length != n) {
        if (estPremier(i)) {
            array.push(i);
        }
        i++;
    }
    return array;

}

const main = () => {
    console.log(estPremier());
}

main();