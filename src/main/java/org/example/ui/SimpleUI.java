package org.example.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SimpleUI {
    private JFrame frame;
    private JButton embedButton;
    private JButton extractButton;
    private JButton browseButton;
    private JTextArea messageTextArea;
    private JLabel imageLabel;
    private File selectedImageFile;

    public SimpleUI() {
        frame = new JFrame("Steganography Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        embedButton = new JButton("Embed Message");
        extractButton = new JButton("Extract Message");
        browseButton = new JButton("Browse Image");
        messageTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(messageTextArea);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        panel.add(embedButton, BorderLayout.NORTH);
        panel.add(extractButton, BorderLayout.SOUTH);
        panel.add(browseButton, BorderLayout.WEST);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(imageLabel, BorderLayout.EAST);

        embedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement embedding logic here
                // Use selectedImageFile and messageTextArea.getText()
                // Display a message or update the UI as needed
            }
        });

        extractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement extraction logic here
                // Use selectedImageFile
                // Display the extracted message or update the UI as needed
            }
        });

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedImageFile = fileChooser.getSelectedFile();
                    // Display the selected image
                    ImageIcon imageIcon = new ImageIcon(selectedImageFile.getPath());
                    imageLabel.setIcon(imageIcon);
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleUI();
            }
        });
    }
}
