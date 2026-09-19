import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {

    private long comparisons = 0;
    private int maxRecursionDepth = 0;

    public static class Result {

        private final Point first;
        private final Point second;
        private final double distance;

        public Result(Point first, Point second, double distance) {
            this.first = first;
            this.second = second;
            this.distance = distance;
        }

        public Point getFirst() {
            return first;
        }

        public Point getSecond() {
            return second;
        }

        public double getDistance() {
            return distance;
        }

        @Override
        public String toString() {
            return first +
                    " <-> " +
                    second +
                    ", distance = " +
                    distance;
        }
    }

    public Result findClosestPair(Point[] points) {

        if (points == null || points.length < 2) {
            throw new IllegalArgumentException(
                    "At least two points are required"
            );
        }

        resetMetrics();

        Point[] sortedByX = points.clone();

        Arrays.sort(
                sortedByX,
                Comparator
                        .comparingDouble(Point::getX)
                        .thenComparingDouble(Point::getY)
        );

        Point[] sortedByY = points.clone();

        Arrays.sort(
                sortedByY,
                Comparator
                        .comparingDouble(Point::getY)
                        .thenComparingDouble(Point::getX)
        );

        return closestPair(
                sortedByX,
                sortedByY,
                1
        );
    }

    private Result closestPair(
            Point[] pointsByX,
            Point[] pointsByY,
            int depth
    ) {

        maxRecursionDepth = Math.max(
                maxRecursionDepth,
                depth
        );

        int n = pointsByX.length;

        // Base case
        if (n <= 3) {
            return bruteForce(pointsByX);
        }

        int mid = n / 2;

        Point middlePoint = pointsByX[mid];

        // Split pointsByX
        Point[] leftX = Arrays.copyOfRange(
                pointsByX,
                0,
                mid
        );

        Point[] rightX = Arrays.copyOfRange(
                pointsByX,
                mid,
                n
        );

        /*
         * IMPORTANT:
         * We divide pointsByY according to the actual points
         * contained in leftX/rightX.
         *
         * This prevents ArrayIndexOutOfBoundsException when
         * several points have the same X coordinate.
         */

        Point[] leftY = new Point[leftX.length];
        Point[] rightY = new Point[rightX.length];

        int leftIndex = 0;
        int rightIndex = 0;

        /*
         * We use the left part of pointsByX as the source
         * of membership information.
         */
        for (Point point : pointsByY) {

            boolean belongsToLeft = false;

            for (Point leftPoint : leftX) {

                if (point == leftPoint) {
                    belongsToLeft = true;
                    break;
                }
            }

            if (belongsToLeft) {
                leftY[leftIndex++] = point;
            } else {
                rightY[rightIndex++] = point;
            }
        }

        // Safety check
        if (leftIndex != leftY.length ||
                rightIndex != rightY.length) {

            throw new IllegalStateException(
                    "Incorrect division of points into left and right halves"
            );
        }

        // Recursive calls
        Result leftResult = closestPair(
                leftX,
                leftY,
                depth + 1
        );

        Result rightResult = closestPair(
                rightX,
                rightY,
                depth + 1
        );

        // Choose the better result
        Result best;

        if (leftResult.getDistance()
                <= rightResult.getDistance()) {

            best = leftResult;

        } else {

            best = rightResult;
        }

        double delta = best.getDistance();

        /*
         * Build the strip.
         *
         * pointsByY is already sorted by Y, therefore
         * the strip will also be sorted by Y.
         */
        Point[] strip = new Point[n];

        int stripSize = 0;

        for (Point point : pointsByY) {

            comparisons++;

            if (Math.abs(
                    point.getX()
                            - middlePoint.getX()
            ) < delta) {

                strip[stripSize++] = point;
            }
        }

        /*
         * Compare points inside the strip.
         */
        for (int i = 0; i < stripSize; i++) {

            for (
                    int j = i + 1;
                    j < stripSize;
                    j++
            ) {

                /*
                 * Since strip is sorted by Y,
                 * no further points can produce a smaller
                 * distance if the Y difference is >= delta.
                 */
                if (
                        strip[j].getY()
                                - strip[i].getY()
                                >= delta
                ) {
                    break;
                }

                comparisons++;

                double currentDistance =
                        distance(
                                strip[i],
                                strip[j]
                        );

                if (currentDistance < best.getDistance()) {

                    best = new Result(
                            strip[i],
                            strip[j],
                            currentDistance
                    );

                    delta = currentDistance;
                }
            }
        }

        return best;
    }

    private Result bruteForce(Point[] points) {

        double minDistance =
                Double.POSITIVE_INFINITY;

        Point first = null;
        Point second = null;

        for (int i = 0; i < points.length; i++) {

            for (
                    int j = i + 1;
                    j < points.length;
                    j++
            ) {

                comparisons++;

                double currentDistance =
                        distance(
                                points[i],
                                points[j]
                        );

                if (currentDistance < minDistance) {

                    minDistance = currentDistance;

                    first = points[i];
                    second = points[j];
                }
            }
        }

        return new Result(
                first,
                second,
                minDistance
        );
    }

    private double distance(
            Point a,
            Point b
    ) {

        double dx =
                a.getX() - b.getX();

        double dy =
                a.getY() - b.getY();

        return Math.sqrt(
                dx * dx + dy * dy
        );
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    public void resetMetrics() {
        comparisons = 0;
        maxRecursionDepth = 0;
    }
}