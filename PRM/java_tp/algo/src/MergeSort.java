
import eu.epfc.prm2.*;

public class MergeSort {
    public Array<Integer> merge(Array<Integer> a, Array<Integer> b) {
        Array<Integer> sorted = new Array<Integer>();
        int ia = 0;
        int ib = 0;
        while (ia < a.size() && ib < b.size()) {
            if (a.get(ia) < b.get(ib)) {
                sorted.add(a.get(ia++));
            } else {
                sorted.add(b.get(ib++));
            }
        }
        while (ia < a.size()) {
            sorted.add(a.get(ia++));
        }
        while (ib < b.size()) {
            sorted.add(b.get(ib++));
        }
        return sorted;
    }

    public Array<Integer> copyArray(Array<Integer> from, int start, int end) {
        Array<Integer> dest = new Array<Integer>();
        while (start != end) {
            dest.add(from.get(start++));
        }
        return dest;
    }

    public Array<Integer> mergeSort(Array<Integer> arr) {
        if (arr.size() == 1) {
            return arr;
        }
        int mid = arr.size() / 2;
        Array<Integer> a = this.copyArray(arr, 0, mid);
        Array<Integer> b = this.copyArray(arr, mid, arr.size());
        return this.merge(this.mergeSort(a), this.mergeSort(b));
    }

}
