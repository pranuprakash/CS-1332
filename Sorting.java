import java.util.Comparator;
import java.util.Random;
import java.util.List;
import java.util.PriorityQueue;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Pranu Prakash
 * @version 1.0
 * @userid pprakash49
 * @GTID 903703245
 *
 */
public class Sorting {

    /**
     * Implement selection sort.
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Entered array is null. Selection sort cannot be done.");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Entered comparator is null. Selection sort cannot be done.");
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int lowest = i;

            for (int j = i + 1; j < arr.length; j++) { // arr.length - 1 ?
                if (comparator.compare(arr[j], arr[lowest]) < 0) {
                    lowest = j;
                }
            }

            swap(arr, lowest, i);
        }
    }

    /**
     * This is the swap helper method for all methods.
     *
     * @param arr array to be swapped
     * @param a   lower index
     * @param b   upper index
     * @param <T> type for data
     */
    private static <T> void swap(T[] arr, int a, int b) {
        T dataA = arr[a];
        arr[a] = arr[b];
        arr[b] = dataA;
    }

    /**
     * Implement cocktail sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Entered array is null. Cocktail sort cannot be done.");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Entered comparator is null. Cocktail sort cannot be done.");
        }

        int a = 0;
        int b = arr.length - 1;

        // int swapped = a;

        while (b > a) {
            int swapped = a;
            for (int i = a; i < b; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) >= 0) {
                    swap(arr, i, i + 1);
                    swapped = i;
                }
            }

            b = swapped;

            for (int i = b; i > a; i--) {
                if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                    swap(arr, i, i - 1);
                    swapped = i;
                }
            }

            a = swapped;
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Entered array is null. Merge sort cannot be done.");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Entered comparator is null. Merge sort cannot be done.");
        }

        if (arr.length == 1) {
            return; // needed ?
        }

        if (arr.length > 1) {
            T[] leftArr = (T[]) new Object[arr.length / 2];
            T[] rightArr = (T[]) new Object[arr.length - (arr.length / 2)]; // leftArr.length

            for (int i = 0; i < leftArr.length; i++) {
                leftArr[i] = arr[i];
            }

            for (int j = leftArr.length; j < arr.length; j++) {
                rightArr[j - (arr.length / 2)] = arr[j];
            }

            mergeSort(leftArr, comparator);
            mergeSort(rightArr, comparator);

            rMerge(arr, comparator, leftArr, rightArr);
        }
    }

    /**
     * Merge Sort helper method
     * @param arr backing array
     * @param comp comparator to compare
     * @param left left half of the array
     * @param right right half of the array
     * @param <T> type of data that is being sorted
     */

    private static <T> void rMerge(T[] arr, Comparator<T> comp, T[] left, T[] right) {
        int i = 0;
        int j = 0;

        while (i + j < arr.length) {
            if (j >= right.length || (i < left.length && comp.compare(left[i], right[j]) <= 0)) {
                arr[i + j] = left[i++];
            } else {
                arr[i + j] = right[j++];
            }
        }
    }

    /**
     * Implement kth select.
     * <p>
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     * <p>
     * int pivotIndex = rand.nextInt(b - a) + a;
     * <p>
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     * <p>
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * <p>
     * It should be:
     * in-place
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Entered array is null. kth select sort cannot be done.");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Entered comparator is null. kth select sort cannot be done.");
        }

        if (rand == null) {
            throw new IllegalArgumentException("Entered rand is null. kth select sort cannot be done.");
        }

        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k is not in range. kth select sort cannot be done.");
        }

        return rKthSelect(k, arr, comparator, rand, 0, arr.length);
    }

    /**
     * Helper method for kth select
     * @param k k integer
     * @param arr backing array
     * @param comparator comparator to compare
     * @param rand random for random index
     * @param a left end
     * @param b right end
     * @return the kth element in the array
     * @param <T> the type of data that is being sorted
     */
    private static <T> T rKthSelect(int k, T[] arr, Comparator<T> comparator, Random rand, int a, int b) {
        int pivotIndex = rand.nextInt(b - a) + a;
        T pivotVal = arr[pivotIndex];

        swap(arr, a, pivotIndex);

        int i = a + 1;
        int j = b - 1;

        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                i++;
            }

            while (i <= j && comparator.compare(arr[j], pivotVal) >= 0) {
                j--;
            }

            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }

        swap(arr, a, j);

        if (j == k - 1) {
            return arr[j];
        }

        if (j > k - 1) {
            return rKthSelect(k, arr, comparator, rand, a, j); // j ??
        } else {
            return rKthSelect(k, arr, comparator, rand, j + 1, b);
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     * <p>
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * <p>
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Entered array is null. lsd radix sort cannot be done.");
        }

        int maxNum = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (Math.abs(arr[i]) > maxNum) {
                maxNum = Math.abs(arr[i]);
            }
        }

        int iterations = 1;
        while (maxNum >= 10) {
            iterations++;
            maxNum = maxNum / 10;
        }

        LinkedList<Integer>[] bucket = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            bucket[i] = new LinkedList<>();
        }

        int divisor = 1;
        for (int i = 0; i < iterations; i++) {
            for (Integer num : arr) {
                int currentDigit = (num / divisor) % 10 + 9;
                bucket[currentDigit].add(num); // addLast() ??
            }
            int index = 0;
            for (LinkedList<Integer> pints : bucket) {
                for (Integer item : pints) {
                    arr[index] = item;
                    index++;
                }
                pints.clear();
            }
            divisor *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null. Cannot sort null data in heapSort");
        }

        PriorityQueue<Integer> pQ = new PriorityQueue<>(data);
        int[] finalArr = new int[data.size()]; // data.size()

        for (int i = 0; i < data.size(); i++) {
            finalArr[i] = pQ.remove();
        }

        return finalArr;
    }
}
