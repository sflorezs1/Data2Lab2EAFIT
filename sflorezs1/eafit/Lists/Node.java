package sflorezs1.eafit.lists;

class Node <T> {
    private T item;
    private Node<T> next;
    private Node<T> previous;

    Node(T item, Node<T> next, Node<T> previous) {
        this.item = item;
        this.next = next;
        this.previous = previous;
    }

    T getItem() {
        return item;
    }

    Node<T> getNext() {
        return next;
    }

    Node<T> getPrevious() {
        return previous;
    }

    void setItem(T item) {
        this.item = item;
    }

    void setNext(Node<T> next) {
        this.next = next;
    }

    void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

    String representString() {
        return item.toString();
    }

    String simpleString() {
        return item + (next == null? "": "  ");
    }

    @Override
    public String toString() {
        return item + (next == null? "": "\t");
    }
}
