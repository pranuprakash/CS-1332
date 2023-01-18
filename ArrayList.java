import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayList.
 *
 * @author Pranu Prakash
 * @version 1.0
 * @userid pprakash49
 * @GTID 903703245
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Cannot add at a negative index.");
        }

        if (index > size) { // size == backingArray.length  ??
            throw new IndexOutOfBoundsException("Cannot add at an index greater than size.");
        }

        if (data == null) { // data.equals(null) doesn't work  ??
            throw new IllegalArgumentException("Illegal argument is entered. Cannot add null data to data structure.");
        }

        if (size >= backingArray.length) {
            T[] duplicateArr = (T[]) new Object[backingArray.length * 2];

            if (index == 0) {
                duplicateArr[0] = data;

                for (int i = 0; i < backingArray.length; i++) {
                    duplicateArr[i + 1] = backingArray[i];
                }

                backingArray = duplicateArr;
                size++;
            } else if (index == size) {
                for (int i = 0; i < backingArray.length; i++) { // i < size  ??
                    duplicateArr[i] = backingArray[i];
                }

                duplicateArr[index] = data; // index == size
                backingArray = duplicateArr;
                size++;
            } else {
                for (int i = 0; i < size + 1; i++) {
                    if (i < index) {
                        duplicateArr[i] = backingArray[i];
                    } else if (i == index) {
                        duplicateArr[i] = data;
                    } else if (i > index) {
                        duplicateArr[i] = backingArray[i - 1];
                    }
                }

                backingArray = duplicateArr;
                size++;
            }
        } else {
            if (index == size) {  // index == size && !(size >= backingArray.length)
                backingArray[size] = data; // index == size;
                size++;
            } else {
                for (int i = size; i > index; i--) {
                    backingArray[i] = backingArray[i - 1];
                }

                backingArray[index] = data;
                size++;
            }
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Cannot remove data from a negative index.");
        }

        if (index >= size) {
            throw new IndexOutOfBoundsException("Cannot remove data from an index greater than size.");
        }

        if (index == size - 1) {
            T returnData = backingArray[size - 1];
            backingArray[size - 1] = null;
            size--;
            return returnData;
        } else {
            T returnData = backingArray[index];
            for (int i = index; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }
            backingArray[size - 1] = null;
            size--;
            return returnData;
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty. Cannot remove from an empty list.");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty. Cannot remove from an empty list.");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds. Enter a valid index.");
        }

        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        T[] duplicateArr = (T[]) new Object[backingArray.length];

        backingArray = duplicateArr;
        size = 0;
    }

    /**
     * Returns the backing array of the list.
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
     * Returns the size of the list.
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