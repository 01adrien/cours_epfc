import eu.epfc.prm2.*;

/*
 *  A = [ 4, 9, 8, 7, 2, 3, 1, 1, 5, 4, 8, 6, 3, 1, 2 ]
 *  B = []
 * 
 */

public class EnumSort {

    public int max(Array<Integer> arr, int pos) {
        return pos == arr.size() - 1
                ? arr.get(pos)
                : Math.max(arr.get(pos), max(arr, pos + 1));
    }

    public Array<Integer> countEqual(Array<Integer> arr) {
        Array<Integer> eq = new Array<Integer>();
        eq.extend(max(arr, 0) + 1, 0);
        for (int i = 0; i < arr.size(); i++) {
            int j = arr.get(i);
            int k = eq.get(j);
            eq.set(j, ++k);
        }
        return eq;
    }

    public int sumBetween(Array<Integer> arr, int a, int b) {
        int sum = 0;
        for (int i = a; i != b; i++) {
            sum += arr.get(i);
        }
        return sum;
    }

    public Array<Integer> countLower(Array<Integer> eq) {
        Array<Integer> lo = new Array<Integer>();
        lo.extend(eq.size(), 0);
        for (int i = 1; i < eq.size(); i++) {
            lo.set(i, sumBetween(eq, 0, i));
        }
        return lo;
    }

    public Array<Integer> enumSort(Array<Integer> arr) {
        Array<Integer> eq = this.countEqual(arr);
        Array<Integer> lo = this.countLower(eq);
        Array<Integer> sorted = new Array<Integer>();
        sorted.extend(arr.size(), null);
        for (int i = 0; i < arr.size(); i++) {
            int j = arr.get(i);
            int k = lo.get(j);
            if (eq.get(j) > 1) {
                while (sorted.get(k) != null) {
                    k++;
                }
            }
            sorted.set(k, j);
        }
        return sorted;
    }

}
