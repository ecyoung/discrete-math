// NOTE: make sure to put your code in the default package

import java.util.*; // NOTE: please ask privately on piazza if you think you need to import a package outside of the Java standard library

public class Problem3 {
    /**
     * This method converts an undirected, unweighted, graph represented by vertex set 'V' and edge set 'E' into
     *   an adjacency list representation. In an adjacency list, we associate each vertex with a list of its neighbors.
     *   We say that two vertices i and j are neighbors if there is an edge between them in the edge set.
     *
     * @param V: vertex set represented as a set of Integers; 'V' will always be of the form {0, 1, 2, ..., n-1} for some n.
     * @param E: edge set represented as a set of Pairs; 'E' will always be a subset of V x V.
     *              Here we are using the Pair class (see Pair.java) to represent edges.
     *              Pair(c,d) represents that there is an edge between c and d. Since we are representing an undirected
     *              graph, Pair(c,d) and Pair(d,c) represent the same edge and at most one will be included in 'E'.
     * @returna the corresponding adjacency list represented as a list of lists. The 'i'th list contains all neighbors
     *              of vertex i or is the empty list if vertex i has no neighbors. Each list of neighbors can be in any
     *              order but should not have duplicates.
     */
    public static List<List<Integer>> getAdjacencyList(Set<Integer> V, Set<Pair> E) {
    	// initialize double linked list
        List<List<Integer>> adjList = new LinkedList<>(); 
        for (Integer i : V) {
        	LinkedList adjElem = new LinkedList<>();
        	for (Pair j : E) {
        		if (j.getA() == i) {
        			adjElem.add(j.getB());
        		}
        		if (j.getB() == i) {
        			adjElem.add(j.getA());
        		}
        	}
        	adjList.add(new LinkedList<Integer>(new HashSet<Integer>(adjElem)));
        }
        return adjList;
    }

    /**
     * This method converts an undirected, unweighted, graph represented by vertex set 'V' and edge set 'E' into
     *   an adjacency matrix representation. In an adjacency matrix for an unweighted graph, M[i][j] = 1 if there is an
     *   edge between i and j, and 0 otherwise.
     *   The adjacency matrix for a graph with n vertices will always have n rows and n columns.
     *   For an undirected graph, the adjacency matrix is symmetric, meaning that M[i][j] = M[j][i] for all i and j.
     *
     * @param V: vertex set represented as a set of Integers; 'V' will always be of the form {0, 1, 2, ..., n-1} for some n.
     * @param E: edge set represented as a set of Pairs; 'E' will always be a subset of V x V.
     *              Here we are using the Pair class (see Pair.java) to represent edges.
     *              Pair(c,d) represents that there is an edge between c and d. Since we are representing an undirected
     *              graph, Pair(c,d) and Pair(d,c) represent the same edge and at most one will be included in 'E'.
     * @returna the corresponding adjacency matrix represented as a 2d int array. M[i][j] is 1 if there is an edge between
     *              i and j and 0 otherwise.
     */
    public static int[][] getAdjacencyMatrix(Set<Integer> V, Set<Pair> E) {
        List<List<Integer>> adjList = getAdjacencyList(V,E);
        // dimension: V x V
        int[][] adjMat = new int[V.size()][V.size()]; 
        for (int i = 0; i < adjMat.length; i++) {
        	for (int j : adjList.get(i)) {
        		adjMat[i][j] = 1;
        	}
        }
        return adjMat;
    }

    // NOTE: you do not need to write any code below this line. All code beyond this point is included to help you write and test your code

    /**
     * Print an adjacency list
     * @param adjList: the adjacency list to print
     */
    public static void printAdjacencyList(List<List<Integer>> adjList) {
        System.out.println("*** Adjacency List ***");
        for (int v = 0; v < adjList.size(); v++) {
            System.out.println(v + ": " + adjList.get(v));
        }
    }

    /**
     * Print an adjacency matrix
     * @param adjMatrix: the adjacency matrix to print
     */
    public static void printAdjacencyMatrix(int[][] adjMatrix) {
        System.out.println("*** Adjacency Matrix ***");
        for(int[] row: adjMatrix) System.out.println(Arrays.toString(row));
    }

    /**
     * Test the 'getAdjacencyList' and 'getAdjacencyMatrix' methods on the example graph described in the problem document.
     *    Tests each method on the following graphs, each of which has 4 vertices:
     *      - empty graph: no edges
     *      - complete graph: every pair of vertices is connected by an edge (6 total edges)
     *      - cycle: 0 --- 1
     *               |     |
     *               3 --- 2
     *
     * NOTE: This is here to help you test your code. Nothing written in the main method will be run by the autograder
     */
    public static void main(String[] args) {
        int n = 4;
        Set V = new HashSet<>();
        for (int i = 0; i < n; i++) V.add(i);

        // graph with no edges
        Set<Pair> E_empty = new HashSet<>();
        printAdjacencyList(getAdjacencyList(V, E_empty));  // expected: 0: [], 1: [], 2: [], 3: []
        printAdjacencyMatrix(getAdjacencyMatrix(V, E_empty)); //expected: [[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]

        // complete graph on n vertices
        Set<Pair> E_complete = new HashSet<>();
        for (int a = 0; a < n; a++){
            for (int b = 0; b < a; b++) {
                E_complete.add(new Pair(a, b));
            }
        }
        printAdjacencyList(getAdjacencyList(V, E_complete));  // expected: 0: [1, 2, 3], 1: [0, 2, 3], 2: [0, 1, 3], 3: [0, 1, 2]
        printAdjacencyMatrix(getAdjacencyMatrix(V, E_complete)); //expected: [[0, 1, 1, 1], [1, 0, 1, 1], [1, 1, 0, 1], [1, 1, 1, 0]]

        // cycle on n vertices
        Set<Pair> E_cycle = new HashSet<>();
        for (int v = 0; v < n; v++){
            E_cycle.add(new Pair(v, (v + 1) % n));
        }
        printAdjacencyList(getAdjacencyList(V, E_cycle));  //expected: 0: [1, 3], 1: [0, 2], 2: [1, 3], 3: [2, 0]
        printAdjacencyMatrix(getAdjacencyMatrix(V, E_cycle)); // expected: [[0, 1, 0, 1], [1, 0, 1, 0], [0, 1, 0, 1], [1, 0, 1, 0]]
    }
}
