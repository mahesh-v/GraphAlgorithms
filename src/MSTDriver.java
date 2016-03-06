import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by DarshanNarayana on 3/5/2016.
 */
public class MSTDriver {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("lp1_test/sample.txt"));
        Graph g = Graph.readGraph(scanner, true);
        MST mst = new MST(g);
        System.out.println("MST weight is : " + mst.getWeightOfMST());
    }

}
