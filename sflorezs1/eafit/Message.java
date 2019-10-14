package sflorezs1.eafit;

import sflorezs1.eafit.io.Clipboard;
import sflorezs1.eafit.lists.LinkedList;
import sflorezs1.eafit.lists.Stack;

public class Message {
    private LinkedList<Character> list;
    private final Stack<String> operations;
    private final Clipboard clipboard;

    /**
     * Constructor to be used in the mixer
     * @param list Initial message as a list of characters
     */
    public Message(LinkedList<Character> list) {
        this.list = list;
        this.operations = new Stack<>();
        this.clipboard = new Clipboard();
    }

    /**
     * Constructor to be used in the unmixer
     * @param list Initial message as a list of characters
     * @param operations Stack of operations performed
     */
    public Message(LinkedList<Character> list, Stack<String> operations) {
        this.list = list;
        this.operations = operations;
        this.clipboard = new Clipboard();
    }

    /**
     * Getter for the double linked list of characters
     * @return LinkedList of characters
     */
    public LinkedList<Character> getList() {
        return list;
    }

    /**
     * Setter for the double linked list of characters
     * @param list New list to be set
     */
    public void setList(LinkedList<Character> list) {
        this.list = list;
    }

    /**
     * Getter for the stack of operations performed
     * @return Stack of String representing the operations
     */
    public Stack<String> getOperations() {
        return operations;
    }

    /**
     * Getter for the clipboard
     * @return Clipboard
     */
    public Clipboard getClipboard() {
        return clipboard;
    }
}
