import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        final int source = 1;
        final int sink = 2;

        String[] input = br.readLine().split(" ");

        /*
        first line:
        n < 100    - Number of stations (vertices)
        p < n^2    - Number of pipes (edges)
        k < 10000  - number of improvements
         */
        int numberOfStations = Integer.parseInt(input[0]);
        int NumberOfPipes = Integer.parseInt(input[1]);
        int NumberOfImprovements = Integer.parseInt(input[2]);

        var flowNetwork = new FlowNetwork(numberOfStations+1);

        //Add initial pipes
        for (int i = 0; i < NumberOfPipes; i++) {
            String[] pipeInput = br.readLine().split(" ");
            int pipeA = Integer.parseInt(pipeInput[0]);
            int pipeB = Integer.parseInt(pipeInput[1]);
            int pipeCapacity = Integer.parseInt(pipeInput[2]);
            flowNetwork.addOrUpdateEdgeToUndirectedGraph(pipeA, pipeB, pipeCapacity);
        }

        //Print initial maxflow
        FordFulkerson ff = new FordFulkerson(flowNetwork, source, sink);
        System.out.println((int) ff.maxFlow());
        //for (int l = 0; l < flowNetwork.adj2D.size(); l++) {
        //    for (int j = 0; j < flowNetwork.adj2D.get(l).size(); j++) {
        //        System.out.println(flowNetwork.adj2D.get(l).get(j).toString());
        //    }
        //}

        //k-Improvements
        for (int i = 0; i < NumberOfImprovements; i++) {
            for (int g = 0; g < flowNetwork.adj2D.size(); g++) {
                for (int j = 0; j < flowNetwork.adj2D.get(g).size(); j++) {
                    flowNetwork.adj2D.get(g).get(j).flow = 0;
                }
            }
            String[] ImprovementInput = br.readLine().split(" ");
            int improvementA = Integer.parseInt(ImprovementInput[0]);
            int improvementB = Integer.parseInt(ImprovementInput[1]);
            int improvementCapacity = Integer.parseInt(ImprovementInput[2]);
            flowNetwork.addOrUpdateEdgeToUndirectedGraph(improvementA, improvementB, improvementCapacity);
            FordFulkerson newff = new FordFulkerson(flowNetwork, source, sink);
            System.out.println((int) newff.maxFlow());
            //print all edges with foreach loop
            //for (int l = 0; l < flowNetwork.adj2D.size(); l++) {
            //    for (int j = 0; j < flowNetwork.adj2D.get(l).size(); j++) {
            //        System.out.println(flowNetwork.adj2D.get(l).get(j).toString());
            //    }
            //}
        }
    }
}