import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        final int source = 1;
        final int sink = 2;

        String[] input = br.readLine().split(" ");

        int numberOfStations = Integer.parseInt(input[0]); //n < 100    - Number of stations (vertices)
        int NumberOfPipes = Integer.parseInt(input[1]); //p < n^2    - Number of pipes (edges)
        int NumberOfImprovements = Integer.parseInt(input[2]); //k < 10000  - number of improvements

        var flowNetwork = new FlowNetwork(numberOfStations + 1);

        //Add initial pipes
        for (int i = 0; i < NumberOfPipes; i++) {
            String[] pipeInput = br.readLine().split(" ");
            int pipeA = Integer.parseInt(pipeInput[0]);
            int pipeB = Integer.parseInt(pipeInput[1]);
            int pipeCapacity = Integer.parseInt(pipeInput[2]);
            flowNetwork.addOrUpdateEdgeToUndirectedGraphWorking(pipeA, pipeB, pipeCapacity);
        }

        //Print initial maxflow
        FordFulkerson ff = new FordFulkerson(flowNetwork, source, sink);
        var kattisResult = new StringBuilder().append(ff.maxFlow()).append("\n");

        //k-Improvements
        for (int i = 0; i < NumberOfImprovements; i++) {
            String[] ImprovementInput = br.readLine().split(" ");
            int improvementA = Integer.parseInt(ImprovementInput[0]);
            int improvementB = Integer.parseInt(ImprovementInput[1]);
            int improvementCapacity = Integer.parseInt(ImprovementInput[2]);
            flowNetwork.addOrUpdateEdgeToUndirectedGraphWorking(improvementA, improvementB, improvementCapacity);
            FordFulkerson newff = new FordFulkerson(flowNetwork, source, sink);
            kattisResult.append(newff.maxFlow()).append("\n");
        }
        //Print results
        System.out.println(kattisResult.toString());
    }
}