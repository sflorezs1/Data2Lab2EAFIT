package sflorezs1.eafit.Lists;

public class Node <T> {
    private T item;
    private Node<T> next;
    private Node<T> previous;

    public Node(T item, Node next, Node previous) {
        this.item = item;
        this.next = next;
        this.previous = previous;
    }

    public T getItem() {
        return item;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public String representString() {
        return item.toString();
    }

    @Override
    public String toString() {
        return item + (next == null? "": "  ");
    }
}
