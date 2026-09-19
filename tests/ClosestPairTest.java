public class ClosestPairTest {

    public static void main(String[] args) {

        Point[] points = {
                new Point(0, 0),
                new Point(5, 5),
                new Point(1, 1),
                new Point(10, 10),
                new Point(1.1, 1.1)
        };

        ClosestPairSolver solver = new ClosestPairSolver();

        ClosestPairSolver.Result result =
                solver.findClosestPair(points);

        System.out.println("Closest Pair test:");
        System.out.println(result);

        System.out.println("Closest Pair test passed!");
    }
}