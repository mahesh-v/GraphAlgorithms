import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


/**
 * Solution to problem a
 * 
 * @author Darshan and Mahesh
 *
 */
public class TopologicalOrdering {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("test/input.txt"));
		Graph g = Graph.readGraph(scanner, true);
		
		List<Vertex> orderedVertices = toplogicalOrder1(g);
		System.out.print("Vertices in topo order: ");
		for (Vertex vertex : orderedVertices) {
			System.out.print(vertex.name+" ");
		}
		System.out.println();
		
		Stack<Vertex> orederedVertices2 = toplogicalOrder2(g);
		System.out.print("Vertices in topo order: ");
		while(!orederedVertices2.empty()){
			Vertex v = orederedVertices2.pop();
			System.out.print(v.name+" ");
		}
	}
	
	/**
	 * Algorithm 1. Remove vertices with no incoming edges, one at a
	 * time, along with their incident edges, and add them to a list.
	 * 
	 * @param g Directed Graph on which the algorithm is to be run.
	 * @return List of Vertices in topological order
	 */
	static List<Vertex> toplogicalOrder1(Graph g) { 
		List<Vertex> topList = new ArrayList<Vertex>();
		List<Vertex> vertices = g.verts;
		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		for (Vertex vertex : vertices) {
			if(vertex==null)
				continue;
			vertex.degree = vertex.revAdj.size();
			if(vertex.degree == 0)
				queue.add(vertex);
		}
		int top=1;
		while(!queue.isEmpty()){
			Vertex v = queue.removeFirst();
			v.top = top++;
			topList.add(v);
			for (Edge e : v.Adj) {
				Vertex to_vertex = e.otherEnd(v);
				to_vertex.degree--;
				if(to_vertex.degree == 0)
					queue.add(to_vertex);
			}
		}
		return topList;
	}

	/**
	 * Algorithm 2. Run DFS on g and push nodes to a stack in the
	 * order in which they finish. 
	 * 
	 * @param g Directed Graph on which the algorithm is to be run
	 * @return A stack containing the vertices that would 
	 * appear in topological order
	 */
	static Stack<Vertex> toplogicalOrder2(Graph g) {
		for (Vertex vertex : g) {
			vertex.seen = false;
			vertex.parent = null;
		}
		Stack<Vertex> s = new Stack<>();
		for (Vertex vertex : g) {
			if(!vertex.seen)
				dfsVisit(vertex, s);
		}
		return s;
	}

	private static void dfsVisit(Vertex vertex, Stack<Vertex> s) {
		vertex.seen = true;
		for (Edge e : vertex.Adj) {
			Vertex u = e.otherEnd(vertex);
			if(!u.seen){
				u.parent = vertex;
				dfsVisit(u, s);
			}
		}
		s.add(vertex);
	}

}
