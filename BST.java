import java.sql.Array;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
 *
 * @author Pranu Prakash
 * @version 1.0
 * @userid pprakash49
 * @GTID 903703245
 *
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
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
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
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

    private BSTNode<T> rAdd(BSTNode<T> curr, T value) {
        if (curr == null) {
            size++;
            return new BSTNode<>(value);
        }

        if (value.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), value));
        } else if (value.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), value));
        }

        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (!contains(data)) {
            throw new NoSuchElementException("Data is not in tree.");
        }

        if (data == null) {
            throw new IllegalArgumentException("Entered argument is illegal. Data is null.");
        }

        BSTNode<T> tempNode = new BSTNode<>(null);
        root = rRemove(root, tempNode, data);

        return tempNode.getData();
    }

    private BSTNode<T> rRemove(BSTNode<T> curr, BSTNode<T> temp, T value) {
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
                return curr.getLeft();
            } else if (curr.getLeft() == null && curr.getRight() != null) {
                temp.setData(curr.getData());
                size--;
                return curr.getRight();
            } else {
                temp.setData(curr.getData());
                size--;
                BSTNode<T> tempNode2 = new BSTNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), tempNode2));
                curr.setData(tempNode2.getData());
            }
        }

        return curr;
    }

    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> newSuccessor) {
        if (curr.getLeft() == null) {
            newSuccessor.setData(curr.getData());
            return curr.getRight();
        }

        curr.setLeft(removeSuccessor(curr.getLeft(), newSuccessor));
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Entered argument is illegal. Data is null.");
        }

//        if (!contains(data)) {
//            throw new NoSuchElementException("Data is not in tree.");
//        }

        return rGet(root, data);
    }

    private T rGet(BSTNode<T> curr, T value) {
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
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
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

    private boolean rContains(BSTNode<T> curr, T value) {
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
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> returnList = new ArrayList<>();
        rPreOrder(root, returnList);
        return returnList;
    }

    private void rPreOrder(BSTNode<T> curr, List<T> currList) {
        if (curr != null) {
            currList.add(curr.getData());
            rPreOrder(curr.getLeft(), currList);
            rPreOrder(curr.getRight(), currList);
        }

    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> returnList = new ArrayList<>();
        rInOrder(root, returnList);
        return returnList;
    }

    private void rInOrder(BSTNode<T> curr, List<T> currList) {
        if (curr != null) {
            rInOrder(curr.getLeft(), currList);
            currList.add(curr.getData());
            rInOrder(curr.getRight(), currList);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> returnList = new ArrayList<>();
        rPostOrder(root, returnList);
        return returnList;
    }

    private void rPostOrder(BSTNode<T> curr, List<T> currList) {
        if (curr != null) {
            rPostOrder(curr.getLeft(), currList);
            rPostOrder(curr.getRight(), currList);
            currList.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> returnList = new ArrayList<>();

        if (root == null) {
            return returnList;
        }

        Queue<BSTNode<T>> levelQueue = new LinkedList<>(); // new ArrayList<>();
        levelQueue.add(root);

        while (!(levelQueue.isEmpty())) {
            BSTNode<T> currNode = levelQueue.remove();
            returnList.add(currNode.getData());

            if (currNode.getLeft() != null) {
                levelQueue.add(currNode.getLeft());
            }

            if (currNode.getRight() != null) {
                levelQueue.add(currNode.getRight());
            }
        }

        return returnList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }

        return rHeight(root);
    }

    private int rHeight(BSTNode<T> currNode) {
        if (currNode == null) {
            return -1;
        }

        return 1 + Math.max(rHeight(currNode.getLeft()), rHeight(currNode.getRight()));
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("K is not valid. Needs to be in the range [0, size]");
        }

        List<T> returnList = new ArrayList<>();

        rKLargest(root, returnList, k);
        return returnList;

    }

    private void rKLargest(BSTNode<T> curr, List<T> listK, int sizeOfArr) {
        if (curr == null) {
            return;
        }
        rKLargest(curr.getRight(), listK, sizeOfArr);
        if (listK.size() == sizeOfArr) {
            return;
        }
        listK.add(0, curr.getData());
        rKLargest(curr.getLeft(), listK, sizeOfArr);
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
