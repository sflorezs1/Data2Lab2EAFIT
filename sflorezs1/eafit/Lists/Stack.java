package sflorezs1.eafit.lists;

/**
 * Simple stack implementation for academic purposes
 * @param <T> Data type to store
 */
public class Stack <T> {
    private Node<T> head;

    /**
     * Constructor for the Stack class
     */
    public Stack() {
        this.head = null;
    }

    /**
     * Method to get the top element of the Stack and remove it
     * @return Top element removed from the Stack
     */
    public T pop() {
        T item = this.head.getItem();
        this.head = this.head.getNext();
        return item;
    }

    /**
     * Method to add an element to the top of the Stack
     * @param element Element to be added on top of the Stack
     */
    public void push(T element) {
        if (this.head != null) {
            Node<T> node = new Node<>(element, this.head, null);
            this.head.setPrevious(node);
            this.head = node;
        } else {
            this.head = new Node<>(element, null, null);
        }
    }

    /**
     * Method to get the top element of the Stack
     * @return Element on top of the Stack
     */
    public T peek() {
        return this.head == null ? null : this.head.getItem();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Node<T> temp = this.head;
        while (temp != null) {
            s.append(temp.representString()).append(temp.getNext() == null? "" : '\n');
            temp = temp.getNext();
        }
        return  s.toString();
    }
}
