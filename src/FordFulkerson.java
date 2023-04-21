import java.util.LinkedList;
import java.util.Queue;

public class FordFulkerson {
    public int V;          // number of vertices
    public boolean[] marked;     // marked[v] = true iff s->v path in residual graph
    public FlowEdge[] edgeTo;    // edgeTo[v] = last edge on shortest residual s->v path
    public int value;         // current value of max flow

    public FordFulkerson(FlowNetwork G, int s, int t) {
        V = G.V();
        if (s == t) throw new IllegalArgumentException("Source equals sink");

        // while there exists an augmenting path, use it
        value = excess(G, t);
        while (hasAugmentingPath(G, s, t)) {
            // compute bottleneck capacity
            int bottle = Integer.MAX_VALUE;
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }
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

        // breadth-first search
        //Queue<Integer> queue = new Queue<Integer>();
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        marked[s] = true;
        while (!queue.isEmpty() && !marked[t]) {
            int v = queue.remove();

            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);

                // if residual capacity from v to w
                if (e.residualCapacityTo(w) > 0) {
                    if (!marked[w]) {
                        edgeTo[w] = e;
                        marked[w] = true;
                        queue.add(w);
                    }
                }
            }
        }
        // is there an augmenting path?
        return marked[t];
    }

    // return excess flow at vertex v
    private int excess(FlowNetwork G, int v) {
        int excess = 0;
        for (FlowEdge e : G.adj(v)) {
            if (v == e.from()) excess -= e.flow();
            else excess += e.flow();
        }
        return excess;
    }
}
