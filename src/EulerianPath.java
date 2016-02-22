import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;


public class EulerianPath {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("lp0_test/path.txt"));
		Graph g = Graph.readGraph(scanner, false);
		System.out.println("Starting eulerian...");
		long start = System.currentTimeMillis();
		List<Edge> tour = findEulerPath(g);
		System.out.println("Time taken to find tour = "+(System.currentTimeMillis()-start));
//		if(verifyTour(g, tour, g.getSourceVertex()))
//			System.out.println("Algorithm correct");
//		else
//			System.out.println("Algorithm incorrect");

		Iterator<Edge> itr = tour.iterator();
		while (itr.hasNext()) {
			Edge e = itr.next();
			System.out.println(e.toString());
		}
		System.out.println("Total time taken = "+(System.currentTimeMillis()-start));
	}
	
	static List<Edge> findEulerTour(Graph g)  // Return an Euler tour of g
	{
		List<Edge> tour = new LinkedList<Edge>();
		Vertex source = g.getSourceVertex();
		Edge first = source.Adj.get(0);
		HashMap<Vertex, Edge> mergeMap = new HashMap<Vertex, Edge>();
		mergeMap.put(source, null);
		while(!mergeMap.isEmpty()){
			Entry<Vertex, Edge> entry = mergeMap.entrySet().iterator().next();
			Vertex current_v = entry.getKey();
			if((current_v.Adj.size() - current_v.numOfSeenEdges <= 2)||entry.getValue() == null)
				mergeMap.remove(current_v);
			Edge initial_edge = current_v.getNextUnseenEdge();
			Edge current = initial_edge;
			
			// Loop Invariants:
			// current - The current edge being looked at while building the graph
			// current_v - The current vertex being looked at while building the graph
			while(current.otherEnd(current_v) != entry.getKey()){
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
				
				//move to next edge
				current = current.next;
			}
			current.seen = true;
			current_v.numOfSeenEdges++;
			current_v = current.otherEnd(current_v);
			current_v.numOfSeenEdges++;
			
			//merge
			if(entry.getValue()!= null){
				Edge temp = entry.getValue().next;
				current.next = temp;
				entry.getValue().next = initial_edge;
			}
			else{//initial case, complete the cycle
				current.next = initial_edge;
			}
		}
		Edge iter = first;
		while(iter.next!=first){
			tour.add(iter);
			iter = iter.next;
		}
		tour.add(iter);
		return tour;
	}


	static List<Edge> findEulerPath(Graph g)  // Return an Euler tour of g
	{
		List<Edge> tour = new LinkedList<Edge>();
		Vertex source = null;
		Vertex destination = null;
		boolean initailPathCompleted = false;
		for(Vertex v: g) {
			if (v.Adj.size() % 2 != 0){
				if (source == null) {
					source = v;
				} else if(destination == null){
					destination = v;
				} else {
					System.out.println("Euler Path does not exist");
					return null;
				}
			}
		}
		Edge first = source.Adj.get(0);
		Edge last = null;
		HashMap<Vertex, Edge> mergeMap = new HashMap<Vertex, Edge>();
		mergeMap.put(source, null);
		while(!mergeMap.isEmpty()){
			Entry<Vertex, Edge> entry = mergeMap.entrySet().iterator().next();
			Vertex current_v = entry.getKey();
			if((current_v.Adj.size() - current_v.numOfSeenEdges <= 2)||entry.getValue() == null)
				mergeMap.remove(current_v);
			Edge initial_edge = current_v.getNextUnseenEdge();
			Edge current = initial_edge;

			// Loop Invariants:
			// current - The current edge being looked at while building the graph
			// current_v - The current vertex being looked at while building the graph
			while((initailPathCompleted && current.otherEnd(current_v) != entry.getKey()) || (!initailPathCompleted && current.otherEnd(current_v) != destination)){
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

				//move to next edge
				current = current.next;
			}
			current.seen = true;
			current_v.numOfSeenEdges++;
			current_v = current.otherEnd(current_v);
			current_v.numOfSeenEdges++;

			//merge
			if(entry.getValue()!= null){
				Edge temp = entry.getValue().next;
				current.next = temp;
				entry.getValue().next = initial_edge;
			}
			else{//initial case, complete the cycle
				current.next = null;
				last = current;
				initailPathCompleted = true;

			}
		}
		Edge iter = first;
		while(iter.next!=last){
			tour.add(iter);
			iter = iter.next;
		}
		tour.add(last);
		return tour;
	}
	
	/**
	 * Takes a graph and a tour as input and outputs if it is an Euler tour.
	 * Assumption: All edges are currently marked as seen
	 * 
	 * @param g Input Graph
	 * @param tour A list of edges containing the Euler tour in order
	 * @param start The starting vertex of the Euler Tour
	 * @return
	 */
	static boolean verifyTour(Graph g, List<Edge> tour, Vertex start)  // verify tour is a valid Euler tour
	{
		if(tour.size()!=g.numEdges){
			System.out.println("All edges not visited. Only "+tour.size()+" edges visited");
			return false;
		}
		Iterator<Edge> iter = tour.iterator();
//		Edge prev = null, current = null;
//		if(iter.hasNext()){
//			prev = iter.next();
//			if(!prev.seen)
//				return false;
//			prev.seen = false;
//		}
		Vertex current_v = start;
		while(iter.hasNext()){
			Edge current = iter.next();
			current_v = current.otherEnd(current_v);
			if(current_v == null){
				System.out.println("Euler tour is disconnected");
				return false;
			}
			if(!current.seen){
				System.out.println("Edge repeated in tour");
				return false;
			}
			current.seen = false;
//			HashSet<Vertex> set = new HashSet<Vertex>();
//			set.add(prev.From);
//			set.add(prev.To);
//			set.add(current.From);
//			set.add(current.To);
//			if(set.size()!=3)
//				return false;
//			prev = current;
		}
		return true;
	}
	
}
