
public class FlowEdge {
    // to deal with floating-point roundoff errors
    private final int v;            // from
    private final int w;            // to
    public int capacity;            // capacity
    public int flow;                // flow

    public FlowEdge(int tailVertex, int headVertex, int capacity) {
        this.v = tailVertex;
        this.w = headVertex;
        this.capacity = capacity;
        this.flow = 0;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public int capacity() {
        return capacity;
    }

    public int flow() {
        return flow;
    }

    public int other(int vertex) {
        if (vertex == v) {
            return w;
        } else if (vertex == w) {
            return v;
        } else {
            throw new IllegalArgumentException("invalid endpoint");
        }
    }

    public int residualCapacityTo(int vertex) {
        if (vertex == v) {
            return flow;                // backward edge
        } else if (vertex == w) {
            return capacity - flow;     // forward edge
        } else {
            throw new IllegalArgumentException("invalid endpoint");
        }
    }

    public void addResidualFlowTo(int vertex, int delta) {
        if (vertex == v) {
            flow -= delta;              // backward edge
        } else if (vertex == w) {
            flow += delta;              // forward edge
        } else {
            throw new IllegalArgumentException("invalid endpoint");
        }
        // round flow to 0 or capacity if necessary
        if (flow < 0) {
            flow = 0;
        } else if (flow > capacity) {
            flow = capacity;
        }
    }

    public String toString() {
        return v + "->" + w + " " + flow + "/" + capacity;
    }


}