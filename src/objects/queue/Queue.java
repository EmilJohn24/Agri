package objects.queue;

public class Queue<T> implements QueueInterface<T> {
    private T[] circularBuffer; //circular buffer placed here
    private int filledFront;
    private int emptyBack;
    private int max;
    private int size;

    public Queue(){
        this(1000);
    }
    public Queue(int size){
        circularBuffer = (T[]) new Object[size];
        this.size = 0;
        this.max = size;
        emptyBack = size / 2;
        filledFront = -1;
    }

    private int boundedIndex(int index){
        //Note here: Since Java uses remainders instead of modulo, there had to be some kind of check for negative results
        int modulo = index % max;
        if (modulo < 0) {
            modulo = modulo + max;
        }
        return modulo;
    }

    private boolean isFilled(){
        return filledFront != -1;
    }
    private boolean isFull(){
        return filledFront == emptyBack;
    }

    @Override
    public void push(T item) {
        if (isFull()) throw new OutOfMemoryError("Out of space.");
        if (!isFilled()) filledFront = boundedIndex(emptyBack);
        size++;
        circularBuffer[emptyBack] = item;
        emptyBack = boundedIndex(emptyBack + 1);
    }

    @Override
    public T pop() {
        size--;
        T holder = circularBuffer[filledFront];
        filledFront = boundedIndex(filledFront + 1);
        return holder;
    }

    @Override
    public T front() {
        return circularBuffer[filledFront];
    }

    @Override
    public T back() {
        return circularBuffer[boundedIndex(emptyBack - 1)];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean empty() {
        return this.size == 0;
    }
}
