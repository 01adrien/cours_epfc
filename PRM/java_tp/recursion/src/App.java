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

    public static Array<Integer> merge(Array<Integer> a, Array<Integer> b) {
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

    public static Array<Integer> copyArray(Array<Integer> from, int start, int end) {
        Array<Integer> dest = new Array<Integer>();
        while (start != end) {
            dest.add(from.get(start++));
        }
        return dest;
    }

    public static Array<Integer> mergeSort(Array<Integer> arr) {
        if (arr.size() == 1) {
            return arr;
        }
        int mid = arr.size() / 2;
        Array<Integer> a = copyArray(arr, 0, mid);
        Array<Integer> b = copyArray(arr, mid, arr.size());
        return merge(mergeSort(a), mergeSort(b));
    }

    public static void main(String[] args) throws Exception {

        Array<Integer> arr = new Array<Integer>(8, 10, 9, 3, 2, 4, 5, 6, 85, 7, 1, 89, 2);
        System.out.println(arr);
        Array<Integer> arrSorted = mergeSort(arr);
        System.out.println(arrSorted);

    }

}
