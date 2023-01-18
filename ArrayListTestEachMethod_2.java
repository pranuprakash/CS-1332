import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Random;

/**
 * @author Mukilan Karthikeyan
 * @version 1.0
 */
public class ArrayListTestEachMethod_2 {

    private static final int TIMEOUT = 200;
    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, list.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY],
                list.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexIndexOutOfBoundsNegative() {
        list.addAtIndex(-1, "a");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexIndexOutOfBoundsAboveSize() {
        //list is empty so only acceptable index is 0
        list.addAtIndex(3, "a");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddAtIndexNullData() {
        list.addAtIndex(0, null);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testExcpetionOrderOutOfBoundsAndNullData() {
        // based on Java's implementation of ArrayList
        list.addAtIndex(-5, null);
    }
    @Test(timeout = TIMEOUT)
    public void testAddAtIndex() {
        list.addAtIndex(0, "2a");   // 2a
        list.addAtIndex(0, "1a");   // 1a, 2a
        list.addAtIndex(2, "4a");   // 1a, 2a, 4a
        list.addAtIndex(2, "3a");   // 1a, 2a, 3a, 4a
        list.addAtIndex(0, "0a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToFrontNullData() {
        list.addToFront(null);
    }
    @Test(timeout = TIMEOUT)
    public void testAddToFront() {
        list.addToFront("4a");  // 4a
        list.addToFront("3a");  // 3a, 4a
        list.addToFront("2a");  // 2a, 3a, 4a
        list.addToFront("1a");  // 1a, 2a, 3a, 4a
        list.addToFront("0a");  // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }
    @Test(timeout = TIMEOUT)
    public void addToFrontPastFullCapacity() {
        assertEquals(0, list.size());

        for (int i = 0; i < ArrayList.INITIAL_CAPACITY + 1; i++) {
            String temp = i + "a";
            list.addToFront(temp);
        }

        assertEquals(10, list.size());
        //must cast to an object array here or else it throws and error
        assertEquals(18, ((Object[]) list.getBackingArray()).length);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToBackNullData() {
        list.addToBack(null);
    }
    @Test(timeout = TIMEOUT)
    public void testAddToBack() {
        list.addToBack("0a");   // 0a
        list.addToBack("1a");   // 0a, 1a
        list.addToBack("2a");   // 0a, 1a, 2a
        list.addToBack("3a");   // 0a, 1a, 2a, 3a
        list.addToBack("4a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }


    @Test(timeout = TIMEOUT)
    public void addToBackPastFullCapacity() {
        assertEquals(0, list.size());
        //int initialSize = list.getBackingArray().length;

        for (int i = 0; i < ArrayList.INITIAL_CAPACITY + 1; i++) {
            String temp = i + "a";
            list.addToBack(temp);
        }

        /*
        list.addToBack("0a");   // 0a
        list.addToBack("1a");   // 0a, 1a
        list.addToBack("2a");   // 0a, 1a, 2a
        list.addToBack("3a");   // 0a, 1a, 2a, 3a
        list.addToBack("4a");   // 0a, 1a, 2a, 3a, 4a
        list.addToBack("5a");
        list.addToBack("6a");
        list.addToBack("7a");
        list.addToBack("8a");
        list.addToBack("9a");

         */
        assertEquals(10, list.size());
        //must cast to an object array here or else it throws and error
        assertEquals(18, ((Object[]) list.getBackingArray()).length);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexIndexOutOfBoundsNegative() {
        list.removeAtIndex(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAtRemoveIndexIndexOutOfBoundsAboveSize() {
        //list is empty so only acceptable index is 0
        list.removeAtIndex(3);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexFront() {
        String temp = "0a"; // For equality checking.

        list.addAtIndex(0, temp);   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "5a");   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeAtIndex(0));    // 0a, 1a, 3a, 4a, 5a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexBack() {
        String temp = "5a"; // For equality checking.

        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, temp);   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeAtIndex(5));    // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    //TODO add enough to resize, then remove things below intial capacity to test that the backing array does not shrink
    @Test(timeout = TIMEOUT)
    public void testRemoveDoesNotShrinkBackingArray() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY * 2; i++) {
            String temp = i + "a";
            list.addToFront(temp);
        }
        // checks to see all elements have been added
        assertEquals(ArrayList.INITIAL_CAPACITY * 2, list.size());
        // checks to see if the backing array capacity is doubled
        assertEquals(ArrayList.INITIAL_CAPACITY * 2, ((Object[]) list.getBackingArray()).length);
        while (list.size() > 0) {
            list.removeFromBack();
        }
        // checks list is empty
        assertEquals(0, list.size());
        // checks that the back array capacity has not decreased
        assertEquals(ArrayList.INITIAL_CAPACITY * 2, ((Object[]) list.getBackingArray()).length);

    }
    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex() {
        String    temp = "2a"; // For equality checking.

        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, temp);   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "5a");   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeAtIndex(2));    // 0a, 1a, 3a, 4a, 5a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFromFrontOfEmptyList() {
        list.removeFromFront();
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        String temp = "0a"; // For equality checking.

        list.addAtIndex(0, temp);   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "5a");   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeFromFront());   // 1a, 2a, 3a, 4a, 5a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFromBackOnEmptyList() {
        list.removeFromBack();
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        String temp = "5a"; // For equality checking.

        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, temp);   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeFromBack());    // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetIndexNegative() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        list.get(-1);
    }
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetIndexGreaterThanSize() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        list.get(6);
    }
    @Test(timeout = TIMEOUT)
    public void testGet() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        assertEquals("0a", list.get(0));
        assertEquals("1a", list.get(1));
        assertEquals("2a", list.get(2));
        assertEquals("3a", list.get(3));
        assertEquals("4a", list.get(4));
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmpty() {
        // Should be empty at initialization
        assertTrue(list.isEmpty());

        // Should not be empty after adding elements
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        assertFalse(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testEmptyAfterAddingAndRemovingFromBack() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        assertFalse(list.isEmpty());

        assertSame("4a", list.removeFromBack()); // 0a, 1a, 2a, 3a
        assertSame("3a", list.removeFromBack()); // 0a, 1a, 2a
        assertSame("2a", list.removeFromBack()); // 0a, 1a
        assertSame("1a", list.removeFromBack()); // 0a
        assertSame("0a", list.removeFromBack()); // empty list

        assertTrue(list.isEmpty());

    }

    @Test(timeout = TIMEOUT)
    public void testEmptyAfterAddingAndRemovingFromFront() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        assertFalse(list.isEmpty());

        assertSame("0a", list.removeFromFront()); // 1a, 2a, 3a, 4a
        assertSame("1a", list.removeFromFront()); // 2a, 3a, 4a
        assertSame("2a", list.removeFromFront()); // 3a, 4a
        assertSame("3a", list.removeFromFront()); // 4a
        assertSame("4a", list.removeFromFront()); // empty list

        assertTrue(list.isEmpty());

    }

    @Test(timeout = TIMEOUT)
    public void testEmptyAfterAddingAndRemovingAtIndex() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        assertFalse(list.isEmpty());

        assertSame("3a", list.removeAtIndex(3)); // 0a, 1a, 2a, 4a
        assertSame("2a", list.removeAtIndex(2)); // 0a, 1a, 4a
        assertSame("4a", list.removeAtIndex(2)); // 0a, 1a
        assertSame("0a", list.removeAtIndex(0)); // 1a
        assertSame("1a", list.removeAtIndex(0)); // empty list

        assertTrue(list.isEmpty());

    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        // Clearing the list should empty the array and reset size
        list.clear();

        assertEquals(0, list.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY],
                list.getBackingArray());

        assertTrue(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testAtVolume() {
        Random rand = new Random();
        assertEquals(0, list.size());

        for (int i = 0; i < 100; i++) {
            String temp = i + "a";
            list.addToFront(temp);
        }

        assertEquals(100, list.size());
        //must cast to an object array here or else it throws and error
        assertEquals(144, ((Object[]) list.getBackingArray()).length);


        while (list.size() != 0) {
            list.removeAtIndex(rand.nextInt(list.size()));
        }

        assertEquals(0, list.size());
        assertEquals(144, ((Object[]) list.getBackingArray()).length);
    }

    @Test(timeout = TIMEOUT)
    public void testAddMixed() {
        Random rand = new Random();

        for (int i = 0; i < 300; i++) {
            int r = rand.nextInt(3);
            switch(r) {
                case 0:
                    list.addAtIndex(rand.nextInt(list.size()),i + "a");
                    break;
                case 1:
                    list.addToFront(i + "a");
                    break;
                case 2:
                    list.addToBack(i + "a");
                    break;
            }
        }
        assertEquals(300, list.size());
        assertEquals(576, ((Object[]) list.getBackingArray()).length);

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtVolume() {
        Random rand = new Random();
        assertEquals(0, list.size());

        for (int i = 0; i < 300; i++) {
            String temp = i + "a";
            list.addToFront(temp);
        }

        assertEquals(576, ((Object[]) list.getBackingArray()).length);
        for (int i = 0; i < 100; i++) {
            int r = rand.nextInt(3);
            switch(r) {
                case 0:
                    list.removeFromBack();
                    break;
                case 1:
                    list.removeFromFront();
                    break;
                case 2:
                    list.removeAtIndex(rand.nextInt(list.size()));
                    break;
            }
        }

        assertEquals(200, list.size());
        assertEquals(576, ((Object[]) list.getBackingArray()).length);

    }
}