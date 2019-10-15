package sflorezs1.eafit.io;

import sflorezs1.eafit.lists.LinkedList;

/**
 * Simple clipboard class implementing LinkedLists
 */
public class LinkedClipboard {
    LinkedList<LinkedList<Character>> clip;
    LinkedList<Integer> keys;

    /**
     * Constructor for the LinkedClipboard class
     */
    public LinkedClipboard() {
        clip = new LinkedList<>();
        keys = new LinkedList<>();
    }

    /**
     * Creates a new entry at the holder with the key nClipboard and the value part
     * Like a normal clipboard, if a new value is inserted in a occupied key, the old value will be overwritten
     * @param nClipboard Key for the clipboard
     * @param part Part to be copied
     */
    public void copy(int nClipboard, LinkedList<Character> part) {
        if (keys.contains(nClipboard)) {
            for (int i = 0; i < keys.size(); i++) {
                if (keys.get(i).equals(nClipboard)) {
                    clip.replace(i, part);
                }
            }
        } else {
            keys.append(nClipboard);
            clip.append(part);
        }
    }

    /**
     * Given a key return its holder value
     * @param nClipboard Key for the needed value
     * @return LinkedList of characters
     */
    public LinkedList<Character> paste(int nClipboard) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(nClipboard)) {
                return clip.get(i);
            }
        }
        return null;
    }

    /**
     * Create a String with a representation of a given clipboard index
     * @param nClipboard Key for the needed value
     * @return String
     */
    public String showClipboard(int nClipboard) {
        if (keys.contains(nClipboard)) {
            int i = 0;
            while (i < keys.size()) {
                if (keys.get(i).equals(nClipboard)) {
                    break;
                }
                i++;
            }
            return "Clipboard[" + nClipboard + "] ====> {" + this.clip.get(i).toString() + '}';
        }
        return null;
    }

    /**
     * Create a String for the representation of the given clipboard in a message key
     * @param nClipboard Key for the needed value
     * @return String
     */
    public String saveClipboard(int nClipboard) {
        if (keys.contains(nClipboard)) {
            int i = 0;
            while (i < keys.size()) {
                if (keys.get(i).equals(nClipboard)) {
                    break;
                }
                i++;
            }
            return  nClipboard + " " + this.clip.get(i).toString();
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < keys.size(); i++) {
            sb.append(keys.get(i)).append(" <-> ").append(clip.get(i));
        }
        return sb.toString();
    }
}
