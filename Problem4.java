// NOTE: make sure to put your code in the default package

import java.util.*; // NOTE: please ask privately on piazza if you think you need to import a package outside of the Java standard library

public class Problem4 {
    /**
     * This method returns the total weight of the minimum spanning tree (MST) of the undirected, connected graph
     *  represented by the adjacency matrix M, using the following algorithm:
     *
     *         While the graph represented by M contains a cycle:
     *             1) find a cycle in the graph, then
     *             2) delete the heaviest edge in the cycle from the graph.
     *
     *     It is highly recommended that take advantage of the method "findCycle" given below.
     *
     * @param M: an adjacency matrix representing an undirected connected graph where M[i][j] is the weight of edge (i, j)
     *              if such an edge exists and is 0 otherwise. Edge weights can be positive or negative.
     *              M will always describe a connected, undirected graph.
     *              M will always be square and symmetric (M[i][j] = M[j][i]).
     * @return the total weight (the sum of the weights of the edges) of the minimum spanning tree
     */
    public static int findMST (int[][] M) {
        while(true) {
        	List<Integer> cycle = findCycle(M);
        	if (cycle == null){
        		break;
        	}
        	else {
        		int i = cycle.get(0);
        		int j = cycle.get(1);
        		int k = M[i][j];
        		for (int l = 0; l < cycle.size(); l++) {
        			int start = cycle.get(l);
        			int end;
        			if (l == (cycle.size()-1)) {
        				end = cycle.get(0);
        			}
        			else {
        				end = cycle.get(l+1);
        			}
        			if (M[start][end]>k) {
        				i = start;
        				j = end;
        				k = M[i][j];
        			}
        		}
        	M[i][j] = 0;
        	M[j][i] = 0;
        	}
        }
    	int output = 0;
    	for (int i = 0; i < M.length; i++) {
    		for (int j = 0; j < M.length; j++) {
    			output += M[i][j];
    		}
    	}
    	output = output / 2;
        return output;
    }

    // NOTE: you do not need to write any code below this line. All code beyond this point is included to help you write and test your code

    /**
     * This method finds a cycle (represented by a list of vertices) in the graph represented by the adjacency matrix M,
     *  or returns null if the graph represented by M is acyclic
     *
     * @param M: an adjacency matrix representing an undirected graph
     * @return null if no cycle exists in M, otherwise returns a list of distinct vertices for which any two consecutive
     *          vertices are adjacent according to M, and the first and last vertex are also adjacent according to M
     */
    public static List<Integer> findCycle (int[][] M) {
        if (!isSymmetric(M)) throw new IllegalArgumentException("M must be symmetric");

        HashSet<Integer> visited = new HashSet<>();
        for (int v = 0; v < M.length; v++) {
            if (!visited.contains(v)) {
                List<Integer> cycle = _findCycleHelper(M, visited, new ArrayList<Integer>(), v);
                if (cycle != null) return cycle; // found a cycle
            }
        }
        return null; // graph is acyclic so return null
    }

    /**
     * Returns a cycle (represented by a list of vertices) containing a given vertex curr using only vertices in list
     * 'path' and reachable vertices not yet in 'visited'. If no cycle exists returns null. It is essentially a DFS.
     * NOTE: This method is NOT to be called directly and intended only for use by 'findCycle'. I
     *
     * @param M: an adjacency matrix representing an undirected, connected graph. M must be square and symmetric
     * @param visited: the set of vertices already visited by the DFS
     * @param path: the list of vertices visited by the DFS (in order) on the path to the input vertex 'curr'
     * @param curr: an integer between 0 and the length of M representing the current vertex in the DFS
     * @return null if no cycle exists in M, otherwise a list of distinct vertices for which any two consecutive
     *           vertices are adjacent according to M, and the first and last vertex are also adjacent according to M
     */
    public static List<Integer> _findCycleHelper (int[][] M, Set<Integer> visited, ArrayList<Integer> path, int curr) {
        if (visited.contains(curr)) return null;
        path.add(curr);
        visited.add(curr);

        for (int next = 0; next < M[0].length; next++) {
            if (next != curr && M[curr][next] != 0) {  // (curr, next) is an element of E
                int index = path.indexOf(next);
                if (index >= 0 && index != path.size() - 2) { // if on path and not immediately before (cannot have cycle of only two vertices in an undirected graph)
                    return path.subList(index, path.size());  // found a cycle
                } else if (!visited.contains(next)) {
                    List<Integer> cycle = _findCycleHelper(M, visited, path, next);
                    if (cycle != null) return cycle;
                }
            }
        }
        path.remove(path.size() - 1);  // remove curr from the path
        return null;
    }

    /**
     * Checks if a matrix is square and symmetric
     * @param M: an 2d array of ints representing an adjacency matrix
     * @return true if M is non-empty (has at least 1 row), square, and symmetric, and return false otherwise
     */
    public static boolean isSymmetric (int[][] M) {
        if (M == null || M.length == 0 || M.length != M[0].length) return false;  // must be square
        for (int r = 0; r < M.length; r++) {
            for (int c = 0; c < r; c++) {
                if(M[r][c] != M[c][r]) return false;
            }
        }
        return true;
    }

    /**
     * Test the 'findMST' method on the example graph described in the problem document.
     * NOTE: This is here to help you test your code. Nothing written in the main method will be run by the autograder
     */
    public static void main(String[] args) {
        int[][] M = {
                {0, 12, -5, 3, 4},
                {12, 0, 0, -8, 0},
                {-5, 0, 0, 5, 0},
                {3, -8, 5, 0, 11},
                {4, 0, 0, 11, 0}
        };

        System.out.println("MST cost: " + findMST(M)); // expected: -6
    }
}
