package objects.stack;

public class Stack<T> implements StackInterface<T> {

    @Override
    public void push(T j) {
        reallocStrategy();
        this.container[stackPointer++] = j;
    }

    public void reallocStrategy(){
        //basic strategy, if the stack is full, make the static array twice as long
        if (isFull())
            realloc(this.length * 2);
    }

    public void realloc(int size){
        T[] tmp = this.container.clone();
        this.container = (T[]) new Object[size];
        for (Integer i = 0; i < tmp.length; ++i){
            this.container[i] = tmp[i];
        }
        this.length = size;
    }

    @Override
    public void pop() throws StackEmptyException {
        if (isEmpty()) throw new StackEmptyException("Stack is empty");
        stackPointer--;
    }

    @Override
    public T top() throws StackEmptyException {
        return this.container[stackPointer - 1];
    }

    @Override
    public boolean isEmpty() {
        return stackPointer == 0;
    }

    @Override
    public boolean isFull() {
        return stackPointer == length;
    }

    @Override
    public int size() {
        return this.length;
    }

    public Stack(){
        this(1);
    }
    public Stack(int size){
        this.container = (T[]) new Object[size];
        this.length = size;
        this.stackPointer = 0;

    }

    private T[] container;
    private int length;
    private int stackPointer;
}
