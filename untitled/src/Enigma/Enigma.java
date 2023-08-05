package Enigma;

import java.util.ArrayList;

public class Enigma {
    Reflector reflector;
    Rotor leftRotor;
    Rotor middleRotor;
    Rotor rightRotor;
    Plugboard plugboard;
    String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public Enigma(String reflectorType, String leftRotorType, char leftRotorPosition, int leftRotorRing, String middleRotorType, char middleRotorPosition, int middleRotorRing, String rightRotorType, char rightRotorPosition, int rightRotorRing, ArrayList<String> plugboardConnections){
        this.reflector = new Reflector(reflectorType);

        this.leftRotor = new Rotor(leftRotorType);
        leftRotor.setRing(leftRotorRing);
        leftRotor.setRotorInitialPosition(leftRotorPosition);

        this.middleRotor = new Rotor(middleRotorType);
        middleRotor.setRing(middleRotorRing);
        middleRotor.setRotorInitialPosition(middleRotorPosition);

        this.rightRotor = new Rotor(rightRotorType);
        rightRotor.setRing(rightRotorRing);
        rightRotor.setRotorInitialPosition(rightRotorPosition);
        this.plugboard = new Plugboard(plugboardConnections);
    }
    public char enigmaMechanism(int signal){
        int c = this.plugboard.forward(signal);

        int c1 =  this.rightRotor.forward(c);

        int c2 = this.middleRotor.forward(c1);

        int c3 =  this.leftRotor.forward(c2);

        int c4 =  this.reflector.reflect(c3);

        int c5 = this.leftRotor.backward(c4);
        int c6 =  this.middleRotor.backward(c5);
        int c7 =  this.rightRotor.backward(c6);
        //System.out.println(ALPHABET.charAt(plugboard.backward(c7)));
        return ALPHABET.charAt(plugboard.backward(c7));
    }
    public char encipher(String letter){

        //System.out.println();
        if (middleRotor.isAtNotch()){
            leftRotor.spin(1, true);
            middleRotor.spin(1, true);
        }

        else if (rightRotor.isAtNotch()){
            middleRotor.spin(1,true);
        }

        rightRotor.spin(1, true);

        char signal = enigmaMechanism(this.ALPHABET.indexOf(letter));
        //System.out.println("LETRA " + letter);
        //System.out.println("LEFT POSITION: " + leftRotor.ALPHABET.charAt(0));
        //System.out.println("MIDDLE POSITION: " +middleRotor.ALPHABET.charAt(0));
        //System.out.println("RIGHT POSITION: " + rightRotor.ALPHABET.charAt(0));
        return signal;
    }

    public String encipher_text(String plain_text) {
        StringBuilder str = new StringBuilder();
        for (int j=0; j < plain_text.length(); ++j){
            str.append(encipher(String.valueOf(plain_text.charAt(j))));
        }

        return String.valueOf(str);

    }
}
