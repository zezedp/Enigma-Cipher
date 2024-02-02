package Ngram;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

public class Trigram {
    private float[] trigram;

    private static int triIndex(int a, int b, int c) {
        return (a << 10) | (b << 5) | c;
    }
    protected final float epsilon = 3e-10f;

    public Trigram() {
        // Bigrams
        this.trigram = new float[26426];
        Arrays.fill(this.trigram, (float)Math.log10(epsilon));
        try (final InputStream is = Trigram.class.getResourceAsStream("trigramModel");
             final Reader r = new InputStreamReader(is, StandardCharsets.UTF_8);
             final BufferedReader br = new BufferedReader(r);
             final Stream<String> lines = br.lines()) {
            lines.map(line -> line.split(" "))
                    .forEach(s -> {
                        String key = s[0];
                        int i = triIndex(key.charAt(0) - 65, key.charAt(1) - 65, key.charAt(2) -65);
                        this.trigram[i] = Float.parseFloat(s[1]);
                    });
        } catch (IOException e) {
            this.trigram = null;
        }
    }

    public float score(char[] text){
        float fitness= 0;
        int current = 0;
        int next1 = text[0] - 65;
        int next2 = text[1] - 65;
        for (int i=2; i < text.length; ++i){
            current = next1;
            next1 = next2;
            next2 = text[i]-65;
            fitness += this.trigram[triIndex(current, next1,next2)];
        }
        return fitness;
    }

}
