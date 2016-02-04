import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;


/**
 * Solution to problem b
 * 
 * @author Darshan and Mahesh
 *
 */
public class TreeDiameter {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("treeDiameterTest2.txt"));
		Graph g = Graph.readGraph(scanner, false);
		System.out.println("Diameter of graph = "+diameter(g));
	}
	
	/**
	 * Calculates and returns the length of the longest diameter in the graph
	 * Assumption: All nodes are connected.
	 * 
	 * @param t The undirected tree(graph)
	 * @return
	 */
	public static int diameter(Graph t){
		for (Vertex vertex : t) {
			vertex.seen = false;
		}
		IntegerVertexPair mostDistantVertex = null;
		for (Vertex vertex : t) {
			if(!vertex.seen){
				mostDistantVertex = bfs(vertex);
			}
		}
		if(mostDistantVertex!= null){
			for (Vertex vertex : t) {
				vertex.seen = false;
			}
			IntegerVertexPair diameter = bfs(mostDistantVertex.v);
			return diameter.length;
		}
		return -1;
	}

	/**
	 * Runs BFS from the source and returns the farthest vertex in the search, 
	 * along with the distant to the farthest vertex
	 * 
	 * @param source
	 * @return An IntegerVertex pair containing a vertex
	 *         and its distance from source
	 */
	private static IntegerVertexPair bfs(Vertex source) {
		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		queue.add(source);
		IntegerVertexPair mostDistantVertex = new IntegerVertexPair();
		while(queue.size()>0){
			Vertex v = queue.removeFirst();
			v.seen = true;
			for (Edge e : v.Adj) {
				Vertex u = e.otherEnd(v);
				if(!u.seen){
					u.parent = v;
					queue.add(u);
				}
				else if(u.seen && u!=v.parent)//cycle exists.
					return null;
			}
			mostDistantVertex.v = v;
			mostDistantVertex.length++;
		}
		mostDistantVertex.length--;
		return mostDistantVertex;
	}
	
	/**
	 * A static class defined to return the level of the vertex,
	 * and a reference to that vertex in the BFS search used for the above algorithm
	 *
	 */
	static class IntegerVertexPair{
		int length;
		Vertex v;
		public IntegerVertexPair() {
			length = 0;
			v=null;
		}
	}
}
