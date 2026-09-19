import java.util.Arrays;

public class SortingTest {

    public static void main(String[] args) {

        testRandom();
        testSorted();
        testReverseSorted();
        testDuplicates();
        testEmpty();
        testSingleElement();

        System.out.println("All sorting tests passed!");
    }

    private static void checkSort(int[] original) {

        int[] expected = original.clone();
        Arrays.sort(expected);

        int[] mergeArray = original.clone();
        int[] quickArray = original.clone();

        MergeSorter mergeSorter = new MergeSorter();
        QuickSorter quickSorter = new QuickSorter();

        mergeSorter.sort(mergeArray);
        quickSorter.sort(quickArray);

        if (!Arrays.equals(mergeArray, expected)) {
            throw new AssertionError("MergeSort failed");
        }

        if (!Arrays.equals(quickArray, expected)) {
            throw new AssertionError("QuickSort failed");
        }
    }

    private static void testRandom() {
        checkSort(new int[]{8, 3, 5, 1, 9, 2, 7, 4});
    }

    private static void testSorted() {
        checkSort(new int[]{1, 2, 3, 4, 5, 6, 7});
    }

    private static void testReverseSorted() {
        checkSort(new int[]{7, 6, 5, 4, 3, 2, 1});
    }

    private static void testDuplicates() {
        checkSort(new int[]{5, 2, 5, 2, 5, 1, 1, 3});
    }

    private static void testEmpty() {
        checkSort(new int[]{});
    }

    private static void testSingleElement() {
        checkSort(new int[]{42});
    }
}