package Enigma;

public class Reflector {
    String wiring;
    String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public Reflector(String reflectorType) {
        switch (reflectorType) {
            case "A" -> this.wiring = "EJMZALYXVBWFCRQUONTSPIKHGD";
            case "B" -> this.wiring = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
            case "C" -> this.wiring = "FVPJIAOYEDRZXWGCTKUQSBNMHL";
            default -> System.out.println("This Enigma.Reflector does not exist! Please insert a valid character (A, B or C)");
        }
    }

    public int reflect(int signal){
        String letter = String.valueOf(ALPHABET.charAt(signal));
        return wiring.indexOf(letter);
    }

}
