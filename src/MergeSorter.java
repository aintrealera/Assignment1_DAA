import java.util.Arrays;

public class MergeSorter {

    private static final int INSERTION_SORT_CUTOFF = 16;

    private long comparisons = 0;

    public void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        int[] buffer = new int[array.length];
        mergeSort(array, buffer, 0, array.length - 1);
    }

    private void mergeSort(int[] array, int[] buffer, int left, int right) {
        if (right - left + 1 <= INSERTION_SORT_CUTOFF) {
            insertionSort(array, left, right);
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(array, buffer, left, mid);
        mergeSort(array, buffer, mid + 1, right);

        if (array[mid] <= array[mid + 1]) {
            return;
        }

        merge(array, buffer, left, mid, right);
    }

    private void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= left) {
                comparisons++;

                if (array[j] <= key) {
                    break;
                }

                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key;
        }
    }

    private void merge(
            int[] array,
            int[] buffer,
            int left,
            int mid,
            int right
    ) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            comparisons++;

            if (array[i] <= array[j]) {
                buffer[k++] = array[i++];
            } else {
                buffer[k++] = array[j++];
            }
        }

        while (i <= mid) {
            buffer[k++] = array[i++];
        }

        while (j <= right) {
            buffer[k++] = array[j++];
        }

        for (int index = left; index <= right; index++) {
            array[index] = buffer[index];
        }
    }

    public long getComparisons() {
        return comparisons;
    }

    public void resetMetrics() {
        comparisons = 0;
    }
}