import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * @author Darshan and Mahesh
 *
 */
public class Eulerian {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("eulerianTest4.txt"));
		Graph g = Graph.readGraph(scanner, false);
		g.printGraph();
		testEulerian(g);
	}
	
	/**
	 * This algorithm contains two parts.
	 * The first part tests if the graph is connected.
	 * The second part tests if it is a Eulerian, or has a Eulerian path.
	 * If it does not, it states how many vertices are of odd degree.
	 * 
	 * @param g The undirected Graph to be tested
	 */
	public static void testEulerian(Graph g){
		for (Vertex vertex : g) {
			vertex.seen = false;
		}
		
		int numberOfComponents = 0;
		List<Vertex> oddVertices = new ArrayList<>();
		for (Vertex vertex : g) {
			if(!vertex.seen){
				numberOfComponents++;
				if(numberOfComponents > 1){
					System.out.println("This graph is not connected. Number of components = "+numberOfComponents);
					break;
				}
				dfsVisit(vertex);
			}
			if(vertex.degree%2!=0){
				oddVertices.add(vertex);
			}
		}
		
		if(oddVertices.size() == 0)
			System.out.println("Graph is a Eulerian");
		else if (oddVertices.size() == 2)
			System.out.println("Graph has a Eulerian path from "+oddVertices.get(0) +" to "+oddVertices.get(1));
		else
			System.out.println("Number of vertices with odd degree = "+oddVertices.size());
	}
	
	private static void dfsVisit(Vertex vertex) {
		vertex.seen = true;
		vertex.degree = vertex.Adj.size();
		for (Edge e : vertex.Adj) {
			Vertex u = e.otherEnd(vertex);
			if(!u.seen){
				dfsVisit(u);
			}
		}
	}
}
