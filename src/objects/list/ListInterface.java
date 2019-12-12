package objects.list;

public interface ListInterface<T> {
    void add(T item); //front
    void add(int index, T item);
    void remove(int index);
    T get(int index);
    void set(int index, T item);
    int size();
    boolean empty();
    void clear();
    boolean contains(T item);
    int indexOf(T item);
}
