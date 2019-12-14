package objects.queue;

public interface QueueInterface<T> {
    void push(T item);
    T pop();
    T front();
    T back();
    int size();
    boolean empty();
}
