package sflorezs1.eafit.Lists;

/**
 * Simple implementation of a double linked list without a tail for academic purposes
 * @param <T> Data type to store
 */
public class LinkedList <T> {
    private Node<T> head;

    public LinkedList() {
        this.head = null;
    }

// --Commented out by Inspection START (10/13/2019 5:03 PM):
//    public LinkedList(LinkedList<T> another) {
//        this.head = another.head;
//    }
// --Commented out by Inspection STOP (10/13/2019 5:03 PM)

    /**
     * Method to get the size of the double linked list
     * @return Number of items in the double linked list
     */
    public int size() {
        int count = 0;
        Node<T> item = this.head;
        while (item != null) {
            count++;
            item = item.getNext();
        }
        return count;
    }

    /**
     * Helper method to get if a given index is within bounds of the list
     * @param index Index to be checked
     * @return True if the index is out of bounds else False
     */
    private boolean outOfBounds(int index) {
        return index >= this.size() || index < 0;
    }

    /**
     * Method for getting an item from the double linked list
     * @param index Index of the item to get
     * @return Item to get
     * @throws IndexOutOfBoundsException if the Index is out of bounds
     */
    public T get(int index) throws IndexOutOfBoundsException {
        if (this.outOfBounds(index)) throw new IndexOutOfBoundsException();
        else {
            int i = 0;
            Node<T> item = this.head;
            while (i < index) {
                item = item.getNext();
                i++;
            }
            return item.getItem();
        }
    }

    /**
     * Method to remove a specific item from the double linked list
     * @param index Index of the item to remove
     * @throws IndexOutOfBoundsException if the Index is out of bounds
     */
    public void remove(int index) throws IndexOutOfBoundsException {
        if (this.outOfBounds(index)) throw new IndexOutOfBoundsException();
        else {
            if (index == 0) {
                if (this.head.getNext() == null) {
                    this.head = null;
                } else {
                    this.head.getNext().setPrevious(null);
                    this.head = this.head.getNext();
                }
            } else if (index == size() - 1) {
                Node<T> temp = this.head;
                while (temp.getNext() != null) {
                    temp = temp.getNext();
                }
                temp.getPrevious().setNext(null);
            } else {
                int i = 0;
                Node<T> item = this.head;
                while (i < index - 1) {
                    item = item.getNext();
                    i++;
                }
                item.getNext().getNext().setPrevious(item);
                item.setNext(item.getNext().getNext());
            }
        }
    }

    /**
     * Method to insert a given element T in a given index in the double linked list
     * @param index Index for to element to be inserted at
     * @param element Element to be inserted
     * @throws IndexOutOfBoundsException if the Index is out of bounds
     */
    public void insert(int index, T element) throws IndexOutOfBoundsException {
        if (this.outOfBounds(index)) throw new IndexOutOfBoundsException();
        else if (index == 0) prepend(element);
        else {
            int i = 0;
            Node<T> item = this.head;
            while (i < index - 1) {
                item = item.getNext();
                i++;
            }
            Node<T> next = item.getNext();
            if (next != null) {
                Node<T> node = new Node<>(element, next, item);
                next.setPrevious(node);
                item.setNext(node);
            } else {
                item.setNext(new Node<>(element, null, item));
            }
        }
    }

    /**
     * Method to insert a element T at the end of the double linked list
     * @param element Element to be appended
     */
    public void append(T element) {
        if (this.head != null) {
            Node<T> item = this.head;
            while (item.getNext() != null) item = item.getNext();
            item.setNext(new Node<>(element, null, item));
        } else {
            this.head = new Node<>(element, null, null);
        }
    }

    /**
     * Method to insert a element T at the start of the double linked list
     * @param element Element to be prepended
     */
    public void prepend(T element) {
        if (this.head != null) {
            Node<T> node = new Node<>(element, this.head, null);
            this.head.setPrevious(node);
            this.head = node;
        } else {
            this.head = new Node<>(element, null, null);
        }
    }

    /**
     * Method to replace a element in the list with another one given its index
     * @param index
     * @param element
     * @throws IndexOutOfBoundsException
     */
    public void replace(int index, T element) throws IndexOutOfBoundsException {
        if (outOfBounds(index)) throw new IndexOutOfBoundsException();
        Node<T> temp = this.head;
        int i = 0;
        while (i < index) {
            temp = temp.getNext();
            i++;
        }
        temp.setItem(element);
    }

    public String representString() {
        StringBuilder s = new StringBuilder();
        Node<T> temp = this.head;
        while (temp != null) {
            s.append(temp.representString());
            temp = temp.getNext();
        }
        return s.toString();
    }

    public String simpleString() {
        StringBuilder s = new StringBuilder();
        Node<T> temp = this.head;
        while (temp != null) {
            s.append(temp.simpleString());
            temp = temp.getNext();
        }
        return s.toString();
    }

    public boolean contains(Object someObject) {
        for (int i = 0; i < this.size(); i++) {
            if (get(i).equals(someObject)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Node<T> temp = this.head;
        while (temp != null) {
            s.append(temp.toString());
            temp = temp.getNext();
        }
        return  s.toString();
    }
}
