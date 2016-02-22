import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;


/**
 * @author Darshan and Mahesh
 *
 */
public class EulerianPath {

	/**
	 * Takes the input graph and outputs a list of edges of the Euler tour/path in order
	 * Assumption: The graph is connected
	 * 
	 * @param g The input graph
	 * @return A list of edges that form the Euler tour/path
	 */
	public static List<Edge> findEulerTour(Graph g)  // Return an Euler tour of g
	{
		List<Edge> tour = new LinkedList<Edge>();
		Vertex source = null;
		boolean isPath = false;
		for (Vertex vertex : g) {
			if(vertex.Adj.size()%2!=0)
			{
				source = vertex;
				isPath = true;
				break;
			}
		}
		if(source == null)
			source = g.getSourceVertex();
		Edge first = source.Adj.get(0);
		HashMap<Vertex, Edge> mergeMap = new HashMap<Vertex, Edge>();
		mergeMap.put(source, null);
		
		//Loop Invariants:
		//The hash map contains the set of vertex and edge from which a secondary cycle may be formed 
		// (initially the source vertex and null edge)
		while(!mergeMap.isEmpty()){
			Entry<Vertex, Edge> entry = mergeMap.entrySet().iterator().next();
			Vertex current_v = entry.getKey();
			if((current_v.Adj.size() - current_v.numOfSeenEdges <= 2)||entry.getValue() == null)
				mergeMap.remove(current_v);
			Edge initial_edge = current_v.getNextUnseenEdge();
			Edge current = initial_edge;
			
			// Loop Invariants:
			// current - The current edge being looked at while building the cycle
			// current_v - The current vertex being looked at while building the cycle
			// entry.getValue == null implies that it is the first cycle/path.
			while(entry.getValue() == null ||(entry.getValue()!= null && current.otherEnd(current_v) != entry.getKey())){
				//mark edge as seen
				current.seen = true;
				current_v.numOfSeenEdges++;
				
				//move to the other end of edge and pick up next edge
				current_v = current.otherEnd(current_v);
				current_v.numOfSeenEdges++;
				current.next = current_v.getNextUnseenEdge();
				
				//Add to map if possible initial point of a new loop
				if(current_v.Adj.size() - current_v.numOfSeenEdges >= 2){
					if(!mergeMap.containsKey(current_v))
						mergeMap.put(current_v, current);
				}
				else if(mergeMap.containsKey(current_v))
					mergeMap.remove(current_v);
				
				//if current is null, then Euler path possible, or first cycle is complete
				if(current.next == null){
					if(!isPath){
						current.next = initial_edge;
						current = current.next;
					}
					break;
				}
				
				//move to next edge
				current = current.next;
			}
			if(current!=null){
				current_v.numOfSeenEdges++;
				current.seen = true;
				current_v = current.otherEnd(current_v);
				current_v.numOfSeenEdges++;
			}
			
			//merge secondary cycle with entry from map
			if(entry.getValue()!= null){
				Edge temp = entry.getValue().next;
				current.next = temp;
				entry.getValue().next = initial_edge;
			}
		}
		Edge iter = first;
		while(iter != null && iter.next!=first){
			tour.add(iter);
			iter = iter.next;
		}
		if(iter!=null)
			tour.add(iter);
		return tour;
	}
	
	/**
	 * Takes a graph and a tour as input and outputs if it is an Euler tour/path.
	 * Assumption: All edges are currently marked as seen
	 * 
	 * @param g Input Graph
	 * @param tour A list of edges containing the Euler tour in order
	 * @param start The starting vertex of the Euler Tour/Path
	 * @return true, if the list is a correct Eulerian tour/path
	 */
	static boolean verifyTour(Graph g, List<Edge> tour, Vertex start)  // verify tour is a valid Euler tour
	{
		if(tour.size()!=g.numEdges){ //Check if number of edges covered is correct
			System.out.println("All edges not visited. Only "+tour.size()+" edges visited");
			return false;
		}
		if(tour.size() <= 1)
			return true;
		Vertex v1 = start;
		for (Edge edge : tour) {
			
			//Ensure an edge is not visited more than once
			if(!edge.seen){
				System.out.println("Edge repeated in tour");
				return false;
			}
			edge.seen = false;
			
			//Check if consecutive edges are connected
			Vertex v2 = edge.otherEnd(v1);
			Edge next = edge.next;
			if(next == null)
				break;
			Vertex v3 = next.From;
			Vertex v4 = next.To;
			if(v1==v3 || v1==v4) {
				if (v2==v3 || v2 == v4)
					return false;
			}
			else if(v2==v3 || v2==v4) {
				if (v1==v3 || v1 == v4)
					return false;
			}
			else
				return false;
			v1=v4;
		}
		
		return true;
	}
	
	
	/**
	 * Tests if the given graph is Eulerian
	 * 
	 * @param g The graph to be tested
	 * @return true if the graph contains either a Eulerian tour/path
	 */
	public static boolean testEulerian(Graph g){
		int numOfOdd = 0;
		for (Vertex vertex : g) {
			if(vertex.degree%2!=0){
				numOfOdd++;
			}
		}
		if(numOfOdd == 0 || numOfOdd == 2)
			return true;
		return false;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("lp0_test/lp0-big.txt"));
		Graph g = Graph.readGraph(scanner, false);
		if(testEulerian(g)){ //first check if the given graph contains a Eulerian
			System.out.println("Graph is a Eulerian. Starting find tour/path");
			long start = System.currentTimeMillis();
			List<Edge> tour = findEulerTour(g);
			System.out.println("Time taken to find tour = "+(System.currentTimeMillis()-start));
			Vertex start_v = g.getSourceVertex();
			if(tour.size() >= 2){
				Edge first = tour.get(0);
				Edge second = tour.get(1);
				if(first.From == second.From || first.From == second.To)
					start_v = first.To;
				else
					start_v = first.From;
			}
			if(verifyTour(g, tour, start_v))
				System.out.println("Algorithm correct");
			else
				System.out.println("Algorithm incorrect");
			System.out.println("Total time taken = "+(System.currentTimeMillis()-start));
			
			//Comment below lines for large inputs
//			for (Edge edge : tour) {
//				System.out.println(edge);
//			}
		}
		else{
			System.out.println("Not a Eulerian graph");
		}
	}
}
/**
SAMPLE INPUT: 
6 10
1 2 1
1 3 1
1 4 1
1 6 1
2 3 1
3 6 1
3 4 1
4 5 1
4 6 1
5 6 1

SAMPLE OUTPUT:
Graph is a Eulerian. Starting find tour/path
Time taken to find tour = 15
Algorithm correct
Total time taken = 15
(1,2)
(2,3)
(1,3)
(1,4)
(4,5)
(5,6)
(4,6)
(3,4)
(3,6)
(1,6)

 */

