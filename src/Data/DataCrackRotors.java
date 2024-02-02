package Data;

public class DataCrackRotors implements Comparable<DataCrackRotors> {
    private float IOC;
    private char[] bestKeys;
    private String[] rotors;
    private String possiblePlainText;

    public DataCrackRotors(float ioc, char[] bestKeys, String[] rotors, String possiblePlainText){
        this.IOC = ioc;
        this.bestKeys = bestKeys;
        this.rotors = rotors;
        this.possiblePlainText = possiblePlainText;
    }

    public float getIOC() {
        return IOC;
    }

    public char[] getBestKeys() {
        return bestKeys;
    }

    public String[] getRotors() {
        return rotors;
    }

    public String getPossiblePlainText() {
        return possiblePlainText;
    }

    @Override
    public int compareTo(DataCrackRotors other){
        return Float.compare(other.IOC, this.IOC);
    }
}
