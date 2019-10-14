package sflorezs1.eafit.unmixer;

import sflorezs1.eafit.io.InputOutput;
import sflorezs1.eafit.lists.LinkedList;
import sflorezs1.eafit.lists.Stack;
import sflorezs1.eafit.menu.Menu;
import sflorezs1.eafit.Message;
import sflorezs1.eafit.mixer.Mixer;

import java.util.InputMismatchException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * UnMixer program, to unmix a given message mixed up by the Mixer program
 */
public class UnMixer extends Mixer {

    /**
     * Constructor for the UnMixer class
     * @param message Message to be unmixed
     */
    public UnMixer(Message message) {
        super(message);
    }

    /**
     * Display a message when an operation could not be undone
     * @param operation Operation that could not be undone
     */
    private void cantBeUndone(String operation) {
        System.out.println("Operation [" + operation + "] could not be undone, please check the structure of the key");
    }

    /**
     * Display a message when an operation could be undone
     * @param operation Operation that could be undone
     */
    private void undone(String operation) {
        System.out.println("Operation [" + (operation.contains(";%;")? operation.split(";%;")[0] : operation) + "] undone successfully");
    }

    /**
     * Unmix a given message
     */
    public void unMix() {
        Stack<String> operations = super.getMessage().getOperations();
        while (operations.peek() != null) {
            String operation = operations.pop();
            System.out.println("Undoing [" + (operation.contains(";%;")? operation.split(";%;")[0] : operation) + "]");
            String[] parts = operation.split(" ");
            switch (parts[0]) {
                case "b":
                    try {
                        String sb = IntStream.range(1, parts.length - 1).mapToObj(i -> parts[i] + (i >= parts.length - 1 ? "" : " ")).collect(Collectors.joining());
                        int startB = Integer.parseInt(parts[parts.length - 1]);
                        int endB = sb.length() + startB - 2;
                        LinkedList<Character> unInsertAt = deleteRange(startB, endB);
                        if (unInsertAt == null) {
                            cantBeUndone(operation);
                        } else {
                            undone(operation);
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.err.println("The given key [" + operation + "] does not match the structure [b s #]," +
                                " the key may be corrupted");
                        break;
                    }
                case "r":
                    try {
                        int position = Integer.parseInt(operation.split(";%;")[0].split(" ")[1]);
                        LinkedList<Character> insertion = InputOutput.readMessage(operation.split(";%;")[1]);
                        boolean unDeleteRange;
                        if (getMessage().getList().size() <= position) {
                            getMessage().getList().append(' ');
                            unDeleteRange = insertAt(position, insertion);
                            getMessage().getList().remove(getMessage().getList().size() - 1);
                        } else {
                            unDeleteRange = insertAt(position, insertion);
                        }
                        if (!unDeleteRange) {
                            cantBeUndone(operation);
                        } else {
                            undone(operation);
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.err.println("The given key [" + operation + "] does not match the structure [r # # $]," +
                                " the key may be corrupted");
                        break;
                    }
                case "d":
                    try {
                        if (operation.split(";%;").length > 1) {
                            String[] partsd = operation.split(";%;");
                            LinkedList<Character> deleted = InputOutput.readMessage(operation.charAt(operation.indexOf(";%;") - 1) + "");
                            LinkedList<Integer> positions = InputOutput.parseIntList(partsd[1]);
                            for (int i = 0; i < positions.size(); i++) {
                                if (positions.get(i) >= getMessage().getList().size()) {
                                    getMessage().getList().append(deleted.get(0));
                                } else {
                                    insertAt(positions.get(i), deleted);
                                }
                            }
                        }
                        undone(operation);
                        break;
                    } catch (InputMismatchException e) {
                        System.err.println("The given key [" + operation + "] does not match the structure [d *;%;#l]," +
                                " the key may be corrupted");
                        break;
                    }
                case "f":
                    try {
                        if (operation.split(";%;").length > 1) {
                            if (operation.substring(2).split(";%;")[0].length() > 3) {
                                System.err.println("The given key [" + operation + "] does not match the structure [f * *;%;#l]," +
                                        " the key may be corrupted");
                                break;
                            }
                            char original = operation.substring(2).charAt(0);
                            LinkedList<Integer> positions = InputOutput.parseIntList(operation.split(";%;")[1]);
                            for (int i = 0; i < positions.size(); i++) {
                                getMessage().getList().replace(positions.get(i), original);
                            }
                            undone(operation);
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.err.println("The given key [" + operation + "] does not match the structure [f * *;%;#l]," +
                                " the key may be corrupted");
                        break;
                    }
                case "p":
                    try {
                        LinkedList<Character> clipboard = InputOutput.parseCharList(operation.split(";%;")[1]);
                        int pclipboard = Integer.parseInt(clipboard.get(0) + "");
                        clipboard.remove(0);
                        getMessage().getClipboard().copy(pclipboard, clipboard);
                        LinkedList<Character> unPaste = deleteRange(Integer.parseInt(parts[1]), clipboard.size() + Integer.parseInt(parts[1]));
                        if (unPaste == null) {
                            cantBeUndone(operation);
                        } else {
                            undone(operation);
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.err.println("The given key [" + operation + "] does not match the structure [p # &;%;$]," +
                                " the key may be corrupted");
                        break;
                    }
                case "x":
                    try {
                        int xclipboard = Integer.parseInt(parts[3]);
                        int xposition = Integer.parseInt(parts[1]);
                        boolean paste = paste(xclipboard, xposition);
                        if (!paste) {
                            cantBeUndone(operation);
                        } else {
                            undone(operation);
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.err.println("The given key [" + operation + "] does not match the structure [p # % &]," +
                                " the key may be corrupted");
                        break;
                    }
                case "m":
                    try {
                        boolean mirror = mirror();
                        if (!mirror) {
                            cantBeUndone(operation);
                        } else {
                            undone(operation);
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.err.println("The given key [" + operation + "] does not match the structure [m]," +
                                " the key may be corrupted");
                        break;
                    }
                case "cc":
                    try {
                        int rotations = Integer.parseInt(parts[1]);
                        boolean caesarCipher = caesarCipher(26 - rotations);
                        if (!caesarCipher) {
                            cantBeUndone(operation);
                        } else {
                            undone(operation);
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.err.println("The given key [" + operation + "] does not match the structure [cc #]," +
                                " the key may be corrupted");
                        break;
                    }
                case "s":
                    try {
                        int times = Integer.parseInt(parts[1]);
                        boolean shift = shift(getMessage().getList().size() - times);
                        if (!shift) {
                            cantBeUndone(operation);
                        } else {
                            undone(operation);
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.err.println("The given key [" + operation + "] does not match the structure [s #]," +
                                " the key may be corrupted");
                        break;
                    }
                default:
                    System.out.println("Given key [" + operation + "] is not a valid key, please check your message");
            }
            System.out.println(getMessage().getList());
        }
    }

    /**
     * Main method for the UnMix program
     * @param args Arguments given by the user at call
     */
    public static void main(String[] args) {
        String message = IntStream.range(0, args.length - 1).mapToObj(i -> args[i] + (i >= args.length - 1? "" : " ")).collect(Collectors.joining());
        message = message.substring(0, message.length() - 1);
        Menu.mainUnmixMenu(message, args[args.length - 1]);
    }
}
