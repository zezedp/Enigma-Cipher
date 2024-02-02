import Data.DataCrackRings;
import Data.DataCrackRotors;
import Enigma.Enigma;
import Text.ConvertText;
import Enigma.CrackEnigma;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // The convert() method takes any string and returns only the characters aA-zZ;
        //Text.ConvertText plain = new Text.ConvertText("To summarize, the convert() method will take the input text, keep only the uppercase English letters, and return the resulting string. All other characters, such as digits, lowercase letters, punctuation marks, etc., will be ignored.");
        ConvertText plain = new ConvertText("Polish and British solutions to encrypted German Enigma.Enigma traffic relied on sophisticated known plaintext attacks. The rotor wiring was known through brilliant cryptanalysis by the Polish mathematician Marian Rejewski 3 and later through capture of naval Enigma.Enigma rotors 4. ");
        String text = plain.convert();
        System.out.println(text);
        ArrayList<String> plugboardConnections = new ArrayList<>();
        plugboardConnections.add("DA");
        plugboardConnections.add("EH");
        //plugboardConnections.add("ZB");
        //plugboardConnections.add("YC");
        //plugboardConnections.add("PU");

        Enigma enigma = new Enigma("B",
                "III", 'K', 1,
                "II", 'A', 1,
                "IV", 'A', 1,
                plugboardConnections);

        String cipher_text = enigma.encipher_text(text);
        System.out.println(cipher_text);


        CrackEnigma crack = new CrackEnigma(cipher_text);

        //System.out.println(crack.indexOfCoincidence("ORTONZYEQZGRBDSNVTDFINUJSQPRGLXAERBPNQLZZYHMUDCECIHPQMLCMWOYNQUNOZAMZBUGSXGWNYYEYZEFJYOYAZRJEEWTTDEFZBLEFWAADIVICWQBRTSZBBNUJRXZPQCLCNAGCQJDPNHYJKJPFVPYGRAXRCAWHAFJ"));
        List<DataCrackRotors> possibleRotorsSettings= crack.crackRotors();
        List<DataCrackRings> possibleRingSettings = crack.crackRings(possibleRotorsSettings);
        crack.crackPlugboaard(possibleRingSettings, 2);


        //System.out.println(crack.indexOfCoincidence("WDTYRKREWIATOVFNABXWVLRJYCKACLXVRXKMXUPNBRBJGSOKTSJUNIVIHPODXFOUVILQOOZGXOUOHPTAYHHWKSQYYWHLWOIMVHKFHVUCXPPOAQUNYFRRCQUQILDCIYBBMHTLKKAVEPRXRXPLKIFCMUYOWREJBMLXCBZU"));

        //System.out.println(text);

        //Enigma.Rotor I = new Enigma.Rotor("I", 'D', 0);
        //I.show();

        //System.out.println("II");
        //Enigma.Rotor II = new Enigma.Rotor("II", 'O', 0);
        //II.show();

        //System.out.println("iii");

        //Enigma.Rotor III = new Enigma.Rotor("III", 'G', 1);
        //III.show();

    }
}