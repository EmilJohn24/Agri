package objects.list;

import java.util.Collection;
import java.util.Iterator;

public class List<T> implements ListInterface<T>, Iterable<T> {
    private ListNode<T> root;
    private int size;

    private ListNode<T> getNodeBefore(int index){
        return getNodeAt(index - 1);
    }
    ListNode<T> getRoot(){return root;}
    private ListNode<T> getNodeAt(int index){
        if (index < 0 && index >= size) throw new ArrayIndexOutOfBoundsException("Invalid index");
        ListNode<T> explorer = root;
        for (int i = 0; i < index; ++i){
            explorer = explorer.getNext();
        }
        return explorer;
    }



    public List(){
        this.size = 0;
        this.root = null;
    }

    public List(Collection<T> collection){
        for (T item : collection){
            add(item);
        }
    }

    public void addFront(T item) {
        ListNode<T> newNode = new ListNode<>(item, root);
        if (empty()) this.root = newNode;
        else root = newNode;
        size++;
    }

    @Override
    public void add(T item){
        add(size, item);
    }

    @Override
    public void add(int index, T item) {
        if (index > size || index < 0) throw new IndexOutOfBoundsException("Invalid index");
        else if (index == 0) {
            this.root = new ListNode<>(item, root);
            size++;
            return;
        }
        ListNode<T> previous = getNodeBefore(index);
        ListNode<T> newNode = new ListNode<T>(item, previous.getNext());
        previous.setNext(newNode);
        size++;
    }


    public void remove(T obj){
        remove(indexOf(obj));
    }

    @Override
    public void remove(int index) {
        if (index >= size || index < 0) throw new IndexOutOfBoundsException("Invalid index");
        if (index == 0){
            root = root.getNext();
        }
        else {
            ListNode<T> previous = this.getNodeBefore(index);
            ListNode<T> removedNode = previous.getNext();
            previous.setNext(removedNode.getNext());
        }
        size--;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) throw new IndexOutOfBoundsException("Invalid index");
        ListNode<T> current =  getNodeAt(index);
        return current.getData();
    }

    @Override
    public void set(int index, T item) {
        ListNode<T> current = getNodeAt(index);
        current.setData(item);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean empty() {
        return this.size == 0 && this.root == null;
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public boolean contains(T item) {
        return this.indexOf(item) != -1;
    }

    @Override
    public int indexOf(T item) {
        ListNode<T> explorer = root;
        for (int i = 0; i != size; ++i){
            if (item == explorer.getData() || explorer.getData().equals(item)) return i;
            explorer = explorer.getNext();
        }
        return -1;
    }


    @Override
    public ListIterator<T> iterator() {
        return new ListIterator<>(this);
    }
}
