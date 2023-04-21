import java.util.ArrayDeque;
import java.util.Queue;

public class FordFulkerson {
    private final int V;             // number of vertices
    private boolean[] marked;       // marked[v] = true iff s->v path in residual graph
    private FlowEdge[] edgeTo;      // edgeTo[v] = last edge on shortest residual s->v path
    private int value;              // current value of max flow

    private final FlowNetwork G;    // flow network

    public FordFulkerson(FlowNetwork G, int s, int t) {
        this.G = G;
        this.V = G.V();
        if (s == t) throw new IllegalArgumentException("Source equals sink");

        // while there exists an augmenting path, use it
        value = excess(s);
        while (hasAugmentingPath(s, t)) {
            // compute bottleneck capacity
            int bottle = bottleneck(s, t);
            // augment flow
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle);
            }

            value += bottle;
        }
    }

    public int maxFlow() {
        return value;
    }

    public void addEdge(int u, int v, int capacity) {
        G.addEdge(new FlowEdge(u, v, capacity));
    }

    private boolean hasAugmentingPath(int s, int t) {
        edgeTo = new FlowEdge[V];
        marked = new boolean[V];

        Queue<Integer> queue = new ArrayDeque<>(V);
        queue.offer(s);
        marked[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.poll();

            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);

                // if residual capacity from v to w
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = e;
                    marked[w] = true;
                    if (w == t) return true; // stop BFS as soon as path to t is found
                    queue.offer(w);
                }
            }
        }
        // no augmenting path found
        return false;
    }

    private int excess(int v) {
        int excess = 0;
        for (FlowEdge e : G.adj(v)) {
            if (v == e.from()) excess += e.flow();
            else excess -= e.flow();
        }
        return excess;
    }

    private int bottleneck(int s, int t) {
        int bottle = Integer.MAX_VALUE;
        for (int v = t; v != s; v = edgeTo[v].other(v)) {
            int residualCapacity = edgeTo[v].residualCapacityTo(v);
            bottle = Math.min(bottle, residualCapacity);
        }
        return bottle;
    }
}
