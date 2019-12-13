package objects.array;

import org.omg.CORBA.Object;

public class Array<T> {
    private T[] data;
    private int currentSize;
    private int lastInsertion;

    public Array(){
        currentSize = 1;
        data = (T[]) new Object[currentSize];
        lastInsertion = 0;

    }
    public void add(T passedData){
        data[lastInsertion++] = passedData;
        if (lastInsertion == currentSize) reallocate();
    }

    private void reallocate(){
        data = (T[]) new Object[currentSize * 2];
        currentSize = currentSize * 2;
    }

    public void add(int index, T passedData){
        while (index >= currentSize) reallocate();
        data[index] = passedData;
    }

    public T get(int index){
        return data[index];
    }

    public void set(int index, T obj){
        data[index] = obj;
    }
}
