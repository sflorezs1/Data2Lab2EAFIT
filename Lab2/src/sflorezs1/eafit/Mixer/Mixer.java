package sflorezs1.eafit.Mixer;

import sflorezs1.eafit.IO.Clipboard;
import sflorezs1.eafit.IO.InputOutput;
import sflorezs1.eafit.Lists.LinkedList;
import sflorezs1.eafit.Lists.Stack;
import sflorezs1.eafit.Message;

import java.util.InputMismatchException;

public class Mixer {
    private Message message;

    public Mixer(String text) {
        message = new Message(InputOutput.readMessage(text));
    }

    public Mixer(Message message) {
        this.message = message;
    }

    /**
     * Mix a Message given an operation
     * @param operation Operation to perform
     */
    public void mix(String operation) {
        System.out.println("Performing [" + operation + "] ...");
        Stack<String> operations = this.message.getOperations();
        String[] parts = operation.split(" ");
        switch (parts[0]) {
            case "b":
                try {
                    LinkedList<Character> str = InputOutput.readMessage(parts[1]);
                    int position = Integer.parseInt(parts[2]);
                    boolean insert = insertAt(position, str);
                    if (!insert) {
                        System.out.println("\nThe operation [" + operation + "] could not be performed, try again.\n");
                    } else {
                        System.out.println("\nOperation [" + operation + "] performed successfully.\n");
                        operations.push(operation);
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.err.println("The given operation [" + operation + "] does not match the structure [b s #], please visit the help page [h]");
                    break;
                }
            case "r":
                try {
                    int startr = Integer.parseInt(parts[1]);
                    int endr = Integer.parseInt(parts[2]);
                    LinkedList<Character> remove = deleteRange(startr, endr);
                    if (remove == null) {
                        System.out.println("\nThe operation [" + operation + "] could not be performed, try again.\n");
                    } else {
                        System.out.println("\nOperation [" + operation + "] performed successfully.\n");
                        operations.push(operation + ";%;" + remove.representString());
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.err.println("The given operation [" + operation + "] does not match the structure [r # #], please visit the help page [h]");
                    break;
                }
            case "h":
                helpPage();
                break;
            case "d":
                try {
                    char deletable = parts[1].charAt(0);
                    LinkedList delete = deleteChar(deletable);
                    if (delete.size() == 0) {
                        System.out.println("\nThe operation [" + operation + "]could not be performed there is no ["+ deletable +"], try again.\n");
                    } else {
                        System.out.println("\nOperation [" + operation + "] performed successfully.\n");
                        operations.push(operation + ";%;" + delete.toString());
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.err.println("The given operation [" + operation + "] does not match the structure [d #], please visit the help page [h]");
                    break;
                }
            case "f":
                try {
                    char original = parts[1].charAt(0);
                    char replacing = parts[2].charAt(0);
                    LinkedList<Integer> replace = replace(original, replacing);
                    if (replace == null) {
                        System.out.println("\nThe operation [" + operation + "] could not be performed. There is no ["
                                + original +"] in the message, try again.\n");
                    } else {
                        System.out.println("\nOperation [" + operation + "] performed successfully.\n");
                        operations.push(operation + ";%;" + replace.toString());
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.err.println("The given operation [" + operation + "] does not match the structure [f * *], please visit the help page [h]");
                    break;
                }
            case "m":
                try {
                    boolean mirror = mirror();
                    if (!mirror) {
                        System.out.println("\nThe operation [" + operation + "] could not be performed, try again.\n");
                    } else {
                        System.out.println("\nOperation [" + operation + "] performed successfully.\n");
                        operations.push(operation);
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.err.println("The given operation [" + operation + "] does not match the structure [m], please visit the help page [h]");
                    break;
                }
            case "cc":
                try {
                    int rotations = Integer.parseInt(parts[1]);
                    boolean caesarCipher = caesarCipher(rotations);
                    if (!caesarCipher) {
                        System.out.println("\nThe operation [" + operation + "] could not be performed, try again.\n");
                    } else {
                        System.out.println("\nOperation [" + operation + "] performed successfully.\n");
                        operations.push(operation);
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.err.println("The given operation [" + operation + "] does not match the structure [cc #], please visit the help page [h]");
                    break;
                }
            case "s":
                try {
                    int times = Integer.parseInt(parts[1]);
                    boolean shift = shift(times);
                    if (!shift) {
                        System.out.println("\nThe operation [" + operation + "] could not be performed, try again.\n");
                    } else {
                        System.out.println("\nOperation [" + operation + "] performed successfully.\n");
                        operations.push(operation);
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.err.println("The given operation [" + operation + "] does not match the structure [s #], please visit the help page [h]");
                    break;
                }
            case "p":
                try {
                    int from = Integer.parseInt(parts[1]);
                    int pclipboard = Integer.parseInt(parts[2]);
                    boolean paste = paste(pclipboard, from);
                    if (!paste) {
                        System.out.println("\nThe operation [" + operation + "] could not be performed, try again.\n");
                    } else {
                        System.out.println("\nOperation [" + operation + "] performed successfully.\n");
                        operations.push(operation + ";%;" + this.message.getClipboard().saveClipboard(pclipboard));
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.err.println("The given operation [" + operation + "] does not match the structure [p # &], please visit the help page [h]");
                    break;
                }
            case "c":
                try {
                    int cstart = Integer.parseInt(parts[1]);
                    int cend = Integer.parseInt(parts[2]);
                    int cclipboard = Integer.parseInt(parts[3]);
                    boolean copy = copy(cclipboard, cstart, cend);
                    if (!copy) {
                        System.out.println("\nThe operation [" + operation + "] could not be performed, try again.\n");
                    } else {
                        System.out.println("\nOperation [" + operation + "] performed successfully.\n");
                        System.out.println(this.message.getClipboard().showClipboard(cclipboard));
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.err.println("The given operation [" + operation + "] does not match the structure [c # % &], please visit the help page [h]");
                    break;
                }
            case "x":
                try {
                    int xstart = Integer.parseInt(parts[1]);
                    int xend = Integer.parseInt(parts[2]);
                    int xclipboard = Integer.parseInt(parts[3]);
                    boolean cut = cut(xclipboard, xstart, xend);
                    if (!cut) {
                        System.out.println("\nThe operation [" + operation + "] could not be performed, try again.\n");
                    } else {
                        System.out.println("\nOperation [" + operation + "] performed successfully.\n");
                        System.out.println(this.message.getClipboard().showClipboard(xclipboard));
                        operations.push(operation);
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.err.println("The given operation [" + operation + "] does not match the structure [x # % &], please visit the help page [h]");
                    break;
                }
            case "z":
                System.out.println("=-==-===-====-=====Randomizing======-====-===-==-=");
                randomize((int) (Math.random() * 7));
                System.out.println("=-==-===-====-=====Randomized======-====-===-==-=");
                break;
            default:
                System.out.println("The specified operation [" + operation +
                        "] was nos recognized as a valid operation, please visit the help page [h]");
        }
        System.out.println(this.getMessage().getList());
    }

    public void helpPage() {
        /* TODO() */
    }

    /**
     * Deletes all instances of a given character from the message
     * @param c Character to be deleted
     * @return True if the operation was successfully performed else False
     */
    private LinkedList<Integer> deleteChar(Character c) {
        LinkedList<Integer> deleted = new LinkedList<>();
        for (int i = this.message.getList().size() - 1; i >= 0; i--) {
            if (c.equals(this.message.getList().get(i))) {
                this.message.getList().remove(i);
                deleted.prepend(i);
            }
        }
        return deleted;
    }

    /**
     * Inserts a given list of characters into a Message
     * @param position Initial position
     * @param insert List of characters to insert
     * @return True if the operation was successfully performed else False
     */
    protected boolean insertAt(int position, LinkedList<Character> insert) {
        if (position >= this.message.getList().size() || position < 0) return false;
        for (int i = insert.size() - 1; i >= 0; i--) {
            this.message.getList().insert(position, insert.get(i));
        }
        return true;
    }

    /**
     * Deletes all the characters from a message within a given a range
     * @param start Index of the first character
     * @param end Index of the last character
     * @return True if the operation was successfully performed else False
     */
    protected LinkedList<Character> deleteRange(int start, int end) {
        if (start >= this.message.getList().size() || start < 0 || start > end || end >= this.message.getList().size()) return null;
        LinkedList<Character> removed = new LinkedList<>();
        for (int i = end; i >= start; i--) {
            removed.prepend(this.message.getList().get(i));
            this.message.getList().remove(i);
        }
        return removed;
    }

    /**
     * Replace all instances of a character with another one in the message
     * @param o Character to be replaced
     * @param r New character
     * @return True if the operation was successfully performed else False
     */
    private LinkedList<Integer> replace(Character o, Character r) {
        LinkedList<Integer> positions = this.message.getList().contains(o)? new LinkedList<>() : null;
        for(int i = 0; i < this.message.getList().size(); i++) {
            if (this.message.getList().get(i).equals(o)) {
                this.message.getList().replace(i, r);
                positions.append(i);
            }
        }
        return positions;
    }

    protected boolean mirror() {
        LinkedList<Character> mirrored = new LinkedList<>();
        for (int i = 0; i < this.message.getList().size(); i++) mirrored.prepend(this.message.getList().get(i));
        this.message.setList(mirrored);
        return true;
    }

    private boolean copy(int nClipboard, int start, int end) {
        if (start >= this.message.getList().size() || start < 0 || start > end || end >= this.message.getList().size()) return false;
        LinkedList<Character> part = new LinkedList<>();
        for (int i = start; i <= end; i++) {
            part.append(this.message.getList().get(i));
        }
        this.message.getClipboard().copy(nClipboard, part);
        return true;
    }

    private boolean cut(int nClipboard, int start, int end) {
        return copy(nClipboard, start, end) && (deleteRange(start, end) != null);
    }

    protected boolean paste(int nClipboard, int position) {
        if (position >= this.message.getList().size() || position < 0) return false;
        return insertAt(position, this.message.getClipboard().paste(nClipboard));
    }

    protected boolean caesarCipher(int times) {
        if (times < 0 || times > 25) return false;
        for (int i = 0; i < this.message.getList().size(); i++) {
            Character c = this.message.getList().get(i);
            if (Character.isAlphabetic(c)) {
                if (c > 'Z') {
                    if ((c + times) > 'z') {
                        c = (char) (((c + times) - 122) + 96);
                    } else {
                        c = (char) (c + times);
                    }
                } else {
                    if ((c + times) > 'Z') {
                        c = (char) (((c + times) - 90) + 64);
                    } else {
                        c = (char) (c + times);
                    }
                }
            }
            this.message.getList().replace(i, c);
        }
        return true;
    }

    protected boolean shift(int rotations) {
        if (rotations > this.message.getList().size() || rotations < 0) return false;
        LinkedList<Character> end = new LinkedList<>();
        for (int i = rotations; i < this.message.getList().size(); i++) end.append(this.message.getList().get(i));
        for (int i = 0; i < rotations; i++) end.append(this.message.getList().get(i));
        this.message.setList(end);
        return true;
    }

    private boolean randomize(int times) {
        if (times == -1) return true;
        String[] cases = {"b", "r", "d", "f"};
        String str = cases[(int)(Math.random() * cases.length)];
        switch (str) {
            case "b" -> {
                String stringB = randomString();
                int positionB = (int) (Math.random() * this.message.getList().size());
                str += " " + stringB + " " + positionB;
            }
            case "r" -> {
                int startR = (int) (Math.random() * this.message.getList().size());
                int endR = (int) (Math.random() * this.message.getList().size());
                str += " " + Math.min(startR, endR) + " " + Math.max(startR, endR);
            }
            case "d" -> {
                char c = this.message.getList().get((int) (Math.random() * this.message.getList().size()));
                str += " " + (c == ' ' ? 'a' : c);
            }
            case "f" -> {
                char part1 = this.message.getList().get((int) (Math.random() * this.message.getList().size()));
                char part2 = this.message.getList().get((int) (Math.random() * this.message.getList().size()));
                str += " " + (part1 == ' ' ? 'a' : part1) + " " + (part2 == ' ' ? 'a' : part2);
            }
        }
        mix(str);
        return randomize(times - 1);
    }

    private String randomString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ((int) (Math.random() * 9)) + 1; i++) {
            char c = (char) ((int) (Math.random() * (126 - 33)) + 33);
            sb.append(c);
        }
        return sb.toString();
    }

    public Message getMessage() {
        return message;
    }
}
