import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;


public class EulerianPath {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("lp0_test/lp0-big.txt"));
		Graph g = Graph.readGraph(scanner, false);
//		g.printGraph();
		System.out.println("Starting eulerian...");
//		printEulerianPath(g);
		long start = System.currentTimeMillis();
		List<Edge> tour = findEulerTour(g);
		System.out.println("Time taken to find tour = "+(System.currentTimeMillis()-start));
		System.out.println("Algorithm correct = "+verifyTour(g, tour, g.getSourceVertex()));
		System.out.println("Time taken = "+(System.currentTimeMillis()-start));
//		for (Edge edge : tour) {
//			System.out.println(edge);
//		}
	}
	
	static List<Edge> findEulerTour(Graph g)  // Return an Euler tour of g
	{
		List<Edge> tour = new LinkedList<Edge>();
		Vertex source = g.getSourceVertex();
		Edge first = source.Adj.get(0);
		HashMap<Vertex, Edge> mergeMap = new HashMap<Vertex, Edge>();
		Edge current = first;
		Vertex current_v = source;
		while(current.otherEnd(current_v) != source){
			current.seen = true;
			current_v.numOfSeenEdges++;
			current_v.Unseen.remove(current);
//			tour.add(current);
			
			//go to the next edge
			current_v = current.otherEnd(current_v);
			current_v.numOfSeenEdges++;
			current_v.Unseen.remove(current);
			current.next = current_v.Unseen.iterator().next();
//			for (Edge e : current_v.Adj) {
//				if(!e.seen){
//					current.next = e;
//					break;
//				}
//			}
			if(current_v.Adj.size() - current_v.numOfSeenEdges >= 2)
				mergeMap.put(current_v, current);
			else if(mergeMap.containsKey(current_v))
				mergeMap.remove(current_v);
			current = current.next;
		}
		current.seen = true;
		current.next = first;
		current_v.numOfSeenEdges++;
		current_v.Unseen.remove(current);
		source.numOfSeenEdges++;
		source.Unseen.remove(current);
		if(source.numOfSeenEdges >=2)
			mergeMap.put(source, current);
//		tour.add(current);
		
		while(!mergeMap.isEmpty()){
			Entry<Vertex, Edge> entry = mergeMap.entrySet().iterator().next();
			if(entry.getKey().Adj.size() - entry.getKey().numOfSeenEdges <= 2)
				mergeMap.remove(entry.getKey());
			Edge initial_edge = entry.getKey().Unseen.iterator().next();
//			for (Edge e : entry.getKey().Adj) {
//				if(!e.seen){
//					initial_edge = e;
//					break;
//				}
//			}
			current = initial_edge;
			
			current_v = entry.getKey();
			while(current.otherEnd(current_v) != entry.getKey()){
				current.seen = true;
				current_v.numOfSeenEdges++;
				current_v.Unseen.remove(current);
//				System.out.println(current);
				current_v = current.otherEnd(current_v);
				current_v.numOfSeenEdges++;
				current_v.Unseen.remove(current);
				current.next = current_v.Unseen.iterator().next();
//				for (Edge e : current_v.Adj) {
//					if(!e.seen){
//						current.next = e;
//						break;
//					}
//				}
				if(current_v.Adj.size() - current_v.numOfSeenEdges >= 2){
					if(!mergeMap.containsKey(current_v))
						mergeMap.put(current_v, current);
				}
				else if(mergeMap.containsKey(current_v))
					mergeMap.remove(current_v);
				current = current.next;
			}
			current.seen = true;
			current_v.numOfSeenEdges++;
			current_v.Unseen.remove(current);
			current.otherEnd(current_v).numOfSeenEdges++;
			current.otherEnd(current_v).Unseen.remove(current);
			Edge temp = entry.getValue().next;
			current.next = temp;
			entry.getValue().next = initial_edge;
//			System.out.println(current);
		}
		
		
		Edge iter = first;
		while(iter.next!=first){
//			System.out.println(iter);
			tour.add(iter);
			iter = iter.next;
		}
//		System.out.println(iter);
		tour.add(iter);
		return tour;
	}
	static boolean verifyTour(Graph g, List<Edge> tour, Vertex start)  // verify tour is a valid Euler tour
	{
		if(tour.size()!=g.numEdges)
			return false;
		for (Edge edge : tour) {
			if(!edge.seen)
				return false;
			edge.seen = false;
		}
		return true;
	}

	private static void printEulerianPath(Graph g) {
		int rejectCount = 0;
		long start = System.currentTimeMillis();
		Vertex source = g.getSourceVertex();
		Node sourceNode = new Node(null, source);
		Node mainTour = new Node(sourceNode, null);
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(mainTour);
		while(!queue.isEmpty()){
			Node pointerNode = queue.removeFirst();
			Node current_source;
			if(pointerNode.next.v.Adj.size()==0){
				rejectCount++;
				continue;
			}
			if (pointerNode == mainTour){
				current_source = sourceNode;
			}else {
				current_source = new Node(null, pointerNode.next.v);
			}
			Node current = current_source;
//			if(0 == current.v.Adj.size())
//				continue;
			while(true){
				Edge nextEdge=null;
				Iterator<Edge> iter = current.v.Adj.iterator();
				while(iter.hasNext()){
					nextEdge = iter.next();
					iter.remove();
					if(current.v.Adj.size()>=2){
						Node secondaryPath = new Node(current, null);
						queue.addFirst(secondaryPath);
					}
					break;
				}
//				for (Edge e : current.v.Adj) {
////					if(!e.seen){
//						nextEdge = e;
//						if(nextEdge.otherEnd(current.v) == current_source.v)
//							continue;
////						e.seen = true;
////						current.v.numOfSeenEdges++;
////						nextEdge.otherEnd(current.v).numOfSeenEdges++;
//						if(current.v.Adj.size()>2 && !queue_check.contains(current.v)){
//							Node secondaryPath = new Node(current, null);
//							queue_check.add(current.v);
//							queue.addFirst(secondaryPath);
//						}
//						break;
////					}
//				}
				if(nextEdge == null){
					System.out.println("No unseen edges from "+current.v);
					break;
				}
				Vertex other_end = nextEdge.otherEnd(current.v);
//				current.v.Adj.remove(nextEdge);
				other_end.Adj.remove(nextEdge);
				if(other_end == current_source.v){
					current.next = current_source;
					break;
				}
				Node next = new Node(null, other_end);
				current.next = next;
				current = next;
			}
			if(pointerNode!=mainTour){
				Node mergePoint = pointerNode.next;
				Node endPoint = mergePoint.next;
				mergePoint.next = current_source.next;
				current_source.next = endPoint;
			}
		}
		System.out.println("Time taken = "+(System.currentTimeMillis()-start));
		System.out.println("rejected nodes from queue"+rejectCount);
		Node node = mainTour.next;
		System.out.println();
		int count = 0;
		while(node!=null){
//			System.out.print(node.v+"->");
			node=node.next;
			count++;
			if(node==mainTour.next){
//				System.out.println(node.v);
				break;
			}
		}
		System.out.println("Number of edges = "+count);
	}
	
	static class Node{
		Node next;
		Vertex v;
		public Node(Node n, Vertex vertex){
			next = n;
			v=vertex;
		}
	}
	
}
