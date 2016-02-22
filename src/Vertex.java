/**
 * Class to represent a vertex of a graph
 * 
 *
 */

import java.util.*;

public class Vertex {
    public int name; // name of the vertex
    public boolean seen; // flag to check if the vertex has already been visited
    public Vertex parent; // parent of the vertex
    public int distance; // distance to the vertex from the source vertex
    public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or ArrayList
    
    //Additions for SP2
    public int degree; // Number of incoming edges to the vertex
    public int top; // The topological number of the vertex
    public int componentNumber;
    
    //Additions for LP0
    public int numOfSeenEdges;
    private int unseenPointer;

    /**
     * Constructor for the vertex
     * 
     * @param n
     *            : int - name of the vertex
     */
    Vertex(int n) {
	name = n;
	seen = false;
	parent = null;
	Adj = new ArrayList<Edge>();
	revAdj = new ArrayList<Edge>();   /* only for directed graphs */
	
	degree = 0;
    //initializing component number to 0 to mark it uninitialized
    componentNumber = 0;
    
    numOfSeenEdges = 0;
    unseenPointer = 0;
    }

    /**
     * Method to represent a vertex by its name
     */
    public String toString() {
	return Integer.toString(name);
    }
    
    public Edge getNextUnseenEdge(){
    	Edge e = Adj.get(unseenPointer++);
    	while(e.seen){
    		if(unseenPointer == Adj.size())
    			return null;
    		e = Adj.get(unseenPointer++);
    	}
    	return e;
    }
    
    @Override
    public int hashCode(){
    	return this.name;
    }
    
    @Override
    public boolean equals(final Object o){
    	if(o==null)
    		return false;
    	Vertex that = (Vertex)o;
    	return this.name == that.name;
    }
}