import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Compression {
    public static Map<Character, String> buildTree(String text) {
        HashMap<Character, Integer> freqMap = new HashMap<>();

        // map to count frequency of each character
        for (char c : text.toCharArray()) {
            if (freqMap.containsKey(c)) {
                freqMap.put(c, freqMap.get(c) + 1);
            }
            else {
                freqMap.put(c, 1);
            }
        }

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new NodeComparator());
        for (Map.Entry<Character, Integer> e: freqMap.entrySet()) {
            priorityQueue.add(new Node(e.getKey(), e.getValue()));
        }

        while (priorityQueue.size() > 1) {
            Node x = priorityQueue.poll();
            Node y = priorityQueue.poll();

            Node newNode = new Node('\0', x.frequency + y.frequency);
            newNode.left = x;
            newNode.right = y;

            priorityQueue.add(newNode);
        }

        Node root = priorityQueue.poll();
        Map<Character, String> codes = new HashMap<>();
        buildCodes(root, "", codes);

        return codes;
    }

    public static void buildCodes(Node root, String code, Map<Character, String> codes) {
        if (root != null) {
            if (root.left == null && root.right == null) {
                codes.put(root.data, code);
            }

            buildCodes(root.left, code + "0", codes);
            buildCodes(root.right, code + "1", codes);
        }
    }

    public static void compress(FileReader file) throws IOException {
        BufferedReader reader = new BufferedReader(file);
        String originalText = reader.readLine();

        Map<Character, String> codes = buildTree(originalText);

        StringBuilder header = new StringBuilder();
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            header.append(entry.getKey()).append(entry.getValue()).append(',');
        }
        header.deleteCharAt(header.length() - 1);
        header.append('\n');

        StringBuilder compressedText = new StringBuilder(header.toString());
        for (char c : originalText.toCharArray()) {
            compressedText.append(codes.get(c));
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("compressedData.txt"));
        bufferedWriter.write(compressedText.toString());
        bufferedWriter.close();
    }
}
