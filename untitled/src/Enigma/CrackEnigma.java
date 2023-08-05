package Enigma;

import Data.DataCrackPlugboard;
import Data.DataCrackRings;
import Data.DataCrackRotors;
import Ngram.Trigram;

import java.lang.reflect.Array;
import java.util.*;

public class CrackEnigma {
    String cipher_text;
    ArrayList<String> plugboardConnections;

    protected String[] rotorsToCrack = new String[] {"I", "II", "III", "IV", "V"};

    public char[] ALPHABET = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    public CrackEnigma(String cipher_text) {
        this.cipher_text = cipher_text;
        this.plugboardConnections = new ArrayList<String>();

        Rotor I = new Rotor("I");
        I.setRing(1);
        I.setRotorInitialPosition('A');

        Rotor II = new Rotor("II");
        II.setRing(1);
        II.setRotorInitialPosition('A');

        Rotor III = new Rotor("III");
        III.setRing(1);
        III.setRotorInitialPosition('A');

        Rotor IV = new Rotor("IV");
        IV.setRing(1);
        IV.setRotorInitialPosition('A');

        Rotor V = new Rotor("V");
        V.setRing(1);
        V.setRotorInitialPosition('A');

        Reflector A = new Reflector("A");
        Reflector B = new Reflector("B");
        Reflector C = new Reflector("C");
    }

    public int[] calculateFreqText(String text) {
        int[] freq_cipher_text = new int[26];
        char[] textchar = text.toCharArray();
        for (int i=0; i < text.length(); ++i){
            char c = textchar[i];
            freq_cipher_text[c-65]++;
        }
        return freq_cipher_text;
    }

    public float indexOfCoincidence(String text){
        int sum = 0;
        int [] freqCipherText = calculateFreqText(text);
        for (int ni : freqCipherText){
            sum += (ni*(ni-1));
        }
        return (float) sum / (text.length() * (text.length()-1));
    }

    public List<DataCrackRotors> crackRotors() {
        String[] bestRotors = new String[0];
        int totalRotors = 60;
        ArrayList<DataCrackRotors> results = new ArrayList<>();
        System.out.println("Searching for Best Enigma.Rotor Configuration...");
        int i = 0;
        for (String leftRotor : rotorsToCrack) {
            for (String middleRotor : rotorsToCrack) {
                if (leftRotor.equals(middleRotor)) continue;
                for (String rightRotor : rotorsToCrack) {
                    if (leftRotor.equals(rightRotor) || middleRotor.equals(rightRotor)) continue;
                    int a=totalRotors-i;
                    System.out.println("Remaining: " + a);
                    i++;

                    float bestKey = -50f;
                    float IOC_key = 0f;
                    String[] rotors = new String[]{leftRotor, middleRotor, rightRotor};
                    char[] bestKeys = new char[0];
                    String possiblePlainText = "";
                    for (char leftKey : ALPHABET) {
                        for (char middleKey : ALPHABET) {
                            for (char rightKey : ALPHABET) {
                                Enigma enigma = new Enigma("B", leftRotor, leftKey, 1, middleRotor, middleKey, 1, rightRotor, rightKey, 1, this.plugboardConnections);
                                String plain_text = enigma.encipher_text(cipher_text);
                                IOC_key = indexOfCoincidence(plain_text);
                                if (IOC_key > bestKey) {
                                    bestKey = IOC_key;
                                    bestKeys = new char[]{leftKey, middleKey, rightKey};
                                    possiblePlainText = plain_text;
                                }
                            }
                        }
                    }
                    results.add(new DataCrackRotors(bestKey, bestKeys, rotors, possiblePlainText));
                }
            }
        }
        System.out.println("Done!");
        Collections.sort(results);
        int n = Math.min(results.size(), 3);
        List<DataCrackRotors> top3 = results.subList(0,n);
        int k = 1;
        for (DataCrackRotors data : top3){
            System.out.println(k + "º PLACE");
            k++;
            System.out.println("IOC: " + data.getIOC());
            System.out.println("BEST KEYS: " + Arrays.toString(data.getBestKeys()));
            System.out.println("ROTORS: " + Arrays.toString(data.getRotors()));
            System.out.println("POSSIBLE PLAIN TEXT: " + data.getPossiblePlainText());
            System.out.println();
        }
        return top3;
    }

