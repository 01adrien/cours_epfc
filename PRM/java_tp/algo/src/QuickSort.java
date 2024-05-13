
import eu.epfc.prm2.*;

public class QuickSort {

    public void swap(Array<Integer> arr, int a, int b) {
        int temp = arr.get(a);
        arr.set(a, arr.get(b));
        arr.set(b, temp);
    }

    public int arrange(Array<Integer> arr, int start, int end, int pivot) {
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
                this.swap(arr, start, end);
            }
        }
        return end;
    }

    public void quickSort(Array<Integer> arr, int start, int end, int pivot) {
        if (start < end) {
            int p = this.arrange(arr, start, end, pivot);
            this.quickSort(arr, start, p - 1, (start + p - 1) / 2);
            this.quickSort(arr, p + 1, end, (end + p + 1) / 2);
        }
    }

    public void qsort(Array<Integer> arr) {
        this.quickSort(arr, 0, arr.size() - 1, arr.size() / 2);
    }

}
