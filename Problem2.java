// NOTE: make sure to put your code in the default package

import java.util.*; // NOTE: please ask privately on piazza if you think you need to import a package outside of the Java standard library

public class Problem2 {
    /*
    INSTRUCTIONS: For each for the following four functions, the input consists of a set A, and
    a relation R represented as a set of ordered pairs. R will always be a subset of A x A.
    Method "isPropertyX(A,R)" returns true if R has property X, and false otherwise.
    Note that here we use the Pair class (see Pair.java) to represent an ordered pair.
    Pair(c, d) represents cRd and Pair(d, c) represents dRc.
 */

public static boolean isReflexive (Set<Integer> A, Set<Pair> R) {
    // initialize a HashSet that contains all elements
	HashSet<Pair> elements = new HashSet();
	for (int i : A) {
		elements.add(new Pair(i, i));
	}
	if (R.containsAll(elements) == true){
		return true;
	}
	else {
		return false;
	}
}

public static boolean isTransitive (Set<Integer> A, Set<Pair> R) {
    TreeMap<Integer, HashSet<Integer>> relationship = new TreeMap<>();
    for (Pair i : R) {
    	if (relationship.containsKey(i.getA())) {
    		relationship.get(i.getA()).add(i.getB());
    	}
    	else {
    		// create new HashSet
    		HashSet<Integer> B = new HashSet<>();
    		B.add(i.getB());
    		relationship.put(i.getA(), B);
    	}
    }
    for (Map.Entry i : relationship.entrySet()) {
    	HashSet<Integer> relation = (HashSet<Integer>)i.getValue();
    	for (Integer j : relation) {
    		if (! relation.containsAll(relationship.getOrDefault(j, new HashSet<Integer>()))) {
    			return false;
    		}
    	}
    }
    return true;
}

public static boolean isSymmetric (Set<Integer> A, Set<Pair> R) {
    for (Pair i : R) {
    	if (i.getA() == i.getB()){
    		continue;
    	}
    	if (! R.contains(new Pair(i.getB(),i.getA()))) {
    		return false;
    	}
    }
    return true;
}


public static boolean isAsymmetric (Set<Integer> A, Set<Pair> R) {
	// check size
	if (A.size() == 0 && R.size() == 0) {
		return true;
	}
    // needs to be irreflexive
	if (isReflexive(A,R) == true) {
		return false;
	}
	// needs to be antisymmetric
	for (Pair i : R) {
		if (R.contains(new Pair(i.getB(),i.getA())) == true) {
			return false;
		}
	}
    return true;
}

/**
 * This is an example for the relation <= on the set {0, 1, 2, 3, 4}
 *    Here A = {0, 1, 2, 3, 4} and
 *    R = {(0,0), (0,1), (0,2), (0,3), (0,4), (1,1), (1,2), (1,3), (1,4), (2,2), (2,3), (2,4), (3,3), (3,4), (4,4)}
 *    This relation is reflexive and transitive but is neither symmetric nor asymmetric.
 * NOTE: This is here to help you test your code. Nothing written in the main method will be run by the autograder
 */
public static void main(String[] args) {
   Set<Integer> A = new HashSet<>();
   Set<Pair> R = new HashSet<>();

    for (int a = 0; a < 5; a++) A.add(a);
    for(int a: A){
        for(int b: A){
            if(a <= b) R.add(new Pair(a, b));
        }
    }

    System.out.println("reflexive: " + isReflexive(A, R));      // expected: true
    System.out.println("transitive: " + isTransitive(A, R));    // expected: true
    System.out.println("symmetric: " + isSymmetric(A, R));      // expected: false
    System.out.println("asymmetric: " + isAsymmetric(A, R));    // expected: false
}
}