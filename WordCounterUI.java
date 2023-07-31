import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WordCounterUI extends JFrame {
    private JTextArea textArea;
    private JButton countButton;
    private JLabel resultLabel;
    private JRadioButton textRadioBtn;
    private JRadioButton fileRadioBtn;
    private JButton uploadButton;

    public WordCounterUI() {
        setTitle("Word Counter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        textArea = new JTextArea();
        countButton = new JButton("Count Words");
        resultLabel = new JLabel();
        textRadioBtn = new JRadioButton("Enter Text");
        fileRadioBtn = new JRadioButton("Upload File");
        uploadButton = new JButton("Upload");

        // Create button group for radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(textRadioBtn);
        buttonGroup.add(fileRadioBtn);

        // Set layout
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Create panel for radio buttons and upload button
        JPanel radioPanel = new JPanel();
        radioPanel.add(textRadioBtn);
        radioPanel.add(fileRadioBtn);
        radioPanel.add(uploadButton);
        add(radioPanel, BorderLayout.NORTH);

        // Add event listener to countButton
        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();
                int wordCount = countWords(text);
                resultLabel.setText("Total words: " + wordCount);
            }
        });

        // Add event listener to uploadButton
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(WordCounterUI.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        String text = new String(Files.readAllBytes(selectedFile.toPath()));
                        textArea.setText(text);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(WordCounterUI.this, "Error reading file.", "File Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Add event listener to radio buttons
        textRadioBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadButton.setEnabled(false);
                textArea.setEditable(true);
                textArea.setText("");
            }
        });

        fileRadioBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadButton.setEnabled(true);
                textArea.setEditable(false);
                textArea.setText("");
            }
        });

        // Add countButton and resultLabel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(countButton);
        add(buttonPanel, BorderLayout.SOUTH);
        add(resultLabel, BorderLayout.WEST);
    }

    private int countWords(String text) {
        if (text.isEmpty()) {
            return 0;
        }
        String[] words = text.split("\\s+");
        return words.length;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                WordCounterUI wordCounterUI = new WordCounterUI();
                wordCounterUI.setVisible(true);
            }
        });
    }
}