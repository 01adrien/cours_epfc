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
}



function sort(array) {
    if (array.length <= 1) {
        return array;
    }
    let m = Math.floor(array.length / 2);
    let a = sort(array.slice(0, m));
    let b = sort(array.slice(m, array.length));
    return fusion(a, b);
}

a = sort([9, 5, 8, 4, 78, 96, 87, 9, 12, 36])
console.log(a);
