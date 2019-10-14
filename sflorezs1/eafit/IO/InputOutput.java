package sflorezs1.eafit.io;

import sflorezs1.eafit.lists.LinkedList;
import sflorezs1.eafit.lists.Stack;
import sflorezs1.eafit.Message;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class InputOutput {

    /**
     * Method to convert a String to a list of Characters
     * @param line String to be converted
     * @return Linked list of Characters
     */
    public static LinkedList<Character> readMessage(String line) {
        LinkedList<Character> message = new LinkedList<>();
        for (Character c : line.toCharArray()) {
            message.append(c);
        }
        return message;
    }

    /**
     * Converts a series of operations in text to Operation objects in a Stack
     * @param lines Lines of the key
     * @return A Stack of Operation objects
     */
    private static Stack<String> readOperations(String lines) {
        Stack<String> operations = new Stack<>();
        String[] parts = lines.split(";%%;");
        for (int i = parts.length - 1; i >= 0; i--) {
            operations.push(parts[i]);
        }
        return operations;
    }

    /**
     * Method to read a file with a encrypted message with a key
     * @param pathToFile Path to the file to be analyzed
     * @param message A Message to be decrypted
     * @return Message object with a list and a stack of operations
     * @throws IOException In case there is an error related to the file
     */
    public static Message readFile(LinkedList<Character> message, String pathToFile) throws IOException {
        File file = new File(pathToFile);
        Scanner sc = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine()).append(";%%;");
        }
        Stack<String> operations = readOperations(sb.toString());
        return new Message(message, operations);
    }

    /**
     * Create a key for a mixed message
     * @param pathToSave Path for the key file to be saved
     * @param message Mixed up message
     * @throws IOException If something goes wrong while creating the file
     */
    public static void writeKey(String pathToSave, Message message) throws IOException {
        File file = new File(pathToSave);
        if (!file.exists()) {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                String sb = message.getOperations().toString();
                printWriter.print(sb);
                printWriter.close();
                fileWriter.close();
                System.out.println("File [" + pathToSave + "] created successfully");
            } else {
                System.err.println("Something went wrong while creating the file, try again");
            }
        } else {
            if (overwrite(message)) {
                if (file.delete()) {
                    if (file.createNewFile()) {
                        FileWriter fileWriter = new FileWriter(file);
                        PrintWriter printWriter = new PrintWriter(fileWriter);
                        String sb = message.getOperations().toString();
                        printWriter.print(sb);
                        printWriter.close();
                        fileWriter.close();
                        System.out.println("File [" + pathToSave + "] created successfully");
                    } else {
                        System.err.println("Something went wrong while creating the file, try again");
                    }
                } else {
                    System.err.println("Something went wrong while creating the file, try again");
                }

            }
        }
    }

    /**
     * Method to confirm whether or not a user wants to overwrite an existing file
     * @param message Mixed up message
     * @return True if the user wants to overwrite a file, otherwise false
     */
    private static boolean overwrite(Message message) {
        System.out.print("\nThe specified file already exists, do you want to overwrite it? ");
        String option = readLine("Y/N");
        switch (option.toLowerCase()) {
            case "y":
                return true;
            case "n":
                try {
                    String pathToSave = readLine("Choose another name for the file: ");
                    writeKey(pathToSave, message);
                    return false;
                } catch (IOException e) {
                    System.err.println("Something went wrong while creating the file, try again");
                    return overwrite(message);
                }
            default:
                System.out.println("\n[" + option + "] is not a valid option, try again\n");
                return overwrite(message);
        }
    }

    /**
     * Ask a question and get an input
     * @param ask Question to ask
     * @return The user input for the question
     */
    public static String readLine(String ask) {
        System.out.print(ask + ": ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Parse a String representation of a LinkedList of Integer
     * @param intList String representation
     * @return LinkedList of Integer
     */
    public static LinkedList<Integer> parseIntList(String intList) {
        LinkedList<Integer> list = new LinkedList<>();
        String[] parts = intList.split(" ");
        for(String str: parts) {
            if (!str.equals("")) list.append(Integer.parseInt(str));
        }
        return list;
    }

    /**
     * Parse a String representation of a LinkedList of Character
     * @param intList String representation
     * @return LinkedList of Character
     */
    public static LinkedList<Character> parseCharList(String intList) {
        LinkedList<Character> list = new LinkedList<>();
        String[] parts = intList.split(" ");
        for(String str: parts) {
            list.append(str.charAt(0));
        }
        return list;
    }
}
