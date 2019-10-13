package sflorezs1.eafit.IO;

import sflorezs1.eafit.Lists.*;
import sflorezs1.eafit.Message;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintWriter;

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
    public static Stack<String> readOperations(String lines) {
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
     * @return A Message to be decrypted
     * @throws IOException In case there is an error related to the file
     */
    public static Message readFile(String pathToFile) throws IOException {
        File file = new File(pathToFile);
        Scanner sc = new Scanner(file);
        sc.nextLine(); // Skip header
        String message = sc.nextLine();
        LinkedList<Character> listedMessage = readMessage(message);
        sc.nextLine(); // Skip empty line
        sc.nextLine(); // Skip key header
        StringBuilder sb = new StringBuilder();
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine()).append(";%%;");
        }
        Stack<String> operations = readOperations(sb.toString());
        return new Message(listedMessage, operations);
    }

    public static void writeMessage(String pathToSave, Message message) throws IOException {
        File file = new File(pathToSave);
        if (!file.exists()) {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                String sb = "Message mixed: \n" + String.valueOf(message.getList().representString()) + '\n' +
                        "Key: \n" + message.getOperations();
                printWriter.print(sb);
                printWriter.close();
                fileWriter.close();
                System.out.println("File [" + pathToSave + "] created successfully");
            } else {
                System.err.println("Something went wrong while creating the file, try again");
            }
        } else {
            if (overwrite()) {
                if (file.delete()) {
                    if (file.createNewFile()) {
                        FileWriter fileWriter = new FileWriter(file);
                        PrintWriter printWriter = new PrintWriter(fileWriter);
                        String sb = "Message mixed: \n" + String.valueOf(message.getList().representString()) + "\n\n" +
                                "Key: \n" + message.getOperations() + '\n';
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

    private static boolean overwrite() {
        System.out.print("\nThe specified file already exists, do you want to overwrite it? ");
        String option = readLine("Y/N");
        switch (option.toLowerCase()) {
            case "y":
                return true;
            case "n":
                return false;
            default:
                System.out.println("\n[" + option + "] is not a valid option, try again\n");
                return overwrite();
        }
    }

    public static int readInt(String ask) {
        System.out.print(ask + ": ");
        Scanner sc = new Scanner(System.in);
        int out = sc.nextInt();
        sc.close();
        return out;
    }

    public static String readLine(String ask) {
        System.out.print(ask + ": ");
        Scanner sc = new Scanner(System.in);
        String out = sc.nextLine();
        sc.close();
        return out;
    }

    public static LinkedList<Integer> parseIntList(String intList) {
        LinkedList<Integer> list = new LinkedList<>();
        String[] parts = intList.split(" ");
        for(String str: parts) {
            if (!str.equals("")) list.append(Integer.parseInt(str));
        }
        return list;
    }

    public static LinkedList<Character> parseCharList(String intList) {
        LinkedList<Character> list = new LinkedList<>();
        String[] parts = intList.split(" ");
        for(String str: parts) {
            list.append(str.charAt(0));
        }
        return list;
    }
}
