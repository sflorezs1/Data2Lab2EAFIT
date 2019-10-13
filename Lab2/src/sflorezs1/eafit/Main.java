package sflorezs1.eafit;

import sflorezs1.eafit.IO.InputOutput;
import sflorezs1.eafit.Mixer.Mixer;
import sflorezs1.eafit.UnMixer.UnMixer;

import javax.management.remote.rmi.RMIJRMPServerImpl;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Mixer mixer = new Mixer("Meet me after class in EOSaaaa");
        System.out.println(mixer.getMessage().getList());
        /*
        mixer.mix("b ?S 1");
        mixer.mix("d M");
        mixer.mix("r 20 26");
        mixer.mix("f S l");
        mixer.mix("r 21 26");
        mixer.mix("b 0=c 15");
        mixer.mix("f c a");*/
        mixer.mix("z");
        System.out.println(mixer.getMessage().getList());
        System.out.println(mixer.getMessage().getOperations());

        try {
            InputOutput.writeMessage("Out.txt" , mixer.getMessage());
        } catch (IOException e) {
            System.err.println(e);
        }

        try {
            UnMixer unMixer = new UnMixer(InputOutput.readFile("Out.txt"));
            unMixer.unMix();
            System.out.println(unMixer.getMessage().getList());
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
