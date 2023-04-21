import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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

        //Source
        String[] sourceInput = br.readLine().split(" "); //1 3 10  a to b with capacity 10
        int sourceA = Integer.parseInt(sourceInput[0]);
        int sourceB = Integer.parseInt(sourceInput[1]);
        int sourceCapacity = Integer.parseInt(sourceInput[2]);
        flowNetwork.addEdgeToUndirectedGraph(sourceA, sourceB, sourceCapacity);

        //Destination
        String[] destinationInput = br.readLine().split(" "); //2 3 1  a to b with capacity 1
        int destinationA = Integer.parseInt(destinationInput[0]);
        int destinationB = Integer.parseInt(destinationInput[1]);
        int destinationCapacity = Integer.parseInt(destinationInput[2]);
        flowNetwork.addEdgeToUndirectedGraph(destinationA, destinationB, destinationCapacity);

        //Add initial pipes
        for (int i = 2; i < NumberOfPipes; i++) {
            String[] pipeInput = br.readLine().split(" ");
            int pipeA = Integer.parseInt(pipeInput[0]);
            int pipeB = Integer.parseInt(pipeInput[1]);
            int pipeCapacity = Integer.parseInt(pipeInput[2]);
            flowNetwork.addEdgeToUndirectedGraph(pipeA, pipeB, pipeCapacity);
        }

        final int source = 1;
        final int sink = 2;

        //Print initial maxflow
        FordFulkerson ff = new FordFulkerson(flowNetwork, source, sink);
        System.out.println((int) ff.value());

        //k-Improvements
        for (int i = 0; i < NumberOfImprovements; i++) {
            String[] ImprovementInput = br.readLine().split(" ");
            int improvementA = Integer.parseInt(ImprovementInput[0]);
            int improvementB = Integer.parseInt(ImprovementInput[1]);
            int improvementCapacity = Integer.parseInt(ImprovementInput[2]);
            flowNetwork.addEdgeToUndirectedGraph(improvementA, improvementB, improvementCapacity);
            ff = new FordFulkerson(flowNetwork, source, sink);
            System.out.println((int) ff.value());
        }
    }
}