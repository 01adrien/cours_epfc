//const { performance } = require('perf_hooks');
//const { start } = require('repl');

function swap(array, i, j) {
    temp = array[i];
    array[i] = array[j];
    array[j] = temp;
}

function getRandomInt(max) {
    return Math.floor(Math.random() * max);
}

function getRandomExcept(except) {
    n = getRandomInt(300);
    while (n === except) {
        n = getRandomInt(300);
    }
    return n;
}

function deleteAtPos(array, pos) {
    if (pos < 0 || pos >= array.length || array.length < 2) {
        throw new Error(`pos ${pos} out of bound`);

    }
    for (let i = pos; i < array.length - 1; i++) {
        swap(array, i, i + 1);
    }
    array.length = array.length - 1;
    return true;
}

function deleteAllValSlow(array, val) {
    for (let i = 0; i < array.length; i++) {
        while (array[i] === val) {
            deleteAtPos(array, i);
        }
    }
}

function shuffle(array) {
    return array.sort((a, b) => 0.5 - Math.random());
}


function randList(size, p, val) {
    if (p < 0 || p > 100) {
        throw new Error(`% is invalid ${p}`);
    }
    array = new Array(size);
    num = Math.ceil(size * (p / 100));
    for (let i = 0; i < array.length; i++) {
        if (i < num) {
            array[i] = val;
        } else {
            array[i] = getRandomExcept(val)
        }
    }
    return shuffle(array);
}


function deleteAllValFast(array, val) {
    start = 0;
    end = array.length - 1;
    toDelete = 0;
    while (start <= end) {
        while (array[end] == val) {
            end--;
            toDelete++;
        }
        if (array[start] == val) {
            swap(array, start, end);
            end--;
            toDelete++;
        } else {
            start++;
        }
    }
    array.length = array.length - toDelete;
}


function main() {
    array = [5, 8, 9, 5, 5, 5, 6, 9, 5, 5];
    deleteAllValFast(array, 5);
    console.log(array);
}

main();
