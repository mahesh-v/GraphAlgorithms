import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


public class TopologicalOrdering {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("input.txt"));
		Graph g = Graph.readGraph(scanner, true);
//		g.printGraph();
		List<Vertex> orderedVertices = toplogicalOrder1(g);
		for (Vertex vertex : orderedVertices) {
			System.out.print(vertex.name+" ");
		}
		System.out.println();
		Stack<Vertex> orederedVertices2 = toplogicalOrder2(g);
		while(!orederedVertices2.empty()){
			Vertex v = orederedVertices2.pop();
			System.out.print(v.name+" ");
		}
	}
	
	static List<Vertex> toplogicalOrder1(Graph g) { 
	  /* Algorithm 1. Remove vertices with no incoming edges, one at a
	 	time, along with their incident edges, and add them to a list.
	   */
		List<Vertex> topList = new ArrayList<Vertex>();
		List<Vertex> vertices = g.verts;
		List<Vertex> queue = new LinkedList<Vertex>();
		for (Vertex vertex : vertices) {
			if(vertex==null)
				continue;
			vertex.degree = vertex.revAdj.size();
			if(vertex.degree == 0)
				queue.add(vertex);
		}
		int top=1;
		while(!queue.isEmpty()){
			Vertex v = queue.remove(0);
			v.top = top++;
			topList.add(v);
			for (Edge e : v.Adj) {
				Vertex to_vertex = e.To;
				to_vertex.degree--;
				if(to_vertex.degree == 0)
					queue.add(to_vertex);
			}
		}
		return topList;
	}

	static Stack<Vertex> toplogicalOrder2(Graph g) {
	 /* Algorithm 2. Run DFS on g and push nodes to a stack in the
	 	 order in which they finish.  Write code without using global variables.
	 */
		for (Vertex vertex : g) {
			vertex.seen = false;
			vertex.parent = null;
		}
		Stack<Vertex> s = new Stack<>();
		for (Vertex vertex : g) {
			if(!vertex.seen)
				DFSVisit(vertex, s);
		}
		return s;
	}

	private static void DFSVisit(Vertex vertex, Stack<Vertex> s) {
		vertex.seen = true;
		for (Edge e : vertex.Adj) {
			Vertex u = e.To;
			if(!u.seen){
				u.parent = vertex;
				DFSVisit(u, s);
			}
		}
		s.add(vertex);
	}

}
