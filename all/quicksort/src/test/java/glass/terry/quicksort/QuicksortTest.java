package glass.terry.quicksort;

import org.junit.*;
import static org.junit.Assert.assertArrayEquals;

public class QuicksortTest {
    @Test
    public void sortTest() {
        Integer[] nums = {3, 8, 10, 9, 4, 5, 1, 7, 2, 6};
        Integer[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        Quicksort.sort(nums);
        assertArrayEquals(expected, nums);
	System.out.println("Expected: " + expected);
	System.out.println("Actual: "+ nums);
    }
}
