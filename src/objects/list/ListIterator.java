package objects.list;

import java.util.Iterator;

public class ListIterator<E> implements Iterator<E> {
    private List<E> thisList;
    private ListNode<E> current;
    ListIterator(List<E> list){
        thisList = list;
        current = thisList.getRoot();
    }
    @Override
    public boolean hasNext() {
        return current != null && current.getNext() != null;
    }

    @Override
    public E next() {
        E data = current.getData();
        current = current.getNext();
        return data;

    }
}
