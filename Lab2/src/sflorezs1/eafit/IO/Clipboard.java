package sflorezs1.eafit.IO;

import sflorezs1.eafit.Lists.LinkedList;

import java.util.HashMap;

public class Clipboard {
    private HashMap<Integer, LinkedList<Character>> holder;

    public Clipboard() {
        this.holder = new HashMap<>();
    }

    public void copy(int clipboardN, LinkedList<Character> part) {
        this.holder.put(clipboardN, part);
    }

    public LinkedList<Character> paste(int clipboardN) {
        return this.holder.get(clipboardN);
    }

    public String showClipboard(int nClipboard) {
        return  "Clipboard[" + nClipboard + "] ====> {" + this.holder.get(nClipboard).toString() + '}';
    }

    public String saveClipboard(int nClipboard) {
        return nClipboard + " " + this.holder.get(nClipboard).toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.holder.entrySet().forEach(entry->{
            sb.append(entry.getKey()).append(" <-> ").append(entry.getValue()).append('\n');
        });
        return sb.toString();
    }
}
