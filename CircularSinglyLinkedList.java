import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Pranu Prakash
 * @version 1.0
 * @userid pprakash49
 * @GTID 903703245
 *
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
    */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Cannot add at a negative index.");
        }

        if (index > size) {
            throw new IndexOutOfBoundsException("Cannot add at an index greater than size.");
        }

        if (data == null) {
            throw new IllegalArgumentException("Illegal argument is entered. Cannot add null data to data structure.");
        }

        if (index == 0) { // addToFront()
            if (size == 0) {
                CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(data);
                head = newNode;
                newNode.setNext(head);

                size++;
            } else {
                CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(head.getData());
                newNode.setNext(head.getNext());
                head.setNext(newNode);

                // head.getNext().setData(head.getData());
                head.setData(data);

                size++;
            }
        } else if (index == size) { // addToBack()
            addAtIndex(0, data);
            head = head.getNext();
        } else { // addAtIndex()
            CircularSinglyLinkedListNode<T> currNode = head;

            // int count = 0;
            for (int i = 0; i < index - 1; i++) {
                currNode = currNode.getNext();
            }

            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(data);

            newNode.setNext(currNode.getNext());
            currNode.setNext(newNode);
            size++;
        }
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Cannot remove at a negative index.");
        }

        if (index >= size) {
            throw new IndexOutOfBoundsException("Cannot remove at an index equal or greater than size.");
        }

        if (index == 0) { // removeFromFront()
            if (size == 1) {
                T returnData = head.getData();

                head = null;
                // head.setNext(head);

                size--;
                return returnData;
            } else {
                T returnData = head.getData();

                head.setData(head.getNext().getData());
                head.setNext(head.getNext().getNext());

                size--;
                return returnData;
            }
        } else if (index == size - 1) { // removeFromBack()
            CircularSinglyLinkedListNode<T> currNode = head;

            for (int i = 0; i < size - 2; i++) {
                currNode = currNode.getNext();
            }

            T returnData = currNode.getNext().getData();
            currNode.setNext(head);

            size--;
            return returnData;
        } else { // removeAtIndex()
            CircularSinglyLinkedListNode<T> currNode = head;

            for (int i = 0; i < index - 1; i++) {
                currNode = currNode.getNext();
            }

            T returnData = currNode.getNext().getData();
            currNode.setNext(currNode.getNext().getNext());

            size--;
            return returnData;
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Cannot get data at a negative index.");
        }

        if (index >= size) {
            throw new IndexOutOfBoundsException("Cannot get data at an index equal or greater than size.");
        }

        if (index == 0) {
            T returnData = head.getData();

            return returnData;
        } else {
            CircularSinglyLinkedListNode<T> currNode = head;

            for (int i = 0; i < index - 1; i++) {
                currNode = currNode.getNext();
            }

            T returnData = currNode.getNext().getData();

            return returnData;
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }

        return false;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Passed in data is null.");
        }

        if (size == 1) {
            if (head.getData().equals(data)) {
                return removeAtIndex(0);
                // head = null;
                // size = 0;
            } else {
                throw new NoSuchElementException("Data does not exist in the list");
            }
        } else { // size > 1
            CircularSinglyLinkedListNode<T> dataNode = new CircularSinglyLinkedListNode<>(null);

            if (head.getData().equals(data)) {
                return removeAtIndex(0);
            } else {
                CircularSinglyLinkedListNode<T> currNode = head;
                int count = 0;

                for (int i = 0; i < size - 1; i++) {
                    if (currNode.getNext().getData().equals(data)) {
                        dataNode = currNode;
                        count++;

                        currNode = currNode.getNext();
                    } else {
                        currNode = currNode.getNext();
                    }
                }

                if (dataNode.getData() == null || count == 0) {
                    throw new NoSuchElementException("Data does not exist in list.");
                } else {
                    T returnData = dataNode.getNext().getData();
                    dataNode.setNext(dataNode.getNext().getNext());

                    size--;
                    return returnData;
                }
            }
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] newArr = (T[]) new Object[size];

        CircularSinglyLinkedListNode<T> currNode = head;

        for (int i = 0; i < size; i++) {
            newArr[i] = currNode.getData();
            currNode = currNode.getNext();
        }

        return newArr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
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
        // DO NOT MODIFY!
        return size;
    }
}
