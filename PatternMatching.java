import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Pranu Prakash
 * @version 1.0
 * @userid pprakash49
 * @GTID 903703245
 *
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the buildFailureTable() method before implementing
     * this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern is not in right format. Cannot do KMP algorithm");
        }

        if (text == null) {
            throw new IllegalArgumentException("Text is null. Cannot do KMP algorithm");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null. Cannot do KMP algorithm");
        }

        List<Integer> matchArr = new ArrayList<>();

        int m = pattern.length();
        int n = text.length();

        if (m > n) {
            return matchArr;
        }

        int[] failTable = buildFailureTable(pattern, comparator);

        int i = 0;
        int j = 0;

        while (i <= n - m) {
            while (j < m && comparator.compare(pattern.charAt(j),
                    text.charAt(i + j)) == 0) { // text[i + j] == pattern[j]
                j++;
            }

            if (j == 0) {
                i++;
            } else {
                if (j == m) {
                    matchArr.add(i);
                }

                int shiftTable = failTable[j - 1];
                i = i + j - shiftTable;
                j = shiftTable;
            }
        }

        return matchArr;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input pattern.
     *
     * Note that a given index i will contain the length of the largest prefix
     * of the pattern indices [0..i] that is also a suffix of the pattern
     * indices [1..i]. This means that index 0 of the returned table will always
     * be equal to 0
     *
     * Ex.
     * pattern:       a  b  a  b  a  c
     * failureTable: [0, 0, 1, 2, 3, 0]
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern is not in right format. Cannot do buildFailTable()");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null. Cannot do buildFailTable()");
        }

        int m = pattern.length();

        if (m == 0) {
            return new int[0];
        }

        int[] failTable = new int[m];

        int i = 0;
        int j = 1;

        failTable[0] = 0;

        while (j < m) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) { // pattern[i] == pattern[j]
                failTable[j] = i + 1;
                i++;
                j++;
            } else {
                if (i > 0) {
                    i = failTable[i - 1];
                } else {
                    failTable[j] = i;
                    j++;
                }
            }
        }

        return failTable;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the buildLastTable() method before implementing
     * this method. Do NOT implement the Galil Rule in this method.
     *
     * Note: You may find the getOrDefault() method from Java's Map class
     * useful.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern is not in right format. Cannot do boyerMoore algorithm");
        }

        if (text == null) {
            throw new IllegalArgumentException("Text is null. Cannot do boyerMoore algorithm");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null. Cannot do boyerMoore algorithm");
        }

        Map<Character, Integer> lastTable = buildLastTable(pattern);

        int m = pattern.length();
        int n = text.length();

        int i = 0;

        List<Integer> matchArr = new ArrayList<>();

        if (m > n) {
            return matchArr;
        }

        while (i <= n - m) {
            int j = m - 1;
            while ((j >= 0) && (comparator.compare(text.charAt(i + j),
                    pattern.charAt(j)) == 0)) { // text[i + j] == pattern[j]
                j--;
            }

            if (j == -1) {
                matchArr.add(i);
                i++;
            } else {
                Integer shift = lastTable.get(text.charAt(i + j));
                if (shift == null) {
                    shift = -1;
                }

                if (shift < j) {
                    i = i + j - shift;
                } else {
                    i++;
                }
            }
        }

        return matchArr;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. pattern = octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern is null. Cannot do buildLastTable");
        }

        int m = pattern.length();
        Map<Character, Integer> mapLast = new HashMap<>();

        for (int i = 0; i < m; i++) {
            mapLast.put(pattern.charAt(i), i);
        }

        return mapLast;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i)
     *   c is the integer value of the current character, and
     *   i is the index of the character
     *
     * We recommend building the hash for the pattern and the first m characters
     * of the text by starting at index (m - 1) to efficiently exponentiate the
     * BASE. This allows you to avoid using Math.pow().
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to calculate BASE^(m - 1).
     *
     * Ex. Hashing "bunn" as a substring of "bunny" with base 113
     * = (b * 113 ^ 3) + (u * 113 ^ 2) + (n * 113 ^ 1) + (n * 113 ^ 0)
     * = (98 * 113 ^ 3) + (117 * 113 ^ 2) + (110 * 113 ^ 1) + (110 * 113 ^ 0)
     * = 142910419
     *
     * Another key point of this algorithm is that updating the hash from
     * one substring to the next substring must be O(1). To update the hash,
     * subtract the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar as shown by this formula:
     * (oldHash - oldChar * BASE ^ (pattern.length - 1)) * BASE + newChar
     *
     * Ex. Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y
     *              = (142910419 - 98 * 113 ^ 3) * 113 + 121
     *              = 170236090
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^(m - 1) is for updating the hash.
     *
     * Do NOT use Math.pow() in this method.
     * Do NOT implement your own version of Math.pow().
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern is not in right format. Cannot do rabinKarp algorithm");
        }

        if (text == null) {
            throw new IllegalArgumentException("Text is null. Cannot do rabinKarp algorithm");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null. Cannot do rabinKarp algorithm");
        }

        List<Integer> matchArr = new ArrayList<>();


        int m = pattern.length();
        int n = text.length();

        if (m > n) {
            return matchArr;
        }

        int patternHash = generateHash(pattern, m); // pattern.length()
        int textHash = generateHash(text, m); // pattern.length()

        int i = 0;

        while (i <= n - m) {
            if (patternHash == textHash) {
                int j = 0;

                while ((j < m) && (comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0)) {
                    j++;
                }

                if (j == m) {
                    matchArr.add(i);
                }
            }

            i++;

            if (i <= n - m) {
                textHash = updateHash(textHash, m, text.charAt(i - 1), text.charAt(i + m - 1));
            }
        }

        return matchArr;
    }

    /**
     * generateHash function for rabinKarp algorithm
     * @param currChars current string to be inputted
     * @param iterations length of the iterations
     * @return hash as an int
     */
    private static int generateHash(CharSequence currChars, int iterations) {
        int finalHash = 0;

        for (int i = 0; i < iterations; i++) {
            finalHash += currChars.charAt(i) * powerFunction(BASE, iterations - 1 - i);
        }

        return finalHash;
    }

    /**
     * Power function to replace Math.pow()
     * @param base base of the computation
     * @param power to which power is needed to be computed
     * @return the int equivalent of the computation
     */
    private static int powerFunction(int base, int power) {
        if (power == 0) {
            return 1;
        }

        return base * powerFunction(base, power - 1);
    }

    /**
     * hash update method for rabinKarp hash
     * @param oldHash old hash as an int
     * @param length length of the string that the hash was computed for
     * @param oldChars old character to be removed
     * @param newChars new character to be added as equivalent hash
     * @return the new int equivalent of hash
     */
    private static int updateHash(int oldHash, int length, char oldChars, char newChars) {
        return (oldHash - oldChars * powerFunction(BASE, length - 1)) * BASE + newChars;
    }

    /**
     * This method is OPTIONAL and for extra credit only.
     *
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     *
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        return null; // if you are not implementing this method, do NOT modify this line
    }
}
