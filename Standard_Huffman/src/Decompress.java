import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Decompress {
    public static void deCompress(FileReader file) throws IOException {
        BufferedReader reader = new BufferedReader(file);
        String header = reader.readLine();
        String compressedText = reader.readLine();

        Map<Character, String> codesTable = new HashMap<>();
        String[] codes = header.split(",");
        for (String code : codes) {
            codesTable.put(code.charAt(0), code.substring(1));
        }

        StringBuilder decompressedText = new StringBuilder();
        String s;
        int i = 0;
        while (i < compressedText.length()) {
            for (Map.Entry<Character, String> entry : codesTable.entrySet()) {
                String code = entry.getValue();
                if (compressedText.startsWith(code, i)) {
                    decompressedText.append(entry.getKey());
                    i += code.length();
                    break;
                }
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("decompressedText.txt"));
        writer.write(decompressedText.toString());
        writer.close();
    }
}
