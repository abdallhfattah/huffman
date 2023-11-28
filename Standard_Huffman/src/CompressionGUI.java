import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CompressionGUI extends JFrame {
    private JTextField filePathField;

    public CompressionGUI() {
        setTitle("Compression GUI");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Panel for file selection
        JPanel filePanel = new JPanel();
        JLabel filePathLabel = new JLabel("Selected File:");
        filePathField = new JTextField(20);
        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(new BrowseButtonListener());
        filePanel.add(filePathLabel);
        filePanel.add(filePathField);
        filePanel.add(browseButton);

        // Panel for compression and decompression buttons
        JPanel actionPanel = new JPanel();
        JButton compressButton = new JButton("Compress");
        JButton decompressButton = new JButton("Decompress");
        compressButton.addActionListener(new CompressButtonListener());
        decompressButton.addActionListener(new DecompressButtonListener());
        actionPanel.add(compressButton);
        actionPanel.add(decompressButton);

        // Add panels to the main panel
        mainPanel.add(filePanel, BorderLayout.NORTH);
        mainPanel.add(actionPanel, BorderLayout.CENTER);

        // Set the main panel as the content pane
        setContentPane(mainPanel);
    }

    private class BrowseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(CompressionGUI.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePathField.setText(selectedFile.getAbsolutePath());
            }
        }
    }

    private class CompressButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath = filePathField.getText();
            if (!filePath.isEmpty()) {
                try {
                    FileReader fileReader = new FileReader(filePath);
                    Compression.compress(fileReader);
                    JOptionPane.showMessageDialog(CompressionGUI.this, "File compressed successfully.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(CompressionGUI.this, "Error compressing file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(CompressionGUI.this, "Please select a file to compress.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DecompressButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath = filePathField.getText();
            if (!filePath.isEmpty()) {
                try {
                    FileReader fileReader = new FileReader(filePath);
                    Decompress.deCompress(fileReader);
                    JOptionPane.showMessageDialog(CompressionGUI.this, "File decompressed successfully.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(CompressionGUI.this, "Error decompressing file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(CompressionGUI.this, "Please select a file to decompress.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CompressionGUI compressionGUI = new CompressionGUI();
            compressionGUI.setVisible(true);
        });
    }
}

