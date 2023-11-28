import java.util.Comparator;

public class Node {
    int frequency;
    char data;
    Node left, right;

    public Node(char d, int f) {
        frequency = f;
        data = d;
        left = right = null;
    }
}

