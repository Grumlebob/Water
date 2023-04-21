public class ArrayQueue<T> {
    private T[] queue;
    private int head;
    private int tail;

    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) {
        queue = (T[]) new Object[capacity];
        head = 0;
        tail = 0;
    }

    public void enqueue(T item) {
        queue[tail++] = item;
        if (tail == queue.length) {
            tail = 0;
        }
    }

    public T dequeue() {
        T item = queue[head];
        queue[head++] = null;
        if (head == queue.length) {
            head = 0;
        }
        return item;
    }

    public boolean isEmpty() {
        return head == tail && queue[head] == null;
    }
}
