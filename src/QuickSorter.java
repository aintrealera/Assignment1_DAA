import java.util.Random;

public class QuickSorter {

    private final Random random = new Random();

    private long comparisons = 0;
    private long swaps = 0;
    private int maxRecursionDepth = 0;

    public void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        quickSort(array, 0, array.length - 1, 1);
    }

    private void quickSort(
            int[] array,
            int low,
            int high,
            int depth
    ) {
        while (low < high) {

            maxRecursionDepth = Math.max(maxRecursionDepth, depth);

            int pivotIndex = low + random.nextInt(high - low + 1);

            swap(array, pivotIndex, high);

            int pivot = array[high];

            int partitionIndex = partition(
                    array,
                    low,
                    high,
                    pivot
            );

            int leftSize = partitionIndex - low;
            int rightSize = high - partitionIndex;

            // Recurse into smaller partition.
            if (leftSize < rightSize) {

                if (low < partitionIndex - 1) {
                    quickSort(
                            array,
                            low,
                            partitionIndex - 1,
                            depth + 1
                    );
                }

                // Iterate over larger partition.
                low = partitionIndex + 1;

            } else {

                if (partitionIndex + 1 < high) {
                    quickSort(
                            array,
                            partitionIndex + 1,
                            high,
                            depth + 1
                    );
                }

                // Iterate over larger partition.
                high = partitionIndex - 1;
            }
        }
    }

    private int partition(
            int[] array,
            int low,
            int high,
            int pivot
    ) {
        int storeIndex = low;

        for (int i = low; i < high; i++) {

            comparisons++;

            if (array[i] < pivot) {
                swap(array, i, storeIndex);
                storeIndex++;
            }
        }

        swap(array, storeIndex, high);

        return storeIndex;
    }

    private void swap(int[] array, int i, int j) {
        if (i == j) {
            return;
        }

        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;

        swaps++;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swaps;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    public void resetMetrics() {
        comparisons = 0;
        swaps = 0;
        maxRecursionDepth = 0;
    }
}