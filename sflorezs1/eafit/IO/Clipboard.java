package sflorezs1.eafit.io;

import sflorezs1.eafit.lists.LinkedList;

import java.util.HashMap;

/**
 * Simple clipboard class implementing a HashMap
 */
public class Clipboard {
    /**
     * Holder of the clipboard
     */
    private final HashMap<Integer, LinkedList<Character>> holder;

    /**
     * Constructor for the Clipboard class
     */
    public Clipboard() {
        this.holder = new HashMap<>();
    }

    /**
     * Creates a new entry at the holder with the key clipboardN and the value part
     * Like a normal clipboard, if a new value is inserted in a occupied key, the old value will be overwritten
     * @param clipboardN Key for the clipboard
     * @param part Part to be copied
     */
    public void copy(int clipboardN, LinkedList<Character> part) {
        this.holder.put(clipboardN, part);
    }

    /**
     * Given a key return its holder value
     * @param clipboardN Key for the needed value
     * @return LinkedList of characters
     */
    public LinkedList<Character> paste(int clipboardN) {
        return this.holder.get(clipboardN);
    }

    /**
     * Create a String with a representation of a given clipboard index
     * @param nClipboard Key for the needed value
     * @return String
     */
    public String showClipboard(int nClipboard) {
        return  "Clipboard[" + nClipboard + "] ====> {" + this.holder.get(nClipboard).toString() + '}';
    }

    /**
     * Create a String for the representation of the given clipboard in a message key
     * @param nClipboard Key for the needed value
     * @return String
     */
    public String saveClipboard(int nClipboard) {
        return nClipboard + " " + this.holder.get(nClipboard).toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.holder.forEach((key, value) -> sb.append(key).append(" <-> ").append(value).append('\n'));
        return sb.toString();
    }
}
