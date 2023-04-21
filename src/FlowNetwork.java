import java.util.ArrayList;
import java.util.List;

public class FlowNetwork {
    private final int V;
    private int E;
    public List<List<FlowEdge>> adj2D = new ArrayList<List<FlowEdge>>();
    public FlowNetwork(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Graph must be non-negative");
        this.V = V;
        this.E = 0;
        adj2D = new ArrayList<List<FlowEdge>>(V);
        for (int v = 0; v < V; v++)
            adj2D.add(new ArrayList<FlowEdge>());
    }
    public int V() {
        return V;
    }

    public void addOrUpdateEdgeToUndirectedGraph(int tailVertex, int headVertex, double capacity) {
        var oneDirection = new FlowEdge(tailVertex, headVertex, capacity);
        var otherDirection = new FlowEdge(headVertex, tailVertex, capacity);
        //Check if the edge already exists, if so update the capacity
        for (FlowEdge e : adj2D.get(tailVertex)) {
            if (e.to() == tailVertex && e.from() == headVertex) {
                e.capacity += capacity;
                adj2D.get(headVertex).get(tailVertex).capacity += capacity;
                //Reset current flow, from previous calculation
                e.flow = 0;
                adj2D.get(headVertex).get(tailVertex).flow = 0;
                return;
            }
        }
        addEdge(oneDirection);
        addEdge(otherDirection);
    }

    public void addEdge(FlowEdge e) {
        int v = e.from();
        int w = e.to();
        adj2D.get(v).add(e);
        adj2D.get(w).add(e);
        E++;
    }

    //Create method to reset all flows to 0
    public void resetFlows() {
        for (int v = 0; v < V; v++) {
            for (FlowEdge e : adj2D.get(v)) {
                e.flow = 0;
            }
        }
    }

    public Iterable<FlowEdge> adj(int v) {
        return adj2D.get(v);
    }

}
