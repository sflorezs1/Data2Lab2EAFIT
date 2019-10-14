package sflorezs1.eafit.menu;

import sflorezs1.eafit.io.InputOutput;
import sflorezs1.eafit.Message;
import sflorezs1.eafit.mixer.Mixer;
import sflorezs1.eafit.unmixer.UnMixer;

import java.io.IOException;

public class Menu {
    /* Menu for mixing */

    /**
     * Mixer in which the operations will be done/undone
     */
    private static Mixer mixer;

    /**
     * Main menu for the Mixer program
     * @param message Initial message
     */
    public static void mainMixMenu(String message) {
        System.out.println("********************************************************************");
        System.out.println("If your message contained [\"] or ['], it may not be displayed right");
        System.out.println("      Due to the way arguments and parameters work in the CLI      ");
        System.out.println("********************************************************************\n\n\n");
        System.out.println("Agent, welcome to this ciphering program");
        System.out.println("Here, you can mix a message, the message that you inserted was: " + message);
        String s = InputOutput.readLine("Is that correct? Y/N").toLowerCase();
        switch (s) {
            case "y":
                initMixSystem(message);
                break;
            case "n":
                System.out.println("Then, the program will close itself, try again with another message.");
                break;
            default:
                System.out.println("Invalid option, try again.");
                mainMixMenu(message);
                break;
        }
    }

    /**
     * Init menu for the Mix program
     * @param message Initial message
     */
    private static void initMixSystem(String message) {
        System.out.println("Ok then, you can mix your message with the following commands: ");
        mixer = new Mixer(message);
        mixer.helpPage();
        mixer.displayCurrentMessage();
        decisionMixMenu();
    }

    /**
     * Decision menu for the Mix program, where the user gives the commands to edit the message
     */
    private static void decisionMixMenu() {
        String decision = InputOutput.readLine("\nEnter a command");
        if (decision.split(" ")[0].equals("Q")) {
            mixer.mix(decision);
        } else {
            mixer.mix(decision);
            decisionMixMenu();
        }
    }

    /* End of the mixing menu - Start of the unmixing menu */

    /**
     * Main menu for the UnMix menu
     * @param messageStr Mixed up message
     * @param pathToFile Path to the file that contains the key of the mixed up message
     */
    public static void mainUnmixMenu(String messageStr, String pathToFile) {
        try {
            System.out.println("********************************************************************");
            System.out.println("If your message contained [\"] or ['], it may not be displayed right");
            System.out.println("      Due to the way arguments and parameters work in the CLI      ");
            System.out.println("********************************************************************\n\n\n");
            Message message = InputOutput.readFile(InputOutput.readMessage(messageStr), pathToFile);
            System.out.println("Do you really want to unmix the message [" + message.getList().representString() + "] ?");
            UnMixer unMixer = new UnMixer(message);
            unMixer.unMix();
            System.out.println("Your original message was: " + unMixer.getMessage().getList().representString());
        } catch (IOException e) {
            System.err.println("Something went wrong while opening the file, please try again");
        }
    }
}
