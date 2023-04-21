public class FordFulkerson {
    public int V;          // number of vertices
    public boolean[] marked;     // marked[v] = true iff s->v path in residual graph
    public FlowEdge[] edgeTo;    // edgeTo[v] = last edge on shortest residual s->v path
    public int value;         // current value of max flow

    public FordFulkerson(FlowNetwork G, int s, int t) {
        V = G.V();
        if (s == t) throw new IllegalArgumentException("Source equals sink");

        // while there exists an augmenting path, use it
        value = excess(G, s);
        while (hasAugmentingPath(G, s, t)) {
            // compute bottleneck capacity
            int bottle = bottleneck(G, s, t);
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

    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];

        ArrayQueue<Integer> queue = new ArrayQueue<>(G.V());
        queue.enqueue(s);
        marked[s] = true;

        while (!queue.isEmpty()) {
            int v = queue.dequeue();

            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);

                // if residual capacity from v to w
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = e;
                    marked[w] = true;
                    if (w == t) return true; // stop BFS as soon as path to t is found
                    queue.enqueue(w);
                }
            }
        }
        // no augmenting path found
        return false;
    }


    private int excess(FlowNetwork G, int v) {
        int excess = 0;
        for (FlowEdge e : G.adj(v)) {
            if (v == e.from()) excess += e.flow();
            else excess -= e.flow();
        }
        return excess;
    }

    private int bottleneck(FlowNetwork G, int s, int t) {
        int bottle = Integer.MAX_VALUE;
        for (int v = t; v != s; v = edgeTo[v].other(v)) {
            int residualCapacity = edgeTo[v].residualCapacityTo(v);
            bottle = Math.min(bottle, residualCapacity);
        }
        return bottle;
    }
}
