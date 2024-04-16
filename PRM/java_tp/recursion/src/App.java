import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import eu.epfc.prm2.Array;

public class App {

    public static boolean isOdd(int n) {
        return n % 2 != 0;
    }

    public static boolean isEven(int n) {
        return !isOdd(n);
    }

    // EX 2
    public static int fact(int n) {
        return n > 1 ? n * fact(n - 1) : n;
    }

    // EX 3
    public static int fib(int n) {
        return n >= 2 ? fib(n - 2) + fib(n - 1) : n;
    }

    // EX 4
    public static int pow(int n, int exp) {
        if (exp == 1) {
            return n;
        } else if (exp == 0) {
            return 1;
        }
        return n * pow(n, exp - 1);
    }

    // EX 5
    public static int pow2(int n, int exp) {
        return isEven(n) ? pow(n * n, exp / 2) : pow(n * n, (exp - 1) / 2) * n;
    }

    // EX 6
    public static int ex6(int n) {
        return n >= 10 ? 1 + ex6(n / 10) : 1;
    }

    // EX 7
    public static int ex7(int n) {
        return n < 10 ? n : n % 10 + ex7(n / 10);
    }

    // EX 8
    public static int ex8(Array<Integer> arr) {
        if (arr.size() == 1) {
            return arr.get(0);
        }
        int last = arr.get(arr.size() - 1);
        arr.reduceTo(arr.size() - 1);
        return last + ex8(arr);

    }

    // EX 9
    public static int binSearch(Array<Integer> arr, int low, int high, int num) {
        if (low > high) {
            return -1;
        }
        int mid = (high + low) / 2;
        if (arr.get(mid) == num) {
            return mid;
        } else if (arr.get(mid) > num) {
            return binSearch(arr, low, mid - 1, num);
        } else {
            return binSearch(arr, mid + 1, high, num);
        }
    }

    // EX 10
    public static int ex10(int n) {
        return n <= 1
                ? 0
                : 1 + ex10(n - 2);
    }

    // EX 11
    public static boolean ex11(int n) {
        return n < 10
                ? isEven(n)
                : isEven(n % 10) && ex11(n / 10);
    }

    // EX 12

    public static void ex12(int n) {
        System.out.print(n % 10);
        if (n > 9) {
            ex12(n / 10);
        }
    }

    // EX 13

    public static int ex13(int n) {
        return n < 10
                ? n
                : Math.max(n % 10, ex13(n / 10));
    }

    // EX 14
    public static int ex14(int n, int exp) {
        if (n == 1) {
            return pow2(2, exp);
        } else if (n == 0) {
            return 0;
        } else if (n % 10 == 1) {
            return pow2(2, exp) + ex14(n / 10, exp + 1);
        } else {
            return ex14(n / 10, exp + 1);
        }
    }

    // EX 15
    public static int ex15(Array<Integer> arr, int pos) {
        return (pos == arr.size() - 1)
                ? arr.get(pos)
                : Math.max(arr.get(pos), ex15(arr, pos + 1));
    }

    public static int ex15Bis(Array<Integer> arr, int low, int high) {
        int mid = (high + low) / 2;
        return low == high
                ? arr.get(high)
                : Math.max(ex15Bis(arr, low, mid), ex15Bis(arr, mid + 1, high));
    }

    public static int isCap(char c) {
        return c >= 'A' && c <= 'Z'
                ? 1
                : 0;
    }

    // EX 16
    public static int ex16(String str) {
        return str.length() == 0
                ? 0
                : isCap(str.charAt(0)) + ex16(str.substring(1));
    }

    // --------------------------------------------------------------------------------------------------------

    public static void swap(Array<Integer> arr, int a, int b) {
        int temp = arr.get(a);
        arr.set(a, arr.get(b));
        arr.set(b, temp);
    }

    public static int arrange(Array<Integer> arr, int start, int end, int pivot) {
        int pVal = arr.get(pivot);
        while (start < end) {
            while (arr.get(start) < pVal) {
                start++;
            }
            while (arr.get(end) > pVal) {
                end--;
            }
            if (arr.get(start) == pVal && arr.get(end) == pVal) {
                start++;
            } else {
                swap(arr, start, end);
            }
        }
        return end;
    }

    public static void quickSort(Array<Integer> arr, int start, int end, int pivot) {
        if (start < end) {
            int p = arrange(arr, start, end, pivot);
            quickSort(arr, start, p - 1, (start + p - 1) / 2);
            quickSort(arr, p + 1, end, (end + p + 1) / 2);
        }
    }

    public static void qsort(Array<Integer> arr) {
        quickSort(arr, 0, arr.size() - 1, arr.size() / 2);
    }

    public static Array<Integer> merge(Array<Integer> a1, Array<Integer> a2) {
        Array<Integer> sorted = new Array<Integer>();
        int lenMin = Math.min(a1.size(), a2.size());
        int lenMax = Math.max(a1.size(), a2.size());
        int ia = 0;
        int ib = 0;
        int i = 0;
        System.out.println("a1 => " + a1);
        System.out.println("a2 => " + a2);
        while (ia < a1.size() && ib < a1.size()) {
            if (a1.get(ia) < a2.get(ib)) {
                sorted.add(a1.get(ia));
                ia++;
            } else if (a2.get(ib) < a1.get(ia)) {
                sorted.add(a2.get(ib));
                ib++;
            } else {
                sorted.add(a2.get(ib));
                sorted.add(a1.get(ia));
                ia++;
                ib++;
            }
        }
        System.out.println(sorted);
        return sorted;
    }

    public static void copyArray(Array<Integer> from, Array<Integer> to, int start, int end) {
        while (start != end) {
            to.add(from.get(start++));
        }
    }

    public static void mergeSort(Array<Integer> arr, int n) {
        if (arr.size() == 1) {
            return;
        }
        int mid = arr.size() / 2;
        Array<Integer> a1 = new Array<Integer>();
        Array<Integer> a2 = new Array<Integer>();
        copyArray(arr, a1, 0, mid);
        copyArray(arr, a2, mid, arr.size());
        // System.out.println("STEP " + n);
        // System.out.println("A1 => " + a1);
        // System.out.println("A2 => " + a2);
        mergeSort(a1, n + 1);
        mergeSort(a2, n + 1);
        arr = merge(a1, a2);
    }

    public static void main(String[] args) throws Exception {

        Array<Integer> arr = new Array<Integer>(8, 9, 2, 3, 5, 6, 9, 7);
        System.out.println(arr);
        mergeSort(arr, 0);
        System.out.println(arr);
        // Array<Integer> arr2 = new Array<Integer>();
        // copyArray(arr, arr2, 0, 1);
        // System.out.println(arr);
        // System.out.println(arr2);

    }

}
