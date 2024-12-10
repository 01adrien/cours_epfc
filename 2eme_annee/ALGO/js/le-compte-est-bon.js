function leCompteEstBon(list, n) {
    //list.sort();
    sum = 0
    ///lastIndex = getLastIndexLowerOrEqual(list, n);
    for (let i = 0; i <= list.length; i++) {
        temp = Array.from(list);
        e = temp[i];
        temp.splice(i, 1);
        res = [i];
        for (let j = 0; j < temp.length; j++) {
            console.log(e)
            if (e == n) {
                return res;
            }
            res.push(j)
            e += temp[j];

        }

    }

}

function getLastIndexLowerOrEqual(list, n) {
    for (let index = 0; index < list.length; index++) {
        if (list[index] > n) return index - 1;
    }
    return list.length - 1;
}



console.log(leCompteEstBon([3, 2], 5));
