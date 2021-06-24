import java.util.Arrays;

public class Problem1 {
    /**
     * This function computes and returns a DNF formula, represented as a string, which is equivalent to
     *      the given truth table 'table'.
     *
     * @param n: the number of Boolean variables (this will always be one less than the length of each row in 'table')
     * @param table: a truth table for a boolean function f on n variables represented as a 2d array of booleans.
     *                 The array will always have 2^n rows (one for each possible assignment of the variables) and n + 1 columns.
     *                 The first n values of each row denote the truth assignment of the n variables x0,x1,...,x(n-1) and
     *                 the last value of the row is the truth value of f([x0,x1,...,x(n-1)]).
     *                 See the main method for an example table.
     *
     * @return A string that is the DNF formula equivalent to that represented by 'table' with the following specifications:
     *           1) the OR symbol is represented by the letter 'v'
     *           2) the AND symbol is represented by the carat, '^'
     *           3) the NEG symbol is represented by the letter 'n'
     *           4) parentheses enclose every conjunction, even if there is only one conjunction and/or one literal in a conjunction, but are not used otherwise
     *           5) the first variable has index 0 (zero), and the n'th variable has index n-1
     *           6) ordering of the literals in each conjunction does NOT matter
     *           7) ordering of the conjunctions does NOT matter
     *           8) spacing does NOT matter (but for your sake, use spaces for readability and testing)
     *           9) a DNF formula for which there are no conjunctions is represented by the empty string: ''
     *
     *        For example, the following are valid DNF formulae to be output (for some input truth tables)
     *         - ''
     *         - '(x0)'
     *         - '(x0 ^ nx1 ^ x2)'
     *         - '(x0 ^ nx1 ^ x2) v (x2 ^ nx0 ^ x1)'
     */
    public static String convertToDNF (int n, boolean[][] table) {
        // initialize DNF array that contains the corresponding function truth values of each row
    	boolean[] DNF = new boolean[table.length];
    	// iterate through all rows of the given 2D boolean array "table"
    	for (int row = 0; row < table.length; row++) {
    		// check if the last column of each row is true 
    		if (table[row][n] == true) {
    			DNF[row] = true;
    		}
   
    		else {
    			DNF[row] = false;
    		}
    	}
    	System.out.println(Arrays.toString(DNF));
    	// initialize output string as a StringBuilder object
    	StringBuilder output = new StringBuilder("");
    	// for each row with a corresponding DNF entry as true, iterate through all items but the last
    	for (int row = 0; row < DNF.length; row++) {
    		// if the corresponding overall function is true
    		if (DNF[row] == true) {
    			output.append("(");
    			for (int col = 0; col < n; col ++) {
    				// convert column number (integer) to string 
    				String colNum = String.valueOf(col);
    				if (table[row][col] == true) {
    					output.append("x" + colNum);
    				}
    				else {
    					output.append("nx" + colNum);
    				}
    				if (col < n-1) {
    					output.append(" ^ ");
    				}
    				else {
    					output.append(")");
    				}
    			}
    			// if the rest of the array still has a true, print V
    			boolean moreT = false;
                for (int i = row + 1; i < DNF.length; i++) {
                	System.out.printf("i=%d value=%b\n",i,DNF[i]);
                	if (DNF[i] == true) {
                		moreT = true;
                		break;
                	}
                }
    			if (moreT) {
    				output.append(" V ");
    			}
    		}
    	}
    return output.toString();
    }
    /**
     * This is an example for the Boolean function xor of two variables (x0, x1) corresponding to the following truth table:
     *
     *          | x0 | x1 |x0 xor x1|
     *          ---------------------------
     *          | F  |  F |    F    |
     *          | F  |  T |    T    |
     *          | T  |  F |    T    |
     *          | T  |  T |    F    |
     * NOTE: This is here to help you test your code. Nothing written in the main method will be run by the autograder
     */
    public static void main(String[] args) {
        boolean[][] table = {
                {false, false, false},
                {false, true, true},
                {true, false, true},
                {true, true, false}
        };
        System.out.println(convertToDNF(2, table)); //sample solution: (nx0 ^ x1) v (x0 ^ nx1)
    }
}