import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author YOUR NAME HERE
 * @version 1.0
 * @userid YOUR USER ID HERE (i.e. gburdell3)
 * @GTID YOUR GT ID HERE (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * Consider how to most efficiently determine if the list contains null data.
     * 
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Entered argument is null. Illegal argument exception thrown");
        }

        backingArray = (T[]) new Comparable[(data.size() * 2) + 1]; // 2n + 1

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Cannot copy null data.");
            }

            backingArray[i + 1] = data.get(i);
            size++;
        }

        for (int j = size / 2; j > 0; j--) {
            heapify(j);
        }
    }

    /**
     * private helper method for upHeap() method for the above constructor
     * @param value that represents the int to be changed
     */
    private void heapify(int value) {
        while (size >= value * 2) {
            int child = 2 * value;
            if (child < size && backingArray[child].compareTo(backingArray[child + 1]) < 0) {
                child++;
            }

            if (!(backingArray[value].compareTo(backingArray[child]) < 0)) {
                break;
            }

            swap(child, value);
            value = child;
        }
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Entered argument is null. Illegal argument exception thrown");
        }

        if (size == backingArray.length - 1) {
            T[] dupArr = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                dupArr[i] = backingArray[i];
            }

            backingArray = dupArr;
        }

        // int indexOfData = size + 1;

        size++;
        backingArray[size] = data;
        int tempVal = size;

        while (tempVal > 1 && backingArray[tempVal / 2].compareTo(backingArray[tempVal]) < 0) {
            swap(tempVal, tempVal / 2);

            tempVal = tempVal / 2;
        }
    }

    /**
     * This is the helper method for the add() method for Max Heaps.
     * @param rChild child index
     * @param rParent parent index
     */
    private void swap(int rChild, int rParent) {
        T temp = backingArray[rParent];
        backingArray[rParent] = backingArray[rChild];
        backingArray[rChild] = temp;
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (backingArray[1] == null || size == 0) {
            throw new NoSuchElementException("Heap is empty. Cannot remove from an empty heap."); // isEmpty()
        }

        T maxElement = backingArray[1];
        backingArray[1] = backingArray[size];
        // swap(1, size);
        heapify(1);
        backingArray[size] = null;
        size--;

        return maxElement;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (isEmpty()) { // size == 0
            throw new NoSuchElementException("Heap is empty. Cannot get data from empty heap.");
        }

        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
