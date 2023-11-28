import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileReader file = new FileReader("original.txt");
        Compression.compress(file);

        FileReader compressedFile = new FileReader("compressed.txt");
        Decompress.deCompress(compressedFile);
    }
}