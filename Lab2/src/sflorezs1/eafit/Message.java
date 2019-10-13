package sflorezs1.eafit;

import sflorezs1.eafit.IO.Clipboard;
import sflorezs1.eafit.Lists.LinkedList;
import sflorezs1.eafit.Lists.Stack;

public class Message {
    private LinkedList<Character> list;
    private Stack<String> operations;
    private Clipboard clipboard;

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
    }

    public LinkedList<Character> getList() {
        return list;
    }

    public void setList(LinkedList<Character> list) {
        this.list = list;
    }

    public Stack<String> getOperations() {
        return operations;
    }

    public void setOperations(Stack<String> operations) {
        this.operations = operations;
    }

    public Clipboard getClipboard() {
        return clipboard;
    }

    public void setClipboard(Clipboard clipboard) {
        this.clipboard = clipboard;
    }
}
