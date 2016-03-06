import java.util.*;

/**
 * Created by Darshan and Mahesh on 3/3/2016.
 * This creates MST for a given input Graph
 */
public class MST {
    private Graph g;
    private long weightOfMST;

    public MST(Graph g) {
        this.g = g;
        constructMST();
    }

    /*
    * construct the MST of the Graph g
    * */
    private void constructMST() {
        decreaseWeights();
        Vertex unseenVertex = BFSandReturnUnreachedVertex();
        if(unseenVertex == null){
            System.out.println("MST complete");
        }
    }


    public long getWeightOfMST() {
        return weightOfMST;
    }
    /*
    * This method decreases the weights of all incoming edges of a vertex by the minimum weight of the incoming edges.
    * */
    private void decreaseWeights() {
        for(Vertex v: g) {
            int minimumWeightOfEdge = -1;
            for(Edge e : v.revAdj) {
                if(minimumWeightOfEdge == -1 || e.ReComputedWeight < minimumWeightOfEdge) {
                    minimumWeightOfEdge = e.ReComputedWeight;
                }
            }

            if(minimumWeightOfEdge > -1) {
                for (Edge e: v.revAdj) {
                    e.ReComputedWeight -= minimumWeightOfEdge;
                }
            }
        }
    }

    private Vertex BFSandReturnUnreachedVertex() {
        LinkedList<Vertex> queue = new LinkedList<Vertex>();
        long treeWeight = 0;
        Vertex root = g.getSourceVertex();
        root.seen = true;
        queue.add(root);

        while (!queue.isEmpty()) {
            Vertex v = queue.remove();
            for (Edge e : v.Adj) {
                if (e.ReComputedWeight == 0 && !e.otherEnd(v).seen) {
                    queue.add(e.otherEnd(v));
                    e.otherEnd(v).seen = true;
                    treeWeight += e.Weight;
                }
            }
        }

        for(Vertex v: g) {
            if(!v.seen){
                return v;
            }
        }
        weightOfMST = treeWeight;
        return null;
    }

    private void markAllVerticesNotSeen() {

    }
}
