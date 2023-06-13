import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * Your implementation of an AVL.
 *
 * @author Pranu Prakash
 * @version 1.0
 * @userid pprakash49
 * @GTID 903703245
 *
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Entered argument is illegal. Data is null.");
        }

        for (T dataCollection : data) {
            if (dataCollection == null) {
                throw new IllegalArgumentException("Entered argument is illegal. Data is null.");
            } else {
                add(dataCollection);
            }
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Entered argument is illegal. Data is null.");
        }

        root = rAdd(root, data);
    }

    /**
     * Helper method for the AVL add().
     * @param curr current node
     * @param value data to be added
     * @return AVLNode
     */

    private AVLNode<T> rAdd(AVLNode<T> curr, T value) {
        if (curr == null) {
            size++;
            return new AVLNode<>(value);
        } else {
            if (value.compareTo(curr.getData()) < 0) {
                curr.setLeft(rAdd(curr.getLeft(), value));
            } else if (value.compareTo(curr.getData()) > 0) {
                curr.setRight(rAdd(curr.getRight(), value));
            }

            update(curr); // update the height of current node
            return rotate(curr); // balance the tree after add and make appropriate rotation

            // AVLNode<T> dummyNode = rotate(curr);
            // update(curr);
            // return dummyNode;
        }
    }

    /**
     * Update method for the AVL methods.
     * @param currUpdate the node to be updated
     */

    private void update(AVLNode<T> currUpdate) {
        int leftChildHeight = 0;
        int rightChildHeight = 0;

        if (currUpdate.getLeft() == null) {
            leftChildHeight = -1;
        } else {
            leftChildHeight = currUpdate.getLeft().getHeight();
        }

        if (currUpdate.getRight() == null) {
            rightChildHeight = -1;
        } else {
            rightChildHeight = currUpdate.getRight().getHeight();
        }

        currUpdate.setHeight(Math.max(leftChildHeight, rightChildHeight) + 1);
        currUpdate.setBalanceFactor(leftChildHeight - rightChildHeight);
    }

    /**
     * Balance method for the AVL
     * @param currRotate node to be balanced
     * @return AVLNode
     */
    private AVLNode<T> rotate(AVLNode<T> currRotate) {
        if (currRotate.getBalanceFactor() < -1) {
            if (currRotate.getRight().getBalanceFactor() > 0) {
                currRotate.setRight(rightRotate(currRotate.getRight()));
            }

            currRotate = leftRotate(currRotate);
        } else if (currRotate.getBalanceFactor() > 1) {
            if (currRotate.getLeft().getBalanceFactor() < 0) {
                currRotate.setLeft(leftRotate(currRotate.getLeft()));
            }

            currRotate = rightRotate(currRotate);
        }

        return currRotate;
    }

    /**
     * Right rotate method for the AVL
     * @param node the pivot node for the right rotate
     * @return AVLNode
     */

    private AVLNode<T> rightRotate(AVLNode<T> node) {
        AVLNode<T> nodeLeft = node.getLeft();
        node.setLeft(nodeLeft.getRight());
        nodeLeft.setRight(node);

        update(node);
        update(nodeLeft);
        return nodeLeft;
    }

    /**
     * Left rotate method for the AVL
     * @param node the pivot node for the left rotate
     * @return AVLNode
     */

    private AVLNode<T> leftRotate(AVLNode<T> node) {
        AVLNode<T> nodeRight = node.getRight();
        node.setRight(nodeRight.getLeft());
        nodeRight.setLeft(node);

        update(node);
        update(nodeRight);

        return nodeRight;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Entered argument is illegal. Data is null.");
        }

        AVLNode<T> tempNode = new AVLNode<>(null);
        root = rRemove(root, tempNode, data);

        return tempNode.getData();
    }

    /**
     * Helper method for AVL remove()
     * @param curr current node to traverse
     * @param temp temp node to store returned data
     * @param value value to be removed
     * @return AVLNode
     */

    private AVLNode<T> rRemove(AVLNode<T> curr, AVLNode<T> temp, T value) {
        if (curr == null) { // checks if root is null
            throw new NoSuchElementException("Node is null. Cannot remove null data.");
        }

        if (value.compareTo(curr.getData()) < 0) {
            curr.setLeft(rRemove(curr.getLeft(), temp, value));
        } else if (value.compareTo(curr.getData()) > 0) {
            curr.setRight(rRemove(curr.getRight(), temp, value));
        } else {
            if (curr.getLeft() == null && curr.getRight() == null) {
                temp.setData(curr.getData());
                size--;

                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                temp.setData(curr.getData());
                size--;

                update(curr.getLeft());
                return rotate(curr.getLeft());
            } else if (curr.getLeft() == null && curr.getRight() != null) {
                temp.setData(curr.getData());
                size--;

                update(curr.getRight());
                return rotate(curr.getRight());
            } else {
                temp.setData(curr.getData());
                size--;

                AVLNode<T> tempNode2 = new AVLNode<>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), tempNode2));
                curr.setData(tempNode2.getData());
            }
        }

        update(curr);
        return rotate(curr);
    }

    /**
     * Remove predecessor method for the AVL remove()
     * @param curr node to be traversed
     * @param newPredecessor node to be determined as new predecessor
     * @return AVLNode
     */

    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> newPredecessor) {
        if (curr.getRight() == null) { // curr.getLeft() ??
            newPredecessor.setData(curr.getData());
            return curr.getLeft(); // curr.getRight() ??
        }

        curr.setRight(removePredecessor(curr.getRight(), newPredecessor));
        return curr;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Entered argument is illegal. Data is null.");
        }

        return rGet(root, data);
    }

    /**
     * Helper method for the AVL get()
     * @param curr node to be traversed
     * @param value value to be gotten
     * @return data that was in existing node
     */

    private T rGet(AVLNode<T> curr, T value) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in tree.");
        }
        if (value.compareTo(curr.getData()) < 0) {
            return rGet(curr.getLeft(), value);
        } else if (value.compareTo(curr.getData()) > 0) {
            return rGet(curr.getRight(), value);
        } else {
            return curr.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Entered argument is illegal. Data is null.");
        }

        return rContains(root, data);
    }

    /**
     * Helper method for the AVL contains()
     * @param curr node to be traversed
     * @param value value to be checked if existed
     * @return boolean true or false
     */

    private boolean rContains(AVLNode<T> curr, T value) {
        if (curr == null) {
            return false;
        }

        if (value.compareTo(curr.getData()) < 0) {
            return rContains(curr.getLeft(), value);
        } else if (value.compareTo(curr.getData()) > 0) {
            return rContains(curr.getRight(), value);
        }

        return true;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }

        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * This must be done recursively.
     *
     * Your list should not have duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> newList = new ArrayList<>();

        rDeepestBranches(root, newList);
        return newList;
    }

    /**
     * Helper method for the deepest branch.
     * @param curr node to be traversed
     * @param arr list to which the data should be added to.
     */

    private void rDeepestBranches(AVLNode<T> curr, List<T> arr) {
        if (curr == null) {
            return;
        } else {
            arr.add(curr.getData());

            if ((curr.getLeft() != null)
                    && (curr.getHeight() - curr.getLeft().getHeight() <= 1)) { // difference in height is <= 1
                rDeepestBranches(curr.getLeft(), arr);
            }

            if ((curr.getRight() != null)
                    && (curr.getHeight() - curr.getRight().getHeight() <= 1)) {
                rDeepestBranches(curr.getRight(), arr);
            }
        }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * This must be done recursively.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @return a sorted list of data that is > data1 and < data2
     * @throws IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null) {
            throw new IllegalArgumentException("Inputted data1 is null.");
        }

        if (data2 == null) {
            throw new IllegalArgumentException("Inputted data2 is null.");
        }

        if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("Data 1 is greater than Data 2.");
        }

        List<T> aList = new ArrayList<>();

        if (data1.compareTo(data2) == 0) { // data1.equals(data2)
            return aList;
        }

        rSortedInBetween(root, aList, data1, data2);
        return aList;
    }

    /**
     * Helper method for the AVL sortedInBetween()
     * @param curr node to be traversed
     * @param arr list to which the data is added to
     * @param dataA start of range
     * @param dataB end of range
     */

    private void rSortedInBetween(AVLNode<T> curr, List<T> arr, T dataA, T dataB) {
        if (curr == null) {
            return;
        } else {
            if (curr.getData().compareTo(dataA) <= 0) { // curr < dataA
                rSortedInBetween(curr.getRight(), arr, dataA, dataB);
            }

            if (curr.getData().compareTo(dataA) > 0) { // curr > dataA
                rSortedInBetween(curr.getLeft(), arr, dataA, dataB);

                if ((curr.getData().compareTo(dataA) > 0) // curr > dataA && curr < dataB
                        && (curr.getData().compareTo(dataB) < 0)) {
                    arr.add(curr.getData());
                }

                rSortedInBetween(curr.getRight(), arr, dataA, dataB);
            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
