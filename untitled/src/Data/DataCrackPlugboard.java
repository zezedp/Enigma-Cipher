package Data;

public class DataCrackPlugboard implements Comparable<DataCrackPlugboard> {
    private float IOC;
    private int[] rings;
    private char[] key;
    private String[] rotors;
    private String connector;
    private String possiblePlainText;

    public DataCrackPlugboard(float ioc, int[] rings, String[] rotors, char[] key, String possiblePlainText, String connector){
        this.IOC = ioc;
        this.rings = rings;
        this.rotors = rotors;
        this.key = key;
        this.possiblePlainText = possiblePlainText;
        this.connector = connector;
    }

    public String getConnector() {
        return connector;
    }

    public String getPossiblePlainText() {
        return possiblePlainText;
    }

    public float getIOC() {
        return IOC;
    }

    public char[] getKey() {
        return key;
    }

    public String[] getRotors() {
        return rotors;
    }

    public int[] getBestRings() {
        return rings;
    }

    @Override
    public int compareTo(DataCrackPlugboard other){
        return Float.compare(other.IOC, this.IOC);
    }
}
