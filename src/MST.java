import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
        System.out.print(weightOfMST);
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
                weightOfMST+=minimumWeightOfEdge;
                for (Edge e: v.revAdj) {
                    e.ReComputedWeight -= minimumWeightOfEdge;
                }
            }
        }
    }
}
