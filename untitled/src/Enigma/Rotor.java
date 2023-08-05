package Enigma;

import java.sql.SQLOutput;
import java.util.Arrays;

import static java.lang.Math.abs;
public class Rotor {
    protected String wiring = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    protected char notch='A';
    protected int ringSetting=1;

    protected String ALPHABET;

    public Rotor(String rotorType){
        switch(rotorType) {
            case "I" -> {
                this.wiring = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
                this.notch = 'Q';
            }
            case "II" -> {
                this.wiring = "AJDKSIRUXBLHWTMCQGZNPYFVOE";
                this.notch = 'E';
            }
            case "III" -> {
                this.wiring = "BDFHJLCPRTXVZNYEIWGAKMUSQO";
                this.notch = 'V';
            }
            case "IV" -> {
                this.wiring = "ESOVPZJAYQUIRHXLNFTGKDCMWB";
                this.notch = 'J';
            }
            case "V" -> {
                this.wiring = "VZBRGITYUPSDNHLXAWMJQOFECK";
                this.notch = 'Z';
            }
            default -> {
                this.wiring = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                this.notch= 'A';
            }
        }
        this.ALPHABET="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }
    public int forward(int signal){
        char letter = wiring.charAt(signal);
        return ALPHABET.indexOf(letter);
    }

    public int backward(int signal){
        char letter = ALPHABET.charAt(signal);
        return wiring.indexOf(letter);
    }

    public void show(){
        System.out.println(this.ALPHABET);
        System.out.println(this.wiring);
        System.out.println(this.notch);

        System.out.println(this.ringSetting);
        System.out.println();
    }

    // make backward inverseWiring
    // forward wiring
    public void spin(int n, boolean forward){
        for (int i=0; i < n; ++i){
            if (forward){
                ALPHABET = ALPHABET.substring(1) + ALPHABET.charAt(0);
                wiring = wiring.substring(1) +wiring.charAt(0);
            }
            else{
                ALPHABET = ALPHABET.charAt(25) + ALPHABET.substring(0, 25);
                wiring = wiring.charAt(25) + wiring.substring(0,25);
            }
        }
    }
    public void setRotorInitialPosition(char letter){
        spin("ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(letter), true);
    }
    public boolean isAtNotch(){
        return (ALPHABET.charAt(0) == notch);
    }
    public void setRing(int n){
        int n_notch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(notch);
        this.notch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(abs(n_notch - (n-1)) % 26);
    }

}