    public List<DataCrackRings> crackRings(List<DataCrackRotors> results){
        ArrayList<DataCrackRings> ringsList = new ArrayList<>();
        System.out.println("Searching for Best Ring Settings");
        int l = 0;
        for (DataCrackRotors data : results){
            String[] rotors = data.getRotors();
            char[] keys = data.getBestKeys();
            String possiblePlainText = "";
            float bestScore = -1e30f;
            int bestRightRing=1;
            for (int i = 1; i <= 26;++i){
                Enigma enigma = new Enigma("B", rotors[0], keys[0], 1, rotors[1], keys[1], 1, rotors[2], keys[2], i, this.plugboardConnections);
                String plain_text = enigma.encipher_text(cipher_text);

                float IOC = indexOfCoincidence(plain_text);

                if (IOC > bestScore){
                    bestRightRing = i;
                    bestScore=IOC;
                }
            }

            bestScore = -1e30f;
            int bestMiddleRing = 1;
            for (int i=1; i <=26; ++i){
                Enigma enigma = new Enigma("B", rotors[0], keys[0], 1, rotors[1], keys[1], i, rotors[2], keys[2], bestRightRing, this.plugboardConnections);
                String plain_text = enigma.encipher_text(cipher_text);
                float IOC = indexOfCoincidence(plain_text);

                if (IOC > bestScore){
                    bestMiddleRing = i;
                    bestScore=IOC;
                    possiblePlainText=plain_text;
                }
                l++;
            }

            ringsList.add(new DataCrackRings(bestScore, new int[]{1, bestMiddleRing, bestRightRing}, rotors, keys, possiblePlainText));

        }

        System.out.println("Done!");
        Collections.sort(ringsList);
        int n = Math.min(ringsList.size(), 5);
        List<DataCrackRings> top5 = ringsList.subList(0,n);
        int k = 1;
        for (DataCrackRings data : top5){
            System.out.println(k + "º PLACE");
            k++;
            System.out.println("ROTORS: " + Arrays.toString(data.getRotors()));
            System.out.println("KEY: " + Arrays.toString(data.getKey()));
            System.out.println("BEST RINGS: " + Arrays.toString(data.getBestRings()));
            System.out.println("SCORE: " + data.getIOC());
            System.out.println("POSSIBLE PLAIN TEXT: " + data.getPossiblePlainText());
            System.out.println();

        }
        return ringsList;
    }

    public ArrayList<String> findBestPlug(List<DataCrackRings> results, ArrayList<String> connections){
        char[] availableChar = ALPHABET;
        DataCrackRings bestSetting =results.get(0);
        String[] rotors = bestSetting.getRotors();
        char[] key = bestSetting.getKey();
        int[] rings = bestSetting.getBestRings();
        Trigram trigram = new Trigram();

        Enigma enigma = new Enigma("B",
                rotors[0], key[0], rings[0],
                rotors[1], key[1], rings[1],
                rotors[2], key[2], rings[2], this.plugboardConnections);

        String plain = enigma.encipher_text(cipher_text);
        float scoreWithoutPlugs = trigram.score(plain.toCharArray());
        String bestPlug = "";
        ArrayList<String> tempConnection = new ArrayList<>();

        for (char firstLetter : availableChar){
            for (char secondLetter : availableChar){
                if (firstLetter >= secondLetter) continue;
                if (connections.contains(""+firstLetter+secondLetter)) continue;

                tempConnection.add(""+firstLetter+secondLetter);
                Enigma e1 = new Enigma("B",
                        rotors[0], key[0], rings[0],
                        rotors[1], key[1], rings[1],
                        rotors[2], key[2], rings[2], tempConnection);

                String possiblePlainText = e1.encipher_text(cipher_text);
                tempConnection.remove(0);
                float newScore = trigram.score(possiblePlainText.toCharArray());
                if (newScore > scoreWithoutPlugs){
                    scoreWithoutPlugs=newScore;
                    bestPlug = ""+firstLetter+secondLetter;
                }
            }
        }
        connections.add(bestPlug);
        return connections;
    }

