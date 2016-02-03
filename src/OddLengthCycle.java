import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Mahesh and Darshan on 2/2/16.
 */
public class OddLengthCycle {

    public static void main (String[] args) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File("input.txt"));
        Graph g = Graph.readGraph(scanner, false);
        g.printGraph();
        List<Vertex> cycle = oddLengthCycle(g);
        if(cycle == null) {
            System.out.println("Its Bipartite graph");
        } else{
            System.out.print("Found a odd length cycle:");
            for(Vertex v: cycle) {
                System.out.print(v.name+ " ");
            }
        }


    }

    static List<Vertex> oddLengthCycle(Graph g) {
        List<Vertex> vertices = g.verts;


        for( Vertex source: g) {
            if(!source.seen) {
                List<Vertex> cycleVerticesLeftQueue = new LinkedList<Vertex>();
                Stack<Vertex> cycleVerticesRightStack = new Stack<Vertex>();
                List<Vertex> queue = new LinkedList<Vertex>();

                source.seen = true;
                source.distance = 0;
                queue.add(source);

                while(!queue.isEmpty()) {
                    Vertex currentVertex = queue.remove(0);
                    List<Edge> adjEdges = currentVertex.Adj;
                    for(Edge e: adjEdges) {
                        Vertex v = e.otherEnd(currentVertex);
                        if(!v.seen) {
                            v.seen = true;
                            v.parent = currentVertex;
                            v.distance = currentVertex.distance + 1;
                            queue.add(v);
                        } else if(v.seen && v.distance == currentVertex.distance) {
                            cycleVerticesLeftQueue.add(v);
                            cycleVerticesRightStack.push(currentVertex);
                            Vertex x1 = v;
                            Vertex x2 = currentVertex;
                            while(x1.parent != x2.parent) {
                                cycleVerticesLeftQueue.add(x1);
                                cycleVerticesRightStack.push(x2);
                            }

                            cycleVerticesLeftQueue.add(x1.parent);
                            while(!cycleVerticesRightStack.isEmpty()) {
                                cycleVerticesLeftQueue.add(cycleVerticesRightStack.pop());
                            }
                            return cycleVerticesLeftQueue;
                        }
                    }
                }
            }
        }

        return null;
    }

}
