package Enigma;

import java.util.ArrayList;

public class Plugboard{
    private String ALPHABET;
    private String strConnections;
    public Plugboard(ArrayList<String> plugboardConnections){
        this.ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.strConnections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (String connection : plugboardConnections){
            char A = connection.charAt(0);
            char B = connection.charAt(1);

            int posA = this.strConnections.indexOf(A);
            int posB = this.strConnections.indexOf(B);

            this.strConnections = this.strConnections.substring(0, posA) + B + this.strConnections.substring(posA+1);
            this.strConnections = this.strConnections.substring(0, posB) + A + this.strConnections.substring(posB+1);
        }
    }

    public int forward(int signal){
        char letter = this.ALPHABET.charAt(signal);
        return this.strConnections.indexOf(letter);
    }

    public int backward(int signal){
        char letter = this.strConnections.charAt(signal);
        return this.ALPHABET.indexOf(letter);
    }

}