    public void crackPlugboaard(List<DataCrackRings> results, int numConnectors){
        DataCrackRings bestSetting =results.get(0);
        String[] rotors = bestSetting.getRotors();
        char[] key = bestSetting.getKey();
        int[] rings = bestSetting.getBestRings();

        ArrayList<String> connections = new ArrayList<>();
        for (int i=0; i< numConnectors; ++i){
            connections = findBestPlug(results, connections);
        }


        Enigma enigma = new Enigma("B",
                rotors[0], key[0], rings[0],
                rotors[1], key[1], rings[1],
                rotors[2], key[2], rings[2], connections);

        System.out.println("BEST PLUGBOARD SETTING: " + connections);
        String possiblePlainText = enigma.encipher_text(cipher_text);
        System.out.println("POSSIBLE PLAIN TEXT: " + possiblePlainText);

    }

    public ArrayList<DataCrackPlugboard> crackPlugboard(List<DataCrackRings> results, int numConnectors) {
        ArrayList<DataCrackPlugboard> finalConnectors = new ArrayList<>();
        char[] availableChar = ALPHABET;
        for (DataCrackRings data : results) {
            String[] rotors = data.getRotors();
            char[] key = data.getKey();
            int[] rings = data.getBestRings();
            float bestPlugIOC = -1f;
            String bestPlug = null;
            String plain_text = null;
            ArrayList<String> temporaryConnectors = new ArrayList<>();
            for (int i = 0; i < numConnectors; ++i) {
                for (char firstLetter : availableChar) {
                    for (char secondLetter : availableChar) {
                        if ((firstLetter - 65) >= (secondLetter - 65)) continue;

                        String connection = "" + firstLetter + secondLetter;
                        System.out.println(connection);
                        temporaryConnectors.add(connection);
                        Enigma enigma = new Enigma("B", rotors[0], key[0], rings[0], rotors[1], key[1], rings[1], rotors[2], key[2], rings[2], temporaryConnectors);
                        plain_text = enigma.encipher_text(cipher_text);
                        float IOC = indexOfCoincidence(plain_text);

                        if (IOC > bestPlugIOC) {
                            bestPlugIOC = IOC;
                            bestPlug = connection;
                            temporaryConnectors.subList(0, temporaryConnectors.size()-1).clear();

                            char[] updatedAvailableChar = new char[availableChar.length - 2];
                            int index = 0;
                            for (char c : availableChar) {
                                if (c != firstLetter && c != secondLetter) {
                                    updatedAvailableChar[index] = c;
                                    index++;
                                }
                            }
                            availableChar = updatedAvailableChar;
                        }
                    }
                }

                finalConnectors.add(new DataCrackPlugboard(bestPlugIOC, rings, rotors, key, plain_text, bestPlug));
            }
        }
        for (DataCrackPlugboard data : finalConnectors) {
            System.out.println();
            System.out.println("ROTORS: " + Arrays.toString(data.getRotors()));
            System.out.println("KEY: " + Arrays.toString(data.getKey()));
            System.out.println("BEST RINGS: " + Arrays.toString(data.getBestRings()));
            System.out.println("IOC: " + data.getIOC());
            System.out.println("POSSIBLE PLAIN TEXT: " + data.getPossiblePlainText());
            System.out.println();;
            System.out.println();

        }
        return finalConnectors;
    }
}


