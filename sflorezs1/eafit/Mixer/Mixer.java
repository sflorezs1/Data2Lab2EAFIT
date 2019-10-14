package sflorezs1.eafit.mixer;

import sflorezs1.eafit.io.InputOutput;
import sflorezs1.eafit.lists.LinkedList;
import sflorezs1.eafit.lists.Stack;
import sflorezs1.eafit.menu.Menu;
import sflorezs1.eafit.Message;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Mixer {
    /**
     * Message to be mixed up
     */
    private final Message message;

    /**
     * Constructor for the case only the message text is available
     * @param text Initial text message
     */
    public Mixer(String text) {
        message = new Message(InputOutput.readMessage(text));
    }

    /**
     * Constructor for the case the entire message is available
     * @param message Message to be mixed up
     */
    protected Mixer(Message message) {
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
            case "Q":
                try {
                    String output = IntStream.range(1, parts.length).mapToObj(i -> parts[i] + (i >= parts.length - 1? "" : " ")).collect(Collectors.joining());
                    InputOutput.writeKey(output, this.message);
                    System.out.println("Your final mixed up message: " + message.getList().representString());
                    System.out.println("The key to your message was written to the file [" + output + "]");
                    return;
                } catch (Exception e) {
                    System.err.println("Something went wrong while saving, the file, please try again");
                    return;
                }
            case "b":
                try {
                    String sb = IntStream.range(1, parts.length - 1).mapToObj(i -> parts[i] + (i >= parts.length - 2? "" : " ")).collect(Collectors.joining());
                    LinkedList<Character> str = InputOutput.readMessage(sb);
                    int position = Integer.parseInt(parts[parts.length - 1]);
                    boolean insert = insertAt(position, str);
                    if (!insert) {
                        System.out.println("\nThe operation [" + operation + "] could not be performed, try again.\n");
                    } else {
                        System.out.println("\nOperation [" + operation + "] performed successfully.\n");
                        operations.push(operation);
                    }
                    break;
                } catch (Exception e) {
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
                } catch (Exception e) {
                    System.err.println("The given operation [" + operation + "] does not match the structure [r # #], please visit the help page [h]");
                    break;
                }
            case "h":
                helpPage();
                break;
            case "d":
                try {
                    String sb = operation.substring(1);
                    char deletable = sb.charAt(1);
                    LinkedList<Integer> delete = deleteChar(deletable);
                    if (delete.size() == 0) {
                        System.out.println("\nThe operation [" + operation + "]could not be performed there is no ["+ deletable +"], try again.\n");
                    } else {
                        System.out.println("\nOperation [" + operation + "] performed successfully.\n");
                        operations.push(operation + ";%;" + delete.simpleString());
                    }
                    break;
                } catch (Exception e) {
                    System.err.println("The given operation [" + operation + "] does not match the structure [d #], please visit the help page [h]");
                    break;
                }
            case "f":
                try {
                    String fstring = operation.substring(2);
                    if (fstring.length() > 3) {
                        System.err.println("The given operation [" + operation + "] does not match the structure [f * *], please visit the help page [h]");
                        break;
                    }
                    char original = fstring.charAt(0);
                    char replacing = fstring.charAt(2);
                    LinkedList<Integer> replace = replace(original, replacing);
                    if (replace == null) {
                        System.out.println("\nThe operation [" + operation + "] could not be performed. There is no ["
                                + original +"] in the message, try again.\n");
                    } else {
                        System.out.println("\nOperation [" + operation + "] performed successfully.\n");
                        operations.push(operation + ";%;" + replace.simpleString());
                    }
                    break;
                } catch (Exception e) {
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
                } catch (Exception e) {
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
                } catch (Exception e) {
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
                } catch (Exception e) {
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
                } catch (NumberFormatException e) {
                    System.err.println("The given operation [" + operation + "] does not match the structure [p # &], please visit the help page [h]");
                    break;
                } catch (NullPointerException e) {
                    System.err.println("The given clipboard ["+ Integer.parseInt(parts[2]) +"] does not exist");
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
                } catch (NumberFormatException e) {
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
                } catch (Exception e) {
                    System.err.println("The given operation [" + operation + "] does not match the structure [x # % &], please visit the help page [h]");
                    break;
                }
            case "z":
                System.out.println("=-==-===-====-=====Randomizing======-====-===-==-=");
                randomize((int) (Math.random() * 14) + 4);
                System.out.println("=-==-===-====-=====Randomized======-====-===-==-=");
                break;
            default:
                System.out.println("The specified operation [" + operation +
                        "] was nos recognized as a valid operation, please visit the help page [h]");
        }
        displayCurrentMessage();
    }

    /**
     * Display current message with enumeration
     */
    public void displayCurrentMessage() {
        LinkedList<Integer> enumeration = new LinkedList<>();
        for (int i = 0; i < this.message.getList().size(); i++) enumeration.append(i);
        System.out.println("\nYour current message is: \n");
        System.out.println(this.getMessage().getList());
        System.out.println(enumeration);
        System.out.println();
    }

    /**
     * Display help page of the Mixer program
     */
    public void helpPage() {
        System.out.println("\n=-==-Help page-==-=\n");
        System.out.println("These are the commands available for this program:\n");
        System.out.println("\tQ filename\t -Quit editing, save the key in a file [filename] and show final message.");
        System.out.println("\tb s #\t\t -Insert a String [s], at the position [#] in the message.");
        System.out.println("\tr # #\t\t -Remove all characters in the interval [#, 3] fom the message.");
        System.out.println("\th\t\t\t -Display this help page.");
        System.out.println("\td #\t\t\t -Delete all the characters [#] from the message.");
        System.out.println("\tf * *\t\t -Replace all the characters [*] with [*] in the message. {Note: both [*] are single characters.}");
        System.out.println("\tz\t\t\t -Perform random series of [b], [r], [d] or [f] operations.");
        System.out.println("\ts #\t\t\t -Shift all the characters a number [#] of times. {Note: 0 > # > length of the message.}");
        System.out.println("\tm\t\t\t -Mirror the message.");
        System.out.println("\tcc #\t\t -Change all the letters with its [#]th successor alphabetically (Caesar cipher). {Note: 0 > # > 26}");
        System.out.println("\tp # &\t\t -Paste from clipboard [&], start at [#]. {Note: [&] is a number}  \n" +
                "\tc # % &\t\t -Copy to clipboard [&], starting at [#] to [%] (inclusive). {Note: [&] is a number}\n" +
                "\tx # % &\t\t -Cut to clipboard [&], starting at [#] to [%] (inclusive). {Note: [&] is a number}\n");
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
        if (this.message.getList().size() == 0 && position == 0) {
            this.message.getList().append(' ');
            if (position >= this.message.getList().size()) return false;
            for (int i = insert.size() - 1; i >= 0; i--) {
                this.message.getList().insert(position, insert.get(i));
            }
            this.message.getList().remove(0);
            return true;
        }
        if (position > this.message.getList().size() || position < 0) return false;
        if (position == this.message.getList().size()) {
            for (int i = 0; i < insert.size(); i++) {
                this.message.getList().append(insert.get(i));
            }
        } else {
            for (int i = insert.size() - 1; i >= 0; i--) {
                this.message.getList().insert(position, insert.get(i));
            }
        }
        return true;
    }

    /**
     * Deletes all the characters from a message within a given a range
     * @param start Index of the first character
     * @param end Index of the last character
     * @return List of deleted characters
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
     * @return Positions where the characters where replaced
     */
    private LinkedList<Integer> replace(Character o, Character r) {
        LinkedList<Integer> positions = this.message.getList().contains(o)? new LinkedList<>() : null;
        for(int i = 0; i < this.message.getList().size(); i++) {
            if (this.message.getList().get(i).equals(o)) {
                this.message.getList().replace(i, r);
                Objects.requireNonNull(positions).append(i);
            }
        }
        return positions;
    }

    /**
     * Reverse the message
     * @return True if the operation was successfully performed else False
     */
    protected boolean mirror() {
        LinkedList<Character> mirrored = new LinkedList<>();
        for (int i = 0; i < this.message.getList().size(); i++) mirrored.prepend(this.message.getList().get(i));
        this.message.setList(mirrored);
        return true;
    }

    /**
     * Copy to clipboard
     * @param nClipboard Number of the clipboard to be copied at
     * @param start Start [Inclusive] of the interval to be copied
     * @param end End [Inclusive] of the interval to be copied
     * @return True if the operation was successfully performed else False
     */
    private boolean copy(int nClipboard, int start, int end) {
        if (start >= this.message.getList().size() || start < 0 || start > end || end >= this.message.getList().size()) return false;
        LinkedList<Character> part = new LinkedList<>();
        for (int i = start; i <= end; i++) {
            part.append(this.message.getList().get(i));
        }
        this.message.getClipboard().copy(nClipboard, part);
        return true;
    }

    /**
     * Cut to clipboard
     * @param nClipboard Number of the clipboard to be cut at
     * @param start Start [Inclusive] of the interval to be cut
     * @param end End [Inclusive] of the interval to be cut
     * @return True if the operation was successfully performed else False
     */
    private boolean cut(int nClipboard, int start, int end) {
        return copy(nClipboard, start, end) && (deleteRange(start, end) != null);
    }

    /**
     * Paste from clipboard
     * @param nClipboard Number of the clipboard to be pasted from
     * @param position Position to paste at
     * @return True if the operation was successfully performed else False
     */
    protected boolean paste(int nClipboard, int position) {
        if (position >= this.message.getList().size() || position < 0) return false;
        return insertAt(position, this.message.getClipboard().paste(nClipboard));
    }

    /**
     * Change all the letters within the message with its successor alphabetically
     * @param times Number of times the operation will be performed
     * @return True if the operation was successfully performed else False
     */
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

    /**
     * Shift all characters n times to the right
     * @param rotations Number of times the characters will be shifted
     * @return True if the operation was successfully performed else False
     */
    protected boolean shift(int rotations) {
        if (rotations > this.message.getList().size() || rotations < 0) return false;
        LinkedList<Character> end = new LinkedList<>();
        for (int i = rotations; i < this.message.getList().size(); i++) end.append(this.message.getList().get(i));
        for (int i = 0; i < rotations; i++) end.append(this.message.getList().get(i));
        this.message.setList(end);
        return true;
    }

    /**
     * Performs a series of operations InsertAt, DeleteRange, DeleteCharacter and Replace
     * @param times Number of times this will be executed
     * @return True if the operation was successfully performed else False
     */
    private boolean randomize(int times) {
        if (times == -1) return true;
        String[] cases = {"b", "r", "d", "f"};
        String str = this.message.getList().size() == 0? "b" : cases[(int)(Math.random() * cases.length)];
        switch (str) {
            case "b":
                String stringB = randomString();
                int positionB = (int) (Math.random() * this.message.getList().size());
                str += " " + stringB + " " + positionB;
                break;
            case "r":
                int startR = (int) (Math.random() * this.message.getList().size());
                int endR = (int) (Math.random() * this.message.getList().size());
                str += " " + Math.min(startR, endR) + " " + Math.max(startR, endR);
                break;
            case "d":
                char c = this.message.getList().get((int) (Math.random() * this.message.getList().size()));
                str += " " + c;
                break;
            case "f":
                char part1 = this.message.getList().get((int) (Math.random() * this.message.getList().size()));
                char part2 = this.message.getList().get((int) (Math.random() * this.message.getList().size()));
                str += " " + part1 + " " + part2;
                break;
        }
        mix(str);
        return randomize(times - 1);
    }

    /**
     * Helper method for randomize, creates a random String with minimum 1 character and maximum 10
     * @return Random String
     */
    private String randomString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ((int) (Math.random() * 9)) + 1; i++) {
            char c = (char) ((int) (Math.random() * (126 - 33)) + 33);
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Getter for the Message
     * @return Message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Main method for the Mixer program
     * @param args Arguments given by the user at call
     */
    public static void main(String[] args) {
        String message = IntStream.range(0, args.length).mapToObj(i -> args[i] + (i >= args.length - 1? "" : " ")).collect(Collectors.joining());
        Menu.mainMixMenu(message);
    }
}
