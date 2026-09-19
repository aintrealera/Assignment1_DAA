import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        testMergeSort();

        testQuickSort();

        testDeterministicSelect();

        testClosestPair();

        System.out.println();

        System.out.println(
                "Running performance experiments..."
        );

        Experiment.runExperiments();

        System.out.println();

        System.out.println(
                "Program finished."
        );
    }

    private static void testMergeSort() {

        int[] array = {
                8, 3, 5, 1, 9, 2, 7, 4
        };

        int[] expected =
                array.clone();

        Arrays.sort(expected);

        MergeSorter sorter =
                new MergeSorter();

        sorter.sort(array);

        System.out.println(
                "MergeSort test: "
                        + Arrays.equals(
                        array,
                        expected
                )
        );

        System.out.println(
                "Result: "
                        + Arrays.toString(array)
        );
    }

    private static void testQuickSort() {

        int[] array = {
                10, 4, 7, 2, 9, 1, 5
        };

        int[] expected =
                array.clone();

        Arrays.sort(expected);

        QuickSorter sorter =
                new QuickSorter();

        sorter.sort(array);

        System.out.println(
                "QuickSort test: "
                        + Arrays.equals(
                        array,
                        expected
                )
        );

        System.out.println(
                "Result: "
                        + Arrays.toString(array)
        );

        System.out.println(
                "Recursion depth: "
                        + sorter.getMaxRecursionDepth()
        );
    }

    private static void testDeterministicSelect() {

        int[] array = {
                7, 2, 9, 4, 1, 6, 3, 8, 5
        };

        int k = 4;

        int[] sorted =
                array.clone();

        Arrays.sort(sorted);

        DeterministicSelector selector =
                new DeterministicSelector();

        int result =
                selector.select(array, k);

        System.out.println(
                "Deterministic Select test: "
                        + (result == sorted[k])
        );

        System.out.println(
                "k = " + k
                        + ", result = " + result
        );
    }

    private static void testClosestPair() {

        Point[] points = {

                new Point(0, 0),

                new Point(5, 5),

                new Point(1, 1),

                new Point(10, 10),

                new Point(1.1, 1.1)
        };

        ClosestPairSolver solver =
                new ClosestPairSolver();

        ClosestPairSolver.Result result =
                solver.findClosestPair(points);

        System.out.println(
                "Closest Pair test:"
        );

        System.out.println(result);
    }
}