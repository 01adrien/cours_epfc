let x = 15;
let y = 45;
let z = 45;

if (x == y || x == z) {
    console.log(x);
}
else if (y == z){
    console.log(y);
}

/*

if (x > y && x > z) {
    console.log(x);
    if (y > z) {
        console.log(y);
        console.log(z);
    } else {                  // add this
        console.log(z);
        console.log(y);
    }
} else if (y > z && y > x) {
    console.log(y);
    if (z > x) {
        console.log(z);
        console.log(x);
    } else {                  // add this
        console.log(x);
        console.log(z);
    }
} else {                      // you can skip the comparison here,
    console.log(z);        // because there is no other possibillity
    if (x < y) {
        console.log(x);
        console.log(y);
    } else {                  // add this
        console.log(y);
        console.log(x);
    }
}
*/