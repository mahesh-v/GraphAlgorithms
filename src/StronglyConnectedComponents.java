import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by mahesh and darshan on 2/2/16.
 */
public class StronglyConnectedComponents {

    public static void main (String[] args) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File("input.txt"));
        Graph g = Graph.readGraph(scanner, true);
        System.out.println("Input Graph:");
        g.printGraph();
        int numberOfConnectedComponents = stronglyConnectedComponents(g);
        System.out.println("Number of strongly connected componenets:" + numberOfConnectedComponents);

        //Print the Strongly connected components in the Graph
        ArrayList<ArrayList<Vertex>> components = new ArrayList<ArrayList<Vertex>>(numberOfConnectedComponents);
        while (numberOfConnectedComponents > 0) {
            components.add(new ArrayList<Vertex>());
            numberOfConnectedComponents--;
        }
        for (Vertex v : g) {
            ArrayList<Vertex> currentVertexList = components.get(v.componentNumber-1);
            if (currentVertexList == null) {
                currentVertexList = new ArrayList<Vertex>();
            }

            currentVertexList.add(v);

        }
        Iterator<ArrayList<Vertex>> listsIterator = components.iterator();
        while (listsIterator.hasNext()) {
            ArrayList<Vertex> scc = listsIterator.next();
            Iterator<Vertex> vertexIterator = scc.iterator();
            System.out.print("Strongly connected Component: ");
            while (vertexIterator.hasNext()) {
                Vertex v = vertexIterator.next();
                System.out.print(v.name + " ");
            }
            System.out.println("");
        }

    }
    /**
     * Computes and returns number of strongly connected components in a given graph
     *
     * @param g Directed Graph as input
     * @return number of strongly connected components */
    static int stronglyConnectedComponents(Graph g) {
        Stack<Vertex> initialVisitStack = new Stack<Vertex>();
        int stronglyConnectedComponentNumber = 0;
        //Run DFS on the graph and put vertices in the stack in order of finishing order
        for (Vertex v : g) {
            if (!v.seen) {
                dfsVisit(v, initialVisitStack);
            }
        }
        //Mark all vertices as not seen, to run DFS again
        for (Vertex v : g) {
            v.seen = false;
        }
        //Run DFS on the G transpose in the order they pop out of stack and assign component numbers
        while(!initialVisitStack.isEmpty()) {
            Vertex currentVertex = initialVisitStack.pop();
            if (!currentVertex.seen) {
                stronglyConnectedComponentNumber++;
                dfsVisitForSCC(currentVertex, stronglyConnectedComponentNumber);
            }
        }

        return stronglyConnectedComponentNumber;

    }

    /**
     * DFS visit the vertices from the given vertex and assign the vertices visited the component number
     *
     * @param vertex source vertex to start DFS
     * @param sccNo strongly connected component number that is being assigned to the current component*/
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
    /**
    * DFS from the given vertex and puts all the finished vertex into stack
    * @param vertex source vertex to start DFS visit
    * @param s stack to push all vertices in the finishing order
    * */
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
