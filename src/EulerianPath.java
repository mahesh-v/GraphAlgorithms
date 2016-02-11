import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;


public class EulerianPath {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("lp0_test/LP0_sample.txt"));
		Graph g = Graph.readGraph(scanner, false);
//		g.printGraph();
		System.out.println("Starting eulerian...");
		printEulerianPath(g);
	}

	private static void printEulerianPath(Graph g) {
		long start = System.currentTimeMillis();
		Vertex source = g.getSourceVertex();
		Node sourceNode = new Node(null, source);
		Node mainTour = new Node(sourceNode, null);
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(mainTour);
		while(!queue.isEmpty()){
			Node pointerNode = queue.removeFirst();
			Node current_source;
			if (pointerNode == mainTour){
				current_source = sourceNode;
			}else {
				current_source = new Node(null, pointerNode.next.v);
			}
			Node current = current_source;
			if(0 == current.v.Adj.size())
				continue;
			while(true){
				Edge nextEdge=null;
				
				for (Edge e : current.v.Adj) {
//					if(!e.seen){
						nextEdge = e;
//						e.seen = true;
//						current.v.numOfSeenEdges++;
//						nextEdge.otherEnd(current.v).numOfSeenEdges++;
						if(current.v.Adj.size()>2){
							Node secondaryPath = new Node(current, null);
							queue.addFirst(secondaryPath);
						}
						break;
//					}
				}
				if(nextEdge == null){
					System.out.println("No unseen edges from "+current.v);
					break;
				}
				Vertex other_end = nextEdge.otherEnd(current.v);
				current.v.Adj.remove(nextEdge);
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
