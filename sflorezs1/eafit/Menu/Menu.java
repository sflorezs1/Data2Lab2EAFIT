package sflorezs1.eafit.Menu;

import sflorezs1.eafit.IO.InputOutput;
import sflorezs1.eafit.Message;
import sflorezs1.eafit.Mixer.Mixer;
import sflorezs1.eafit.UnMixer.UnMixer;

import java.io.IOException;

public class Menu {
    /* Menu for mixing */

    private static Mixer mixer;

    public static void mainMixMenu(String message) {
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

    private static void initMixSystem(String message) {
        System.out.println("Ok then, you can mix your message with the following commands: ");
        mixer = new Mixer(message);
        mixer.helpPage();
        decisionMixMenu();
    }

    private static void decisionMixMenu() {
        String decision = InputOutput.readLine("\nEnter a command");
        if (decision.split(" ")[0].equals("Q")) {
            mixer.mix(decision);
        } else {
            mixer.mix(decision);
            decisionMixMenu();
        }
    }

    /* End of the mixing menu*/
    /* Menu for unmixing */

    public static void mainUnmixMenu(String messageStr, String pathToFile) {
        try {
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
