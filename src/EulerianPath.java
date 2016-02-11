import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;


public class EulerianPath {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("test/eulerianTest4.txt"));
		Graph g = Graph.readGraph(scanner, false);
		g.printGraph();
		printEulerianPath(g);
	}

	private static void printEulerianPath(Graph g) {
		Vertex source = g.getSourceVertex();
		Node sourceNode = new Node(null, source);
		Node mainTour = new Node(sourceNode, null);
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(mainTour);
		while(!queue.isEmpty()){
			Node pointerNode = queue.removeFirst();
			Node current_source = new Node(null, pointerNode.next.v);
			Node current = current_source;
			while(true){
				Edge nextEdge=null;
				for (Edge e : current.v.Adj) {
					if(nextEdge!= null && !e.seen){
						Node secondaryPath = new Node(current, current.v);
						queue.addFirst(secondaryPath);
						break;
					}
					else if(!e.seen){
						nextEdge = e;
						e.seen = true;
					}
				}
				Vertex other_end = nextEdge.otherEnd(current.v);
				if(other_end == current_source.v)
					break;
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
		Node node = mainTour.next;
		while(node!=null){
			if(node.v!=null)
				System.out.print(node.v+"->");
			node=node.next;
		}
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
