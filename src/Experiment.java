import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Experiment {

    private static final Random RANDOM = new Random(42);

    private static final int[] SIZES = {
            100,
            500,
            1000,
            5000,
            10000,
            20000
    };

    private enum InputType {
        RANDOM,
        SORTED,
        REVERSE,
        DUPLICATE_HEAVY
    }

    public static void runExperiments() {

        File directory = new File("results");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = "results/results.csv";

        try (FileWriter writer =
                     new FileWriter(fileName)) {

            writer.write(
                    "algorithm,input_type,n,time_ns," +
                            "recursion_depth,operations\n"
            );

            for (InputType type : InputType.values()) {

                for (int n : SIZES) {

                    int[] data =
                            generateArray(n, type);

                    runMergeSort(
                            data,
                            type,
                            writer
                    );

                    runQuickSort(
                            data,
                            type,
                            writer
                    );

                    runSelect(
                            data,
                            type,
                            writer
                    );

                    if (n <= 20000) {

                        runClosestPair(
                                n,
                                type,
                                writer
                        );
                    }
                }
            }

            System.out.println(
                    "Experiments saved to "
                            + fileName
            );

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private static void runMergeSort(
            int[] original,
            InputType type,
            FileWriter writer
    ) throws IOException {

        int[] data =
                original.clone();

        MergeSorter sorter =
                new MergeSorter();

        long start =
                System.nanoTime();

        sorter.sort(data);

        long end =
                System.nanoTime();

        writer.write(
                "MergeSort,"
                        + type + ","
                        + data.length + ","
                        + (end - start) + ","
                        + "0,"
                        + sorter.getComparisons()
                        + "\n"
        );
    }

    private static void runQuickSort(
            int[] original,
            InputType type,
            FileWriter writer
    ) throws IOException {

        int[] data =
                original.clone();

        QuickSorter sorter =
                new QuickSorter();

        long start =
                System.nanoTime();

        sorter.sort(data);

        long end =
                System.nanoTime();

        writer.write(
                "QuickSort,"
                        + type + ","
                        + data.length + ","
                        + (end - start) + ","
                        + sorter.getMaxRecursionDepth()
                        + ","
                        + sorter.getComparisons()
                        + "\n"
        );
    }

    private static void runSelect(
            int[] original,
            InputType type,
            FileWriter writer
    ) throws IOException {

        int[] data =
                original.clone();

        int k =
                data.length / 2;

        DeterministicSelector selector =
                new DeterministicSelector();

        long start =
                System.nanoTime();

        selector.select(data, k);

        long end =
                System.nanoTime();

        writer.write(
                "DeterministicSelect,"
                        + type + ","
                        + data.length + ","
                        + (end - start) + ","
                        + selector.getMaxRecursionDepth()
                        + ","
                        + selector.getComparisons()
                        + "\n"
        );
    }

    private static void runClosestPair(
            int n,
            InputType type,
            FileWriter writer
    ) throws IOException {

        Point[] points =
                generatePoints(n, type);

        ClosestPairSolver solver =
                new ClosestPairSolver();

        long start =
                System.nanoTime();

        solver.findClosestPair(points);

        long end =
                System.nanoTime();

        writer.write(
                "ClosestPair,"
                        + type + ","
                        + n + ","
                        + (end - start) + ","
                        + solver.getMaxRecursionDepth()
                        + ","
                        + solver.getComparisons()
                        + "\n"
        );
    }

    private static int[] generateArray(
            int n,
            InputType type
    ) {

        int[] array =
                new int[n];

        switch (type) {

            case RANDOM:

                for (int i = 0; i < n; i++) {

                    array[i] =
                            RANDOM.nextInt(
                                    n * 10 + 1
                            );
                }

                break;

            case SORTED:

                for (int i = 0; i < n; i++) {
                    array[i] = i;
                }

                break;

            case REVERSE:

                for (int i = 0; i < n; i++) {
                    array[i] = n - i;
                }

                break;

            case DUPLICATE_HEAVY:

                for (int i = 0; i < n; i++) {

                    array[i] =
                            RANDOM.nextInt(10);
                }

                break;
        }

        return array;
    }

    private static Point[] generatePoints(
            int n,
            InputType type
    ) {

        Point[] points =
                new Point[n];

        for (int i = 0; i < n; i++) {

            double x;
            double y;

            switch (type) {

                case SORTED:

                    x = i;
                    y = RANDOM.nextDouble() * n;
                    break;

                case REVERSE:

                    x = n - i;
                    y = RANDOM.nextDouble() * n;
                    break;

                case DUPLICATE_HEAVY:

                    x = RANDOM.nextInt(20);
                    y = RANDOM.nextInt(20);
                    break;

                case RANDOM:
                default:

                    x = RANDOM.nextDouble() * n;
                    y = RANDOM.nextDouble() * n;
                    break;
            }

            points[i] =
                    new Point(x, y);
        }

        return points;
    }
}