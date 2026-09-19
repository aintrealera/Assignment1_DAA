import java.util.Arrays;
import java.util.Random;

public class DeterministicSelectTest {

    public static void main(String[] args) {

        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {

            int size = 1 + random.nextInt(100);

            int[] array = new int[size];

            for (int i = 0; i < size; i++) {
                array[i] = random.nextInt(1000);
            }

            int k = random.nextInt(size);

            int[] expectedArray = array.clone();
            Arrays.sort(expectedArray);

            DeterministicSelector selector =
                    new DeterministicSelector();

            int result = selector.select(array, k);

            if (result != expectedArray[k]) {
                throw new AssertionError(
                        "Select failed at test " + test
                                + ", k = " + k
                                + ", expected = " + expectedArray[k]
                                + ", got = " + result
                );
            }
        }

        System.out.println(
                "100 Deterministic Select tests passed!"
        );
    }
}