package Text;

public class ConvertText {
    public String plain_text;

    public ConvertText(String plain){
        this.plain_text = plain;
    }

    public String convert(){
        StringBuilder str = new StringBuilder();
        for (char c : plain_text.toCharArray()){
            if (c < 65) continue;
            else if (c >= 97 && c <=122) c -= 32;
            str.append(c);
        }
        return String.valueOf(str);
    }
}
