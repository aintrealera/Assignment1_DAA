public class DeterministicSelector {

    private long comparisons = 0;
    private long swaps = 0;
    private int maxRecursionDepth = 0;

    /**
     * Returns the k-th smallest element.
     *
     * k is zero-based:
     * k = 0 -> smallest
     * k = array.length - 1 -> largest
     */
    public int select(int[] array, int k) {

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }

        if (k < 0 || k >= array.length) {
            throw new IllegalArgumentException("Invalid k");
        }

        int[] copy = array.clone();

        return select(copy, 0, copy.length - 1, k, 1);
    }

    private int select(
            int[] array,
            int left,
            int right,
            int k,
            int depth
    ) {

        maxRecursionDepth = Math.max(
                maxRecursionDepth,
                depth
        );

        while (true) {

            if (left == right) {
                return array[left];
            }

            int pivotValue = medianOfMedians(
                    array,
                    left,
                    right
            );

            int pivotIndex = findValue(
                    array,
                    left,
                    right,
                    pivotValue
            );

            pivotIndex = partitionAroundPivot(
                    array,
                    left,
                    right,
                    pivotIndex
            );

            if (k == pivotIndex) {
                return array[pivotIndex];
            }

            if (k < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }

            depth++;

            maxRecursionDepth = Math.max(
                    maxRecursionDepth,
                    depth
            );
        }
    }

    private int medianOfMedians(
            int[] array,
            int left,
            int right
    ) {

        int n = right - left + 1;

        if (n <= 5) {
            insertionSort(array, left, right);

            return array[left + n / 2];
        }

        int numberOfGroups = (n + 4) / 5;

        for (int group = 0; group < numberOfGroups; group++) {

            int groupLeft = left + group * 5;
            int groupRight = Math.min(
                    groupLeft + 4,
                    right
            );

            insertionSort(
                    array,
                    groupLeft,
                    groupRight
            );

            int medianIndex =
                    groupLeft +
                            (groupRight - groupLeft) / 2;

            swap(
                    array,
                    left + group,
                    medianIndex
            );
        }

        return select(
                array,
                left,
                left + numberOfGroups - 1,
                left + numberOfGroups / 2,
                1
        );
    }

    private int partitionAroundPivot(
            int[] array,
            int left,
            int right,
            int pivotIndex
    ) {

        int pivotValue = array[pivotIndex];

        swap(
                array,
                pivotIndex,
                right
        );

        int storeIndex = left;

        for (int i = left; i < right; i++) {

            comparisons++;

            if (array[i] < pivotValue) {
                swap(
                        array,
                        i,
                        storeIndex
                );

                storeIndex++;
            }
        }

        swap(
                array,
                storeIndex,
                right
        );

        return storeIndex;
    }

    private int findValue(
            int[] array,
            int left,
            int right,
            int value
    ) {

        for (int i = left; i <= right; i++) {

            comparisons++;

            if (array[i] == value) {
                return i;
            }
        }

        throw new IllegalStateException(
                "Pivot value not found"
        );
    }

    private void insertionSort(
            int[] array,
            int left,
            int right
    ) {

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

    private void swap(
            int[] array,
            int i,
            int j
    ) {

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