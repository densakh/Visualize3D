package list;

public class DLNode<T> {
    private T data;
    private DLNode<T> next;
    private DLNode<T> prev;
    
    DLNode(){
        next=null;
        prev=null;
        data=null;
    }
    
    DLNode(T data) {
        this(data, null, null);
    }
    
    DLNode(T data, DLNode<T> next, DLNode<T> prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
    
    T getData() {
        return data;
    }
    
    public void setNextNode(DLNode<T> next) {
        this.next = next;
    }
    
    public DLNode<T> getPrevNode() {
        return prev;
    }
    
    public void setPrevNode(DLNode<T> prev) {
        this.prev = prev;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public DLNode<T> getNextNode() {
        return next;
    }
}
