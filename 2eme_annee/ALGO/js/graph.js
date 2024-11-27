class Queue {
    constructor() {
        this.items = {}
        this.frontIndex = 0
        this.backIndex = 0
    }
    enqueue(item) {
        this.items[this.backIndex] = item
        this.backIndex++
        return item + ' inserted'
    }
    dequeue() {
        const item = this.items[this.frontIndex]
        delete this.items[this.frontIndex]
        this.frontIndex++
        return item
    }
    empty() {
        return this.frontIndex == this.backIndex;
    }
}



queue = new Queue();
set = new Set();


function visit(n) {
    if (!visited(n)) {
        console.log(n);
        set.add(n);
    }
}

function visited(n) {
    return set.has(n);
}

function ex1Iter(n) {
    e = 1;
    queue.enqueue(e);
    visit(e)
    end = false;
    while (!queue.empty()) {
        e = queue.dequeue();
        l = e * 2
        r = Math.floor(e / 3)
        if (!visited(l)) {
            if (l == n) break;
            queue.enqueue(l);
            visit(l)
        } if (!visited(r)) {
            if (r == n) break;
            queue.enqueue(r);
            visit(r)
        }
    }
    console.log(n);
}

function rec(n, e) {
    if (e != n) {
        visit(e)
        l = e * 2
        r = Math.floor(e / 3)
        if (!visited(l)) {
            rec(n, l);
        }
        if (!visited(r)) {
            rec(n, r);
        }
    }

}

function ex1Rec(n) {
    e = 1;
    visit(e);
    rec(n, e)
}



function main() {
    ex1Iter(3);

}

main(3);