import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author Darshan and Mahesh
 */
public class OddLengthCycle {

    public static void main (String[] args) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File("oddLengthCycleTest1.txt"));
        Graph g = Graph.readGraph(scanner, false);
        List<Vertex> cycle = oddLengthCycle(g);
        if(cycle == null) {
            System.out.println("Its Bipartite graph");
        } else{
            System.out.print("Found a odd length cycle:");
            for(Vertex v: cycle) {
                System.out.print(v.name+ " ");
            }
        }
    }

    /**
     * This method runs BFS on the given graph, while testing 
     * if there exists an edge between two vertices at any level of the BFS.
     * 
     * If such an edge exists, it forms a cycle with 
     * the starting point (source) of the BFS.
     * 
     * @param g The undirected Graph to be tested
     * @return A list of vertices that form the odd length cycle
     */
    public static List<Vertex> oddLengthCycle(Graph g) {
    	
        for( Vertex source: g) {
            if(!source.seen) {
                return bfsForOddLengthCycle(source);
            }
        }
        return null;
    }

	/**
	 * Runs the BFS algorithm while testing for odd length cycles in the graph
	 * 
	 * @param source The source vertex where BFS is to be started
	 * @return A list of vertices containing the odd length cycle which contains source.
	 */
	private static List<Vertex> bfsForOddLengthCycle(Vertex source) {
		List<Vertex> queue = new LinkedList<Vertex>();
		source.seen = true;
		source.distance = 0;
		queue.add(source);

		while(!queue.isEmpty()) {
		    Vertex currentVertex = queue.remove(0);
		    List<Edge> adjEdges = currentVertex.Adj;
		    for(Edge e: adjEdges) {
		        Vertex v = e.otherEnd(currentVertex);
		        if(!v.seen) {
		            v.seen = true;
		            v.parent = currentVertex;
		            v.distance = currentVertex.distance + 1;
		            queue.add(v);
		        } else if(v.seen && v.distance == currentVertex.distance) {
		        	return findOddLengthCycleVertices(currentVertex, v);
		        }
		    }
		}
		return null;
	}

	private static List<Vertex> findOddLengthCycleVertices(Vertex currentVertex, Vertex v) {
		//A FIFO data structure to store the vertices along one side of the cycle
		List<Vertex> cycleVerticesLeftQueue = new LinkedList<Vertex>();
		//A LIFO data structure to store the vertices along the other side of the cycle so that order is preserved
		Stack<Vertex> cycleVerticesRightStack = new Stack<Vertex>();
		
		cycleVerticesLeftQueue.add(v);
		cycleVerticesRightStack.push(currentVertex);
		Vertex x1 = v;
		Vertex x2 = currentVertex;
		while(x1.parent != x2.parent) {
		    cycleVerticesLeftQueue.add(x1);
		    cycleVerticesRightStack.push(x2);
		}

		cycleVerticesLeftQueue.add(x1.parent);
		while(!cycleVerticesRightStack.isEmpty()) {
		    cycleVerticesLeftQueue.add(cycleVerticesRightStack.pop());
		}
		return cycleVerticesLeftQueue;
	}

}
