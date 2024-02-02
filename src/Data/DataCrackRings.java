package Data;

public class DataCrackRings implements Comparable<DataCrackRings> {
    private float bigram;
    private int[] bestRings;
    private char[] key;
    private String[] rotors;
    private String possiblePlainText;

    public DataCrackRings(float score, int[] bestRings, String[] rotors, char[] key, String possiblePlainText){
        this.bigram = score;
        this.bestRings = bestRings;
        this.rotors = rotors;
        this.key = key;
        this.possiblePlainText = possiblePlainText;
    }

    public String getPossiblePlainText() {
        return possiblePlainText;
    }

    public float getIOC() {
        return bigram;
    }

    public char[] getKey() {
        return key;
    }

    public String[] getRotors() {
        return rotors;
    }

    public int[] getBestRings() {
        return bestRings;
    }

    @Override
    public int compareTo(DataCrackRings other){
        return Float.compare(other.bigram, this.bigram);
    }
}
