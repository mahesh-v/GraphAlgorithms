import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by mahesh and darshan on 2/2/16.
 */
public class StronglyConnectedComponents {

    public static void main (String[] args) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File("input.txt"));
        Graph g = Graph.readGraph(scanner, true);
        g.printGraph();
        int numberOfConnectedComponents = stronglyConnectedComponents(g);
        System.out.println("Number of strongly connected componenets:" + numberOfConnectedComponents);

    }

    static int stronglyConnectedComponents(Graph g) {
        Stack<Vertex> initialVisitStack = new Stack<Vertex>();
        int stronglyConnectedComponentNumber = 0;

        for (Vertex v : g) {
            if (!v.seen) {
                dfsVisit(v, initialVisitStack);
            }
        }
        for (Vertex v : g) {
            v.seen = false;
        }

        while(!initialVisitStack.isEmpty()) {
            Vertex currentVertex = initialVisitStack.pop();
            if (!currentVertex.seen) {
                stronglyConnectedComponentNumber++;
                dfsVisitForSCC(currentVertex, stronglyConnectedComponentNumber);
            }
        }

        return stronglyConnectedComponentNumber;

    }

    private static void dfsVisitForSCC(Vertex vertex, int sccNo) {
        vertex.seen = true;
        vertex.componentNumber = sccNo;
        for (Edge e : vertex.revAdj) {
            Vertex u = e.otherEnd(vertex);
            if(!u.seen){
                u.parent = vertex;
                dfsVisitForSCC(u, sccNo);
            }
        }
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
