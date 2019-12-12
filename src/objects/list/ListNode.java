package objects.list;

class ListNode<T> {
    private T data;
    private ListNode<T> next;

    ListNode(T data){
        this(data, null);
    }
    ListNode(T data, ListNode<T> next){
        this.data = data;
        this.next = next;
    }

    ListNode<T> getNext() {
        return next;
    }

    void setNext(ListNode next) {
        this.next = next;
    }

    T getData() {
        return data;
    }

    void setData(T data) {
        this.data = data;
    }
}
