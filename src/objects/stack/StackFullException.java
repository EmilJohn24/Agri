package objects.stack;

public class StackFullException extends RuntimeException {
    public StackFullException(String s) {
        super(s);
    }
}
