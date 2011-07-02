package glass.terry;
import org.junit.Test;
import static org.junit.Assert.*;
import glass.terry.ReversibleList;

public class ReversibleListTests {
    private ReversibleList<Character> f_list;

    public ReversibleListTests() {
    }

    public void setup() {
        f_list = new ReversibleList<Character>();
    }

    @Test
    public void testReverse() {
        char[] chars = {'a', 'b', 'c', 'd'};
        for(char c : chars) {
            f_list.add(c);
        }
        f_list.reverse();
        String expected = "a b c d";
        String actual = f_list.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testAdd() {
        f_list.add('a');
        String expected = "a";
        String actual = f_list.toString();
        assertEquals(expected, actual);
    }
}
